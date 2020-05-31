package br.matheus.desafioseniorhotel.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

import br.matheus.desafioseniorhotel.model.CheckIn;
import br.matheus.desafioseniorhotel.model.Hospede;

public class CheckInService {
	
	public static CheckIn checkIn(Hospede hospede, boolean adicionalVeiculo) {
		CheckIn checkin = new CheckIn();
		checkin.setDataEntrada(LocalDateTime.now());
		checkin.setDataSaida(null);
		checkin.setAdicionalVeiculo(adicionalVeiculo);
		checkin.setHospede(hospede);
		checkin.setValor(null);
		return checkin;
	}
	
	public static Double calcularValorCheckOut(LocalDateTime dataEntrada, LocalDateTime dataSaida, boolean adicionalVeiculo) {
		double diariaSemana = 120;
		double diariaFimSemana = 150;
		double adicionalVeiculoSemana = 15;
		double adicionalVeiculoFimSemana = 20;

		double valorTotal = 0f;

		var diarias = getQtdDiarias(dataEntrada, dataSaida);

		var tempDate = dataEntrada;
		for (int i = 0; i < diarias; i++) {
			var isFimDeSemana = isFimDeSemana(tempDate);

			valorTotal += isFimDeSemana ? diariaFimSemana : diariaSemana;
			if (adicionalVeiculo) {
				valorTotal += isFimDeSemana ? adicionalVeiculoSemana : adicionalVeiculoFimSemana;
			}

			tempDate = tempDate.plusDays(1);
		}

		return valorTotal;
	}

	private static float getQtdDiarias(LocalDateTime dataEntrada, LocalDateTime dataSaida) {
		var dataDiariaExtra = LocalDateTime.of(dataSaida.getYear(), dataSaida.getMonth(), dataSaida.getDayOfMonth(), 16,
				30);
		var dias = Duration.between(dataEntrada, dataSaida).toDays();
		if (dataSaida.isAfter(dataDiariaExtra)) {
			dias++;
		}
		return dias;
	}

	public static boolean isFimDeSemana(LocalDateTime data) {
		DayOfWeek d = data.getDayOfWeek();
		return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
	}
}
