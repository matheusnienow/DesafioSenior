package br.matheus.desafioseniorhotel.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.matheus.desafioseniorhotel.exception.ResourceNotFoundException;
import br.matheus.desafioseniorhotel.model.CheckIn;
import br.matheus.desafioseniorhotel.repository.CheckInRepository;
import br.matheus.desafioseniorhotel.repository.HospedeRepository;

@RestController
public class CheckInController {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private CheckInRepository checkInRepository;

	@Autowired
	private HospedeRepository hospedeRepository;

	@GetMapping("/hospedes/{hospedeId}/checkins")
	public List<CheckIn> getCheckInsByHospedeId(@PathVariable Long hospedeId) {
		return checkInRepository.findByHospedeId(hospedeId);
	}

	@PostMapping("/hospedes/{hospedeId}/checkins")
	public CheckIn addCheckIn(@PathVariable Long hospedeId, @Valid @RequestBody CheckIn checkIn)
			throws ResourceNotFoundException {
		return hospedeRepository.findById(hospedeId).map(hospede -> {
			checkIn.setHospede(hospede);
			return checkInRepository.save(checkIn);
		}).orElseThrow(() -> new ResourceNotFoundException("Hospede com o id " + hospedeId + " não encontrado."));
	}

	@PutMapping("/hospedes/{hospedeId}/checkins/{checkInId}")
	public CheckIn updateCheckIn(@PathVariable Long hospedeId, @PathVariable Long checkInId,
			@Valid @RequestBody CheckIn checkInRequest) throws ResourceNotFoundException {
		if (!hospedeRepository.existsById(hospedeId)) {
			throw new ResourceNotFoundException("Hospede com o id " + hospedeId + " não encontrado.");
		}

		return checkInRepository.findById(checkInId).map(checkIn -> {
			checkIn.setDataEntrada(checkInRequest.getDataEntrada());
			checkIn.setDataSaida(checkInRequest.getDataSaida());
			checkIn.setHospede(checkInRequest.getHospede());
			checkIn.setAdicionalVeiculo(checkInRequest.isAdicionalVeiculo());
			return checkInRepository.save(checkIn);
		}).orElseThrow(() -> new ResourceNotFoundException("CheckIn com o id " + checkInId + " não encontrado."));
	}

	@DeleteMapping("/hospedes/{hospedeId}/checkins/{checkInId}")
	public ResponseEntity<?> deleteCheckIn(@PathVariable Long hospedeId, @PathVariable Long checkInId)
			throws ResourceNotFoundException {
		if (!hospedeRepository.existsById(hospedeId)) {
			throw new ResourceNotFoundException("Hospede com o id " + hospedeId + " não encontrado.");
		}

		return checkInRepository.findById(checkInId).map(checkIn -> {
			checkInRepository.delete(checkIn);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("CheckIn com o id " + checkInId + " não encontrado."));

	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handle(Exception e) {
		logger.warn("Returning HTTP 400 Bad Request", e);
	}
}
