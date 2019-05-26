package algoritmo;

import java.util.ArrayList;
import java.util.List;

import gramatica.Gramatica;
import gramatica.Primero;
import gramatica.Regla;

public class Primeros {
	
	public static List<Primero> calcular(Gramatica gramatica){
		List<String> calcular = new ArrayList<String>();
		calcular.addAll(gramatica.noTerminales());
		List<Primero> primeros = new ArrayList<Primero>();
		auxiliar(gramatica, primeros, calcular, null);
		return primeros;
	}
	
	private static void auxiliar(Gramatica gramatica, List<Primero> primeros, List<String> calcular, String siguiente){
		if(calcular.isEmpty() == false) {
			String noTerminal = null;
			if(siguiente != null) {
				noTerminal = siguiente;
				calcular.remove(siguiente);
			}
			else { noTerminal = calcular.remove(0); }		
			
			Primero nuevoPrimero = new Primero(noTerminal);
			Regla regla = gramatica.regla(noTerminal);			
			
			for(List<String> terminal : regla.terminales()) {
				for(String simbolo : terminal) {
					if(simbolo == null || simbolo.equals(noTerminal) == false) {
						if(gramatica.noTerminales().contains(simbolo)) { 
							List<String> lista = primeros(primeros, simbolo);
							if(lista == null) {
								auxiliar(gramatica, primeros, calcular, simbolo);
								lista = primeros(primeros, simbolo);
							}
							nuevoPrimero.agregarPrimero(lista);
							if(lista.contains(null)) {
								if(terminal.lastIndexOf(simbolo)+1 != terminal.size()) {
									nuevoPrimero.primeros().remove(null);
								}
								continue;
							}
							break;	
						}
						else {
							nuevoPrimero.agregarPrimero(simbolo);
							break;
						}
					}
					else { break; }
				}
			}
			primeros.add(nuevoPrimero);
			if(siguiente == null) {
				auxiliar(gramatica, primeros, calcular, null);
			}
		}
	}
	
	private static List<String> primeros(List<Primero> primeros, String noTerminal){
		for(Primero primero : primeros) {
			if(primero.noTerminal().equals(noTerminal)) { return primero.primeros(); }
		}
		return null;
	}
	
}