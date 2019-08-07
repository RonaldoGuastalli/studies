package br.com.studies.rabbitmqstart.tut8;

public class Mapper {

    public static Produto mapperToProduto(String id, String nome, String valor, String codigo, String cidade){
        return Produto.builder()
                .id(id)
                .nome(nome)
                .valor(valor)
                .filial(new Filial(codigo, cidade))
                .build();
    }
}
