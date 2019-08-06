package br.com.studies.rabbitmqstart.tut7;

import com.google.gson.Gson;

public class ConvertObjectToByteArray {

    public static byte[] mapperToByte(Object object) {

        final Gson gson = new Gson();

        return gson.toJson(object).getBytes();
    }
}
