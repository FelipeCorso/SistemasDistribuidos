package br.furb.ws.venda.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

import br.furb.ws.leaderelection.Server;
import br.furb.ws.status.Status;
import br.furb.ws.venda.server.VendaServerInterface;

public class VendaClient {

    private static final String URL_VENDA_WS = "http://127.0.0.1:9876/br.furb.ws.venda.server?wsdl";
    private static final String NAMESPACE_VENDA_WS = "http://server.venda.ws.furb.br/";

    public static void main(String args[]) throws Exception {
	URL url = new URL(URL_VENDA_WS);
	QName qname = new QName(NAMESPACE_VENDA_WS, "VendaServerImplService");
	Service ws = Service.create(url, qname);
	VendaServerInterface venda = ws.getPort(VendaServerInterface.class);
	Server server = venda.getServer();
	System.out.println("IP: " + server.getIp());
	System.out.println("Port: " + server.getPort());
	System.out.println("Type: " + server.getTypeServer().toString());
    }

    public static VendaServerInterface retornaClientVendas() {
	try {
	    URL url = new URL(URL_VENDA_WS);
	    QName qname = new QName(NAMESPACE_VENDA_WS, "VendaServerImplService");
	    Service ws = Service.create(url, qname);
	    VendaServerInterface venda = ws.getPort(VendaServerInterface.class);
	    return venda;
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public LocalTime getServerTime() throws MalformedURLException {
	URL url = new URL(URL_VENDA_WS);
	QName qname = new QName(NAMESPACE_VENDA_WS, "VendaServerImplService");
	Service ws = Service.create(url, qname);
	VendaServerInterface vendaServer = ws.getPort(VendaServerInterface.class);
	return vendaServer.getServerTime();
    }

    public void setServerTime(LocalTime wsTime) throws MalformedURLException {
	URL url = new URL(URL_VENDA_WS);
	QName qname = new QName(NAMESPACE_VENDA_WS, "VendaServerImplService");
	Service ws = Service.create(url, qname);
	VendaServerInterface vendaServer = ws.getPort(VendaServerInterface.class);
	vendaServer.setServerTime(wsTime);
    }

    public Status getServerStatus(Server server) throws MalformedURLException {
	URL url = new URL("http://" + server.getIp() + ":" + server.getPort() + "/br.furb.ws.venda.server?wsdl");
	QName qname = new QName(NAMESPACE_VENDA_WS, "VendaServerImplService");
	try {
	    Service ws = Service.create(url, qname);
	    VendaServerInterface vendaServer = ws.getPort(VendaServerInterface.class);
	    return vendaServer.getServer().getStatus();
	} catch (WebServiceException wse) {
	    wse.printStackTrace();
	    return Status.INTERNAL_SERVER_ERROR;
	}
    }
}