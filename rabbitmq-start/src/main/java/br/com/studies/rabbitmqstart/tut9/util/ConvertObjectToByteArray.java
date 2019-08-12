package br.com.studies.rabbitmqstart.tut9.util;

import com.google.gson.Gson;

public class ConvertObjectToByteArray {

    public static byte[] mapperToByte(Object object) {

        final Gson gson = new Gson();

        return gson.toJson(object).getBytes();
    }
}
