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
import java.time.format.DateTimeFormatter;

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

    public String toString(LocalDateTime currentDateTime) {
        return currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
