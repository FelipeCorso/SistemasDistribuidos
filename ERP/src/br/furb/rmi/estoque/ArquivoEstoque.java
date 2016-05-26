package br.furb.rmi.estoque;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.furb.common.Produto;
import br.furb.rmi.estoque.client.EstoqueClientCompra;

public class ArquivoEstoque implements Serializable {
	private String arquivo = "src\\br\\furb\\rmi\\estoque\\server\\ProdutoEstoque.json";

	/**
	 * Verifica se o produto existe dentro do estoque.
	 * @param codigoProduto
	 * @return
	 */
	private boolean produtoExisteEstoque(int codigoProduto) {
		Gson gson = new GsonBuilder().create();
		try {
			BufferedReader bufArquivo = new BufferedReader(new FileReader(arquivo));
			if (bufArquivo.ready()) {
				Produto produtoArquivo = gson.fromJson(bufArquivo.readLine(), Produto.class);
				if (produtoArquivo.getCodigoProduto() == codigoProduto) {
					bufArquivo.close();
					return true;
				}
			}
			bufArquivo.close();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Adiciona um novo produto no arquivo de Estoque
	 * @param umProduto Produto Novo
	 */
	private void adicionaProdutoEstoque(Produto umProduto) {
		String arquivoTmp = "ARQUIVO-tmp";

		BufferedWriter writer;
		BufferedReader reader;
		try {
			writer = new BufferedWriter(new FileWriter(arquivoTmp));
			reader = new BufferedReader(new FileReader(arquivo));

			Gson gson = new GsonBuilder().create();

			String linha;
			while ((linha = reader.readLine()) != null) {
				Produto produtoArquivo = gson.fromJson(linha, Produto.class);
				String json = gson.toJson(produtoArquivo);
				writer.write(json);
			}
			
			String json = gson.toJson(umProduto);
			writer.write(json);

			writer.close();
			reader.close();

			new File(arquivo).delete();
			new File(arquivoTmp).renameTo(new File(arquivo));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Le o arquivo de estoque e adiciona o produto no estoque
	 * @param umProduto Produto que deve ser incrementado.
	 */
	private void incrementaQtdProdutoEstoque(Produto umProduto) {
		String arquivoTmp = "ARQUIVO-tmp";

		BufferedWriter writer;
		BufferedReader reader;
		try {
			writer = new BufferedWriter(new FileWriter(arquivoTmp));
			reader = new BufferedReader(new FileReader(arquivo));

			Gson gson = new GsonBuilder().create();

			String linha;
			while ((linha = reader.readLine()) != null) {
				Produto produtoArquivo = gson.fromJson(linha, Produto.class);
				if (produtoArquivo.getCodigoProduto() == umProduto.getCodigoProduto()) {
					produtoArquivo.setQtdProduto(produtoArquivo.getQtdProduto() + umProduto.getQtdProduto());
				}
				String json = gson.toJson(produtoArquivo);
				writer.write(json);
			}

			writer.close();
			reader.close();

			new File(arquivo).delete();
			new File(arquivoTmp).renameTo(new File(arquivo));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Metodo vai ate o arquivo que contem os produtos e retiva uma quantidade x
	 * do produto no estoque, caso essa quantidade zera o estoque, ent�o � solicitado 
	 * para o server de compras comprar mais produtos.
	 * @param produto: Deve conter o codigo e a quantidade desejada.
	 * @return Retorna o produto completo com a quantidade desejada.
	 */
	
	private Produto retiraProdutoEstoqueInterno(Produto produto) {
		String arquivoTmp = "ARQUIVO-tmp";

		BufferedWriter writer;
		BufferedReader reader;
		try {
			writer = new BufferedWriter(new FileWriter(arquivoTmp));
			reader = new BufferedReader(new FileReader(arquivo));

			Gson gson = new GsonBuilder().create();
			Produto newProduto = null;

			String linha;
			while ((linha = reader.readLine()) != null) {
				Produto produtoArquivo = gson.fromJson(linha, Produto.class);
				if (produtoArquivo.getCodigoProduto() == produto.getCodigoProduto()) {
					if (produtoArquivo.getQtdProduto() > produto.getQtdProduto()) {
						// se tiver produto suficiente, ent�o pode retorar o produto
						newProduto = new Produto();
						newProduto.setCodigoProduto(produtoArquivo.getCodigoProduto());
						newProduto.setDescricaoProduto(produtoArquivo.getDescricaoProduto());
						newProduto.setQtdProduto(produto.getQtdProduto());
						newProduto.setValorUnitario(produtoArquivo.getValorUnitario());

						produtoArquivo.setQtdProduto(produtoArquivo.getQtdProduto() - produto.getQtdProduto());
					}else{
                      EstoqueClientCompra clientCompra = new EstoqueClientCompra();
                      clientCompra.cominicarCompra(produtoArquivo);
					}
				}
				String json = gson.toJson(produtoArquivo);
				writer.write(json);
			}

			writer.close();
			reader.close();

			new File(arquivo).delete();
			new File(arquivoTmp).renameTo(new File(arquivo));

			return newProduto;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo adiciona mais produtos no Estoque(Adiciona/Incrementa)
	 * @param umProduto Produto que deseja adicionar
	 */
	public void addProdutoEstoque(Produto umProduto) {
		if (produtoExisteEstoque(umProduto.getCodigoProduto())) {
			incrementaQtdProdutoEstoque(umProduto);
		} else {
			adicionaProdutoEstoque(umProduto);
		}

	}

	/**
	 * Retira o produto do estoque(decrementa a quantidade)
	 * @param produto Deve conter o codigo e a quantidade do produto
	 * @return Retorna o produto caso haja no estoque.
	 */
	public Produto retiraProdutoEstoque(Produto produto) {
		if (produtoExisteEstoque(produto.getCodigoProduto())) {
			return retiraProdutoEstoqueInterno(produto);
		}
		return null;
	}
}
