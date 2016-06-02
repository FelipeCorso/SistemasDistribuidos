package br.furb.ws.venda.server;

import java.net.MalformedURLException;

import javax.xml.ws.Endpoint;

import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.TypeServer;
import br.furb.ws.leaderelection.bully.client.BullyClient;

public class VendaServerPublisher {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 9876;
        if (args != null && args.length > 0) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(host, port, TypeServer.WS);
        VendaServerInterface vendaServer = new VendaServerImpl(server);
        Endpoint.publish("http://" + server.getIp() + ":" + server.getPort() + "/br.furb.ws.venda.server", vendaServer);
        try {
            BullyClient.getInstance().addServer(server);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        vendaServer.checkIfLeaderIsAlive();
    }

}
