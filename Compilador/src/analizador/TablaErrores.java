package analizador;

import java.util.ArrayList;
import java.util.List;

public class TablaErrores {
	
	private List<Error> errores;
	
	public TablaErrores() {
		this.errores = new ArrayList<Error>();
	}
	
	public void nuevoError(Integer linea, String descripcion) {
		this.errores.add(new Error(linea, descripcion));
	}
	
	public List<Error> errores(){
		return this.errores;
	}
	
	public void imprimir() {
		for(Error error : this.errores) {
			System.out.println(error);
		}
	}
	
}