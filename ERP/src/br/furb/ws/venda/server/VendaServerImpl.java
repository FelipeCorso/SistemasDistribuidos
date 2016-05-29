package br.furb.ws.venda.server;

import java.net.MalformedURLException;
import java.time.LocalTime;

import javax.jws.WebService;

import br.furb.common.UpdateServerTime;
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

	public boolean efetuarVendaProduto() {
		comunicarFinancaVenda();
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
