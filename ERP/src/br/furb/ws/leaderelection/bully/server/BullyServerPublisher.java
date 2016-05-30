package br.furb.ws.leaderelection.bully.server;

import javax.xml.ws.Endpoint;

public class BullyServerPublisher {

    public static void main(String[] args) {
        BullyServerInterface server = new BullyServerImpl();
        Endpoint.publish("http://127.0.0.1:9090/br.furb.ws.leaderelection.bully.server", server);
    }
}
