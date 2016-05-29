package br.furb.ws.leaderelection.bully.server;

import br.furb.ui.UiServer;
import br.furb.ws.leaderelection.Server;

public interface BullyServerInterface {

	public UiServer getUiServer();

	public void addServer(Server server);

	public Server getLeader();

	public void electServer(Server server);
}
