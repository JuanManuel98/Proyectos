package analizador;

public class Token {
	
	private Integer linea;
	private String lexema, token;
	
	public Token(Integer linea, String lexema, String token) {
		this.linea = linea;
		this.lexema = lexema;
		this.token = token;
	}
	
	public Integer linea() {
		return this.linea;
	}
	
	public String lexema() {
		return this.lexema;
	}
	
	public String token() {
		return this.token;
	}
	
	public String toString() {
		return "(" + this.linea + " " + this.lexema + " " + this.token + ")";
	}

	
	
}