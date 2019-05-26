package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import algoritmo.AnalizadorSintactico;
import analizador.AnalizadorLexico;
import gramatica.Gramatica;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class Abrirarchivo extends JFrame {

	private JPanel contentPane;
	private String [][] datos;
	private static List<List<String>> Analisis = new ArrayList<List<String>>(); 
	private static List<String> Errores = new ArrayList<String>(); 
	AnalizadorLexico lexico = new AnalizadorLexico();
	Gramatica gramatica = new Gramatica();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Abrirarchivo frame = new Abrirarchivo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Abrirarchivo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 196);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton programa = new JButton("Programa");
		programa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					int seleccion = fc.showOpenDialog(contentPane);
					if(seleccion ==JFileChooser.APPROVE_OPTION);
						File fichero = fc.getSelectedFile();
						try(FileReader fr = new FileReader(fichero)){
							String cadena=" ";
							int valor = fr.read();
							while(valor != -1) {
								cadena = cadena +(char)valor;
								valor = fr.read();
							}
							lexico.procesarArchivo(fichero.getAbsolutePath());
							System.out.println("----- Tira de tokens -----");
							lexico.tiraTokens().imprimir();
							System.out.println("----- Tabla de simbolos -----");
							lexico.tablaSimbolos().imprimir();
							System.out.println("----- Tabla de errores -----");
							lexico.tablaErrores().imprimir();

							
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			}
		});
		programa.setBackground(new Color(32, 178, 170));
		programa.setBounds(10, 53, 178, 31);
		contentPane.add(programa);
		
		JButton btnGramatica = new JButton("Gramatica");
		btnGramatica.setBackground(new Color(51, 153, 204));
		btnGramatica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int seleccion = fc.showOpenDialog(contentPane);
				if(seleccion ==JFileChooser.APPROVE_OPTION);
					File fichero = fc.getSelectedFile();
					try(FileReader fr = new FileReader(fichero)){
						String cadena=" ";
						int valor = fr.read();
						while(valor != -1) {
							cadena = cadena +(char)valor;
							valor = fr.read();
						}
						
						gramatica = new Gramatica(fichero.getAbsolutePath());
	
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		}
			
				
	});
		btnGramatica.setBounds(10, 95, 178, 31);
		contentPane.add(btnGramatica);
		
		JLabel lblNewLabel = new JLabel("Analisis Sintactico, Seleccionar");
		lblNewLabel.setBounds(10, 11, 188, 46);
		contentPane.add(lblNewLabel);
		
		JButton Calcular = new JButton("Calcular");
		Calcular.setBackground(new Color(0, 153, 51));
		Calcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnalizadorSintactico sintactico = new AnalizadorSintactico(gramatica);
				sintactico.analizar(lexico.tiraTokens());
				Analisis = sintactico.getSalidaSintactica();
				System.out.print(Analisis);
				for(List<String> lista : Analisis) {
					System.out.print("\nLista: " + lista);
				}
				Abrirarchivo abrir = new Abrirarchivo();
				abrir.setVisible(true);
				try {
					File Apila = new File("pila1.txt");
					FileWriter escribir1 = new FileWriter(Apila,true);
					File Aprograma = new File("programa1.txt");
					FileWriter escribir2 = new FileWriter(Aprograma,true);
					File Aaccion = new File("accion1.txt");
					FileWriter escribir3 = new FileWriter(Aaccion,true);
					File Aerror = new File("errores1.txt");
					FileWriter escribir4 = new FileWriter(Aerror,true);
					datos = new String [Analisis.size()][3];
					int cont =0;
					String [] cabecera = {"Pila","Programa","Accion"}; 
					for(List<String> i : Analisis) {
						datos[cont][0] = "" + i.get(0);
						datos[cont][1] = "" + i.get(1);
						if(i.size()==3) {
							datos[cont][2] = "" + i.get(2);
						}
						else {
							datos[cont][2] = "" + i.get(2)+" " + i.get(3);
						}
						
						escribir1.write(i.get(0) + "\r\n");
						System.out.println(i.get(0));
						escribir1.write("\r\n");
						escribir2.write(i.get(1));
						escribir2.write("\r\n");
						if(i.size() ==3) {
							escribir3.write(i.get(2) + "");
						}
						else {
							escribir3.write(i.get(2) + "  " + i.get(3));
						}
						escribir3.write("\r\n");
						cont++;						
					}
					
					for (int i=0; i< Errores.size();i++) {
						//System.out.println(Errores.get(i));
						escribir4.write(Errores.get(i));
						escribir4.write("\r\n");
					}
					AnalisisSintactico.setNTable(datos, cabecera);
					AnalisisSintactico.setNtextArea(sintactico.getErrores());
					AnalisisSintactico ana = new AnalisisSintactico();
					ana.setVisible(true);
					ana.getContentPane().add(ana.AP);
					ana.getContentPane().add(ana.PE);
					escribir1.close();
					escribir2.close();
					escribir3.close();
					escribir4.close();
				}
				catch(Exception e1) {
					System.out.println("Error al escribir" + e1);
					
				}
				
			}
		});
		
		Calcular.setBounds(253, 95, 120, 31);
		contentPane.add(Calcular);
	}
}
