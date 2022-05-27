package com.goKart.goKart.excel;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.goKart.goKart.model.Reserva;

public class ReservaExcel {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private List<Reserva> listaReserva;
	
	public ReservaExcel(List<Reserva> listaReservas) {
		this.listaReserva=listaReservas;
		workbook = new XSSFWorkbook();
	}
	
	private void criaCelula(Row linha,int contadorColuna, Object value,CellStyle style) {
		sheet.autoSizeColumn(contadorColuna);
		Cell cell=linha.createCell(contadorColuna);
		if(value instanceof Long) {
			cell.setCellValue((Long) value);
		}else if(value instanceof Integer) {
			cell.setCellValue((Integer) value);
		}else if(value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}
	private void writeHeaderLine() {
		sheet=workbook.createSheet("Baterias");
		
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		
		row=sheet.createRow(0);
		font.setBold(false);
        font.setFontHeight(12);
        style.setFont(font);
        criaCelula(row, 0, "Nome", style);
        criaCelula(row, 1, "Sobrenome", style);
        criaCelula(row, 2, "E-mail", style);
        criaCelula(row, 3, "Cidade", style);
        criaCelula(row, 4, "Última reserva", style);
        
	}
	
	private void writeDataLines() {
		int rowCount=1;
		
		CellStyle style=workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		font.setFontHeight(12);
		style.setFont(font);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		for(Reserva reserva : listaReserva) {
			Row row=sheet.createRow(rowCount++);
			int columnCount=0;
			
			criaCelula(row, columnCount++, reserva.getPiloto().getNome(), style);
			criaCelula(row, columnCount++, reserva.getPiloto().getSobrenome(), style);
			criaCelula(row, columnCount++, reserva.getPiloto().getEmail(), style);
			criaCelula(row, columnCount++, reserva.getPiloto().getCidade(), style);
			criaCelula(row, columnCount++, reserva.getDataReserva().format(formatter), style);
		}
	}
	
	public void export(HttpServletResponse response) throws IOException{
		writeHeaderLine();
		writeDataLines();
		
		String fileName = "Pilotos.csv";
		
		String headerKey = "Content-Disposition";
		String headervalue = "attachment; filename=" + fileName;
		
		response.setHeader(headerKey, headervalue);
		
		ServletOutputStream outputStream=response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
		
	}
	
	

}
