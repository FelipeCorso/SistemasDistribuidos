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
	private CompraClientEstoque clientEstoque;

	public CompraImpl() {
		runUiServer();
		clientEstoque = new CompraClientEstoque();
	}

	  public boolean recebeNota (int aCodigo, String aNome, int aQuantidade, double aValorUnitario) {
		  uiServer.addServerLog("Compras recebeu nota fiscal de entrada");	  
		  Produto produto = new Produto();
		  produto.setCodigoProduto(aCodigo);
		  produto.setDescricaoProduto(aNome);
		  produto.setQtdProduto(aQuantidade);
		  produto.setValorUnitario(aValorUnitario);
		  return comunicaEstoque(produto);
	  };

	  private boolean comunicaEstoque(Produto umProduto) {
		  uiServer.addServerLog("Servidor compras comunicando servidor estoque");
		  clientEstoque.solicitandoEstoque(umProduto);
	  	  return true;
	  }
	  
	  public void adicionaLog(String log){
		  uiServer.addServerLog(log);
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