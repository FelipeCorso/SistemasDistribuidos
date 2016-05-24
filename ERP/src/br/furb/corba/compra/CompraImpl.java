package br.furb.corba.compra;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.common.UpdateServerTime;
import br.furb.corba.compra.client.CompraClientEstoque;
import br.furb.ui.UiServer;

public class CompraImpl extends CompraPOA {

	private LocalTime serverTime = LocalTime.now();
	private UiServer uiServer;
	private CompraClientEstoque clientEstoque;

	public CompraImpl() {
		runUiServer();
		clientEstoque = new CompraClientEstoque();
	}

	  public boolean recebeNota (br.furb.corba.compra.Produto umProduto) {
		  System.out.println("Compras recebeu nota fiscal de entrada");	  
		  return comunicaEstoque(umProduto);
	  };

	  private boolean comunicaEstoque(br.furb.corba.compra.Produto umProduto) {  	  
		  clientEstoque.solicitandoEstoque(umProduto);
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