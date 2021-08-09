/*
 * Universidade Federal do Rio Grande do Norte
 * Instituto Metrópole Digital
 * Diretoria de Tecnologia da Informação
 *
 * snooper
 * DateUtilsTest
 * @since 09/08/2021
 */
package br.com.jadson.snooper.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author Jadson Santos - jadson.santos@ufrn.br
 */
class DateUtilsTest {


    /**
     *
     */
    @Test
    void isBetweenDates() {

        LocalDateTime start = LocalDateTime.of(2020, 10, 1, 10, 01, 10);
        LocalDateTime end   = LocalDateTime.of(2021, 10, 10, 10, 01, 10);

        LocalDateTime date = LocalDateTime.of(2021, 7, 10, 10, 01, 10);

        Assertions.assertTrue(new DateUtils().isBetweenDates(date, start, end));

    }

    @Test
    void isBetweenDates2() {

        LocalDateTime start = LocalDateTime.of(2020, 10, 1, 10, 01, 10);
        LocalDateTime end   = LocalDateTime.of(2021, 10, 10, 10, 01, 10);

        LocalDateTime date = LocalDateTime.of(2020, 7, 10, 10, 01, 10);

        Assertions.assertFalse(new DateUtils().isBetweenDates(date, start, end));

    }


    @Test
    void isBetweenDates3() {

        LocalDateTime start = LocalDateTime.of(2020, 10, 1, 10, 01, 10);
        LocalDateTime end   = LocalDateTime.of(2021, 10, 10, 10, 01, 10);

        LocalDateTime date = LocalDateTime.of(2022, 7, 10, 10, 01, 10);

        Assertions.assertFalse(new DateUtils().isBetweenDates(date, start, end));

    }

    @Test
    void isBetweenDates4() {

        LocalDateTime start = LocalDateTime.of(2020, 10, 1, 10, 01, 10);
        LocalDateTime end   = LocalDateTime.of(2021, 10, 10, 10, 01, 10);

        LocalDateTime date = LocalDateTime.of(2021, 10, 10, 10, 01, 10);

        Assertions.assertTrue(new DateUtils().isBetweenDates(date, start, end));

    }

    @Test
    void isBetweenDates5() {

        LocalDateTime start = LocalDateTime.of(2020, 10, 1, 10, 01, 10);
        LocalDateTime end   = LocalDateTime.of(2021, 10, 10, 10, 01, 10);

        LocalDateTime date = LocalDateTime.of(2021, 10, 10, 10, 01, 11);

        Assertions.assertFalse(new DateUtils().isBetweenDates(date, start, end));

    }

    @Test
    void isBetweenDates6() {

        LocalDateTime start = LocalDateTime.of(2020, 10, 1, 10, 01, 10);
        LocalDateTime end   = LocalDateTime.of(2021, 10, 10, 10, 01, 10);

        LocalDateTime date = LocalDateTime.of(2020, 10, 1, 10, 01, 9);

        Assertions.assertFalse(new DateUtils().isBetweenDates(date, start, end));

    }


    @Test
    void isBetweenDates7() {

        LocalDateTime start = LocalDateTime.of(2020, 10, 1, 10, 01, 10);
        LocalDateTime end   = LocalDateTime.of(2021, 10, 10, 10, 01, 10);

        LocalDateTime date = LocalDateTime.of(2020, 10, 1, 10, 30, 20);

        Assertions.assertTrue(new DateUtils().isBetweenDates(date, start, end));

    }
}