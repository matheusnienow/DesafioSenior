package br.matheus.desafioseniorhotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DesafioseniorhotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioseniorhotelApplication.class, args);
	}

}
