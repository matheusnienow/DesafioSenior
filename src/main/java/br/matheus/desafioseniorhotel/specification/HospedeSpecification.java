package br.matheus.desafioseniorhotel.specification;

import java.text.MessageFormat;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import br.matheus.desafioseniorhotel.model.Hospede;

public class HospedeSpecification {
	public static Specification<Hospede> byFilter(Map<String, String> filter) {
		var nome = filter.get("nome");
		var documento = filter.get("documento");
		var telefone = filter.get("telefone");
		var email = filter.get("email");

		Specification<Hospede> specification = Specification
				.where(nome == null ? null : nomeContains(nome))
				.and(documento == null ? null : documentoContains(documento))
				.and(telefone == null ? null : telefoneContains(telefone))
				.and(email == null ? null : emailContains(email));
		
		return specification;
	}

	public static Specification<Hospede> nomeContains(String expression) {
		return (root, query, builder) -> builder.like(root.get("nome"), contains(expression));
	}

	public static Specification<Hospede> documentoContains(String expression) {
		return (root, query, builder) -> builder.like(root.get("documento"), contains(expression));
	}

	public static Specification<Hospede> telefoneContains(String expression) {
		return (root, query, builder) -> builder.like(root.get("telefone"), contains(expression));
	}

	public static Specification<Hospede> emailContains(String expression) {
		return (root, query, builder) -> builder.like(root.get("email"), contains(expression));
	}

	private static String contains(String expression) {
		return MessageFormat.format("%{0}%", expression);
	}
}
