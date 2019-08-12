package br.com.studies.rabbitmqstart.tut9.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private Integer id;
    private String nome;
    private Double valor;
    private Filial filial;

}
