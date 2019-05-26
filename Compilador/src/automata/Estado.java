package automata;

import java.util.ArrayList;
import java.util.List;

public class Estado implements Comparable<Estado> {
	
	private Integer numero;
	private Boolean estadoFinal;
	private List<Transicion> transiciones;

	public Estado(Integer numero) {
		this.numero = numero;
		this.estadoFinal = false;
		this.transiciones = new ArrayList<Transicion>();
	}
	
	public void asignarNumero(Integer numero) {
		this.numero = numero;
	}
	
	public void asignarEstadoFinal(Boolean estadoFinal) {
		this.estadoFinal = estadoFinal;
	}
	
	public Integer numero() {
		return this.numero;
	}
	
	public Boolean estadoFinal() {
		return this.estadoFinal;
	}
	
	public List<Transicion> transiciones(){
		return this.transiciones;
	}
	
	public String toString() {
		return "(" + this.numero + " -> " + this.transiciones + ")";
	}
	
	public void nuevaTransicion(Integer destino, Character simbolo) {
		Transicion transicion = this.transicion(destino);
		if(transicion != null) {
			if(transicion.simbolos().contains(simbolo) == false) {
				transicion.simbolos().add(simbolo);
			}
		}
		else { this.transiciones.add(new Transicion(destino, (Character) simbolo)); }
	}
	
	public void nuevaTransicion(Integer destino, List<Character> simbolos) {
		Transicion transicion = this.transicion(destino);
		if(transicion != null) {
			for(Character simbolo : simbolos) {
				if(transicion.simbolos().contains(simbolo) == false) {
					transicion.simbolos().add(simbolo);
				}
			}
		}
		else { this.transiciones.add(new Transicion(destino, simbolos)); }
	}
	
	public Boolean existeTransicion(Integer numero) {
		for(Transicion transicion : this.transiciones) {
			if(transicion.destino() == numero) { return true; }
		}
		return false;
	}
	
	public Transicion transicion(Integer numero) {
		for(Transicion transicion : this.transiciones) {
			if(transicion.destino() == numero) { return transicion; }
		}
		return null;
	}

	public int compareTo(Estado estado) {
		if(this.numero < estado.numero()) { return -1; }
		if(this.numero > estado.numero()) { return 1;  }
		return 0;
	}
	
}