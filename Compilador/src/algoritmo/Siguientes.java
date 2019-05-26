package algoritmo;

import java.util.ArrayList;
import java.util.List;
import gramatica.Gramatica;
import gramatica.Primero;
import gramatica.Siguiente;
import gramatica.Regla;

public class Siguientes {
	
	public static List<Siguiente> calcular(Gramatica gramatica, List<Primero> primeros){
		List<String> calcular = new ArrayList<String>();
		calcular.addAll(gramatica.noTerminales());
		List<Siguiente> siguientes = new ArrayList<Siguiente>();
		auxiliar(gramatica, siguientes, primeros, calcular, null);
		return siguientes;
	}
	
	private static void auxiliar(Gramatica gramatica, List<Siguiente> siguientes, List<Primero> primeros, List<String> calcular, String nuevo){
		if(calcular.isEmpty() == false) {
			String noTerminal = null;
			if(nuevo != null) {
				noTerminal = nuevo;
				calcular.remove(nuevo);
			}
			else { noTerminal = calcular.remove(0); }
			//System.out.println("Calcular S(" + noTerminal + ")");
			Gramatica temporal = producciones(gramatica, noTerminal);
			Siguiente nuevoSiguiente = new Siguiente(noTerminal);
			if(noTerminal == gramatica.noTerminales().get(0)) {
				nuevoSiguiente.agregarSiguiente("$");
			}
			for(Regla regla : temporal.reglas()) {
				for(List<String> produccion : regla.terminales()) {
					Integer indice = produccion.indexOf(noTerminal);
					if(indice+1 == produccion.size()) {
						if(regla.simbolo() != noTerminal) {
							List<String> lista = siguientes(siguientes, regla.simbolo());
							if(lista == null) {
								auxiliar(gramatica, siguientes, primeros, calcular, regla.simbolo());
								lista = siguientes(siguientes, regla.simbolo());
							}
							nuevoSiguiente.agregarSiguiente(lista);
						}
					}
					else {
						String beta = produccion.get(indice+1);
						if(gramatica.noTerminales().contains(beta) == true) {
							List<String> primerosBeta = primeros(primeros, beta);
							if(primerosBeta.contains(null) == true) {
								nuevoSiguiente.agregarSiguiente(primerosBeta);
								nuevoSiguiente.siguientes().remove(null);
								List<String> lista = siguientes(siguientes, regla.simbolo());
								if(lista == null) {
									auxiliar(gramatica, siguientes, primeros, calcular, regla.simbolo());
									lista = siguientes(siguientes, regla.simbolo());
								}
								nuevoSiguiente.agregarSiguiente(lista);
							}
							else {
								nuevoSiguiente.agregarSiguiente(primerosBeta);
							}
						}
						else {
							nuevoSiguiente.agregarSiguiente(beta);
						}
					}
				}				
			}
			siguientes.add(nuevoSiguiente);
			if(nuevo == null) {
				auxiliar(gramatica, siguientes, primeros, calcular, null);
			}
		} 
	}
	
	private static Gramatica producciones(Gramatica gramatica, String noTerminal){
		Gramatica producciones = new Gramatica();
		for(Regla regla : gramatica.reglas()) {
			for(List<String> terminal : regla.terminales()) {
				if(terminal.contains(noTerminal)) {
					producciones.nuevaRegla(regla.simbolo(), terminal);
				}
			}
		}
		return producciones;
	}
	
	private static List<String> primeros(List<Primero> primeros, String noTerminal){
		for(Primero primero : primeros) {
			if(primero.noTerminal().equals(noTerminal)) { return primero.primeros(); }
		}
		return null;
	}
	
	private static List<String> siguientes(List<Siguiente> siguientes, String noTerminal){
		for(Siguiente siguiente : siguientes) {
			if(siguiente.noTerminal().equals(noTerminal)) { return siguiente.siguientes(); }
		}
		return null;
	}
	
}