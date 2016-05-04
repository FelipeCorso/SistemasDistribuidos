package com;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

import com.moduloCompra.*;

public class compraClient {

  public static void main(String args[]) {
    try {
      // Cria e inicializa o ORB
      ORB orb = ORB.init(args, null);

      // Obtem referencia para o servico de nomes
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      NamingContextExt namecontextRef = NamingContextExtHelper.narrow(objRef);
 
      // Obtem referencia para o servidor
      String name = "Compras";
      compra moduloCompra = compraHelper.narrow(namecontextRef.resolve_str(name));

      moduloCompra.recebeNota();
      moduloCompra.comunicaEstoque();
	      
      System.out.println("Resultado: Compras Cliente Executado.");

    } catch (Exception e) {
        System.out.println("ERROR : " + e) ;
        e.printStackTrace(System.out);
    }
  }
}

