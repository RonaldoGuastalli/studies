package br.com.studies.rabbitmqstart.tut9.mapper;

import br.com.studies.rabbitmqstart.tut9.model.Filial;
import br.com.studies.rabbitmqstart.tut9.model.Produto;

public class Mapper {

    public static Produto mapperToProduto(Integer id, String nome, Double valor, String codigo, String cidade){
        return Produto
                .builder()
                .id(id)
                .nome(nome)
                .valor(valor)
                .filial(mapperToFilial(codigo, cidade))
                .build();
    }

    public static Filial mapperToFilial(String codigo, String cidade){
        return Filial
                .builder()
                .codigo(codigo)
                .cidade(cidade)
                .build();
    }
}
