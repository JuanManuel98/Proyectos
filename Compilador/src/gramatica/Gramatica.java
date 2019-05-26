package gramatica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gramatica {
	
	private List<Regla> reglas;
	private List<String> noTerminales, terminales;
	private Boolean accionesSemanticas, reglasSemanticas;
	private BufferedReader buffer;
	
	public Gramatica() {
		this.reglas = new ArrayList<Regla>();
		this.noTerminales = new ArrayList<String>();
		this.terminales = new ArrayList<String>();
		this.reglasSemanticas = false;
		this.accionesSemanticas = false;
	}
	
	public Gramatica(String ruta) {
		this.reglas = new ArrayList<Regla>();
		this.noTerminales = new ArrayList<String>();
		this.terminales = new ArrayList<String>();
		try {
			buffer = new BufferedReader(new FileReader(ruta));
			List<String> listaAtributos = null;
			Integer numeroRegla = 1;
			while(true) {
				String linea = buffer.readLine();
				if(linea != null) {
					if(linea.contains("->") == true) {
						String simbolos[] = linea.split(" ");
						String noTerminal = simbolos[0];
						List<String> terminal = new ArrayList<String>();
						for(Integer i=2; i<simbolos.length; i++) {
							terminal.add(simbolos[i].equals("null") ? null : simbolos[i]);
						}
						this.nuevaRegla(noTerminal, terminal);
					}
					else {
						String auxiliar = "";
						Boolean comillas = false;
						for(Integer i=0; i<linea.length(); i++) {
							String caracter = linea.substring(i, i+1);
							if(caracter.equals("\"") == true) { comillas = comillas == false ? true : false; }
							if(comillas == true || caracter.equals(" ") == false) { auxiliar += caracter; }
						}
						linea = auxiliar;
						if(linea.equals("{") == true) { 
							listaAtributos = new ArrayList<String>();
						}
						else if(linea.equals("}") == true) { 
							this.regla(numeroRegla).agregarAtributos(listaAtributos);
							numeroRegla++;
						}
						else {
							listaAtributos.add(linea);
						}
					}
				}
				else { break; }
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void nuevaRegla(String noTerminal, List<String> terminales) {
		Regla nueva = new Regla(noTerminal);
		nueva.asignarTerminales(terminales);
		if(this.noTerminales.contains(noTerminal) == false) {
			if(this.terminales.contains(noTerminal) == true) { this.terminales.remove(noTerminal); }
			this.noTerminales.add(noTerminal); 
		}
		for(String terminal : terminales) {
			if(this.noTerminales.contains(terminal) == false && this.terminales.contains(terminal) == false) {
				this.terminales.add(terminal);
			}
		}
		this.reglas.add(nueva);
	}
	
	public Regla regla(String noTerminal) {
		for(Regla regla : this.reglas) {
			if(regla.noTerminal().equals(noTerminal)) { return regla; }
		}
		return null;
	}
	
	public Gramatica extendida() {
		Gramatica extendida = this.clone();
		Regla nueva = new Regla(this.reglas.get(0).noTerminal() + "'");
		List<String> terminales = new ArrayList<String>();
		terminales.add(this.reglas.get(0).noTerminal());
		terminales.add("$");
		nueva.asignarTerminales(terminales);
		extendida.reglas().add(0, nueva);
		extendida.noTerminales().add(0, this.reglas.get(0).noTerminal() + "'");
		extendida.terminales().add(0, "$");
		return extendida;
	}
	
	public List<Regla> producciones(String noTerminal){
		List<Regla> reglas = new ArrayList<Regla>();
		for(Regla regla : this.reglas) {
			if(regla.terminales().contains(noTerminal) == true) { reglas.add(regla); }
		}
		return reglas;
	}
	
	public List<Regla> reglas(String noTerminal){
		List<Regla> reglas = new ArrayList<Regla>();
		for(Regla regla : this.reglas) {
			if(regla.noTerminal().equals(noTerminal)) { reglas.add(regla); }
		}
		return reglas;
	}
	
	public List<Regla> reglas(){
		return this.reglas;
	}
	
	public List<String> noTerminales(){
		return this.noTerminales;
	}
	
	public List<String> terminales(){
		return this.terminales;
	}
	
	public Boolean accionesSemanticas() {
		return this.accionesSemanticas;
	}
	
	public Boolean reglasSemanticas() {
		return this.reglasSemanticas;
	}
	
	public String toString() {
		String str = "";
		for(Regla r : this.reglas) {
			str += r.toString() + "\n";
		}
		return str;
	}
	
	public Regla regla(Integer numero) {
		if(numero > 0 && numero <= this.reglas.size()) {
			return this.reglas.get(numero-1);
		}
		return null;
	}
	
	public void agregarAccionesSemanticas(String ruta) {
		try {
			buffer = new BufferedReader(new FileReader(ruta));
			List<String> listaAtributos = null;
			Integer numeroRegla = 1;
			while(true) {
				String linea = buffer.readLine();
				if(linea != null) {
					String auxiliar = "";
					Boolean comillas = false;
					for(Integer i=0; i<linea.length(); i++) {
						String caracter = linea.substring(i, i+1);
						if(caracter.equals("\"") == true) { comillas = comillas == false ? true : false; }
						if(comillas == true || caracter.equals(" ") == false) { auxiliar += caracter; }
					}
					linea = auxiliar;
					if(linea.equals("{") == true) { 
						listaAtributos = new ArrayList<String>();
					}
					else if(linea.equals("}") == true) { 
						this.regla(numeroRegla).agregarAtributos(listaAtributos);
						numeroRegla++;
					}
					else {
						listaAtributos.add(linea);
					}
				}
				else { break; }
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Gramatica clone() {
		Gramatica copia = new Gramatica();
		for(Regla r : this.reglas) { copia.reglas().add(r.clone()); }
		for(String s : this.noTerminales) { copia.noTerminales().add(s); }
		for(String s : this.terminales) { copia.terminales().add(s); }
		return copia;
	}
	
}