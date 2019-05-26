package gramatica;

public class Arbol {
		
	private Nodo raiz;
	
	public Arbol() {
		this.raiz = null;
	}
	
	public void colocarRaiz(String simbolo) {
		this.raiz = new Nodo(simbolo);
	}
	
	public Nodo raiz() {
		return this.raiz;
	}
	
	public String toString() {
		return this.raiz.toString();
	}
	
}