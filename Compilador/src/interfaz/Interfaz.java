package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Choice;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import algoritmo.ColeccionC;
import automata.Automata;
import gramatica.Gramatica;
public class Interfaz extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz frame = new Interfaz();
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
	public Interfaz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 330, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 12, 309, 282);
		contentPane.add(textArea);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(115, 310, 117, 25);
		contentPane.add(btnBuscar);
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Creamos el objeto JFileChooser
				JFileChooser fc=new JFileChooser();
				
				//Abrimos la ventana, guardamos la opcion seleccionada por el usuario
				int seleccion=fc.showOpenDialog(contentPane);
				//Si el usuario, pincha en aceptar
				if(seleccion==JFileChooser.APPROVE_OPTION){
					//Seleccionamos el fichero
					File fichero = fc.getSelectedFile();
					
					//Ecribe la ruta del fichero seleccionado en el campo de texto
					try(FileReader fr=new FileReader(fichero)){
						String cadena="";
						int valor=fr.read();
						while(valor!=-1){
							cadena=cadena+(char)valor;
							valor=fr.read();
						}
						textArea.setText(cadena);
						Gramatica g = new Gramatica(fichero.getAbsolutePath());
						Object[][] afd = ColeccionC.construir(g);
						
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
