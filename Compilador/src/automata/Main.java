package automata;

import analizador.AnalizadorLexico;

public class Main {

	public static void main(String[] args) {
		AnalizadorLexico a = new AnalizadorLexico();
		a.procesarArchivo("codigo.c");
		System.out.println("----------------------------------- TIRA DE TOKENS");
		a.tiraTokens().imprimir();
		System.out.println("-------------------------------- TABLA DE SIMBOLOS");
		a.tablaSimbolos().imprimir();
		System.out.println("--------------------------------- TABLA DE ERRORES");
		a.tablaErrores().imprimir();
	}
}