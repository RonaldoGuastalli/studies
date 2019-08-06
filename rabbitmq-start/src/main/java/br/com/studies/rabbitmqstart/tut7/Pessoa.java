package br.com.studies.rabbitmqstart.tut7;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa{

    private int id;
    private String nome;
    private String sobrenome;

}
