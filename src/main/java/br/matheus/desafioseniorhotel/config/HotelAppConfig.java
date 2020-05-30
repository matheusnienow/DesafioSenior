package br.matheus.desafioseniorhotel.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class HotelAppConfig {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

	//Deixei esse código abaixo comentado apenas para demonstrar que tentei definir uma formatação global de data para a aplicação
	//porém parece que não é possível fazer isso no momento para os tipo LocalDate e LocalDatetime
	
	
	/*
	 @Bean public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() { return
	 builder -> { builder.simpleDateFormat(dateTimeFormat);
	 builder.serializers(new
	 LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
	 builder.serializers(new
	 LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat))); }; }
	 

	/*
	 * @Bean public FormattingConversionService conversionService() { // Use the
	 * DefaultFormattingConversionService but do not register defaults
	 * DefaultFormattingConversionService conversionService = new
	 * DefaultFormattingConversionService(false);
	 * 
	 * // Ensure @NumberFormat is still supported
	 * conversionService.addFormatterForFieldAnnotation(new
	 * NumberFormatAnnotationFormatterFactory());
	 * 
	 * DateTimeFormatterRegistrar registrarDateTime = new
	 * DateTimeFormatterRegistrar();
	 * registrarDateTime.setDateFormatter(DateTimeFormatter.ofPattern(dateFormat));
	 * registrarDateTime.registerFormatters(conversionService);
	 * 
	 * DateFormatterRegistrar registrarDate = new DateFormatterRegistrar();
	 * registrarDate.setFormatter(new DateFormatter(dateTimeFormat));
	 * registrarDate.registerFormatters(conversionService);
	 * 
	 * return conversionService; }
	 * 
	 * @Bean public Formatter<LocalDate> localDateFormatter() { return new
	 * Formatter<LocalDate>() {
	 * 
	 * @Override public String print(LocalDate object, Locale locale) { return
	 * DateTimeFormatter.ofPattern(dateFormat).format(object); }
	 * 
	 * @Override public LocalDate parse(String text, Locale locale) throws
	 * ParseException { return LocalDate.parse(text,
	 * DateTimeFormatter.ofPattern(dateFormat)); } }; }
	 * 
	 * @Bean public Formatter<LocalDateTime> localDateTimeFormatter() { return new
	 * Formatter<LocalDateTime>() {
	 * 
	 * @Override public String print(LocalDateTime object, Locale locale) { return
	 * DateTimeFormatter.ofPattern(dateTimeFormat).format(object); }
	 * 
	 * @Override public LocalDateTime parse(String text, Locale locale) throws
	 * ParseException { return LocalDateTime.parse(text,
	 * DateTimeFormatter.ofPattern(dateTimeFormat)); } }; }
	 */

}
