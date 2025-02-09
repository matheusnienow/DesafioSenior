package br.matheus.desafioseniorhotel.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.matheus.desafioseniorhotel.config.HotelAppConfig;

@Entity
@Table(name="checkin")
public class CheckIn extends AuditModel {
	@Id
    @GeneratedValue(generator = "checkin_generator")
    @SequenceGenerator(
            name = "checkin_generator",
            sequenceName = "checkin_sequence",
            initialValue = 1
    )
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "hospede_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Hospede hospede;
	
	@JsonFormat(pattern = HotelAppConfig.DATETIME_FORMAT)
	@NotBlank
	private LocalDateTime dataEntrada;
	
	@JsonFormat(pattern = HotelAppConfig.DATETIME_FORMAT)	
	private LocalDateTime dataSaida;
	
	private boolean adicionalVeiculo;
	
	private Double valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hospede getHospede() {
		return hospede;
	}

	public void setHospede(Hospede hospede) {
		this.hospede = hospede;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDateTime getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDateTime dataSaida) {
		this.dataSaida = dataSaida;
	}

	public boolean isAdicionalVeiculo() {
		return adicionalVeiculo;
	}

	public void setAdicionalVeiculo(boolean adicionalVeiculo) {
		this.adicionalVeiculo = adicionalVeiculo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}
