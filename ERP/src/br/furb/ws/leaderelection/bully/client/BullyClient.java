package br.furb.ws.leaderelection.bully.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import br.furb.ws.leaderelection.Server;
import br.furb.ws.leaderelection.bully.server.BullyServerInterface;

public class BullyClient {

    private static final String DEFAULT_LOCALHOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 9090;
    private static final BullyClient INSTANCE = new BullyClient();
    private static final String URL_BULLY = "http://{0}:{1}/br.furb.ws.leaderelection.bully.server?wsdl";
    private static final String NAMESPACE_URI = "http://server.bully.leaderelection.ws.furb.br/";
    private String host;
    private int port;

    private BullyClient() {
        // Singleton
    }

    public static BullyClient getInstance() {
        return INSTANCE;
    }

    public void addServer(Server server) throws MalformedURLException {
        URL url = new URL(getServerURL());
        QName qname = new QName(NAMESPACE_URI, "BullyServerImplService");
        Service ws = Service.create(url, qname);
        BullyServerInterface bullyServer = ws.getPort(BullyServerInterface.class);
        bullyServer.addServer(server);
    }

    public Server getLeader() throws MalformedURLException {
        URL url = new URL(getServerURL());
        QName qname = new QName(NAMESPACE_URI, "BullyServerImplService");
        Service ws = Service.create(url, qname);
        BullyServerInterface bullyServer = ws.getPort(BullyServerInterface.class);
        return bullyServer.getLeader();
    }

    public void electServer(Server server) throws MalformedURLException {
        URL url = new URL(getServerURL());
        QName qname = new QName(NAMESPACE_URI, "BullyServerImplService");
        Service ws = Service.create(url, qname);
        BullyServerInterface bullyServer = ws.getPort(BullyServerInterface.class);
        bullyServer.electServer(server);
    }

    public String getServerURL() {

        if (getHost() == null || getHost().isEmpty()) {
            setHost(DEFAULT_LOCALHOST);
        }
        if (getPort() == 0) {
            setPort(DEFAULT_PORT);
        }
        return MessageFormat.format(URL_BULLY, getHost(), String.valueOf(getPort()));
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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
