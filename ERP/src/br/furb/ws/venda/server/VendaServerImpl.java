package br.furb.ws.venda.server;

import java.net.MalformedURLException;
import java.time.LocalTime;

import javax.jws.WebService;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import br.furb.common.Produto;
import br.furb.common.UpdateServerTime;
import br.furb.rmi.estoque.Estoque;
import br.furb.rmi.estoque.client.ClientEstoque;
import br.furb.ui.UiServer;
import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.bully.BullyAlgorithm;
import br.furb.ws.leaderelection.bully.client.BullyClient;

@WebService(endpointInterface = "br.furb.ws.venda.server.VendaServerInterface")
public class VendaServerImpl implements VendaServerInterface {

	private LocalTime serverTime = LocalTime.now();
	private UiServer uiServer;
	private Server server; // O próprio servidor

	public VendaServerImpl(Server server) {
		this.server = server;
		runUiServer();
		uiServer.addServerLog("Servidor aguardando requisicoes ....");
		checkIfLeaderIsAlive();
	}

	private void checkIfLeaderIsAlive() {
		while (true) {
			try {
				Thread.sleep(5000);
				BullyAlgorithm bullyAlgorithm = new BullyAlgorithm();
				bullyAlgorithm.checkIfLeaderIsAlive(getServer());
			} catch (InterruptedException | MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean comunicarFinancaVenda() {
		return true;// aqui ficará a integração do Vendas com o financeiro.
	}

	public boolean efetuarVendaProduto(Produto produto) {
		try {
			uiServer.addServerLog("executando efetuarVendaProduto()");
			ClientEstoque estoqueClient = new ClientEstoque();
			Estoque estoque = estoqueClient.retornaComunicacaoServer();
			
			uiServer.addServerLog("comunicando Estoque server");
			uiServer.addServerLog("Estoque server retorno: " + estoque.retirarProduto(produto));			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void updateServerTime() {

		while (true) {
			try {
				Thread.sleep(5000);
				BullyClient bullyClient = new BullyClient();
				Server leader = bullyClient.getLeader();
				if (this.getServer().equals(leader)) {
					UpdateServerTime.update(uiServer);
				}
			} catch (InterruptedException | MalformedURLException e) {
				e.printStackTrace();
			}
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
		uiServer.NomeServidor("Server Vendas");
	}

	@Override
	public UiServer getUiServer() {
		return uiServer;
	}

	// @Override
	// public Server getLeader() {
	// return leader;
	// }
	//
	// @Override
	// public void setLeader(Server leader) {
	// this.leader = leader;
	// }

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

}
