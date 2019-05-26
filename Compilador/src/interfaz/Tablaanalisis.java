package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Tablaanalisis extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JScrollPane JS = new JScrollPane();
	static JTable tabla;
	//tabla = new JTable();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tablaanalisis frame = new Tablaanalisis();
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
	public Tablaanalisis() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 1300);
		this.setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane JS = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
	
		contentPane.add(JS, BorderLayout.CENTER);
		
		//tabla = new JTable();
		//JS.setViewportView(tabla);
	}
	
	public static void setNTable (Object [][] coleccion, Object[] columnas) {
		tabla = new JTable(coleccion, columnas);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel=tabla.getColumnModel();
		for (int i=0;i<columnModel.getColumnCount();i++)
		{
			columnModel.getColumn(i).setPreferredWidth(700);
		}
	
	}

}
