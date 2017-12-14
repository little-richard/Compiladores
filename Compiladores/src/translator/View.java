package translator;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Choice;
import javax.swing.JMenu;
import java.awt.TextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View extends JFrame {

	private JPanel contentPane;
	public JMenuItem menuTraduzir = new JMenuItem("Traduzir");
	JTextArea textAreaEntrada = new JTextArea();
	JTextArea textAreaSaida = new JTextArea();
	JTextArea textAreaErro = new JTextArea();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the frame.
	 */
	public View() {
		setTitle("Tradutor Compiladores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,30,1000, 850);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(10, 27, 480, 578);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblEntrada = new JLabel("Entrada");
		lblEntrada.setBounds(210, 11, 46, 14);
		panel.add(lblEntrada);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 460, 531);
		panel.add(scrollPane);
		
		textAreaEntrada.setLineWrap(true);
		scrollPane.setViewportView(textAreaEntrada);
		textAreaEntrada.setEditable(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBounds(500, 27, 480, 578);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblSaida = new JLabel("Saida");
		lblSaida.setBounds(224, 14, 46, 14);
		panel_1.add(lblSaida);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 39, 460, 528);
		panel_1.add(scrollPane_1);
		
		textAreaSaida.setLineWrap(true);
		scrollPane_1.setViewportView(textAreaSaida);
		textAreaSaida.setEditable(false);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 994, 21);
		contentPane.add(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		mnArquivo.add(menuTraduzir);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBounds(10, 631, 970, 179);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 950, 157);
		panel_2.add(scrollPane_2);
		
		scrollPane_2.setViewportView(textAreaErro);
		textAreaErro.setEditable(false);
		
		JLabel lblErros = new JLabel("Erros");
		lblErros.setBounds(487, 616, 46, 14);
		contentPane.add(lblErros);
	}
}
