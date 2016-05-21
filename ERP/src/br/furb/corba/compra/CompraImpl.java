package br.furb.corba.compra;

import br.furb.common.Produto;
import br.furb.corba.compra.client.compraClientEstoque;

public class CompraImpl extends CompraPOA {

	public boolean recebeNota() {
		System.out.println("Compras recebeu nota fiscal de entrada");
		return true;
	};

	public boolean comunicaEstoque() {
		compraClientEstoque clientEstoque = new compraClientEstoque();
		Produto produto = new Produto();
		produto.setCodigoProduto(1);
		produto.setDescricaoProduto("ServerCompras");
		produto.setQtdProduto(2);
		clientEstoque.solicitandoEstoque(produto);
		return true;
	};

}