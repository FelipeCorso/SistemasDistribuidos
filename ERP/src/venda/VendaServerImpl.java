package venda;

import javax.jws.WebService;

@WebService(endpointInterface = "venda.VendaServerInterface")
public class VendaServerImpl implements VendaServerInterface {
         
    private boolean comunicarFinancaVenda(){
    	return true;// aqui ficar� a integra��o do Vendas com o financeiro.
    }
     
    public boolean efetuarVendaProduto(){
    	comunicarFinancaVenda();
    	return true;
    }


}
