package gramatica;

import java.util.ArrayList;
import java.util.List;

public class Regla {
	
	private String noTerminal;
	private List<String> terminales;
	private List<Atributo> atributos;
	
	public Regla(String noTerminal) {
		this.noTerminal = noTerminal;
		this.terminales = new ArrayList<String>();
		this.atributos = new ArrayList<Atributo>();
	}
	
	public void asignarTerminales(List<String> terminales) {
		this.terminales = new ArrayList<String>();
		this.terminales.addAll(terminales);
	}

	public void agregarAtributos(List<String> atributos) {
		for(String stringAtributo : atributos) {
			String partir[] = stringAtributo.split("=");
			String nombreAtributo = partir[0].substring(1);
			String stringConstruccion = partir[1];
			for(Integer k = 2; k < partir.length; k++) { stringConstruccion += "=" + partir[k]; }
			List<String> construccion = new ArrayList<String>();
			if(stringConstruccion.contains("?") == true) {
				stringConstruccion = stringConstruccion.replace("?", "#");
				String separar[] = stringConstruccion.split("#");
				String condicion = separar[0];
				String signoComparacion = null;
				if(condicion.contains("!=") == true) { signoComparacion = "!="; }
				if(condicion.contains("==") == true) { signoComparacion = "=="; }
				partir = condicion.split(signoComparacion);
				construccion.add(partir[0]);
				construccion.add(signoComparacion);
				construccion.add(partir[1]);
				construccion.add("?");
				for(Integer w = 1; w < separar.length; w++) {
					separar[w] = separar[w].replace("||", "#");
					partir = separar[w].split("#");
					for(Integer i = 0; i < partir.length; i++) {
						construccion.add(partir[i]);
					}
					if(w == 1) { construccion.add("#"); }
				}
			}
			else {
				stringConstruccion = stringConstruccion.replace("||", "#");
				partir = stringConstruccion.split("#");
				for(Integer i = 0; i < partir.length; i++) {
					construccion.add(partir[i]);
				}
			}
			this.atributos.add(new Atributo(nombreAtributo, construccion));
		}
	}
	
	public List<String> construccionAtributo(String nombreAtributo){
		for(Atributo atributo : this.atributos) {
			if(atributo.nombre().equals(nombreAtributo) == true) {
				return atributo.construccion();
			}
		}
		return null;
	}
	
	public List<Atributo> atributos(){
		return this.atributos;
	}
	
	public String noTerminal() {
		return this.noTerminal;
	}
	
	public List<String> terminales(){
		return this.terminales;
	}
	
	public String toString() {
		return this.noTerminal + " -> " + this.terminales;
	}
	
	public Regla clone() {
		Regla copia = new Regla(this.noTerminal);
		copia.terminales.addAll(this.terminales);
		return copia;
	}
	
}