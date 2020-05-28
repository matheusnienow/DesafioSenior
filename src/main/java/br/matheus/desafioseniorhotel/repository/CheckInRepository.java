package br.matheus.desafioseniorhotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.matheus.desafioseniorhotel.model.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
	List<CheckIn> findByHospedeId(Long hospedeId);
}
