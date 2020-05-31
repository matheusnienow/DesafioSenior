package br.matheus.desafioseniorhotel.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.matheus.desafioseniorhotel.exception.ResourceNotFoundException;
import br.matheus.desafioseniorhotel.model.Hospede;
import br.matheus.desafioseniorhotel.repository.HospedeRepository;
import br.matheus.desafioseniorhotel.specification.HospedeSpecification;

@RestController
public class HospedeController {

	@Autowired
	private HospedeRepository hospedeRepository;
	
	@GetMapping("/hospedes")
	public Page<Hospede> getHospedes(Pageable pageable) {
		return hospedeRepository.findAll(pageable);
	}
	
	@GetMapping("/hospedes/checkedin")
	public List<Hospede> getCheckedIn() {
		return hospedeRepository.findByCheckInsDataSaidaNullAndCheckInsNotEmpty();
	}
	
	@GetMapping("/hospedes/checkedout")
	public List<Hospede> getHospedesCheckedOut() {
		var hospedes = hospedeRepository.findByCheckInsDataSaidaNull();
		var ids = hospedes.stream().map(c -> c.getId()).collect(Collectors.toList());
		return hospedeRepository.findByIdNotIn(ids);
	}

	@GetMapping("/hospedes/filters")
	public List<Hospede> getHospedesBy(@RequestParam Map<String, String> filter) {
		var specification = HospedeSpecification.byFilter(filter);
		return hospedeRepository.findAll(specification);
	}

	@PostMapping("/hospedes")
	public Hospede createHospede(@Valid @RequestBody Hospede hospede) {
		return hospedeRepository.save(hospede);
	}

	@PutMapping("/hospedes/{hospedeId}")
	public Hospede updateHospede(@PathVariable Long hospedeId, @Valid @RequestBody Hospede hospedeRequest)
			throws ResourceNotFoundException {
		return hospedeRepository.findById(hospedeId).map(hospede -> {

			hospede.setNome(hospedeRequest.getNome());
			hospede.setDocumento(hospedeRequest.getDocumento());
			hospede.setEmail(hospedeRequest.getEmail());
			hospede.setNascimento(hospedeRequest.getNascimento());
			hospede.setTelefone(hospedeRequest.getTelefone());

			return hospedeRepository.save(hospede);
		}).orElseThrow(() -> new ResourceNotFoundException("Hospede com id " + hospedeId + " não foi encontrado."));
	}

	@DeleteMapping("/hospedes/{hospedeId}")
	public ResponseEntity<?> deleteHospede(@PathVariable Long hospedeId) throws ResourceNotFoundException {
		return hospedeRepository.findById(hospedeId).map(hospede -> {

			hospedeRepository.delete(hospede);
			return ResponseEntity.ok().build();

		}).orElseThrow(() -> new ResourceNotFoundException("Hospede com id " + hospedeId + " não foi encontrado."));
	}
}
