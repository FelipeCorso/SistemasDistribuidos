package br.furb.ws.venda.server;

import javax.xml.ws.Endpoint;

public class VendaServerPublisher {

	public static void main(String[] args) {
		Endpoint.publish("http://127.0.0.1:9876/venda", new VendaServerImpl());
		System.out.println("Servidor no Ar!!!");
	}

}
