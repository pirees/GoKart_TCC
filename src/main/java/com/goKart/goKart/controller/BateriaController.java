package com.goKart.goKart.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.goKart.goKart.model.*;
import com.goKart.goKart.service.EnviaEmailService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.goKart.goKart.excel.BateriaExcel;
import com.goKart.goKart.repository.BateriaRepository;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.repository.ReservaRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.goKart.goKart.model.StatusUsuario.PENDENTE;

@Controller
public class BateriaController {

    private BateriaRepository bateriaRepository;

    private KartodromoRepository kartodromoRepository;

    private ReservaRepository reservaRepository;


    public BateriaController(BateriaRepository bateriaRepository, KartodromoRepository kartodromoRepository,
                             ReservaRepository reservaRepository) {
        super();
        this.bateriaRepository = bateriaRepository;
        this.kartodromoRepository = kartodromoRepository;
        this.reservaRepository = reservaRepository;
    }

    //LISTA TODAS AS BATERIAS DISPONÍVEIS
    @GetMapping("piloto/menuPiloto")
    public ModelAndView listarBaterias(ModelAndView model, Pageable pageable, Piloto piloto) {
        List<Bateria> baterias = bateriaRepository.findAllByOrderByDataAsc();
        model.addObject("bateria", baterias);

        return model;
    }

    @PostMapping("***/pesquisarKartodromo")
    public ModelAndView pesquisarPorNomeKartodromo(@RequestParam(value = "nomepesquisa") String nomepesquisa, LocalDate datapesquisa){
        ModelAndView modelAndView = new ModelAndView("piloto/menuPiloto");

        if(!nomepesquisa.isEmpty() && datapesquisa == null){
            List<Bateria> baterias = bateriaRepository.findByKartodromoNome(nomepesquisa);

            modelAndView.addObject("bateria", baterias);

            return modelAndView;
        }
        List<Bateria> baterias = bateriaRepository.findByKartodromoNomeData(nomepesquisa,datapesquisa);

        if(baterias.isEmpty()){
            ModelAndView model = new ModelAndView("piloto/menuPilotoSemBusca");
            return model;
        }

        modelAndView.addObject("bateria", baterias);

        return modelAndView;
    }

    //FAZ O GET DA BATERIA SELECIONADA NA PAGINA DO MENU PILOTO
    @GetMapping("piloto/confirmarReserva/{id}")
    public String listarBaterias(@PathVariable("id") Long id, String email, Model model) {

        Bateria bateria = bateriaRepository.getById(id);

        email = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Reserva> reserva = reservaRepository.findPilotoByReserva(id);

        for (Reserva reservas : reserva) {
            if (reservas.getPiloto().getEmail().equals(email) && reservas.getStatus().equals(StatusPagamento.CONFIRMADO)) {

                model.addAttribute("bateria", bateria);
                model.addAttribute("reserva", reserva);

                return "piloto/confirmarReservaPilotoPagoCancelar";

            } else if(reservas.getBateria().getVagasConfirmadas().equals(reservas.getBateria().getNrMaxPiloto())){

                model.addAttribute("bateria", bateria);
                model.addAttribute("reserva", reserva);

                return "piloto/confirmarReservaBateriaCheia";
            }
        }

        model.addAttribute("bateria", bateria);
        model.addAttribute("reserva", reserva);

        return "piloto/confirmarReserva";
    }

    @GetMapping("kartodromo/cadastroBateria")
    public String formulario(Bateria bateria) {

        return "kartodromo/cadastroBateria";
    }

    @GetMapping("kartodromo/cadastroBateriaExcel")
    public String formularioExcel(Bateria bateria) {

        return "kartodromo/cadastroBateriaExcel";
    }

    @PostMapping("kartodromo/cadastroBateria")
    public String salvarBateria(@Valid Bateria bateria, BindingResult resultado, RedirectAttributes redirectAttributes) {

        boolean isFlag = verificaBateriaExiste(bateria);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kartodromo kartodromo = kartodromoRepository.findByEmail(email);

        if (resultado.hasErrors()) {
            return "kartodromo/cadastroBateria";
        }

        bateria.setKartodromo(kartodromo);
        if(!isFlag){
            redirectAttributes.addFlashAttribute("errormessage", "Já existe bateria cadastrada no mesmo dia ou horário.");
            return "redirect:/kartodromo/cadastroBateria";
        }else{
            bateriaRepository.save(bateria);
            redirectAttributes.addFlashAttribute("sucessmessage", "Bateria salva com sucesso");
        }
        return "redirect:/kartodromo/menuKartodromo";
    }

