package br.matheus.desafioseniorhotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.matheus.desafioseniorhotel.model.Hospede;

public interface HospedeRepository extends JpaRepository<Hospede, Long>{

}
