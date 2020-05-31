package br.matheus.desafioseniorhotel.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.matheus.desafioseniorhotel.config.HotelAppConfig;

@Entity
@Table(name = "hospede")
public class Hospede extends AuditModel {
	@Id
	@GeneratedValue(generator = "hospede_generator")
	@SequenceGenerator(name = "hospede_generator", sequenceName = "hospede_sequence", initialValue = 1)
	private Long id;

	@NotBlank
	@Size(min = 1, max = 100)
	@Column(columnDefinition = "text")
	private String nome;

	@NotBlank
	@Size(min = 1, max = 15)
	@Column(columnDefinition = "text")
	private String documento;

	@NotBlank
	@Size(min = 1, max = 20)
	@Column(columnDefinition = "text")
	private String telefone;

	@Column(columnDefinition = "text")
	private String email;

	@NotBlank
	@JsonFormat(pattern = HotelAppConfig.DATE_FORMAT)
	private LocalDate nascimento;

	@JsonIgnore
	@OneToMany(mappedBy = "hospede", fetch = FetchType.LAZY)
	private List<CheckIn> checkIns = new ArrayList<CheckIn>();

	@Transient
	@JsonProperty("totalGasto")
	private Double totalGasto;

	@Transient
	@JsonProperty("ultimoGasto")
	private Double ultimoGasto;

	@PostLoad
	private void postLoad() {
		if (checkIns == null || checkIns.isEmpty()) {
			return;
		}

		loadTotalGasto();
		loadUltimoGasto();
	}

	private void loadUltimoGasto() {

		var checkIn = checkIns.stream()
				.min(Comparator.comparing(CheckIn::getDataSaida, Comparator.nullsLast(Comparator.reverseOrder())));
		checkIn.ifPresent(c -> {
			this.ultimoGasto = c.getValor();
		});
	}

	private void loadTotalGasto() {
		var totalGasto = checkIns.stream().filter(Objects::nonNull).map(CheckIn::getValor).filter(Objects::nonNull).mapToDouble(c -> c).sum();
		this.totalGasto = totalGasto == 0.0 ? null : totalGasto;;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public List<CheckIn> getCheckIns() {
		return checkIns;
	}

	public void setCheckIns(List<CheckIn> checkIns) {
		this.checkIns = checkIns;
	}
}
