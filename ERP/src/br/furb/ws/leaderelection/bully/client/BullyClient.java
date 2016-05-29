package br.furb.ws.leaderelection.bully.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.bully.server.BullyServerInterface;

public class BullyClient {

	public void addServer(Server server) throws MalformedURLException {
		URL url = new URL("http://127.0.0.1:9090/bully?wsdl");
		QName qname = new QName("http://bully/", "BullyServerImplService");
		Service ws = Service.create(url, qname);
		BullyServerInterface bullyServer = ws.getPort(BullyServerInterface.class);
		bullyServer.addServer(server);
	}

	public Server getLeader() throws MalformedURLException {
		URL url = new URL("http://127.0.0.1:9090/bully?wsdl");
		QName qname = new QName("http://bully/", "BullyServerImplService");
		Service ws = Service.create(url, qname);
		BullyServerInterface bullyServer = ws.getPort(BullyServerInterface.class);
		return bullyServer.getLeader();
	}

	public void electServer(Server server) throws MalformedURLException {
		URL url = new URL("http://127.0.0.1:9090/bully?wsdl");
		QName qname = new QName("http://bully/", "BullyServerImplService");
		Service ws = Service.create(url, qname);
		BullyServerInterface bullyServer = ws.getPort(BullyServerInterface.class);
		bullyServer.electServer(server);
	}
}
