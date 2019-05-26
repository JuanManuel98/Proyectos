package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;

public class AnalisisSintactico extends JFrame {

	private JPanel contentPane;
	JScrollPane AP = new JScrollPane();
	JScrollPane PE = new JScrollPane();
	static JTextArea errores = new JTextArea();
	static JTable tabla;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnalisisSintactico frame = new AnalisisSintactico();
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
	public AnalisisSintactico() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 871, 541);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane AP = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		//JScrollPane AP = new JScrollPane();
		AP.setBounds(10, 5, 835, 350);
		contentPane.add(AP);
		
		
		PE.setBounds(10, 366, 835, 125);
		contentPane.add(PE);
		errores.setForeground(new Color(255, 0, 0));
		errores.setFont(new Font("Monospaced", Font.PLAIN, 20));
		errores.setEditable(false);
		
		PE.setViewportView(errores);
	}
	public static void setNtextArea(String datos) {
		String cadena = "" + datos;
		//for(String i : datos)
			//cadena = cadena + i+" ";
		errores.setText(cadena);
	}
	public static void setNTable (String [][] coleccion, String[] columnas) {
		tabla = new JTable(coleccion, columnas); 
	}
}
