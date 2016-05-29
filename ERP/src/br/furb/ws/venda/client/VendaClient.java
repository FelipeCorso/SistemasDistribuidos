package br.furb.ws.venda.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import br.furb.ws.leaderelection.Server;
import br.furb.ws.venda.server.VendaServerInterface;

public class VendaClient {

	public static void main(String args[]) throws Exception {
		URL url = new URL("http://localhost/br.furb.ws.venda.server?wsdl");
		QName qname = new QName("http://server.venda.ws.furb.br/", "VendaServerImplService");
		Service ws = Service.create(url, qname);
		VendaServerInterface venda = ws.getPort(VendaServerInterface.class);
        // venda.efetuarVendaProduto();

	}
	
	public static VendaServerInterface retornaClientVendas(){
		URL url;
		try {
			url = new URL("http://localhost/br.furb.ws.venda.server?wsdl");
			QName qname = new QName("http://server.venda.ws.furb.br/", "VendaServerImplService");
			Service ws = Service.create(url, qname);
			VendaServerInterface venda = ws.getPort(VendaServerInterface.class);
			return venda;			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public LocalTime getServerTime() throws MalformedURLException {
		URL url = new URL("http://localhost/br.furb.ws.venda.server?wsdl");
		QName qname = new QName("http://server.venda.ws.furb.br/", "VendaServerImplService");
		Service ws = Service.create(url, qname);
		VendaServerInterface vendaServer = ws.getPort(VendaServerInterface.class);
		return vendaServer.getServerTime();
	}

	public void setServerTime(LocalTime wsTime) throws MalformedURLException {
		URL url = new URL("http://localhost/br.furb.ws.venda.server?wsdl");
		QName qname = new QName("http://server.venda.ws.furb.br/", "VendaServerImplService");
		Service ws = Service.create(url, qname);
		VendaServerInterface vendaServer = ws.getPort(VendaServerInterface.class);
		vendaServer.setServerTime(wsTime);
	}

	public int getServerStatus(Server leader) {
		// TODO Auto-generated method stub
		return 0;
	}
}