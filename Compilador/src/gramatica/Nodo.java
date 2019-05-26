package gramatica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Nodo {
	
	private String simbolo;
	private Map<String, String> atributos;
	private List<Transicion> hijos;
	
	public Nodo(String simbolo) {
		this.simbolo = simbolo;
		this.atributos = new HashMap<String, String>();
		this.hijos = new ArrayList<Transicion>();
	}
	
	public void agregarHijo(String simbolo) {
		this.hijos.add(0, new Transicion(simbolo, new Nodo(simbolo)));
	}
	
	public Nodo hijo(String simbolo) {
		for(Transicion transicion : this.hijos) {
			if(transicion.simbolo.equals(simbolo) == true) { return transicion.nodo; }
		}
		return null;
	}
	
	public String atributo(String atributo) {
		return atributos.get(atributo);
	}
	
	public void nuevoAtributo(String atributo, String valor) {
		this.atributos.put(atributo, valor);
	}
	
	public static void mostrar(Nodo nodo, Integer numero) {
		String tabs = "";
		for(Integer i=0; i<numero; i++) { tabs += "... "; }
		System.out.println(tabs + nodo.simbolo());
		for(Transicion transicion : nodo.hijos) {
			mostrar(transicion.nodo, numero+1);
		}
	}
	
	public String simbolo() {
		return this.simbolo;
	}
	
	public String toString() {
		return "(" + this.simbolo + " -> " + this.hijos + ")";
	}
	
}

class Transicion {
	
	public String simbolo;
	public Nodo nodo;
	
	public Transicion(String simbolo, Nodo nodo) {
		this.simbolo = simbolo;
		this.nodo = nodo;
	}
	
	public String toString() {
		return this.nodo.toString();
	}
	
}