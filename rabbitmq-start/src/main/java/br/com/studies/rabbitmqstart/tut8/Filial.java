package br.com.studies.rabbitmqstart.tut8;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filial implements Serializable {
    private String codigo;
    private String cidade;
}
