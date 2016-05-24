package br.furb.corba.compra.client;

import java.rmi.Naming;
import java.util.ArrayList;

import br.furb.rmi.estoque.Estoque;

public class CompraClientEstoque {
	public void solicitandoEstoque(br.furb.corba.compra.Produto produto) {
		try {
			Estoque obj = (Estoque) Naming.lookup("//localhost/Estoque");
			System.out.println("Mensagem do Servidor: " + obj.receberProduto(produto));
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
	}
}
