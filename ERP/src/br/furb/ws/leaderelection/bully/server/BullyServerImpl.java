package br.furb.ws.leaderelection.bully.server;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.furb.ui.UiServer;
import br.furb.ws.leaderelection.Server;

public class BullyServerImpl implements BullyServerInterface {

	private LocalTime serverTime = LocalTime.now();
	private UiServer uiServer;
	private List<Server> servers = new ArrayList<>();
	private Server leader;

	public BullyServerImpl() {
		runUiServer();
	}

	public void runUiServer() {
		uiServer = new UiServer(serverTime);
		uiServer.setVisible(true);
	}

	@Override
	public UiServer getUiServer() {
		return uiServer;
	}

	private void checkServersStatus() {
		while (true) {
			try {
				Thread.sleep(5000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addServer(Server server) {
		servers.add(server);
	}

	@Override
	public Server getLeader() {
		return leader;
	}

	@Override
	public void electServer(Server server) {
		synchronized (this.leader) {
			this.leader = server;
		}
	}

}
