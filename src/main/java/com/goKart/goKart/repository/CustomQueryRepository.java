package com.goKart.goKart.repository;

import com.goKart.goKart.dto.FaturarDTO;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;

@Repository
public class CustomQueryRepository {

    @PersistenceContext
    private EntityManager em;

    public List<FaturarDTO> listarKartodromosFaturar (){
        String query = "select new com.goKart.goKart.dto.FaturarDTO(k.nome, k.email, sum(b.valorBateria), sum(b.valorBateria - (b.valorBateria / 100 * 20))) from Reserva r " +
                "INNER JOIN r.kartodromo k " +
                "INNER JOIN r.bateria b " +
                "WHERE MONTH (r.dataReserva) = MONTH(CURRENT_DATE()) " +
                "AND r.status = 0" +
                "group by k";

        TypedQuery<FaturarDTO> typedQuery = em.createQuery(query, FaturarDTO.class);

        return typedQuery.getResultList();
    }

}
