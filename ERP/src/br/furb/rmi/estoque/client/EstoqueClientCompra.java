package br.furb.rmi.estoque.client;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import br.furb.common.Produto;
import br.furb.corba.compra.Compra;
import br.furb.corba.compra.CompraHelper;



public class EstoqueClientCompra {

	public void cominicarCompra(Produto produto) {

		try {
			// Cria e inicializa o ORB
			ORB orb = ORB.init();

			// Obtem referencia para o servico de nomes
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt namecontextRef = NamingContextExtHelper.narrow(objRef);

			// Obtem referencia para o servidor
			String name = "Compras";
			Compra moduloCompra = CompraHelper.narrow(namecontextRef.resolve_str(name));

			moduloCompra.recebeNota(produto.getCodigoProduto(), produto.getDescricaoProduto(),produto.getQtdProduto(),produto.getValorUnitario());

			System.out.println("Resultado: Compras Cliente Executado.");

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}
}
