package interfaz;

import java.util.List;

import algoritmo.AnalizadorSintactico;
import analizador.AnalizadorLexico;
import gramatica.Gramatica;

public class Main {

	public static void main(String[] args) {
		AnalizadorLexico lexico = new AnalizadorLexico();
		lexico.procesarArchivo("archivos/codigo.txt");
		System.out.println("----- Tira de tokens -----");
		lexico.tiraTokens().imprimir();
		System.out.println("----- Tabla de simbolos -----");
		lexico.tablaSimbolos().imprimir();
		System.out.println("----- Tabla de errores -----");
		lexico.tablaErrores().imprimir();
		Gramatica gramatica = new Gramatica("archivos/gramatica.txt");
		//gramatica.agregarAccionesSemanticas("archivos/acciones_semanticas.txt");
		AnalizadorSintactico sintactico = new AnalizadorSintactico(gramatica);
		sintactico.analizar(lexico.tiraTokens());
		//System.out.println(gramatica);
	}

}
