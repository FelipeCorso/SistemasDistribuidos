package br.furb.rmi.estoque.client;

import java.rmi.Naming;

import br.furb.rmi.estoque.Estoque;

public class ClientEstoque {

    public Estoque retornaComunicacaoServer() {
        try {
            Estoque obj = (Estoque) Naming.lookup("//localhost/Estoque");
            return obj;
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return null;
    }

}
