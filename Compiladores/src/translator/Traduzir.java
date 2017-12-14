package translator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Traduzir {
	private Automato automato = new Automato();
	private Map<String, ArrayList<String>> tabelaTransicao;
	private ArrayList<String> tableVariaveis;
	private File traduzido;
	private String currentState;
	
	//Construtor responsável por criar nova tabela de variaveis, receber a tabela de transição do automato e iniciar a váriavel currentState
	Traduzir() {
		tabelaTransicao = automato.getTable();
		currentState = automato.getStart();
		tableVariaveis = new ArrayList<String>();
	}
	
	//Regra semântica, verifica se a variável termina com $ ou se não começa com número
	public boolean verificarVariavel(String var) {
		Character c = var.charAt(0);
		if (var.endsWith("$") && !Character.isDigit(c)) {
			return true;
		} else {
			return false;
		}
	}
	
	//Verifica se determinada entrada é um numero, True = É numero, False = não é numero
	public boolean verificarNum(String num) {
		boolean valido = false;
		for (int i = 0; i < num.length(); i++) {
			Character carac = num.charAt(i);
			if (Character.isDigit(carac)) {
				valido = true;
			} else {
				valido = false;
				break;
			}
		}
		return valido;
	}
	
	//Recebo o arquivo aberto e a referencia da JFrame(Interface gráfica) e começo a tradução do arquivo.
	public void traducao(File arq, View fram) {
		boolean erro = false; // Controle de erro da tradução
		String path = arq.getAbsolutePath(); // Pega o caminho do arquivo a ser aberto
		try {
			BufferedWriter escrever = new BufferedWriter(new FileWriter("Traduzido.c")); // Crio um novo arquivo .c onde será escrito minha tradução
			BufferedReader buffer = new BufferedReader(new FileReader(path)); // Lê arquivo a ser traduzido.
			String linha = ""; // Variavel que recebe as linhas do bufferedReader.
			
			while (true) {
				if (linha != null) {
					if (linha.equals("")) {
						//Imprime linha vazia se caso ela for vazia.
						fram.textAreaSaida.append(linha);
					} else {
						//Se linha nao for vazia começamos a tradução.
						//arrayLinha é onde guarda as palavras separadas por espaço da linha
						String[] arrayLinha = linha.split("\\s");
						int tam = arrayLinha.length; // Quantidade de palavras a serem analizadas na linha.
						for (int i = 0; i < tam; i++) { // Laço responsável por percorrer cada palavra da linha separadamente
							
							switch (currentState) {// Estrutura condicional onde fazemos o automato funcionar, cada Case representa um estado do automato
							case "0":
								if (tabelaTransicao.containsKey(arrayLinha[i])) { // Verifica se existe essa entrada na tabela de transição
									currentState = tabelaTransicao.get(arrayLinha[i]).get(0); // Estado Atual recebe proximo estado
									escrever.append("#include <stdio.h>\n"); // escreve no arquivo a tradução
									fram.textAreaSaida.append("#include <stdio.h>\n"); // escreve na textAreaSaida a tradução
									break;
								} else {
									erro = true; // Se entrou aqui quer dizer que o token lido não existe para essa linguagem, então emite-se o erro
									fram.textAreaErro.append("Esperado Id=Programa " + arrayLinha[i] + "\n");
									break;
								}
							case "1":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(1);
									fram.textAreaSaida.append("int ");
									escrever.append("int ");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=var: " + arrayLinha[i] + "\n");
									break;
								}
							case "2":
								while (verificarVariavel(arrayLinha[i]) && !arrayLinha[i].equals(";")) {
									if(arrayLinha[i+1].equals(";")) {
										fram.textAreaSaida.append(arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")));
										escrever.append(arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")));
										tableVariaveis.add(arrayLinha[i]);
										i++;
									}else {
										fram.textAreaSaida.append(arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")) + ", ");
										escrever.append(arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")) + ", ");
										tableVariaveis.add(arrayLinha[i]);
										i++;
									}
								}
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									fram.textAreaSaida.append(";\n");
									escrever.append(";\n");
									currentState = tabelaTransicao.get(arrayLinha[i]).get(2);
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=;: " + arrayLinha[i] + "\n");
									break;
								}
							case "3":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(3);
									fram.textAreaSaida.append(";" + "\n");
									escrever.append(";\n");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=; : " + arrayLinha[i] + "\n");
									break;
								}
							case "4":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(4);
									fram.textAreaSaida.append("int main() {" + "\n" + "scanf(\"%d\", ");
									escrever.append("int main() {" + "\n" + "scanf(\"%d\", ");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=leia: " + arrayLinha[i] + "\n");
									break;
								}
							case "5":
								if (tableVariaveis.contains(arrayLinha[i])) {
									currentState = tabelaTransicao.get("idVar").get(5);
									fram.textAreaSaida
											.append(arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")) + ");\n");
									escrever.append(arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")) + ");\n");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado uma variável: " + arrayLinha[i] + "\n");
									break;
								}
							case "6":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									if (arrayLinha[i].equals("leia")) {
										currentState = tabelaTransicao.get(arrayLinha[i]).get(6);
										fram.textAreaSaida.append("scanf(\"%d\", ");
										escrever.append("scanf(\"%d\", ");
										break;
									} else {
										currentState = tabelaTransicao.get(arrayLinha[i]).get(6);
										fram.textAreaSaida.append("printf");
										escrever.append("printf");
										break;
									}

								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=leia ou escreva: " + arrayLinha[i] + "\n");
									break;
								}
							case "7":
								if (tableVariaveis.contains(arrayLinha[i])) {
									currentState = tabelaTransicao.get("idVar").get(7);
									fram.textAreaSaida.append("(\"%d\\n\","
											+ arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")) + ");\n");
									escrever.append("(\"%d\\n\","
											+ arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")) + ");\n");
									break;
								} else if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(7);
									escrever.append(arrayLinha[i]);
									fram.textAreaSaida.append(arrayLinha[i]);
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=( ou uma variável: " + arrayLinha[i]);
									break;
								}
							case "8":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									if (arrayLinha[i].equals("leia")) {
										currentState = tabelaTransicao.get(arrayLinha[i]).get(8);
										escrever.append("scanf(\"%d\", ");
										fram.textAreaSaida.append("scanf(\"%d\", ");
										break;
									} else if (arrayLinha[i].equals("escreva")) {
										currentState = tabelaTransicao.get(arrayLinha[i]).get(8);
										escrever.append("printf");
										fram.textAreaSaida.append("printf");
										break;
									} else if (arrayLinha[i].equals("at")) {
										currentState = tabelaTransicao.get(arrayLinha[i]).get(8);
										break;
									}
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado id=at ou leia: " + arrayLinha[i] + "\n");
									break;
								}
							case "9":
								if (!arrayLinha[i].equals(")")) {
									currentState = tabelaTransicao.get("texto").get(9);
									if (i > 1) {
										fram.textAreaSaida.append(arrayLinha[i] + " ");
										escrever.append(arrayLinha[i] + " ");
										break;
									} else {
										escrever.append("\"" + arrayLinha[i] + " ");
										fram.textAreaSaida.append("\"" + arrayLinha[i] + " ");
										break;
									}
								} else if (arrayLinha[i].equals(")")) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(9);
									escrever.append("\")\n");
									fram.textAreaSaida.append("\")\n");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Erro9: " + arrayLinha[i] + "\n");
									break;
								}
							case "10":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									if (arrayLinha[i].equals("escreva")) {
										currentState = tabelaTransicao.get(arrayLinha[i]).get(10);
										escrever.append("printf");
										fram.textAreaSaida.append("printf");
										break;
									} else if (arrayLinha[i].equals("leia")) {
										currentState = tabelaTransicao.get(arrayLinha[i]).get(10);
										escrever.append("scanf(\"%d\", ");
										fram.textAreaSaida.append("scanf(\"%d\", ");
										break;
									} else if (arrayLinha[i].equals("at")) {
										currentState = tabelaTransicao.get(arrayLinha[i]).get(10);
										break;
									}
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=at ou leia: " + arrayLinha[i] + "\n");
									break;
								}
							case "11":
								if (tableVariaveis.contains(arrayLinha[i])) {
									currentState = tabelaTransicao.get("idVar").get(11);
									escrever.append(arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")));
									fram.textAreaSaida.append(arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")));
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado uma variável: " + arrayLinha[i] + "\n");
									break;
								}

							case "12":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(12);
									escrever.append("=");
									fram.textAreaSaida.append("=");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id = = : " + arrayLinha[i] + "\n");
									break;
								}
							case "13":
								if (verificarNum(arrayLinha[i])) {
									currentState = tabelaTransicao.get("num").get(13);
									escrever.append(arrayLinha[i] + "\n");
									fram.textAreaSaida.append(arrayLinha[i] + "\n");
									break;
								} else if (tableVariaveis.contains(arrayLinha[i])) {
									currentState = tabelaTransicao.get("idVar").get(13);
									escrever.append(arrayLinha[i] + ";\n");
									fram.textAreaSaida.append(arrayLinha[i] + ";\n");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append(
											"Esperado um numero inteiro ou uma variavel:  " + arrayLinha[i] + "\n");
									break;
								}
							case "14":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(14);
									if (arrayLinha[i].equals("at")) {
										break;
									} else if (arrayLinha[i].equals("se")) {
										escrever.append("if(");
										fram.textAreaSaida.append("if(");
										break;
									}
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado id= se: " + arrayLinha[i] + "\n");
									break;
								}
							case "15":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(15);
									if (arrayLinha[i].equals("at")) {
										break;
									} else if (arrayLinha[i].equals("se")) {
										escrever.append("if(");
										fram.textAreaSaida.append("if(");
										break;
									}
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado id= se: " + arrayLinha[i] + "\n");
									break;
								}
							case "16":
								if (tableVariaveis.contains(arrayLinha[i])) {
									currentState = tabelaTransicao.get("idVar").get(16);
									fram.textAreaSaida.append(arrayLinha[i]);
									escrever.append(arrayLinha[i]);
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado uma variável: " + arrayLinha[i] + "\n");
									break;
								}
							case "17":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(17);
									fram.textAreaSaida.append(arrayLinha[i]);
									escrever.append(arrayLinha[i]);
									break;
								} else {
									erro = true;
									fram.textAreaErro
											.append("Esperado Operadores (<,>,<=,>=,==,!=): " + arrayLinha[i] + "\n");
									break;
								}
							case "18":
								if (verificarNum(arrayLinha[i])) {
									currentState = tabelaTransicao.get("num").get(18);
									fram.textAreaSaida.append(arrayLinha[i]);
									escrever.append(arrayLinha[i]);
									break;
								} else if (tableVariaveis.contains(arrayLinha[i])) {
									currentState = tabelaTransicao.get("idVar").get(18);
									escrever.append(arrayLinha[i]);
									fram.textAreaSaida.append(arrayLinha[i]);
									break;
								} else {
									erro = true;
									fram.textAreaErro.append(
											"Esperado um numero inteiro ou uma variável: " + arrayLinha[i] + "\n");
									break;
								}
							case "19":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(19);
									escrever.append(")\n{\n");
									fram.textAreaSaida.append(")\n{\n");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=entao: " + arrayLinha[i] + "\n");
									break;
								}
							case "20":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(20);
									escrever.append(")\n{\n");
									fram.textAreaSaida.append(")\n{\n");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=entao: " + arrayLinha[i] + "\n");
									break;
								}
							case "21":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(21);
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Erro20: " + arrayLinha[i] + "\n");
									break;
								}
							case "22":
								if (tableVariaveis.contains(arrayLinha[i])) {
									currentState = tabelaTransicao.get("idVar").get(22);
									fram.textAreaSaida.append(arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")));
									escrever.append(arrayLinha[i].substring(0, arrayLinha[i].indexOf("$")));
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado uma variável: " + arrayLinha[i] + "\n");
									break;
								}
							case "23":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(23);
									fram.textAreaSaida.append(arrayLinha[i]);
									escrever.append(arrayLinha[i]);
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado id= = :" + arrayLinha[i] + "\n");
									break;
								}
							case "24":
								if (verificarNum(arrayLinha[i])) {
									currentState = tabelaTransicao.get("num").get(24);
									fram.textAreaSaida.append(arrayLinha[i]);
									escrever.append(arrayLinha[i]);
									break;
								} else if (tableVariaveis.contains(arrayLinha[i])) {
									currentState = tabelaTransicao.get("idVar").get(24);
									fram.textAreaSaida.append(arrayLinha[i]);
									escrever.append(arrayLinha[i]);
									break;
								} else {
									erro = true;
									fram.textAreaErro.append(
											"Esperado um numero inteiro ou uma variável" + arrayLinha[i] + "\n");
									break;
								}
							case "25":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(25);
									fram.textAreaSaida.append(arrayLinha[i]);
									escrever.append(arrayLinha[i]);
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado operações (+,-,*): " + arrayLinha[i] + "\n");
									break;
								}
							case "26":
								if (verificarNum(arrayLinha[i])) {
									currentState = tabelaTransicao.get("num").get(26);
									fram.textAreaSaida.append(arrayLinha[i]);
									escrever.append(arrayLinha[i]);
									break;
								} else if (tableVariaveis.contains(arrayLinha[i])) {
									currentState = tabelaTransicao.get("idVar").get(26);
									escrever.append(arrayLinha[i]);
									fram.textAreaSaida.append(arrayLinha[i]);
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado num ou variavel: " + arrayLinha[i] + "\n");
									break;
								}
							case "27":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(27);
									escrever.append(arrayLinha[i]);
									fram.textAreaSaida.append(arrayLinha[i]);
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado operações (+,-,*): " + arrayLinha[i] + "\n");
									break;
								}
							case "28":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(28);
									fram.textAreaSaida.append("\n}\nelse\n");
									escrever.append("\n}\nelse\n");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=senao: " + arrayLinha[i] + "\n");
									break;
								}
							case "29":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(29);
									fram.textAreaSaida.append("printf");
									escrever.append("printf");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=escreva: " + arrayLinha[i] + "\n");
									break;
								}
							case "30":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(30);
									fram.textAreaSaida.append("(\"");
									escrever.append("(\"");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=(: " + arrayLinha[i] + "\n");
									break;
								}
							case "31":
								if (!arrayLinha[i].equals(")")) {
									currentState = tabelaTransicao.get("texto").get(31);
									if (i > 1) {
										fram.textAreaSaida.append(arrayLinha[i] + " ");
										escrever.append(arrayLinha[i] + " ");
										break;
									} else {
										escrever.append("\"" + arrayLinha[i] + " ");
										fram.textAreaSaida.append("\"" + arrayLinha[i] + " ");
										break;
									}
								} else if (arrayLinha[i].equals(")")) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(31);
									escrever.append("\\n\");\n");
									fram.textAreaSaida.append("\\n\");\n");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Erro31: " + arrayLinha[i] + "\n");
									break;
								}
							case "32":
								if (tabelaTransicao.containsKey(arrayLinha[i])) {
									currentState = tabelaTransicao.get(arrayLinha[i]).get(32);
									escrever.append("exit(0);\n}");
									fram.textAreaSaida.append("exit(0);\n}");
									break;
								} else {
									erro = true;
									fram.textAreaErro.append("Esperado Id=fim: " + arrayLinha[i]);
									break;
								}
							}
							if(erro)
								break;
						}
					}
				} else
					break;
				// Se houver alguma erro dentro do automato, paramos a execução do programa. 
				if(erro)
					break;
				// senão vamos para a próxima linha
				else
					linha = buffer.readLine();
			}
			escrever.close();
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
