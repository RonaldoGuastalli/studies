package br.com.studies.rabbitmqstart.tut9.util;

import com.google.gson.Gson;

public class ConvertByteToObjeto {

    public static Object mapperToObjeto(byte[] objetoEmBytes) {
        final Gson gson = new Gson();

        return gson.toJson(objetoEmBytes);
    }
}
