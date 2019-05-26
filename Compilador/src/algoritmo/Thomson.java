package algoritmo;

import java.util.Arrays;
import java.util.List;

import automata.Automata;
import automata.Transicion;

public class Thomson {
	
    private static final List<Character> cerraduras = Arrays.asList('*', '+', '?');
    private static final List<Character> tokens = Arrays.asList('(', ')', '|', '&', '*', '+', '?');
    private static final List<Character> operadores = Arrays.asList('|', '&');
	
	private static String procesarExpresion(String expresion) {
		expresion = expresion.replace("letra", "$");
		expresion = expresion.replace("digito", "#");		
		String nueva = "";
		Integer parentesis = 0;
		for(int i=expresion.length()-1; i>=0; i--) {
			Character c = expresion.charAt(i);
			if(c == '|') {	
				Integer w, p = 0;
				for(w = 0; w < nueva.length(); w++) {
					if(nueva.charAt(w) == '(') { p++; }
					if(nueva.charAt(w) == ')') {
						if(p > 0) { p--; }
						else { break; }
					}
				}
				nueva = "(" + nueva.substring(0, w) + ")" + nueva.substring(w, nueva.length());
				nueva = ")" + c + nueva;
				parentesis++;
			}
			else if(c == '(' && parentesis > 0) {
				nueva = c + "(" + nueva;
				parentesis--;
			}
			else { nueva = c + nueva; }
		}
		while(parentesis > 0) {
			nueva = "(" + nueva;
			parentesis--;
		}		
		expresion = nueva;
		nueva = "";
		for(int i=0; i<expresion.length()-1; i++) {
			Character a = expresion.charAt(i);
			Character b = expresion.charAt(i+1);
			nueva = nueva + a;
			if(tokens.contains(a) == false && tokens.contains(b) == false) {
				nueva = nueva + '&';
			}
			else if(tokens.contains(a) == false && b == '(') {
				nueva = nueva + '&';
			}
			else if(a == ')' && tokens.contains(b) == false) {
				nueva = nueva + '&';
			}
			else if(a == ')' && b == '(') {
				nueva = nueva + '&';
			}
			else if(cerraduras.contains(a) == true && tokens.contains(b) == false) {
				nueva = nueva + '&';
			}
			else if(cerraduras.contains(a) == true && b == '(' ) {
				nueva = nueva + '&';
			}
		}
		nueva = nueva + expresion.charAt(expresion.length()-1);
		return nueva;
	}
	
	private static String[] partirExpresion(String expresion) {
		if(expresion.length() > 1) {
			String r, s, o;
			Integer parentesis = 0;
			for(int i=0; i<expresion.length(); i++) {
				Character c = expresion.charAt(i);
				if(operadores.contains(c) && parentesis == 0) {
					r = expresion.substring(0, i);
					s = expresion.substring(i+1, expresion.length());
					o = String.valueOf(expresion.charAt(i));
					return new String[]{r, s, o};
				}
				else if(c == '(') { parentesis++; }
				else if(c == ')') { parentesis--; }
			}
			if(cerraduras.contains(expresion.charAt(expresion.length()-1))) {
				r = expresion.substring(0, expresion.length()-1);
				o = String.valueOf(expresion.charAt(expresion.length()-1));
				return new String[]{r, null, o};
			}
			return partirExpresion(expresion.substring(1, expresion.length()-1));
		}
		return null;
	}
	
	private static Automata RSO(String r, String s, String operacion) {
		Automata a = null, b = null;
		String rso[] = partirExpresion(r);
		if(rso != null) {
			a = RSO(rso[0], rso[1], rso[2]);
		}
		else { 
			for(int i=0; i<r.length(); i++) {
				if(r.charAt(i) != '(' && r.charAt(i) != ')') {
					a = new Automata();
					a.nuevoEstado();
					a.nuevoEstado();
					switch(r.charAt(i)){
						case '#': { a.nuevaTransicion(1, 2, Transicion.digito); break; }
						case '$': { a.nuevaTransicion(1, 2, Transicion.letra); break; }
						default : { a.nuevaTransicion(1, 2, r.charAt(i)); break; }
					}
					break;
				}
			}
		}
		if(operacion != null) {
			if(s != null) {
				rso = partirExpresion(s);
				if(rso != null) {
					b = RSO(rso[0], rso[1], rso[2]);
				}
				else { 
					for(int i=0; i<s.length(); i++) {
						if(s.charAt(i) != '(' && s.charAt(i) != ')') {
							b = new Automata();
							b.nuevoEstado();
							b.nuevoEstado();
							switch(s.charAt(i)){
								case '#': { b.nuevaTransicion(1, 2, Transicion.digito); break; }
								case '$': { b.nuevaTransicion(1, 2, Transicion.letra); break; }
								default : { b.nuevaTransicion(1, 2, s.charAt(i)); break; }
							}
							break;
						}
					}
				}
			}
			switch(operacion.charAt(0)) {
				case '*': { a = a.cerraduraKleene(); break; }
				case '+': { a = a.cerraduraPositiva(); break; }
				case '?': { a = a.cerraduraOpcional(); break; }
				case '&': { a = a.and(b); break; }
				case '|': { a = a.or(b); break; }
			}
		}
		return a;
	}
	
	public static Automata expresion(String expresion) {
		expresion = procesarExpresion(expresion);
		Automata automata = RSO(expresion, null, null);
		automata.estado(automata.numeroEstados()).asignarEstadoFinal(true);
		return automata;
	}
	
}