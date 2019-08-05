package com.studies.designpatternjava.implementacoes;

import com.studies.designpatternjava.Receptor;

public class ReceptorVisa implements Receptor {

    @Override
    public String recebe() {
        System.out.println("Recebendo mensagem da Visa.");
        String mensagem = "Mensagem da visa";
        return mensagem;
    }
}
