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
import br.furb.rmi.estoque.Estoque;
import br.furb.rmi.estoque.client.ClientEstoque;
import br.furb.ui.UiServer;

public class CompraImpl extends CompraPOA {

	private LocalTime serverTime = LocalTime.now();
	private UiServer uiServer;
	private ClientEstoque clientEstoque;

	public CompraImpl() {
		runUiServer();
		clientEstoque = new ClientEstoque();
	}

	  public boolean recebeNota (int aCodigo, String aNome, int aQuantidade, double aValorUnitario) {
		  adicionaLog("Compras recebeu nota fiscal de entrada");	  
		  Produto produto = new Produto();
		  produto.setCodigoProduto(aCodigo);
		  produto.setDescricaoProduto(aNome);
		  produto.setQtdProduto(aQuantidade);
		  produto.setValorUnitario(aValorUnitario);
		  return comunicaEstoque(produto);
	  };

	private boolean comunicaEstoque(Produto umProduto) {
		try {
			adicionaLog("Servidor compras comunicando servidor estoque");
			Estoque estoque = clientEstoque.retornaComunicacaoServer();
			adicionaLog("Retorno Estoque: " + estoque.receberProduto(umProduto));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		uiServer.NomeServidor("Server Compras");
	}

}