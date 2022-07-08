package br.com.jadson.snooper.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Date;

public class CustomDateDeserializer extends StdDeserializer<Date> {

    DateUtils dateUtils = new DateUtils();

    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException, JsonProcessingException {
        String dateStr = jsonparser.getText();
        return dateUtils.toDate(dateUtils.fromIso8601(dateStr));
    }
}
