package br.furb.corba.compra;


/**
* br/furb/corba/compra/CompraOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from br/furb/corba/Compra.idl
* Quinta-feira, 26 de Maio de 2016 11h32min34s BRT
*/

public interface CompraOperations 
{
  boolean recebeNota (int aCodigo, String aNome, int aQuantidade, double aValorUnitario);
  void updateServerTime ();
  long getServerTime ();
  void setServerTime (long localTime);
} // interface CompraOperations
