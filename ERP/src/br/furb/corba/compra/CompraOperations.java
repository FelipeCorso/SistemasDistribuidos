package br.furb.corba.compra;


/**
* br/furb/corba/compra/CompraOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Compra.idl
* Domingo, 22 de Maio de 2016 16h24min12s BRT
*/

public interface CompraOperations 
{
  boolean recebeNota ();
  boolean comunicaEstoque ();
  void updateServerTime ();
  long getServerTime ();
  void setServerTime (long localTime);
} // interface CompraOperations