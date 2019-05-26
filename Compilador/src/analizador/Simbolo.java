package analizador;

public class Simbolo {
	
	private String identificador, funcion;
	private Object valor;
	
	public Simbolo(String identificador, Object valor, String funcion) {
		this.identificador = identificador;
		this.valor = valor;
		this.funcion = funcion;
	}
	
	public String identificador() {
		return this.identificador;
	}
	
	public String funcion() {
		return this.funcion;
	}
	
	public Object valor() {
		return this.valor;
	}
	
	public String toString() {
		return "(" + identificador + ", " + valor + ", " + funcion + ")";
	}
	
}