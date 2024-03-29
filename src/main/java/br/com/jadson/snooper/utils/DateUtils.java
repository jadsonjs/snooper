/*
 * Universidade Federal do Rio Grande do Norte
 * Instituto Metrópole Digital
 * Diretoria de Tecnologia da Informação
 *
 * snooper
 * DateUtils
 * @since 09/08/2021
 */
package br.com.jadson.snooper.utils;


//import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * @author Jadson Santos - jadson.santos@ufrn.br
 */
//@Component
public class DateUtils {

    public boolean isBetweenDates(LocalDateTime date,  LocalDateTime start,  LocalDateTime end){

        if(date.equals(start))
            return true;

        if(date.equals(end))
            return true;

        if (date.isAfter(start) && date.isBefore(end)) {
            return true;
        }else {
            return false;
        }
    }

    public String toIso8601(LocalDateTime date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            return formatter.format(date);
        } catch (DateTimeParseException ignored) {
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.000'Z'");
            return formatter.format(date);
        } catch (DateTimeParseException ignored) {
        }
        return null;
    }

    public LocalDateTime fromIso8601(String dateStr) {
        LocalDateTime date = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone( ZoneId.of("UTC") ) ;
            date= LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException ignored) {
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.000'Z'").withZone( ZoneId.of("UTC") ) ;
            date= LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException ignored) {
        }
        return date;
    }

    public LocalDateTime toLocalDateTime(Date dateToConvert) {
        if(dateToConvert != null)
            return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return null;
    }

    public Date toDate(LocalDateTime dateToConvert) {
        if(dateToConvert != null)
            return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
        return null;
    }

    public String toString(LocalDateTime currentDateTime) {
        return currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
