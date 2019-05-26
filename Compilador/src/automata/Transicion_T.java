package automata;

public class Transicion_T {
	
	private Integer destino;
	private Character simbolo;
	
	public Transicion_T(Integer destino, Character simbolo) {
		this.destino = destino;
		this.simbolo = simbolo;
	}
	
	public void asignarDestino(Integer destino) {
		this.destino = destino;
	}
	
	public Integer destino() {
		return this.destino;
	}
	
	public Character simbolo() {
		return this.simbolo;
	}	
	
	public String toString() {
		return "(" + this.destino + ", " + this.simbolo + ")";
	}
	
}