package algoritmo;

import java.util.ArrayList;
import java.util.List;

import gramatica.Gramatica;
import gramatica.Regla;
import gramatica.Siguiente;

public class ColeccionCanonica {
	
	private List<Estado> estados = new ArrayList<Estado>();
	private List<Transicion> transiciones = new ArrayList<Transicion>();
	private List<String> columnas = new ArrayList<String>();
	private List<List<String>> tabla = new ArrayList<List<String>>();
	
	public ColeccionCanonica(Gramatica gramatica) {		
		Gramatica extendida = gramatica.extendida();
		Gramatica copiaExtendida = extendida.clone();		
		for(Regla regla : extendida.reglas()) {
			regla.terminales().add(0, ".");
			regla.terminales().add(0, "->");
			regla.terminales().add(0, regla.noTerminal());
		}		
		List<Regla> reglas = extendida.reglas(extendida.regla(1).noTerminal());
		List<List<String>> conjuntoInicial = new ArrayList<List<String>>();
		for(Regla r : reglas) { conjuntoInicial.add(r.terminales()); }
		List<List<String>> conjunto = cerradura(extendida, conjuntoInicial);
		this.estados.add(new Estado(conjunto));
		List<Integer> visitar = new ArrayList<Integer>();
		visitar.add(0);
		while(visitar.isEmpty() == false) {
			Integer i = visitar.remove(0);
			Estado estado = this.estados.get(i);
			List<String> simbolosIr = listaIr(estado);
			for(String simbolo : simbolosIr) {
				Transicion transicion = new Transicion(i.toString(), null, simbolo);
				if(simbolo != null && simbolo.equals("$") == false) {
					conjunto = irA(extendida, estado, simbolo);
					Integer numeroEstado = this.existeConjunto(conjunto);
					if(numeroEstado < 0) {
						this.estados.add(new Estado(conjunto));
						visitar.add(this.estados.size()-1);
						transicion.destino = String.valueOf(this.estados.size()-1);
					}
					else {
						transicion.destino = numeroEstado.toString();
					}
				}
				else {
					transicion.destino = "aceptacion";
				}
				this.transiciones.add(transicion);
			}
		}
		List<String> terminales = copiaExtendida.terminales();
		terminales.remove(null);
		Integer n = this.estados.size();
		Integer m = terminales.size() + copiaExtendida.noTerminales().size();
		List<Siguiente> siguientes = Siguientes.calcular(copiaExtendida, Primeros.calcular(copiaExtendida));
		
		this.columnas.add("Est");
		for(Integer i=0; i<terminales.size(); i++) {
			this.columnas.add(terminales.get(i));
		}
		for(Integer i=0; i<gramatica.noTerminales().size(); i++) {
			this.columnas.add(gramatica.noTerminales().get(i));
		}
		
		
		
		for(Integer i=0; i<n; i++) {
			this.tabla.add(new ArrayList<String>());
			for(Integer w=0; w<m; w++) {
				this.tabla.get(i).add(null);
			}
		}
		
		for(Integer i=0; i<this.estados.size(); i++) {
			this.tabla.get(i).set(0, i.toString());
			for(List<String> regla : this.estados.get(i).reglas) {
				Integer indice = regla.indexOf(".");
				if(indice+1 == regla.size()) {
					Integer k = numeroRegla(gramatica, regla);					
					Siguiente siguienteNoTerminal = siguienteDe(siguientes, regla.get(0));
					for(String siguiente : siguienteNoTerminal.siguientes()) {
						Integer w = terminales.indexOf(siguiente);
						if(w >= 0) {
							this.tabla.get(i).set(w+1, "r" + k);
						}
					}
				}
			}
		}
		for(Transicion transicion : this.transiciones) {
			Integer i = Integer.parseInt(transicion.origen);
			Integer w = this.columnas.indexOf(transicion.simbolo);
			if(transicion.simbolo.equals("$")) {
				this.tabla.get(i).set(w, "aceptacion");
			}
			else if(gramatica.noTerminales().contains(transicion.simbolo)) {
				this.tabla.get(i).set(w, transicion.destino);
			}
			else {
				this.tabla.get(i).set(w, "d" + transicion.destino);
			}
		}
		
		/*System.out.println(columnas);
		for(List<String> fila : tabla) {
			System.out.println(fila);
		}
		System.out.println();*/
		
	}
	
	public List<List<List<String>>> conjuntoReglas() {
		List<List<List<String>>> conjunto = new ArrayList<List<List<String>>>();
		for(Estado estado : this.estados) {
			conjunto.add(estado.reglas);
		}
		return conjunto;
	}
	
