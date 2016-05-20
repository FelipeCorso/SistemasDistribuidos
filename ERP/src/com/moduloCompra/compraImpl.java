package com.moduloCompra;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import com.moduloCompra.*;

import Estoque.Produto;

import com.*;

class compraImpl extends compraPOA {  

  public boolean recebeNota () {
	  System.out.println("Compras recebeu nota fiscal de entrada");
	  return true;
  };

  public boolean comunicaEstoque () {  	  
	  compraClientEstoque clientEstoque = new compraClientEstoque();
		Produto produto = new Produto();
		produto.setCodigoProduto(1);
		produto.setDescricaoProduto("ServerCompras");
		produto.setQtdProduto(2);	  
	    clientEstoque.solicitandoEstoque(produto);
  	  return true;
  };


}