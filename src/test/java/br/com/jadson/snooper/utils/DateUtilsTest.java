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

    @Test
    void fromIso8601Test() {
        DateUtils util = new DateUtils();
        Assertions.assertEquals( LocalDateTime.of(2019, 12, 19, 1, 00, 22), util.fromIso8601("2019-12-19T01:00:22Z"));
    }


    @Test
    void fromIso8601Test2() {
        DateUtils util = new DateUtils();
        Assertions.assertEquals( LocalDateTime.of(2022, 06, 8, 20, 15, 32), util.fromIso8601("2022-06-08T20:15:32.000Z"));
    }



    @Test
    void toIso8601Test() {
        DateUtils util = new DateUtils();
        Assertions.assertEquals("2019-12-19T01:00:22Z" , util.toIso8601(LocalDateTime.of(2019, 12, 19, 1, 00, 22)));
    }
}
