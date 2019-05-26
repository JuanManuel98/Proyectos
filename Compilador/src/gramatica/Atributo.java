package gramatica;

import java.util.ArrayList;
import java.util.List;

public class Atributo {
	
	private String nombre;
	private List<String> construccion;
	
	public Atributo(String nombre, List<String> construccion) {
		this.nombre = nombre;
		this.construccion = new ArrayList<String>();
		this.construccion.addAll(construccion);
	}
	
	public String nombre() {
		return this.nombre;
	}
	
	public List<String> construccion() {
		return this.construccion;
	}
	
	public Atributo clone() {
		Atributo copia = new Atributo(this.nombre, this.construccion);
		return copia; 
	}
	
	public String toString() {
		return "." + this.nombre + " := " + this.construccion;
	}
	
}