package gramatica;

import java.util.List;

import algoritmo.Primeros;
import algoritmo.Siguientes;

public class Main {

	public static void main(String[] args) {
		Gramatica g = new Gramatica("gramatica.txt");
		List<Primero> primeros = Primeros.calcular(g);
		List<Siguiente> siguientes = Siguientes.calcular(g,primeros);
		//for(Primero p : primeros) {
		//	System.out.println(p);
		//}
		
		for(Siguiente s : siguientes) {
			System.out.println(s);
		}
	}

}
