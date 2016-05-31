package br.furb.ws.leaderelection.bully.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.bully.server.BullyServerInterface;

public class BullyClient {

    private static final String URL_BULLY = "http://127.0.0.1:9090/br.furb.ws.leaderelection.bully.server?wsdl";
    private static final String NAMESPACE_URI = "http://server.bully.leaderelection.ws.furb.br/";

    public void addServer(Server server) throws MalformedURLException {
        URL url = new URL(URL_BULLY);
        QName qname = new QName(NAMESPACE_URI, "BullyServerImplService");
        Service ws = Service.create(url, qname);
        BullyServerInterface bullyServer = ws.getPort(BullyServerInterface.class);
        bullyServer.addServer(server);
    }

    public Server getLeader() throws MalformedURLException {
        URL url = new URL(URL_BULLY);
        QName qname = new QName(NAMESPACE_URI, "BullyServerImplService");
        Service ws = Service.create(url, qname);
        BullyServerInterface bullyServer = ws.getPort(BullyServerInterface.class);
        return bullyServer.getLeader();
    }

    public void electServer(Server server) throws MalformedURLException {
        URL url = new URL(URL_BULLY);
        QName qname = new QName(NAMESPACE_URI, "BullyServerImplService");
        Service ws = Service.create(url, qname);
        BullyServerInterface bullyServer = ws.getPort(BullyServerInterface.class);
        bullyServer.electServer(server);
    }

    // public static void main(String[] args) {
    // try {
    // BullyClient client = new BullyClient();
    // Server server = client.getLeader();
    // System.out.println("IP: " + server.getIp());
    // System.out.println("Port: " + server.getPort());
    // System.out.println("Type: " + server.getTypeServer());
    // } catch (MalformedURLException e) {
    // e.printStackTrace();
    // }
    // }
}
