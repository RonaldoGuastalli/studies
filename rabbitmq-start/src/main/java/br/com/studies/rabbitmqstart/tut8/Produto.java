package br.com.studies.rabbitmqstart.tut8;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Serializable {

    private String id;
    private String nome;
    private String valor;
    private Filial filial;

}
