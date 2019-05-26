package automata;

import java.util.List;
import java.util.ArrayList;

public class Estado_T implements Comparable<Estado_T> {
	
	private Integer numero;
	private List<Transicion_T> transiciones;
	
	public Estado_T(Integer numero) {
		this.numero = numero;
		this.transiciones = new ArrayList<Transicion_T>();
	}
	
	public void asignarNumero(Integer numero) {
		this.numero = numero;
	}
	
	public Integer numero() {
		return this.numero;
	}
	
	public List<Transicion_T> transiciones(){
		return this.transiciones;
	}
	
	public void nuevaTransicion(Integer destino, Character simbolo) {
		this.transiciones.add(new Transicion_T(destino, simbolo));
	}
	
	public String toString() {
		return "(" + this.numero + " -> " + this.transiciones + ")";
	}

	public int compareTo(Estado estado) {
		if(this.numero < estado.numero()) {
			return -1;
		}
		if(this.numero > estado.numero()) {
			return 1;
		}
		return 0;
	}

	@Override
	public int compareTo(Estado_T o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}