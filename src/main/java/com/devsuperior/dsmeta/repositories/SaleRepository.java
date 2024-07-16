package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.amount, obj.date, obj.seller.name) " +
            "FROM Sale obj " +
            "WHERE (obj.date BETWEEN :minDate AND :maxDate) " +
            "AND (UPPER(obj.seller.name) LIKE UPPER(:name))")
    Page<SaleMinDTO> searchReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) " +
            "FROM Sale obj " +
            "WHERE (obj.date BETWEEN :minDate AND :maxDate) " +
            "GROUP BY obj.seller.name")
    Page<SaleSummaryDTO> searchSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable);
}
