package translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Automato {
	private Map<String, ArrayList<String>> tableT = new HashMap<String, ArrayList<String>>(); //criando estrutura da tabela de transi��o
	final String start = "0"; //constante que representa o estado inicial
	final String end = "33"; //constante que representa o estado final

	public Automato() {
		//Sempre que crio meu objeto automato � gerado a tabela de transi��o.
		gerarTabelaTransicao();
	}
	//Retorna estado inicial do automato
	public String getStart() {
		return start;
	}
	
	//Retorna estado final do automato
	public String getEnd() {
		return end;
	}
	
	//Cria a tabela de transi��es do automato
	private void gerarTabelaTransicao() {
		// Inserindo transi��es para entrada Programa
		ArrayList<String> list_Prog = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 0) {
				list_Prog.add(i, "1");
			} else {
				list_Prog.add(i, "X");
			}
		}
		tableT.put("Programa", list_Prog);

		// Inserindo transi��es para entrada var
		ArrayList<String> list_Var = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 1) {
				list_Var.add(i, "2");
			} else {
				list_Var.add(i, "X");
			}
		}
		tableT.put("var", list_Var);

		// Inserindo transi��es para entrada ;
		ArrayList<String> list_PontV = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 2 || i == 3) {
				list_PontV.add(i, "4");
			} else if (i == 25 || i == 27) {
				list_PontV.add(i, "28");
			} else {
				list_PontV.add(i, "X");
			}
		}
		tableT.put(";", list_PontV);

		// Inserindo transi��es para leia
		ArrayList<String> list_Leia = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 4 || i == 6 || i == 8 || i == 10) {
				list_Leia.add(i, "5");
			} else {
				list_Leia.add(i, "X");
			}
		}
		tableT.put("leia", list_Leia);

		// Inserindo Transi��es para escreva
		ArrayList<String> list_Escreva = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 6 || i == 8 || i == 10) {
				list_Escreva.add(i, "7");
			} else if (i == 29) {
				list_Escreva.add(i, "30");
			} else {
				list_Escreva.add(i, "X");
			}
		}
		tableT.put("escreva", list_Escreva);

		// Inserindo Transi��es para "texto"
		ArrayList<String> list_Texto = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 9) {
				list_Texto.add(i, "9");
			} else if (i == 31) {
				list_Texto.add(i, "31");
			} else {
				list_Texto.add(i, "X");
			}
		}
		tableT.put("texto", list_Texto);

		// Inserindo transi��es para "("
		ArrayList<String> list_AbreP = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 7) {
				list_AbreP.add(i, "9");
			} else if (i == 30) {
				list_AbreP.add(i, "31");
			} else {
				list_AbreP.add(i, "X");
			}
		}
		tableT.put("(", list_AbreP);

		// Inserindo transi��es para ")"
		ArrayList<String> list_FechaP = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 9) {
				list_FechaP.add(i, "10");
			} else if (i == 31) {
				list_FechaP.add(i, "32");
			} else {
				list_FechaP.add(i, "X");
			}
		}
		tableT.put(")", list_FechaP);

		// Inserindo transi��es para "at"
		ArrayList<String> list_At = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 8 || i == 10 || i == 14 || i == 15) {
				list_At.add(i, "11");
			} else if (i == 21) {
				list_At.add(i, "22");
			} else {
				list_At.add(i, "X");
			}
		}
		tableT.put("at", list_At);

		// Inserindo transi��es para "="
		ArrayList<String> list_SinalAt = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 12) {
				list_SinalAt.add(i, "13");
			} else if (i == 23) {
				list_SinalAt.add(i, "24");
			} else {
				list_SinalAt.add(i, "X");
			}
		}
		tableT.put("=", list_SinalAt);

		// Inserindo transi��es para "num"
		ArrayList<String> list_Num = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 13) {
				list_Num.add(i, "14");
			} else if (i == 18) {
				list_Num.add(i, "19");
			} else if (i == 24 || i == 26) {
				list_Num.add(i, "27");
			} else {
				list_Num.add(i, "X");
			}
		}
		tableT.put("num", list_Num);

		// Inserindo transi��es para "se"
		ArrayList<String> list_Se = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 14 || i == 15) {
				list_Se.add(i, "16");
			} else {
				list_Se.add(i, "X");
			}
		}
		tableT.put("se", list_Se);

		// Inserindo transi��es para "entao"
		ArrayList<String> list_Entao = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 19 || i == 20) {
				list_Entao.add(i, "21");
			} else {
				list_Entao.add(i, "X");
			}
		}
		tableT.put("entao", list_Entao);

		// Inserindo transi��es para "fim"
		ArrayList<String> list_Fim = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 32) {
				list_Fim.add(i, "33");
			} else {
				list_Fim.add(i, "X");
			}
		}
		tableT.put("fim", list_Fim);

		// Inserindo transi��es para "idVar"
		ArrayList<String> list_IdVar = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 2) {
				list_IdVar.add(i, "3");
			} else if (i == 5) {
				list_IdVar.add(i, "6");
			} else if (i == 7) {
				list_IdVar.add(i, "8");
			} else if (i == 11) {
				list_IdVar.add(i, "12");
			} else if (i == 13) {
				list_IdVar.add(i, "15");
			} else if (i == 16) {
				list_IdVar.add(i, "17");
			} else if (i == 18) {
				list_IdVar.add(i, "20");
			} else if (i == 24 || i == 26) {
				list_IdVar.add(i, "25");
			} else if (i == 22) {
				list_IdVar.add(i, "23");
			} else {
				list_IdVar.add(i, "X");
			}
		}
		tableT.put("idVar", list_IdVar);

		// Inserindo transi��es para ">"
		ArrayList<String> list_Maior = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 17) {
				list_Maior.add(i, "18");
			} else {
				list_Maior.add(i, "X");
			}
		}
		tableT.put(">", list_Maior);

		// Inserindo transi��es para "<"
		ArrayList<String> list_Menor = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 17) {
				list_Menor.add(i, "18");
			} else {
				list_Menor.add(i, "X");
			}
		}
		tableT.put("<", list_Menor);

		// Inserindo trasi��es para ">="
		ArrayList<String> list_MaiorIgual = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 17) {
				list_MaiorIgual.add(i, "18");
			} else {
				list_MaiorIgual.add(i, "X");
			}
		}
		tableT.put(">=", list_MaiorIgual);

		// Inserindo transi��es para "<="
		ArrayList<String> list_MenorIgual = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 17) {
				list_MenorIgual.add(i, "18");
			} else {
				list_MenorIgual.add(i, "X");
			}
		}
		tableT.put("<=", list_MenorIgual);

		// Inserindo transi��es para "!="
		ArrayList<String> list_Diferente = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 17) {
				list_Diferente.add(i, "18");
			} else {
				list_Diferente.add(i, "X");
			}
		}
		tableT.put("!=", list_Diferente);

		// Inserindo transi��es para "+"
		ArrayList<String> list_Mais = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 25 || i == 27) {
				list_Mais.add(i, "26");
			} else {
				list_Mais.add(i, "X");
			}
		}
		tableT.put("+", list_Mais);

		// Inserindo transi��es para "-"
		ArrayList<String> list_Menos = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 25 || i == 27) {
				list_Menos.add(i, "26");
			} else {
				list_Menos.add(i, "X");
			}
		}
		tableT.put("-", list_Menos);

		// Inserindo transi��es para "*"
		ArrayList<String> list_Mult = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 25 || i == 27) {
				list_Mult.add(i, "26");
			} else {
				list_Mult.add(i, "X");
			}
		}
		tableT.put("*", list_Mult);

		// Inserindo transi��es para "senao"
		ArrayList<String> list_Senao = new ArrayList<String>();
		for (int i = 0; i <= 33; i++) {
			if (i == 28) {
				list_Senao.add(i, "29");
			} else {
				list_Senao.add(i, "X");
			}
		}
		tableT.put("senao", list_Senao);
	}
	
	//Retorna a tabela de transi��o do automato
	public Map<String, ArrayList<String>> getTable() {
		return tableT;
	}
}
