package algoritmo;

import analizador.AnalizadorLexico;
import gramatica.Gramatica;

public class Prueba {

	public static void main(String[] args) {
		AnalizadorLexico lexico = new AnalizadorLexico();
		lexico.procesarArchivo("codigo.c");
		System.out.println("----- Tira de tokens -----");
		lexico.tiraTokens().imprimir();
		System.out.println("----- Tabla de simbolos -----");
		lexico.tablaSimbolos().imprimir();
		System.out.println("----- Tabla de errores -----");
		lexico.tablaErrores().imprimir();
		Gramatica gramatica = new Gramatica("Gramatica.txt");
		gramatica.agregarAccionesSemanticas("reglas_semanticas.txt");
		gramatica.agregarReglasSemanticas("reglas_semanticas.txt");
		
		AnalizadorSintactico sintactico = new AnalizadorSintactico(gramatica);
		sintactico.analizar(lexico.tiraTokens());
		
	}

}