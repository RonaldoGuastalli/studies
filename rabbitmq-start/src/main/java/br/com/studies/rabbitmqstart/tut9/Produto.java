package br.com.studies.rabbitmqstart.tut9;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    private Integer id;
    private String nome;
    private String valor;
}
