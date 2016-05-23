package br.furb.corba.compra;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.common.Produto;
import br.furb.common.UpdateServerTime;
import br.furb.corba.compra.client.CompraClientEstoque;
import br.furb.ui.UiServer;

public class CompraImpl extends CompraPOA {

	private LocalTime serverTime = LocalTime.now();
	private UiServer uiServer;

	public CompraImpl() {
		runUiServer();
	}

	public boolean recebeNota() {
		System.out.println("Compras recebeu nota fiscal de entrada");
		return true;
	};

	public boolean comunicaEstoque() {
		CompraClientEstoque clientEstoque = new CompraClientEstoque();
		Produto produto = new Produto();
		produto.setCodigoProduto(1);
		produto.setDescricaoProduto("ServerCompras");
		produto.setQtdProduto(2);
		clientEstoque.solicitandoEstoque(produto);
		return true;
	}

	@Override
	public void updateServerTime() {
		try {
			UpdateServerTime.update(null);
		} catch (MalformedURLException | InvalidName | NotFound | CannotProceed
				| org.omg.CosNaming.NamingContextPackage.InvalidName | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public long getServerTime() {
		return serverTime.toSecondOfDay();
	}

	@Override
	public void setServerTime(long newServerTime) {
		serverTime = LocalTime.ofSecondOfDay(newServerTime);
		uiServer.setCurrentTime(serverTime);
	}

	public void runUiServer() {
		uiServer = new UiServer(serverTime);
		uiServer.setVisible(true);
	}

}