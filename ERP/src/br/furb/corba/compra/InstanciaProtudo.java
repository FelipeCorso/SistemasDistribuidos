package br.furb.corba.compra;

public class InstanciaProtudo {

	public static Produto construtorProduto() {
		try {	
			Produto produto = null;
			return produto;
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		return null;
	}
}
