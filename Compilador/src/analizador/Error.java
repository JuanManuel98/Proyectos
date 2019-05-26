package analizador;

public class Error {
	
	private Integer linea;
	private String descripcion;
	
	public Error(Integer linea, String descripcion) {
		this.linea = linea;
		this.descripcion = descripcion;
	}
	
	public Integer linea() {
		return this.linea;
	}
	
	public String descripcion() {
		return this.descripcion;
	}
	
	public String toString() {
		return this.linea + " " + this.descripcion;
	}
	
}