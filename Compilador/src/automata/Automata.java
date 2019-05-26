package automata;

import java.util.ArrayList;
import java.util.List;

public class Automata {
	
	private List<Estado> estados;
	
	public Automata() {
		this.estados = new ArrayList<Estado>();
	}
	
	public List<Estado> estados(){
		return this.estados;
	}
	
	public String toString() {
		return this.estados.toString();
	}
	
	public Integer numeroEstados() {
		return this.estados.size();
	}
	
	public void nuevoEstado() {
		this.estados.add(new Estado(this.numeroEstados()+1));
	}
	
	public void nuevoEstado(Integer numero) {
		if(this.existeEstado(numero) == false) {
			this.estados.add(new Estado(numero));
		}
	}
	
	public void nuevaTransicion(Integer origen, Integer destino, Character simbolo) {
		Estado estado = this.estado(origen);
		if(estado != null && this.existeEstado(destino)) {
			estado.nuevaTransicion(destino, (Character)(simbolo));
		}
	}
	
	public void nuevaTransicion(Integer origen, Integer destino, List<Character> simbolos) {
		Estado estado = this.estado(origen);
		if(estado != null && this.existeEstado(destino)) {
			estado.nuevaTransicion(destino, simbolos);
		}
	}
	
	public Boolean existeEstado(Integer numero) {
		for(Estado estado : this.estados) {
			if(estado.numero() == numero) { return true; }
		}
		return false;
	}
	
	public Estado estado(Integer numero) {
		for(Estado estado : this.estados) {
			if(estado.numero() == numero) { return estado; }
		}
		return null;
	}
	
	public Automata copiar() {
		Automata copia = new Automata();
		for(Estado estado : this.estados()) {
			copia.estados.add(estado);
		}
		return copia;
	}
	
	public List<Integer> estadosFinales(){
		List<Integer> finales = new ArrayList<Integer>();
		for(Estado estado: this.estados) {
			if(estado.estadoFinal() == true) { finales.add(estado.numero()); }
		}
		return finales;
	}
	
	private void modificarEstados(Integer n) {
		for(Estado estado : this.estados()) {
			for(Transicion transicion: estado.transiciones()) {
				transicion.asignarDestino(transicion.destino() + n);
			}
			estado.asignarNumero(estado.numero() + n);
		}
	}
	
	public Automata cerraduraKleene() {
		Automata a = this.copiar();
		Integer n = a.numeroEstados();
		a.modificarEstados(1);
		a.nuevoEstado(1);
		a.nuevoEstado();
		a.nuevaTransicion(1, 2, (Character) null);
		a.nuevaTransicion(n+1, n+2, (Character) null);
		a.nuevaTransicion(n+1, 2, (Character) null);
		a.nuevaTransicion(1, n+2, (Character) null);
		a.estados().sort(null);
		return a;
	}
	
	public Automata cerraduraPositiva() {
		Automata a = this.copiar();
		Integer n = a.numeroEstados();
		a.modificarEstados(1);
		a.nuevoEstado(1);
		a.nuevoEstado();
		a.nuevaTransicion(1, 2, (Character) null);
		a.nuevaTransicion(n+1, n+2, (Character) null);
		a.nuevaTransicion(n+1, 2, (Character) null);
		a.estados().sort(null);
		return a;
	}
	
	public Automata cerraduraOpcional() {
		Automata a = this.copiar();
		Integer n = a.numeroEstados();
		a.modificarEstados(1);
		a.nuevoEstado(1);
		a.nuevoEstado();
		a.nuevaTransicion(1, 2, (Character) null);
		a.nuevaTransicion(n+1, n+2, (Character) null);
		a.nuevaTransicion(1, n+2, (Character) null);
		a.estados().sort(null);
		return a;
	}
	
	public Automata and(Automata s) {
		Automata a = this.copiar();
		Automata b = s.copiar();
		Integer n = a.numeroEstados();
		b.modificarEstados(n-1);
		a.estados().remove(n-1);
		for(Estado estado : b.estados()) {
			a.estados.add(estado);
		}
		return a;
	}
	
	public Automata or(Automata s) {
		Automata a = this.copiar();
		Automata b = s.copiar();
		Integer n = a.numeroEstados();
		Integer m = b.numeroEstados();
		a.modificarEstados(1);
		b.modificarEstados(n+1);
		a.nuevoEstado(1);
		for(Estado estado : b.estados()) {
			a.estados().add(estado);
		}
		a.nuevoEstado();
		a.nuevaTransicion(1, 2, (Character) null);
		a.nuevaTransicion(1, n+2, (Character) null);
		a.nuevaTransicion(n+1, a.numeroEstados(), (Character) null);
		a.nuevaTransicion(n+m+1, a.numeroEstados(), (Character) null);
		a.estados().sort(null);
		return a;
	}
	
	public Boolean verificarString(String string) {
		Estado actual = this.estado(1);
		for(Integer i=0; i<string.length(); i++) {
			Character caracter = string.charAt(i);
			Boolean existe = false;
			for(Transicion transicion : actual.transiciones()) {
				if(transicion.simbolos().contains(caracter)) {
					actual = this.estado(transicion.destino());
					existe = true; break;
				}
			}
			if(existe == false) { return false; }
		}
		return actual.estadoFinal();
	}
	
	public void imprimir() {
		for(Estado estado : this.estados) {
			if(estado.estadoFinal()) {
				System.out.println("Estado(" + estado.numero() + ")");
			}
			else {
				System.out.println("Estado " + estado.numero());
			}
			for(Transicion transicion : estado.transiciones()) {
				System.out.println("    {" + transicion.destino() + ", " + transicion.simbolos() + "}");
			} 
		}
	}
	
}