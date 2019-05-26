package automata;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Automata_T {
	
        private static final List<Character> cerraduras = Arrays.asList('*', '+', '?');
	private static final List<Character> tokens = Arrays.asList('(', ')', '|', '&', '*', '+', '?');
	private static final List<Character> operadores = Arrays.asList('|', '&');
	
	private List<Estado_T> estados;
	private String expresion;
	
	public Automata_T() {
		this.estados = new ArrayList<Estado_T>();
	}
	
	public List<Estado_T> estados(){
		return this.estados;
	}
	
	public String expresion() {
		return this.expresion;
	}
	
	public Integer numeroEstados() {
		return this.estados.size();
	}
	
	public Estado_T estado(Integer numero) {
		for(Estado_T estado: this.estados()) {
			if(estado.numero() == numero) { return estado; }
		}
		return null;
	}
	
	public boolean existeEstado(Integer numero) {
		for(Estado_T estado: this.estados()) {
			if(estado.numero() == numero) { return true; }
		}
		return false;
	}
	
	public void nuevoEstado() {
		this.estados.add(new Estado_T(this.numeroEstados()));
	}
	
	public void nuevoEstado(Integer numero) {
		this.estados.add(new Estado_T(numero));
	}
	
	public void nuevaTransicion(Integer origen, Integer destino, Character simbolo) {
		Estado_T estado = this.estado(origen);
		if(estado != null && this.existeEstado(destino)) {
			estado.nuevaTransicion(destino, simbolo);
		}
	}
	
	public static Automata_T automataSimple(Character simbolo) {
		Automata_T automata = new Automata_T();
		automata.nuevoEstado();
		automata.nuevoEstado();
		automata.nuevaTransicion(0, 1, simbolo);
		return automata;
	}
	
	public static String procesarExpresion(String expresion) {
		String nueva = "";
		Integer parentesis = 0;
		for(int i=0; i<expresion.length(); i++) {
			Character c = expresion.charAt(i);
			if(c == '|') {				
				Integer w, p = 0;
				for(w = nueva.length()-1; w >= 0; w--) {
					if(nueva.charAt(w) == ')') { p++; }
					if(nueva.charAt(w) == '(') {
						if(p > 0) { p--; }
						else { break; }
					}
				}
				nueva = nueva.substring(0, w+1) + "(" + nueva.substring(w+1, nueva.length());
				nueva = nueva + ")" + c + "(";
				parentesis++;
			}
			else if(c == ')' && parentesis > 0) {
				nueva = nueva + c + ")";
				parentesis--;
			}
			else { nueva = nueva + c; }
		}
		while(parentesis > 0) {
			nueva = nueva + ")";
			parentesis--;
		}		
		expresion = nueva;
		nueva = "";
		for(int i=0; i<expresion.length()-1; i++) {
			Character a = expresion.charAt(i);
			Character b = expresion.charAt(i+1);
			nueva = nueva + a;
			if(tokens.contains(a) == false && tokens.contains(b) == false) {
				nueva = nueva + '&';
			}
			else if(tokens.contains(a) == false && b == '(') {
				nueva = nueva + '&';
			}
			else if(a == ')' && tokens.contains(b) == false) {
				nueva = nueva + '&';
			}
			else if(a == ')' && b == '(') {
				nueva = nueva + '&';
			}
			else if(cerraduras.contains(a) == true && tokens.contains(b) == false) {
				nueva = nueva + '&';
			}
			else if(cerraduras.contains(a) == true && b == '(' ) {
				nueva = nueva + '&';
			}
		}
		nueva = nueva + expresion.charAt(expresion.length()-1); 
		return nueva;
	}
	
	public static String[] partirExpresion(String expresion) {
		if(expresion.length() > 1) {
			String r, s, o;
			Integer parentesis = 0;
			for(int i=0; i<expresion.length(); i++) {
				Character c = expresion.charAt(i);
				if(operadores.contains(c) && parentesis == 0) {
					r = expresion.substring(0, i);
					s = expresion.substring(i+1, expresion.length());
					o = String.valueOf(expresion.charAt(i));
					return new String[]{r, s, o};
				}
				else if(c == '(') { parentesis++; }
				else if(c == ')') { parentesis--; }
			}
			if(cerraduras.contains(expresion.charAt(expresion.length()-1))) {
				r = expresion.substring(0, expresion.length()-1);
				o = String.valueOf(expresion.charAt(expresion.length()-1));
				return new String[]{r, null, o};
			}
			return partirExpresion(expresion.substring(1, expresion.length()-1));
		}
		return null;
	}
	
	public static Automata_T expresion(String expresion) {
		expresion = procesarExpresion(expresion);
		return RSO(expresion, null, null);
	}
	
	private static Automata_T RSO(String r, String s, String o) {
		Automata_T a = null;
		Automata_T b = null;
		String rso[] = partirExpresion(r);
		if(rso != null) {
			a = RSO(rso[0], rso[1], rso[2]);
		}
		else { 
			for(int i=0; i<r.length(); i++) {
				if(r.charAt(i) != '(' && r.charAt(i) != ')') {
					a = automataSimple(r.charAt(i));
					break;
				}
			}
		}
		if(o != null) {
			if(s != null) {
				rso = partirExpresion(s);
				if(rso != null) {
					b = RSO(rso[0], rso[1], rso[2]);
				}
				else { 
					for(int i=0; i<s.length(); i++) {
						if(s.charAt(i) != '(' && s.charAt(i) != ')') {
							b = automataSimple(s.charAt(i));
							break;
						}
					}
				}
			}
			if(cerraduras.contains(o.charAt(0))) {
				a.aplicarCerradura(o.charAt(0));
			}
			a.aplicarOperacion(b, o.charAt(0));
		}
		return a;
	}
	
	private void modificarEstados(Integer n) {
		for(Estado_T estado : this.estados()) {
			for(Transicion_T transicion: estado.transiciones()) {
				transicion.asignarDestino(transicion.destino() + n);
			}
			estado.asignarNumero(estado.numero() + n);
		}
	}
	
	public void aplicarCerradura(Character cerradura) {
		Integer n = this.numeroEstados();
		this.modificarEstados(1);
		this.nuevoEstado(0);
		this.nuevoEstado();
		this.nuevaTransicion(0, 1, null);
		this.nuevaTransicion(n, n+1, null);
		this.estados.sort(null);
		switch(cerradura) {
			case '*':{
				this.nuevaTransicion(n, 1, null);
				this.nuevaTransicion(0, n+1, null);
				break;
			}
			case '+':{
				this.nuevaTransicion(n, 1, null);
				break;
			}
			case '?':{
				this.nuevaTransicion(0, n+1, null);
				break;
			}
		}
	}
	
	public void aplicarOperacion(Automata_T b, Character operacion) {
		switch(operacion) {
			case '&': {
				this.and(b);
				break;
			}
			case '|':{
				this.or(b);
				break;
			}
		}
	}
	
	public void and(Automata_T s) {
		Integer n = this.numeroEstados();
		s.modificarEstados(n-1);
		this.estados.remove(n-1);
		for(Estado_T estado : s.estados()) {
			this.estados.add(estado);
		}
	}
	
	public void or(Automata_T s) {
		Integer n = this.numeroEstados();
		Integer m = s.numeroEstados();
		this.modificarEstados(1);
		s.modificarEstados(n+1);
		this.nuevoEstado(0);
		for(Estado_T estado : s.estados()) {
			this.estados.add(estado);
		}
		this.nuevaTransicion(0, 1, null);
		this.nuevaTransicion(0, n+1, null);
		this.nuevoEstado();
		this.nuevaTransicion(n, this.numeroEstados()-1, null);
		this.nuevaTransicion(n+m, this.numeroEstados()-1, null);
		this.estados.sort(null);
	}
        
        public List<Character> listAlfabeto(){
            List<Character> alfabeto = new ArrayList<Character>();
            for(Estado_T estado : this.estados){
                for(Transicion_T transicion : estado.transiciones()){
                    if(alfabeto.contains(transicion.simbolo()) == false && transicion.simbolo() != null){
                        alfabeto.add(transicion.simbolo());
                    }
                }
            }
            alfabeto.add(null);
            return alfabeto;
        }
        
        public String[] stringAlfabeto(){
            List<Character> lista = this.listAlfabeto();
            String alfabeto[] = new String[lista.size()+1];
            alfabeto[0] = "#";
            for(int i=0; i<lista.size(); i++){
                alfabeto[i+1] = String.valueOf(lista.get(i));
            }
            return alfabeto;
        }
        
        public Object [][] transiciones(){
            List<Character> alfabeto = this.listAlfabeto();
            Integer n = this.numeroEstados();
            Integer m = alfabeto.size()+1;
            Object transiciones[][] = new String[n][m];
            for(int i=0; i<n; i++){
                for(int w=0; w<m; w++){
                    transiciones[i][w] = "";
                }
            }
            for(int i=0; i<this.numeroEstados(); i++){
                Estado_T estado = this.estado(i);
                transiciones[i][0] = estado.numero().toString();
                for(Transicion_T transicion : estado.transiciones()){
                    Integer k = alfabeto.indexOf(transicion.simbolo());
                    transiciones[i][k+1] += "(" + transicion.destino() + ")";
                }
            }
            return transiciones;
        }
	
	public String toString() {
		return this.estados.toString();
	}
	
}