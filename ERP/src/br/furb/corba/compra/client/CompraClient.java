package br.furb.corba.compra.client;

import java.time.LocalTime;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.common.Produto;
import br.furb.corba.compra.Compra;
import br.furb.corba.compra.CompraHelper;
import br.furb.ws.leaderelection.Server;

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

			Produto produto = new Produto();
			produto.setCodigoProduto(1);
			produto.setDescricaoProduto("Toalha");
			produto.setQtdProduto(5);

			moduloCompra.recebeNota(produto.getCodigoProduto(), produto.getDescricaoProduto(), produto.getQtdProduto(),
					produto.getValorUnitario());

			System.out.println("Resultado: Compras Cliente Executado.");

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}
	
	public static Compra retornaClientCompras(String args[]) {
		try {
			// Cria e inicializa o ORB
			ORB orb = ORB.init(args,null);

			// Obtem referencia para o servico de nomes
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt namecontextRef = NamingContextExtHelper.narrow(objRef);

			// Obtem referencia para o servidor
			String name = "Compras";
			Compra moduloCompra = CompraHelper.narrow(namecontextRef.resolve_str(name));
			return moduloCompra;

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
		return null;
	}

	public LocalTime getServerTime()
			throws InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		// Cria e inicializa o ORB
		ORB orb = ORB.init(new String[0], null);

		// Obtem referencia para o servico de nomes
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		NamingContextExt namecontextRef = NamingContextExtHelper.narrow(objRef);

		// Obtem referencia para o servidor
		String name = "Compras";
		Compra compraServer = CompraHelper.narrow(namecontextRef.resolve_str(name));
		return LocalTime.ofSecondOfDay(compraServer.getServerTime());
	}

	public void setServerTime(LocalTime corbaTime)
			throws InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		// Cria e inicializa o ORB
		ORB orb = ORB.init(new String[0], null);

		// Obtem referencia para o servico de nomes
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		NamingContextExt namecontextRef = NamingContextExtHelper.narrow(objRef);

		// Obtem referencia para o servidor
		String name = "Compras";
		Compra compraServer = CompraHelper.narrow(namecontextRef.resolve_str(name));
		compraServer.setServerTime(corbaTime.toSecondOfDay());
	}

	public int getServerStatus(Server server)
			throws InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
		// Cria e inicializa o ORB
		ORB orb = ORB.init(new String[0], null);

		// Obtem referencia para o servico de nomes
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		NamingContextExt namecontextRef = NamingContextExtHelper.narrow(objRef);

		// Obtem referencia para o servidor
		String name = "Compras";
		Compra compraServer = CompraHelper.narrow(namecontextRef.resolve_str(name));
		// TODO
		return 0;
	}
}
