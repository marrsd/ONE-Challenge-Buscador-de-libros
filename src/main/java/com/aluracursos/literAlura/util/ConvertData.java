package com.aluracursos.literAlura.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T convertData(String json, Class<T> toClass) {
        try {
            return objectMapper.readValue(json, toClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
