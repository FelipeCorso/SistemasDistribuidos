package br.furb.common;

import java.io.Serializable;

public class Produto implements Serializable {

    private int codigoProduto;
    private String descricaoProduto;
    private int qtdProduto;
    private double valorUnitario;

    public Produto(Produto umProduto) {
        setCodigoProduto(umProduto.getCodigoProduto());
        setDescricaoProduto(umProduto.getDescricaoProduto());
        setQtdProduto(umProduto.getQtdProduto());
        setValorUnitario(umProduto.getValorUnitario());
    }

    public Produto() {

    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public int getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
        this.qtdProduto = qtdProduto;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigoProduto + "; " + "Descrição: " + descricaoProduto + "; " + "Qtd: " + qtdProduto + "; " + "Valor: " + valorUnitario + "; ";
    }

}
