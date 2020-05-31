package br.matheus.desafioseniorhotel;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class Rascunho {

	public static void main(String[] args) {
		LocalDateTime dataEntrada = LocalDateTime.of(2020,  Month.MAY, 20, 14, 30);
		LocalDateTime dataSaida = LocalDateTime.of(2020,  Month.MAY, 30, 17, 30);
		
		System.out.println(calcularValorCheckOut(dataEntrada, dataSaida, false));
		System.out.println(calcularValorCheckOut(dataEntrada, dataSaida, true));

	}

	private static float calcularValorCheckOut(LocalDateTime dataEntrada, LocalDateTime dataSaida, boolean adicionalVeiculo) {
		float diariaSemana = 120;
		float diariaFimSemana = 150;
		float adicionalVeiculoSemana = 15;
		float adicionalVeiculoFimSemana = 20;
		
		float valorTotal = 0f;
				
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
		var dataDiariaExtra = LocalDateTime.of(dataSaida.getYear(), dataSaida.getMonth(), dataSaida.getDayOfMonth(), 16, 30);
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
