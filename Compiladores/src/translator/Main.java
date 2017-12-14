package translator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class Main {
	static View frame = new View();
	public static File arquivo;
	
	//Função principal
	public static void main(String[] args) {
		//Mostra a interface gráfica na tela
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//Pega ação do menu traduzir, pede que o usuário selecione o arquivo .por e executa a tradução.
		frame.menuTraduzir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.textAreaEntrada.setText("");
				frame.textAreaErro.setText("");
				frame.textAreaSaida.setText("");
				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); // Abre tela de seleção de arquivos
				chooser.setDialogTitle("Selecione o arquivo portugol a ser traduzido"); //Seta titulo da tela de seleção
				chooser.setAcceptAllFileFilterUsed(false); //Não aceita todos os tipos de arquivos
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".por", "por"); //Filtro na tela de seleção para aparecer apenas arquivos .por
				chooser.addChoosableFileFilter(filter); //Adiciono o filtro de arquivos
				int result = chooser.showOpenDialog(null);
				// Se o usuario selecionar um arquivo .por iremos traduzir e imprimir na tela os resultados.
				if (result == JFileChooser.APPROVE_OPTION) {
					arquivo = chooser.getSelectedFile();
					Traduzir tradu = new Traduzir();
					tradu.traducao(arquivo, frame);
					String path = arquivo.getAbsolutePath();
					try {
						//Lê o arquivo e imprime da textAreaEntrada as informações do arquivo .por lido
						BufferedReader buffer = new BufferedReader(new FileReader(path));
						String linha = "";
						while (true) {
							if (linha != null) {
								if (linha == "") {
									//imprime linha vazia
									frame.textAreaEntrada.append(linha);
								} else {
									//imprime linha com informações
									frame.textAreaEntrada.append(linha + "\n");
								}
							} else
								break;
							//proxima linha
							linha = buffer.readLine();
						}
						//Fecha arquivo lido
						buffer.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
	}

}
