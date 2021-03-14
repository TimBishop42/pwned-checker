package com.personal.pwnedchecker.model;

import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Converter
public class DateTimeConverter implements AttributeConverter<String, Date> {

    @SneakyThrows
    @Override
    public Date convertToDatabaseColumn(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return dateFormat.parse(date);

    }

    @Override
    public String convertToEntityAttribute(Date date) {
        return date.toString();
    }
}
