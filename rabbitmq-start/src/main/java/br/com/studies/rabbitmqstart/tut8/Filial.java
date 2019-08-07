package br.com.studies.rabbitmqstart.tut8;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filial {
    private String codigo;
    private String cidade;
}