	public List<List<String>> transiciones() {
		List<List<String>> transiciones = new ArrayList<List<String>>();
		for(Transicion transicion : this.transiciones) {
			List<String> auxiliar = new ArrayList<>();
			auxiliar.add(transicion.origen);
			auxiliar.add(transicion.simbolo);
			auxiliar.add(transicion.destino);
			transiciones.add(auxiliar);
		}
		return transiciones;
	}
	
	public List<String> columnas(){
		return this.columnas;
	}
	
	public List<List<String>> tablaAnalisisSintactico(){
		return this.tabla;
	}
	
	private List<List<String>> cerradura(Gramatica extendida, List<List<String>> reglas) {
		List<List<String>> lista = new ArrayList<List<String>>();
		List<List<String>> visitar = new ArrayList<List<String>>();
		lista.addAll(reglas);
		visitar.addAll(reglas);
		while(visitar.isEmpty() == false) {
			List<String> regla = visitar.remove(0);
			Integer indice = regla.indexOf(".");
			if(indice+1 < regla.size()) {
				String simbolo = regla.get(indice+1);
				if(simbolo == null) {
					List<String> temporal = new ArrayList<String>();
					temporal.addAll(regla);
					temporal.remove(null);
					lista.add(temporal);
					visitar.add(temporal);
				}
				else if(extendida.noTerminales().contains(simbolo)) {
					List<Regla> reglasU = extendida.reglas(simbolo);
					for(Regla a : reglasU) {
						if(a.terminales().contains(null) == true) {
							a.terminales().remove(null);
						}
						if(lista.contains(a.terminales()) == false) {
							lista.add(a.terminales());
							visitar.add(a.terminales());
						}
					}
				}
			}
		}
		return lista;
	}
	
	private List<String> listaIr(Estado estado) {
		List<String> ir = new ArrayList<String>();
		for(List<String> regla : estado.reglas) {
			Integer indice = regla.indexOf(".");
			if(indice+1 < regla.size()) {
				String simbolo = regla.get(indice+1);
				if(ir.contains(simbolo) == false && simbolo != null) {
					ir.add(simbolo);
				}
			}
		}
		return ir;
	}
	
	private List<List<String>> irA(Gramatica extendida, Estado estado, String simbolo) {
		List<List<String>> reglas = new ArrayList<List<String>>();
		for(List<String> regla : estado.reglas) {
			Integer indice = regla.indexOf(".");
			if(indice+1 < regla.size()) {
				String s = regla.get(indice+1);
				if(s != null && s.equals(simbolo)) {
					List<String> auxiliar = new ArrayList<String>();
					auxiliar.addAll(regla);
					auxiliar.remove(".");
					auxiliar.add(indice+1, ".");
					reglas.add(auxiliar);
				}
			}
		}
		return cerradura(extendida, reglas);
	}
	
	private Integer existeConjunto(List<List<String>> conjunto) {
		Integer numero = 0;
		for(Estado estado : this.estados) {
			if(estado.reglas.equals(conjunto)) {
				return numero;
			}
			numero++;
		}
		return -1;
	}
	
	private Integer numeroRegla(Gramatica gramatica, List<String> buscar) {
		String noTerminal = buscar.get(0);
		List<String> auxiliar = new ArrayList<String>();
		auxiliar.addAll(buscar);
		auxiliar = auxiliar.subList(2, auxiliar.size());
		auxiliar.remove(".");
		if(auxiliar.isEmpty() == true){ auxiliar.add(null);	}
		for(Integer numero = 0; numero < gramatica.reglas().size(); numero++) {
			Regla regla = gramatica.reglas().get(numero);
			if(regla.noTerminal().equals(noTerminal) == true) {
				if(regla.terminales().containsAll(auxiliar) == true && regla.terminales().size() == auxiliar.size()) { 
					return numero+1; 
				}
			}
		}
		return 0;
	}
	
	private static Siguiente siguienteDe(List<Siguiente> siguientes, String noTerminal) {
		for(Siguiente s : siguientes) {
			if(s.noTerminal().equals(noTerminal)) {
				return s;
			}
		}
		return null;
	}
	
}

class Estado {
	
	List<List<String>> reglas; 
	
	public Estado(List<List<String>> reglas) {
		this.reglas = new ArrayList<List<String>>();
		this.reglas.addAll(reglas);
	}
	
	public void nuevaRegla(List<String> regla) {
		this.reglas.add(regla);
	}
	
}

class Transicion {
	
	String origen, destino, simbolo;
	
	public Transicion(String origen, String destino, String simbolo) {
		this.origen = origen;
		this.destino = destino;
		this.simbolo = simbolo;
	}
	
}