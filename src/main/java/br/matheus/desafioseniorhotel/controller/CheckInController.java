package br.matheus.desafioseniorhotel.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.matheus.desafioseniorhotel.exception.ResourceNotFoundException;
import br.matheus.desafioseniorhotel.model.CheckIn;
import br.matheus.desafioseniorhotel.model.Hospede;
import br.matheus.desafioseniorhotel.repository.CheckInRepository;
import br.matheus.desafioseniorhotel.repository.HospedeRepository;
import br.matheus.desafioseniorhotel.service.CheckInService;

@RestController
public class CheckInController {
	@Autowired
	private CheckInRepository checkInRepository;

	@Autowired
	private HospedeRepository hospedeRepository;

	@GetMapping("/hospedes/{hospedeId}/checkins")
	public List<CheckIn> getCheckInsByHospedeId(@PathVariable Long hospedeId) {
		List<CheckIn> checkins = checkInRepository.findByHospedeId(hospedeId);
		return checkins;
	}

	@PostMapping("/hospedes/{hospedeId}/checkins")
	public CheckIn addCheckIn(@PathVariable Long hospedeId, @Valid @RequestBody CheckIn checkIn)
			throws ResourceNotFoundException {		
		Optional<Hospede> hospede = hospedeRepository.findById(hospedeId);
		
		@Valid
		CheckIn checkin = hospede.map(h -> {			
			if (checkIn.getDataEntrada() == null) {
				checkIn.setDataEntrada(LocalDateTime.now());
			}			
			checkIn.setHospede(h);
			
			return checkInRepository.save(checkIn);
		}).orElseThrow(() -> new ResourceNotFoundException("Hospede com o id " + hospedeId + " não encontrado."));
		
		return checkin;
	}

	@PutMapping("/hospedes/{hospedeId}/checkout")
	public CheckIn updateCheckIn(@PathVariable Long hospedeId) throws ResourceNotFoundException {
		
		if (!hospedeRepository.existsById(hospedeId)) {
			throw new ResourceNotFoundException("Hospede com o id " + hospedeId + " não encontrado.");
		}

		var checkIn = checkInRepository.findByHospedeIdAndDataSaidaNull(hospedeId);
		checkIn.setDataSaida(LocalDateTime.now());
		
		Double valor = CheckInService.calcularValorCheckOut(checkIn.getDataEntrada(), checkIn.getDataSaida(), checkIn.isAdicionalVeiculo());
		checkIn.setValor(valor);
		
		return checkInRepository.save(checkIn);
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
}
