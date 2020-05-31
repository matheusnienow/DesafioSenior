package br.matheus.desafioseniorhotel.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.matheus.desafioseniorhotel.model.Hospede;

public interface HospedeRepository extends JpaRepository<Hospede, Long>, JpaSpecificationExecutor<Hospede> {

	List<Hospede> findByCheckInsDataSaidaNotNull();

	List<Hospede> findByCheckInsDataSaidaNull();

	List<Hospede> findByCheckInsDataSaidaNullAndCheckInsNotEmpty();

	List<Hospede> findByIdNotIn(Collection<Long> ids);
}
