package venda;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
 
class VendaClient {
 
    public static void main(String args[]) throws Exception {
        URL url = new URL("http://localhost/venda?wsdl");
        QName qname = new QName("http://venda/","VendaServerImplService");
        Service ws = Service.create(url, qname);
        VendaServerInterface venda = ws.getPort(VendaServerInterface.class);
        venda.efetuarVendaProduto();
        
 
    }
}



