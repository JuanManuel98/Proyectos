package analizador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import algoritmo.Subconjunto;
import algoritmo.Thomson;
import automata.Automata;

public class AnalizadorLexico {
	
	protected static final Automata reservadas = Subconjunto.construir(Thomson.expresion(
		"include|stdio.h|int|for|if|while|else|switch|char|float|break|printf|scanf|case|return|default"
	));
	protected static final Automata identificador = Subconjunto.construir(Thomson.expresion(
		"letra(letra|digito)*"
	));
	protected static final Automata entero = Subconjunto.construir(Thomson.expresion("(digito)+"));
	protected static final Automata decimal = Subconjunto.construir(Thomson.expresion("(digito)+(.)(digito)+"));
	
	private static final List<Character> simbolos = Arrays.asList(
		(char)33, (char)44, (char)61, (char)59, (char)40, (char)41, (char)123, (char)125, (char)38, (char)124, 
		(char)42, (char)47, (char)37, (char)92, (char)60, (char)62, (char)43, (char)45, (char)34, (char)39, (char)35,
		(char)91, (char)93, (char)58			
	);

	private TiraTokens tiraTokens;
	private TablaSimbolos tablaSimbolos;
	private TablaErrores tablaErrores;
	private BufferedReader buffer;
	
	public AnalizadorLexico() {
		this.tiraTokens = new TiraTokens();
		this.tablaSimbolos = new TablaSimbolos();
		this.tablaErrores = new TablaErrores();
	}
	
	public TiraTokens tiraTokens() {
		return this.tiraTokens;
	}
	
	public TablaSimbolos tablaSimbolos() {
		return this.tablaSimbolos;
	}
	
	public TablaErrores tablaErrores() {
		return this.tablaErrores;
	}
	
	public void procesarArchivo(String ruta) {
		this.generarTiraTokens(ruta);
		this.obtenerTablaSimbolos();
	}
	
	private void obtenerTablaSimbolos() {
		String funcion = null, identificador = null;
		Integer parentesis = 0, llaves = 0;
		for(Token token : this.tiraTokens.tokens()) {
			if(token.token().equals("identificador")) {
				this.tablaSimbolos.nuevoSimbolo(token.lexema(), null, funcion);
			}
			if(funcion == null) {
				if(token.token().equals("identificador")) {
					identificador = token.lexema();
				}
				else if(identificador != null) {
					if(token.token().equals("(")) {
						parentesis++;
					}
					else if(token.token().equals(")") && parentesis == 1) {
						parentesis++;
					}
					else if(token.token().equals("{") && parentesis == 2) {
						funcion = identificador;
						identificador = null;
						parentesis = 0;
						llaves++;
					}
					else {
						identificador = null;
						parentesis = 0;
					}
				}
			}
			else {
				if(token.token().equals("{")) { llaves++; }
				else if(token.token().equals("}")) { llaves--; }
				if(llaves == 0) {
					funcion = null;
				}
			}
		}
	}
	
	private void generarTiraTokens(String ruta) {
		List<String> lineas = this.procesarLineas(ruta);
		Integer numeroLinea = 0;
		for(String linea : lineas) {
			numeroLinea++;
			List<String> palabras = this.partirLinea(linea);
			for(String palabra : palabras) {
				if(palabra.length() == 0) { continue; }
				if(palabra.length() == 1) {
					if(simbolos.contains(palabra.charAt(0)) == true) {
						this.tiraTokens.nuevoToken(numeroLinea, palabra, palabra);
						continue;
					}
				}
				if(reservadas.verificarString(palabra) == true) {
					this.tiraTokens.nuevoToken(numeroLinea, palabra, "reservada");
				}
				else if(identificador.verificarString(palabra) == true) {
					this.tiraTokens.nuevoToken(numeroLinea, palabra, "identificador");
				}
				else if(entero.verificarString(palabra) == true) {
					this.tiraTokens.nuevoToken(numeroLinea, palabra, "entero");
				}
				else if(decimal.verificarString(palabra) == true) {
					this.tiraTokens.nuevoToken(numeroLinea, palabra, "decimal");
				}
				else if(palabra.charAt(0) == '"') {
					this.tiraTokens.nuevoToken(numeroLinea, palabra, "texto");
				}
				else {
					this.tablaErrores.nuevoError(numeroLinea, "Error \"" + palabra + "\"");
				}
			}
		}
	}
	
	private List<String> procesarLineas(String ruta){
		List<String> palabras = new ArrayList<String>();
		try {
			buffer = new BufferedReader(new FileReader(ruta));
			while(true) {
				String linea = buffer.readLine();
				if(linea != null) {
					linea = linea.replace("\t", "");
					Integer i;
					for(i=0; i<linea.length() && linea.charAt(i) == ' '; i++);
					linea = " " +linea.substring(i) + " ";					
					String auxiliar = " ";
					Boolean separar = true;
					for(Integer w=0; w<linea.length()-1; w++) {
						Character caracter = linea.charAt(w);
						if(caracter == '/') {
							if(linea.charAt(w+1) == '/') { break; }							
						}			
						if(caracter == '"') {
							separar = separar == true ? false : true;
							auxiliar += caracter;
						}
						else if(separar == true) {
							if(caracter == ' ') {
								if(auxiliar.charAt(auxiliar.length()-1) != ' ') {
									auxiliar += caracter;
								}
							}
							else if(simbolos.contains(caracter) == true) {
								if(auxiliar.charAt(auxiliar.length()-1) != ' ') { auxiliar += " "; }
								auxiliar += caracter;
								if(linea.charAt(w+1) != ' ') { auxiliar += " "; }
							}
							else { auxiliar += caracter; }
						}
						else { auxiliar += caracter; }
					}
					if(auxiliar.charAt(auxiliar.length()-1) == ' ') {
						auxiliar = auxiliar.substring(0, auxiliar.length()-1);
					}
					palabras.add(auxiliar.length() > 0 ? auxiliar.substring(1) : auxiliar);
					continue;
				}
				return palabras;
			}
		} 
		catch (FileNotFoundException e) { return null; } 
		catch (IOException e) { return null; }
	}
	
	private List<String> partirLinea(String linea){
		List<String> palabras = new ArrayList<String>();
		Integer izquierda=0, derecha=0;
		Boolean partir = true;
		for(Integer i=0; i<linea.length(); i++) {
			Character caracter = linea.charAt(i);
			if(caracter == '"') {
				partir = partir == true ? false : true;
			}
			if(caracter == ' ' && partir == true) {
				palabras.add(linea.substring(izquierda, derecha));
				izquierda = derecha+1;
			}
			derecha++;
		}
		palabras.add(linea.substring(izquierda, derecha));
		return palabras;
	}
	
}