package gramatica;

import java.util.ArrayList;
import java.util.List;

public class AtributoTrad {
	private String nombre;
	private List<List<String>> construccion;
	
	public AtributoTrad(String nombre) {
		this.nombre = nombre;
		this.construccion = new ArrayList<List<String>>();
	}
	
	public void agregarConstruccion(List<List<String>> construccion) {
		this.construccion.addAll(construccion);
	}
	
	public void agregarCadena(List<String> cadena) {
		this.construccion.add(cadena);
	}
	
	public String nombre() {
		return this.nombre;
	}
	
	public List<List<String>> construccion() {
		return this.construccion;
	}
	
	public AtributoTrad clone() {
		AtributoTrad copia = new AtributoTrad(this.nombre);
		for(List<String> string : construccion) {
			copia.agregarCadena(string);
		}
		return copia; 
	}
	
	public String toString() {
		return "." + this.nombre + " := " + this.construccion;
	}
}
