package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> getReport(String minDateStr, String maxDateStr, String name, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate maxDate = (maxDateStr == null || maxDateStr.isEmpty()) ? today : LocalDate.parse(maxDateStr);
		LocalDate minDate = (minDateStr == null || minDateStr.isEmpty()) ? maxDate.minusYears(1) : LocalDate.parse(minDateStr);

		return repository.searchReport(minDate, maxDate, "%" + name + "%", pageable);
	}

	public Page<SaleSummaryDTO> getSummary(String minDateStr, String maxDateStr, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate maxDate = (maxDateStr == null || maxDateStr.isEmpty()) ? today : LocalDate.parse(maxDateStr);
		LocalDate minDate = (minDateStr == null || minDateStr.isEmpty()) ? maxDate.minusYears(1) : LocalDate.parse(minDateStr);

		return repository.searchSummary(minDate, maxDate, pageable);
	}
}
