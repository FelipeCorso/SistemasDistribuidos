package br.furb.corba.compra.client;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import br.furb.corba.compra.Compra;
import br.furb.corba.compra.CompraHelper;

public class CompraClient {

	public static void main(String args[]) {
		try {
			// Cria e inicializa o ORB
			ORB orb = ORB.init(args, null);

			// Obtem referencia para o servico de nomes
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt namecontextRef = NamingContextExtHelper.narrow(objRef);

			// Obtem referencia para o servidor
			String name = "Compras";
			Compra moduloCompra = CompraHelper.narrow(namecontextRef.resolve_str(name));

			moduloCompra.recebeNota();
			moduloCompra.comunicaEstoque();

			System.out.println("Resultado: Compras Cliente Executado.");

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}
}
