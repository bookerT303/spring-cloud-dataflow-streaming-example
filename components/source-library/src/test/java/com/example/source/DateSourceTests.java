package com.example.source;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class DateSourceTests {
    @Test
    public void testSource() throws Exception {

        DateSource source = new DateSource();

        String returnValue = source.source();

        assertThat(returnValue).isNotNull();
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date asDate = formatter.parse(returnValue);
        assertThat(asDate.before(new Date()));
    }
}
