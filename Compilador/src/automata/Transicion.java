package automata;

import java.util.ArrayList;
import java.util.List;

public class Transicion {
	
	private Integer destino;
	private List<Character> simbolos;
	
	public static final List<Character> letra = letra();
	public static final List<Character> digito = digito();
	public static final List<Character> caracter = caracter();
	
	public Transicion(Integer destino) {
		this.destino = destino;
		this.simbolos = new ArrayList<Character>();
	}
	
	public Transicion(Integer destino, Character simbolo) {
		this(destino);
		this.simbolos.add(simbolo);
	}
	
	public Transicion(Integer destino, List<Character> simbolos) {
		this(destino);
		for(Character simbolo : simbolos) {
			this.simbolos.add(simbolo);
		}
	}
	
	public void asignarDestino(Integer destino) {
		this.destino = destino;
	}
	
	public Integer destino() {
		return this.destino;
	}
	
	public List<Character> simbolos(){
		return this.simbolos;
	}
	
	public String toString() {
		return "(" + this.destino + ", " + this.simbolos + ")";
	}
	
	private static List<Character> letra(){
		List<Character> letra = new ArrayList<Character>();
		for(int i=97; i<=122; i++) {
			letra.add((char)(i));
		}
		for(int i=65; i<=90; i++) {
			letra.add((char)(i));
		}
		return letra;
	}
	
	private static List<Character> digito(){
		List<Character> digito = new ArrayList<Character>();
		for(int i=48; i<=57; i++) {
			digito.add((char)(i));
		}
		return digito;
	}
	
	private static List<Character> caracter(){
		List<Character> caracter = new ArrayList<Character>();
		for(int i=32; i<=126; i++) {
			caracter.add((char)(i));
		}
		return caracter;
	}
	
}