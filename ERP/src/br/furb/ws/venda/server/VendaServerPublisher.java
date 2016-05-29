package br.furb.ws.venda.server;

import java.net.MalformedURLException;

import javax.xml.ws.Endpoint;

import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.TypeServer;
import br.furb.ws.leaderelection.bully.client.BullyClient;

public class VendaServerPublisher {

	public static void main(String[] args) {
		try {
			Server server = new Server("127.0.0.1", 9876, TypeServer.WS);
			VendaServerInterface vendaServer = new VendaServerImpl(server);
			Endpoint.publish("http://" + server.getIp() + ":" + server.getPort() + "/br.furb.ws.venda.server", vendaServer);
			vendaServer.getUiServer().addServerLog("Servidor Vendas no Ar!!!");

			BullyClient bullyClient = new BullyClient();
			bullyClient.addServer(server);
			Server leader = bullyClient.getLeader();
			if (leader == null) {
				bullyClient.electServer(server);
			} else {
				vendaServer.setLeader(leader);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
