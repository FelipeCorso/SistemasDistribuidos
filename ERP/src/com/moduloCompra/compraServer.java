package com.moduloCompra;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import java.io.*;

public class compraServer
{
  public static void main(String args[]) {
    try{
      // Cria e inicializa o ORB
      ORB orb = ORB.init(args, null);

      // Cria a implementação e registra no ORB
      compraImpl compras = new compraImpl();

      // Ativa o POA
      POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      // Pega a referência do servidor
      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(compras);
      compra href = compraHelper.narrow(ref);
	  
      // Obtém uma referência para o servidor de nomes
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      NamingContextExt namecontextRef = NamingContextExtHelper.narrow(objRef);

      // Registra o servidor no servico de nomes
      String name = "Compras";
      NameComponent path[] = namecontextRef.to_name(name);
      namecontextRef.rebind(path, href);

      System.out.println("Servidor aguardando requisicoes ....");

      // Aguarda chamadas dos clientes
      orb.run();
    } catch (Exception e) {
        System.err.println("ERRO: " + e);
        e.printStackTrace(System.out);
    }
    System.out.println("Encerrando o Servidor.");
  }
}
