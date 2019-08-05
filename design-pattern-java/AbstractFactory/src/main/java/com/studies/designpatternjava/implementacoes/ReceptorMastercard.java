package com.studies.designpatternjava.implementacoes;

import com.studies.designpatternjava.Receptor;

public class ReceptorMastercard implements Receptor {

    @Override
    public String recebe() {
        System.out.println("Recebendo mensagem da Mastercard.");
        String mensagem = "Mensagem da Mastercard";
        return mensagem;
    }
}
