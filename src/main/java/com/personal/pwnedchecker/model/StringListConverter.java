package com.personal.pwnedchecker.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHARACTER = ",";

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return strings != null ? String.join(SPLIT_CHARACTER, strings) : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return s != null ? Arrays.asList(s.split(SPLIT_CHARACTER)) : Collections.emptyList();
    }
}
