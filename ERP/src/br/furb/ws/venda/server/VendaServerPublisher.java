package br.furb.ws.venda.server;

import javax.xml.ws.Endpoint;

public class VendaServerPublisher {
 
    public static void main(String[] args)
    {
        Endpoint.publish("http://localhost/venda", new VendaServerImpl());
        System.out.println("Servidor no Ar!!!");
    }



}
