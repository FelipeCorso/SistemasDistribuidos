package br.furb.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.furb.common.Produto;
import br.furb.corba.compra.Compra;
import br.furb.corba.compra.client.CompraClient;
import br.furb.rmi.estoque.client.ClientEstoque;
import br.furb.ws.venda.client.VendaClient;
import br.furb.ws.venda.server.VendaServerInterface;

import java.awt.Dialog.ModalExclusionType;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaClients extends JFrame {

	private JPanel contentPane;
	private JTextField edCodigoProduto;
	private JTextField edDescricaoProduto;
	private JTextField edQtdProduto;
	private JTextField edValor;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaClients frame = new TelaClients();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void CarregaMemoEstoque(JList listaEstoque) {
		ClientEstoque client = new ClientEstoque();
		try {
			ArrayList<Produto> listaProduto = client.retornaComunicacaoServer().retornarProdutos();
			DefaultListModel modelo = new DefaultListModel();

			for (Produto umProduto : listaProduto) {
				modelo.addElement(umProduto.toString());
			}
			listaEstoque.setModel(modelo);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public TelaClients() {
		setTitle("Tela Client");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JList listaEstoque = new JList();		
		
		JButton btnModuloVenda = new JButton("Modulo Venda");
		btnModuloVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VendaServerInterface venda = VendaClient.retornaClientVendas();
				Produto produto = new Produto();
				int xCodigo = Integer.parseInt(edCodigoProduto.getText());				
				int xQtdProduto = Integer.parseInt(edQtdProduto.getText());
				double xValor = Double.parseDouble(edValor.getText());
				produto.setCodigoProduto(xCodigo);
				produto.setDescricaoProduto(edDescricaoProduto.getText());
				produto.setQtdProduto(xQtdProduto);
				produto.setValorUnitario(xValor);
				venda.efetuarVendaProduto(produto);
			}
		});
		
		JButton btnModuloCompra = new JButton("Modulo Compra");				
		btnModuloCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String [] args = new String[2];
				CompraClient compraClient = new CompraClient();
				Compra compra = CompraClient.retornaClientCompras(args);
				int xCodigo = Integer.parseInt(edCodigoProduto.getText());				
				int xQtdProduto = Integer.parseInt(edQtdProduto.getText());
				double xValor = Float.parseFloat(edValor.getText());				
				compra.recebeNota(xCodigo, edDescricaoProduto.getText(), xQtdProduto, xValor);
			}
		});
		
		JButton btnAtualizarGridEstoque = new JButton("Atualizar Grid Estoque");
		btnAtualizarGridEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CarregaMemoEstoque(listaEstoque);
			}
		});	
		
		JLabel lblProdutosEmEstoque = new JLabel("Produtos em Estoque");
		
		JLabel lblCodigoProduto = new JLabel("Codigo Produto:");
		
		edCodigoProduto = new JTextField();
		edCodigoProduto.setColumns(10);
		
		JLabel lblDescrio = new JLabel("Descrição:");
		
		edDescricaoProduto = new JTextField();
		edDescricaoProduto.setColumns(10);
		
		JLabel lblQuantidade = new JLabel("Quantidade:");
		
		edQtdProduto = new JTextField();
		edQtdProduto.setColumns(10);
		
		JLabel lblValorUnitario = new JLabel("Valor Unitario");
		
		edValor = new JTextField();
		edValor.setColumns(10);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(btnAtualizarGridEstoque)
				.addComponent(listaEstoque, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
				.addComponent(lblProdutosEmEstoque)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblValorUnitario)
						.addComponent(lblDescrio)
						.addComponent(lblCodigoProduto)
						.addComponent(lblQuantidade))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(edDescricaoProduto, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(edCodigoProduto, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(edValor, Alignment.LEADING)
							.addComponent(edQtdProduto, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnModuloCompra)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(8)
							.addComponent(btnModuloVenda))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCodigoProduto)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(edCodigoProduto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(edDescricaoProduto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDescrio))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblQuantidade)
								.addComponent(edQtdProduto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblValorUnitario)
								.addComponent(edValor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(51)
							.addComponent(lblProdutosEmEstoque))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnModuloVenda)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnModuloCompra)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listaEstoque, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(btnAtualizarGridEstoque))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
