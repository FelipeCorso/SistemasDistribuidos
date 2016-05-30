package br.furb.ws.venda.server;

import javax.xml.ws.Endpoint;

import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.TypeServer;

public class VendaServerPublisher {

    public static void main(String[] args) {
<<<<<<< HEAD
	Server server = new Server("127.0.0.1", 9876, TypeServer.WS);
	VendaServerInterface vendaServer = new VendaServerImpl(server);
	Endpoint.publish("http://" + server.getIp() + ":" + server.getPort() + "/br.furb.ws.venda.server", vendaServer);
	vendaServer.checkIfLeaderIsAlive();
=======
        Endpoint.publish("http://localhost/br.furb.ws.venda.server", new VendaServerImpl());
>>>>>>> master
    }

}
