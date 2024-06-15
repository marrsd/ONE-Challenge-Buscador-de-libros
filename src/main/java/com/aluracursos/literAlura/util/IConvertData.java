package com.aluracursos.literAlura.util;

public interface IConvertData {
    <T> T convertData(String json, Class<T> toClass);
}
