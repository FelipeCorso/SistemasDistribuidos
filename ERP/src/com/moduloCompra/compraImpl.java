package com.moduloCompra;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import com.moduloCompra.*;

class compraImpl extends compraPOA {  

  public boolean recebeNota () {
	  System.out.println("Compras recebeu nota fiscal de entrada");
	  return true;
  };

  public boolean comunicaEstoque () {  	  
	  System.out.println("Compras comunicou o estoque sobre o produto recebido");
  	  return true;
  };


}