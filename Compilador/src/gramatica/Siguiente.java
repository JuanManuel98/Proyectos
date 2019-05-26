package gramatica;

import java.util.ArrayList;
import java.util.List;

public class Siguiente {
	private String noTerminal;
	private List<String> siguientes;
	
	public Siguiente(String noTerminal) {
		this.noTerminal = noTerminal;
		this.siguientes = new ArrayList<String>();
	}
	
	public void agregarSiguiente(String simbolo) {
		if(this.siguientes.contains(simbolo) == false) {
			this.siguientes.add(simbolo);
		}
	}
	
	public void agregarSiguiente(List<String> siguientes) {
		for(String simbolo : siguientes) {
			if(this.siguientes.contains(simbolo) == false) {
				this.siguientes.add(simbolo);
			}
		}
	}
	
	public String noTerminal(){
		return this.noTerminal;
	}
	
	public List<String> siguientes(){
		return this.siguientes;
	}
	
	public String toString() {
		return "S(" + this.noTerminal + ") = " + this.siguientes;
	}
}
