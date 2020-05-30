package br.matheus.desafioseniorhotel.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name= "hospede")
public class Hospede extends AuditModel {
	@Id
    @GeneratedValue(generator = "hospede_generator")
    @SequenceGenerator(
            name = "hospede_generator",
            sequenceName = "hospede_sequence",
            initialValue = 1
    )
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
	private LocalDate nascimento;

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
}
