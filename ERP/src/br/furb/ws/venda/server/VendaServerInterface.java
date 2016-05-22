package br.furb.ws.venda.server;

import java.time.LocalTime;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface VendaServerInterface {

	@WebMethod
	boolean efetuarVendaProduto();

	@WebMethod
	public void updateServerTime();

	@WebMethod
	public LocalTime getServerTime();

	@WebMethod
	public void setServerTime(LocalTime localTime);
}
