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

public class EstoqueServer extends UnicastRemoteObject implements Estoque {

	private LocalTime serverTime = LocalTime.now();
	private UiServer uiServer;

	public EstoqueServer() throws RemoteException {
		runUiServer();
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

	public String receberProduto(ArrayList<Produto> produto) throws RemoteException {

		for (Produto umProduto : produto) {
			if (produtoExiste(umProduto.getCodigoProduto()))
				IncrementaQtdProduto(umProduto);
			else
				AdicionaProduto(umProduto);
		}
		System.out.println("Executando receberProduto()");

		return "Produto Adicionado no Estoque";
	}

	public boolean produtoExiste(int codigoProduto) throws RemoteException {
		System.out.println("Executando produtoExiste()");
		// verifica se o produto já existe na base.
		return true;
	}

	public void AdicionaProduto(Produto produto) {
		System.out.println("Executando AdicionaProduto()");
		// Cria um produto novo no estoque
	}

	public void IncrementaQtdProduto(Produto produto) {
		System.out.println("Executando IncrementaQtdProduto()");
		System.out.println(produto.getCodigoProduto());
		System.out.println(produto.getDescricaoProduto());
		System.out.println(produto.getQtdProduto());

		// Incrementa a quantidade de um produto já existente.
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
