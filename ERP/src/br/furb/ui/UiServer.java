package br.furb.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledDocument;

public class UiServer extends JFrame {

	private static final long serialVersionUID = -5095957758803292405L;
	private JPanel contentPane;
	private LocalTime serverTime;
	private JLabel lblCurrentTime;
	private JTextPane textPane = new JTextPane();
	private StyledDocument serverLog = textPane.getStyledDocument();

	/**
	 * Create the frame.
	 * 
	 * @param serverTime
	 */

	public UiServer(LocalTime serverTime_) {
		serverTime = serverTime_;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblTime = new JLabel("Hora:");
		lblCurrentTime = new JLabel(serverTime.toString());

		JLabel lblDefineTime = new JLabel("Definir hora:");
		JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss.S");
		timeSpinner.setEditor(timeEditor);

		Instant instant = serverTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();

		timeSpinner.setValue(Date.from(instant));

		JButton btnDefine = new JButton("Definir");
		btnDefine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Date time = (Date) timeSpinner.getValue();
				Instant instant = Instant.ofEpochMilli(time.getTime());
				serverTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
				setCurrentTime(serverTime);
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblDefineTime)
								.addComponent(lblTime, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE, 106,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDefine))
								.addComponent(lblCurrentTime)))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblTime)
								.addComponent(lblCurrentTime))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblDefineTime)
								.addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnDefine))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)));
		scrollPane.setViewportView(textPane);

		textPane.setEditable(false);
		contentPane.setLayout(gl_contentPane);

	}

	public void setCurrentTime(LocalTime localTime) {
		this.lblCurrentTime.setText(localTime.toString());
	}

	public void addServerLog(String log) {
		try {
			serverLog.insertString(serverLog.getLength(), log + "\n", null);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void NomeServidor(String nome){
		this.setTitle(nome);
	}
}
