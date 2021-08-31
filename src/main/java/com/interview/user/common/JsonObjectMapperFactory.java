package com.interview.user.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public final class JsonObjectMapperFactory {

    private JsonObjectMapperFactory() {
    }

    public static ObjectMapper createMapper() {
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
        DateTimeFormatter dateTimeFormatter = Constants.LOCAL_DATE_TIME_FORMATTER;
        DateTimeFormatter dateFormatter = Constants.LOCAL_DATE_FORMATTER;
        DateTimeFormatter zonedTimeFormatter = Constants.ZONED_DATE_TIME_FORMATTER;
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
                                  SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString(dateTimeFormatter.format(localDateTime));
            }
        });
        module.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                String string = parser.getText().trim();
                if (string.length() == 0) {
                    return null;
                }
                try {
                    return LocalDateTime.parse(string, dateTimeFormatter);
                } catch (DateTimeException e) {
                    throw JsonMappingException.from(parser,
                            String.format("Failed to deserialize %s: (%s) %s",
                                    handledType().getName(), e.getClass().getName(), e.getMessage()), e);
                }
            }
        });
        module.addSerializer(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>() {
            @Override
            public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator,
                                  SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString(zonedTimeFormatter.format(zonedDateTime));
            }
        });
        module.addDeserializer(ZonedDateTime.class, new JsonDeserializer<ZonedDateTime>() {
            @Override
            public ZonedDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                String string = parser.getText().trim();
                if (string.length() == 0) {
                    return null;
                }
                try {
                    return ZonedDateTime.parse(string, zonedTimeFormatter);
                } catch (DateTimeException e) {
                    throw JsonMappingException.from(parser,
                            String.format("Failed to deserialize %s: (%s) %s",
                                    handledType().getName(), e.getClass().getName(), e.getMessage()), e);
                }
            }
        });
        module.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public void serialize(LocalDate localDate, JsonGenerator jsonGenerator,
                                  SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString(dateFormatter.format(localDate));
            }
        });
        module.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                String string = parser.getText().trim();
                if (string.length() == 0) {
                    return null;
                }
                try {
                    return LocalDate.parse(string, dateFormatter);
                } catch (DateTimeException e) {
                    throw JsonMappingException.from(parser,
                            String.format("Failed to deserialize %s: (%s) %s",
                                    handledType().getName(), e.getClass().getName(), e.getMessage()), e);
                }
            }
        });
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper;
    }

}
