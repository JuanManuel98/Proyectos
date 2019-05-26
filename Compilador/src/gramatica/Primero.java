package gramatica;

import java.util.ArrayList;
import java.util.List;

public class Primero {
	
	private String noTerminal;
	private List<String> primeros;
	
	public Primero(String noTerminal) {
		this.noTerminal = noTerminal;
		this.primeros = new ArrayList<String>();
	}
	
	public void agregarPrimero(String simbolo) {
		if(this.primeros.contains(simbolo) == false) {
			this.primeros.add(simbolo);
		}
	}
	
	public void agregarPrimero(List<String> primeros) {
		for(String simbolo : primeros) {
			if(this.primeros.contains(simbolo) == false) {
				this.primeros.add(simbolo);
			}
		}
	}
	
	public String noTerminal(){
		return this.noTerminal;
	}
	
	public List<String> primeros(){
		return this.primeros;
	}
	
	public String toString() {
		return "P(" + this.noTerminal + ") = " + this.primeros;
	}
	
}