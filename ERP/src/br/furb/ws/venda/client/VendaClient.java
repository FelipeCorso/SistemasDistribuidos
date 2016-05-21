package br.furb.ws.venda.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import br.furb.ws.venda.server.VendaServerInterface;
 
class VendaClient {
 
    public static void main(String args[]) throws Exception {
        URL url = new URL("http://localhost/venda?wsdl");
        QName qname = new QName("http://venda/","VendaServerImplService");
        Service ws = Service.create(url, qname);
        VendaServerInterface venda = ws.getPort(VendaServerInterface.class);
        venda.efetuarVendaProduto();
        
 
    }
}



