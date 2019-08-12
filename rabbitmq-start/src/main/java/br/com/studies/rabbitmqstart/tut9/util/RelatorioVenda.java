package br.com.studies.rabbitmqstart.tut9.util;

import br.com.studies.rabbitmqstart.tut9.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioVenda {

    private List<String> nomeProdutos;
    private Double valorTotalVenda;

    public static Double totalDeVendas(List<Produto> produtos) {
        Double soma = 0.0;
        for (Produto produto : produtos) {
            soma += produto.getValor();
        }
        return soma;
    }

    public static RelatorioVenda relatorio (List<String> produtos, Double valorTotalVenda){
        return RelatorioVenda.builder()
                .nomeProdutos(produtos)
                .valorTotalVenda(valorTotalVenda)
                .build();
    }

    public static List<String> produtoDaVenda(List<Produto> produtos){
        return produtos.stream()
                .map(produto -> produto.getNome())
                .collect(Collectors.toList());
    }
}
