package br.furb.corba.compra.client;

import java.rmi.Naming;
import java.util.ArrayList;

import br.furb.common.Produto;
import br.furb.rmi.estoque.Estoque;

public class compraClientEstoque {
	public void solicitandoEstoque(Produto produto) {
		try {
			ArrayList<Produto> listaProduto = new ArrayList<Produto>();
			listaProduto.add(produto);
			Estoque obj = (Estoque) Naming.lookup("//localhost/Estoque");
			System.out.println("Mensagem do Servidor: " + obj.receberProduto(listaProduto));
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
	}
}
