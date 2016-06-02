package br.furb.ws.venda.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.joda.time.LocalTime;

import br.furb.ws.leaderelection.Server;
import br.furb.ws.status.Status;
import br.furb.ws.venda.server.VendaServerInterface;

public class VendaClient {

    private static final String DEFAULT_LOCALHOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 9876;
    private static final VendaClient INSTANCE = new VendaClient();
    private static final String URL_VENDA_WS = "http://{0}:{1}/br.furb.ws.venda.server?wsdl";
    private static final String NAMESPACE_VENDA_WS = "http://server.venda.ws.furb.br/";
    private String host;
    private int port;

    private VendaClient() {
        // Singleton
    }

    public static VendaClient getInstance() {
        return INSTANCE;
    }

    public String getServerURL() {

        if (getHost() == null || getHost().isEmpty()) {
            setHost(DEFAULT_LOCALHOST);
        }
        if (getPort() == 0) {
            setPort(DEFAULT_PORT);
        }
        return MessageFormat.format(URL_VENDA_WS, getHost(), String.valueOf(getPort()));
    }

    public static void main(String args[]) throws Exception {
        URL url = new URL(VendaClient.getInstance().getServerURL());
        QName qname = new QName(NAMESPACE_VENDA_WS, "VendaServerImplService");
        Service ws = Service.create(url, qname);
        VendaServerInterface venda = ws.getPort(VendaServerInterface.class);
        Server server = venda.getServer();
        System.out.println("IP: " + server.getIp());
        System.out.println("Port: " + server.getPort());
        System.out.println("Type: " + server.getTypeServer().toString());
    }

    public VendaServerInterface retornaClientVendas() {
        try {
            URL url = new URL(getServerURL());
            QName qname = new QName(NAMESPACE_VENDA_WS, "VendaServerImplService");
            Service ws = Service.create(url, qname);
            VendaServerInterface venda = ws.getPort(VendaServerInterface.class);
            return venda;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocalTime getServerTime() {
        try {
            URL url = new URL(getServerURL());
            QName qname = new QName(NAMESPACE_VENDA_WS, "VendaServerImplService");
            Service ws = Service.create(url, qname);
            VendaServerInterface vendaServer = ws.getPort(VendaServerInterface.class);
            return vendaServer.getServerTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LocalTime.now();
    }

    public void setServerTime(LocalTime wsTime) {
        try {
            URL url = new URL(getServerURL());
            QName qname = new QName(NAMESPACE_VENDA_WS, "VendaServerImplService");
            Service ws = Service.create(url, qname);
            VendaServerInterface vendaServer = ws.getPort(VendaServerInterface.class);
            vendaServer.setServerTime(wsTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Status getServerStatus(Server server) {
        try {
            URL url = new URL("http://" + server.getIp() + ":" + server.getPort() + "/br.furb.ws.venda.server?wsdl");
            QName qname = new QName(NAMESPACE_VENDA_WS, "VendaServerImplService");
            Service ws = Service.create(url, qname);
            VendaServerInterface vendaServer = ws.getPort(VendaServerInterface.class);
            return vendaServer.getServer().getStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Status.INTERNAL_SERVER_ERROR;
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

}