    public Boolean verificaBateriaExiste(Bateria bateria){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kartodromo kartodromo = kartodromoRepository.findByEmail(email);

        bateria.setKartodromo(kartodromo);

        List<Bateria> bateriaList = bateriaRepository.findAll();

        for(Bateria baterias: bateriaList){
            if(baterias.getData().isEqual(bateria.getData()) && baterias.getKartodromo().getId().equals(bateria.getKartodromo().getId()) && !baterias.getId().equals(bateria.getId()) && baterias.getTracado().equals(bateria.getTracado())){
                if(baterias.getHoraBateria().equals(bateria.getHoraBateria()) && baterias.getHoraBateria().getMinute() == bateria.getHoraBateria().getMinute()){
                    return false;
                }
            }
        }

        return true;
    }

    //LISTA TODAS AS BATERIAS DISPONÍVEIS
    @GetMapping("kartodromo/listaBaterias")
    public String listarBateriasKartodromo(Model model, Bateria bateria) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kartodromo kartodromo = kartodromoRepository.findByEmail(email);

        bateria.setKartodromo(kartodromo);

        List<Bateria> baterias = bateriaRepository.findByKartodromoId(bateria.getKartodromo().getId());

        model.addAttribute("baterias", baterias);

        return "kartodromo/listaBaterias";
    }

    @GetMapping("kartodromo/listaBaterias/exports/csv")
    public void listarBateriasKartodromoExport(Bateria bateria, HttpServletResponse response) throws IOException {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kartodromo kartodromo = kartodromoRepository.findByEmail(email);

        bateria.setKartodromo(kartodromo);

        List<Bateria> baterias = bateriaRepository.findByKartodromoId(bateria.getKartodromo().getId());

        BateriaExcel bateriaExcel = new BateriaExcel(baterias);

        bateriaExcel.export(response);

    }

    //LISTA TODAS AS BATERIAS DISPONÍVEIS
    @GetMapping("kartodromo/menuKartodromo")
    public ModelAndView listarMenuKartodromo(ModelAndView model, Bateria baterias) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kartodromo kartodromo = kartodromoRepository.findByEmail(email);

        baterias.setKartodromo(kartodromo);

        List<Bateria> bateria = bateriaRepository.findByDateKartodromoId(baterias.getKartodromo().getId());

        if(kartodromo.getStatusUsuario().equals(PENDENTE)){
            ModelAndView modelAndView = new ModelAndView("/pendenciaCadastro");
            return modelAndView;
        }

        model.addObject("bateria", bateria);
        return model;

    }

    @PostMapping("***/pesquisarMenuKartodromo")
    public ModelAndView pesquisarPorNomeMenuKartodromo(@RequestParam(value = "datapesquisa")LocalDate datapesquisa, Long id, Bateria baterias, String email){

        ModelAndView modelAndView = new ModelAndView("kartodromo/menuKartodromo");

        email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kartodromo kartodromo = kartodromoRepository.findByEmail(email);

        baterias.setKartodromo(kartodromo);

        List<Bateria> bateria = bateriaRepository.findByKartodromoNomeMenuKartodromo(baterias.getKartodromo().getId(), datapesquisa);

        if(bateria.isEmpty()){
            ModelAndView model = new ModelAndView("kartodromo/menuKartodromoSemBusca");
            return model;
        }

        modelAndView.addObject("bateria", bateria);

        return modelAndView;
    }

    @GetMapping("kartodromo/visualizarBateria/{id}")
    public String visualizarBateriaKartodromo(@PathVariable("id") Long id, Model model) {

        Bateria bateria = bateriaRepository.getById(id);

        List<Reserva> reserva = reservaRepository.findPilotoByReserva(id);

        for (Reserva reservas : reserva) {

            if (reservas.getPiloto() != null) {

                model.addAttribute("bateria", bateria);
                model.addAttribute("reserva", reserva);

                return "kartodromo/visualizarBateriaComReserva";
            }
        }

        model.addAttribute("bateria", bateria);
        model.addAttribute("reserva", reserva);

        return "kartodromo/visualizarBateria";
    }

    @PostMapping("kartodromo/visualizarBateria/{id}")
    public String atualizarBateria(@PathVariable("id") Long id, @Valid Bateria bateria, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Boolean isFlag = verificaBateriaExiste(bateria);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kartodromo kartodromo = kartodromoRepository.findByEmail(email);

        String resultado = bateria.getValorBateria().toString();
        List<Bateria> bateriaList = bateriaRepository.findAll();

        for (Bateria baterias : bateriaList) {
            if(!isFlag){
                redirectAttributes.addFlashAttribute("errormessage", "Já existe bateria cadastrada no mesmo dia ou horário.");
                return "redirect:/kartodromo/visualizarBateria/{id}";
            }else{
                bateria.setKartodromo(kartodromo);
                bateriaRepository.save(bateria);
            }
        }
        if (bindingResult.hasErrors()) {
            bateria.setId(id);
            return "kartodromo/visualizarBateria/{id}";
        }


        return "redirect:/kartodromo/menuKartodromo";
    }
    @GetMapping("kartodromo/visualizarBateria/apagar/{id}")
    public ModelAndView delete(@PathVariable("id") Long id, Bateria bateria) {
        bateria = bateriaRepository.getById(id);
        bateriaRepository.delete(bateria);
        return new ModelAndView("redirect:/kartodromo/menuKartodromo");
    }

    /*@PostMapping("/import/csv")
    public String cadastrarBateriaCSV(@ModelAttribute Bateria bateria, RedirectAttributes redirectAttributes) {
        boolean isFlag = salvarArquivo(bateria.getFile());

        if (!isFlag){
            redirectAttributes.addFlashAttribute("sucessmessage", "Bateria(s) salva(s) com sucesso");
        }else{
            redirectAttributes.addFlashAttribute("errormessage", "File not upload Successfuly");
        }
        return "redirect:/kartodromo/cadastroBateriaExcel";
    }*/

    public boolean salvarArquivo(MultipartFile file, RedirectAttributes redirectAttributes){
        boolean isFlag = false;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(extension.equalsIgnoreCase("csv")){
            isFlag = Boolean.parseBoolean(lerDataDeCSV(file, redirectAttributes));
        }
        return true;
    }
    @PostMapping("/import/csv")
    public String lerDataDeCSV(MultipartFile file, RedirectAttributes redirectAttributes){
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).build();
            List<Bateria> baterias = new ArrayList<>();
            String[] campos = null;

            while ((campos = csvReader.readNext()) != null){
                Bateria bateria = new Bateria();
                String email = SecurityContextHolder.getContext().getAuthentication().getName();
                Kartodromo kartodromo = kartodromoRepository.findByEmail(email);

                DateTimeFormatter  formatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(campos[0], formatter);

                BigDecimal valorBateria = new BigDecimal(campos[4]);
                Integer nrMaxPiloto = Integer.valueOf(campos[5]);

                bateria.setData(date);
                bateria.setHoraBateria(LocalTime.parse(campos[1]));
                bateria.setDuracaoBateria(campos[2]);
                bateria.setTracado(campos[3]);
                bateria.setValorBateria(valorBateria);
                bateria.setNrMaxPiloto(nrMaxPiloto);
                bateria.setKartodromo(kartodromo);

                baterias.add(bateria);

                boolean isFlag = verificaBateriaExiste(bateria);

                if (isFlag){
                    redirectAttributes.addFlashAttribute("sucessmessage", "Bateria(s) salva(s) com sucesso");
                    bateriaRepository.save(bateria);
                }else{
                    redirectAttributes.addFlashAttribute("errormessage", "Consulte a página de relatório pois a importação teve erro em alguma bateria.");
                }
            }
        } catch (IOException | CsvValidationException e) {
            return "redirect:/kartodromo/cadastroBateriaExcel";
        }
        return "redirect:/kartodromo/cadastroBateriaExcel";
    }
}
