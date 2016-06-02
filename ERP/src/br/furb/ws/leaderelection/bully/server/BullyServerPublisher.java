package br.furb.ws.leaderelection.bully.server;

import javax.xml.ws.Endpoint;

import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.TypeServer;

public class BullyServerPublisher {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 9090;
        if (args != null && args.length > 0) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(host, port, TypeServer.BULLY);
        BullyServerInterface bullyServer = new BullyServerImpl();
        Endpoint.publish("http://" + server.getIp() + ":" + server.getPort() + "/br.furb.ws.leaderelection.bully.server", bullyServer);
    }
}
