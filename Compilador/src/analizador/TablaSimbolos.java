package analizador;

import java.util.ArrayList;
import java.util.List;

public class TablaSimbolos {
	
	private List<Simbolo> simbolos;
	
	public TablaSimbolos() {
		this.simbolos = new ArrayList<Simbolo>();
	}
	
	public void nuevoSimbolo(String identificador, Object valor, String funcion) {
		if(this.existeSimbolo(identificador, funcion) == false) {
			this.simbolos.add(new Simbolo(identificador, valor, funcion));
		}
	}
	
	public Boolean existeSimbolo(String identificador, String funcion) {
		for(Simbolo simbolo : this.simbolos) {
			if(simbolo.identificador().equals(identificador) == true) {
				return true; 
			}
		}
		return false;
	}
	
	public List<Simbolo> simbolos(){
		return this.simbolos;
	}
	
	public void imprimir() {
		for(Simbolo simbolo : this.simbolos) {
			System.out.println(simbolo);
		}
	}
	
}