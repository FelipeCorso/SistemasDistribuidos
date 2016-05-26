/** HelloServer.java **/
package br.furb.rmi.estoque.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.ArrayList;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.common.Produto;
import br.furb.common.UpdateServerTime;
import br.furb.rmi.estoque.Estoque;
import br.furb.ui.UiServer;
import br.furb.rmi.estoque.ArquivoEstoque;

public class EstoqueServer extends UnicastRemoteObject implements Estoque {

	private LocalTime serverTime = LocalTime.now();
	private UiServer uiServer;
	private ArquivoEstoque controleEstoque;

	public EstoqueServer() throws RemoteException {
		runUiServer();
		controleEstoque = new ArquivoEstoque();
		uiServer.addServerLog("Servidor aguardando requisicoes ....");
	}

	// main()
	public static void main(String[] args) {
		try {
			EstoqueServer obj = new EstoqueServer();
			Naming.rebind("//localhost/Estoque", obj);
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
	}
	
	public String receberProduto(Produto umProduto) throws RemoteException {
		try {
			uiServer.addServerLog("Servidor executando receberProduto()");
			controleEstoque.addProdutoEstoque(umProduto);
			return "Produto recebido";
		} catch (Exception e) {
			return "Produto não recebido";
		}				
	}

	public String retirarProduto(Produto produtoRetornado) throws RemoteException {
		uiServer.addServerLog("Servidor executando retirarProduto()");
		produtoRetornado = controleEstoque.retiraProdutoEstoque(produtoRetornado);
		if (produtoRetornado != null) {
			return "Produto retirado com sucesso";
		}else
			return "não foi possível retirar o Produto"; 
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
	public LocalTime getServerTime() {
		return serverTime;
	}

	@Override
	public void setServerTime(LocalTime newServerTime) {
		serverTime = newServerTime;
		uiServer.setCurrentTime(serverTime);
	}

	public void runUiServer() {
		uiServer = new UiServer(serverTime);
		uiServer.setVisible(true);
	}
}
