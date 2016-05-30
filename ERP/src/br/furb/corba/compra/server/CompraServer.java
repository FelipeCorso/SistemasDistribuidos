package br.furb.corba.compra.server;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import br.furb.corba.compra.Compra;
import br.furb.corba.compra.CompraHelper;
import br.furb.corba.compra.CompraImpl;

public class CompraServer {

    public static void main(String args[]) {
        try {
            // Cria e inicializa o ORB
            ORB orb = ORB.init(args, null);

            // Cria a implementação e registra no ORB
            CompraImpl compras = new CompraImpl(null);

            // Ativa o POA
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Pega a referência do servidor
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(compras);
            Compra href = CompraHelper.narrow(ref);

            // Obtêm uma referência para o servidor de nomes
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt namecontextRef = NamingContextExtHelper.narrow(objRef);

            // Registra o servidor no servico de nomes
            String name = "Compras";
            NameComponent path[] = namecontextRef.to_name(name);
            namecontextRef.rebind(path, href);

            compras.adicionaLog("Servidor aguardando requisicoes ....");

            // Aguarda chamadas dos clientes
            orb.run();
        } catch (Exception e) {
            System.err.println("ERRO: " + e);
            e.printStackTrace(System.out);
        }
        System.out.println("Encerrando o Servidor.");
    }
}
