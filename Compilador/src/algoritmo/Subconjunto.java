package algoritmo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import automata.Automata;
import automata.Estado;
import automata.Transicion;

public class Subconjunto {
	
	public static Automata construir(Automata afn) {
		Automata afd = new Automata();
		List<List<Integer>> subconjuntoEstados = new ArrayList<List<Integer>>();
		List<Integer> subconjunto = cerraduraEpsilon(afn, Arrays.asList(1));
		List<Integer> visitar = new ArrayList<Integer>();
		List<Integer> estadosFinales = afn.estadosFinales();
		afd.nuevoEstado();
		subconjunto.sort(null);
		subconjuntoEstados.add(subconjunto);
		afn.estado(1).asignarEstadoFinal(contieneEstadoFinal(subconjunto, estadosFinales));
		visitar.add(1);
		while(visitar.isEmpty() == false) {
			Estado estadoU = afd.estado(visitar.remove(0));
			List<Integer> subconjuntoU = subconjuntoEstados.get(estadoU.numero()-1);
			List<List<Character>> moverSimbolos = new ArrayList<List<Character>>();
			for(Integer i : subconjuntoU) {
				Estado a = afn.estado(i);
				for(Transicion t : a.transiciones()) {
					List<Character> simbolos = copiarSimbolos(t.simbolos());
					simbolos.remove(null);
					for(List<Character> l : moverSimbolos) {
						if(simbolos.containsAll(l) == true) { simbolos.removeAll(l); }
					}
					if(simbolos.isEmpty() == false) {
						moverSimbolos.add(simbolos);
					}
				}
			}
			for(List<Character> simbolos : moverSimbolos) {
				subconjunto = cerraduraEpsilon(afn, mover(afn, subconjuntoU, simbolos));
				subconjunto.sort(null);
				Integer destinoTransicion = null;
				if(existeEstadoSubconjunto(subconjuntoEstados, subconjunto) == false) {
					afd.nuevoEstado();
					subconjuntoEstados.add(subconjunto);
					afd.estado(afd.numeroEstados()).asignarEstadoFinal(contieneEstadoFinal(subconjunto, estadosFinales));
					visitar.add(afd.numeroEstados());
					destinoTransicion = afd.numeroEstados();
				}
				else { destinoTransicion = afd.estado(subconjuntoEstados.indexOf(subconjunto)+1).numero(); }
				afd.nuevaTransicion(estadoU.numero(), destinoTransicion, simbolos);
			}
		}
		return afd;
	}
	
	private static List<Integer> mover(Automata automata, List<Integer> visitar, List<Character> simbolos) {
		List<Integer> subconjunto = new ArrayList<Integer>();
		for(Integer numero : visitar) {
			Estado estado = automata.estado(numero);
			for(Transicion transicion : estado.transiciones()) {
				if(transicion.simbolos().containsAll(simbolos) == true) {
					if(subconjunto.contains(transicion.destino()) == false) {
						subconjunto.add(transicion.destino());
						break;
					}
				}
			}
		}
		return subconjunto;
	}
	
	private static List<Integer> cerraduraEpsilon(Automata automata, List<Integer> listaEstados) {
		List<Integer> subconjunto = new ArrayList<Integer>();
		List<Integer> visitar = new ArrayList<Integer>();
		for(Integer numero : listaEstados) {
			subconjunto.add(numero);
			visitar.add(numero);
		}
		while(visitar.isEmpty() == false) {
			Estado estado = automata.estado(visitar.remove(0));
    		for(Transicion transicion : estado.transiciones()) {
    			if(transicion.simbolos().contains(null) && subconjunto.contains(transicion.destino()) == false) {
    				visitar.add(transicion.destino());
    				subconjunto.add(transicion.destino());
    			}
    		}
		}
		return subconjunto;
	}
	
	private static List<Character> copiarSimbolos(List<Character> simbolos){
		List<Character> copia = new ArrayList<Character>();
		for(Character simbolo : simbolos) {
			copia.add(simbolo);
		}
		return copia;
	}
	
	private static Boolean existeEstadoSubconjunto(List<List<Integer>> subconjuntoEstados, List<Integer> subconjunto) {
		for(List<Integer> lista : subconjuntoEstados) {
			if(lista.size() == subconjunto.size()) {
				if(lista.containsAll(subconjunto)) { return true; }
			}
		}
		return false;
	}
	
	private static Boolean contieneEstadoFinal(List<Integer> subconjunto, List<Integer> estadosFinales) {
		for(Integer numero : subconjunto) {
			if(estadosFinales.contains(numero) == true) { return true; }
		}
		return false;
	}
	
}