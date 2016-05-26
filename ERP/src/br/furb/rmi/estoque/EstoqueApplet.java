package br.furb.rmi.estoque;

import java.applet.Applet;
import java.rmi.Naming;
import java.util.ArrayList;

import br.furb.common.Produto;

public class EstoqueApplet extends Applet {

	String message = "nada";
	Estoque obj = null;

	public void init() {
		try {
			Produto produto = new Produto();
			produto.setCodigoProduto(1);
			produto.setDescricaoProduto("teste");
			produto.setQtdProduto(2);

			obj = (Estoque) Naming.lookup("//localhost/Estoque");
			message = obj.receberProduto(produto);
		} catch (Exception e) {
			System.out.println("EstoqueApplet exception: " + e.getMessage());
		}
	}

}
