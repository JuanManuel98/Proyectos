package analizador;

import java.util.ArrayList;
import java.util.List;

public class TiraTokens {
	
	private List<Token> tokens;
	
	public TiraTokens() {
		this.tokens = new ArrayList<Token>();
	}
	
	public void nuevoToken(Integer linea, String lexema, String token) {
		this.tokens.add(new Token(linea, lexema, token));
	}
	
	public List<Token> tokens(){
		return this.tokens;
	}
	
	public void imprimir() {
		for(Token token : this.tokens) {
			System.out.println(token);
		}
	}
	
}