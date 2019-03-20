/* Copyright
 * 
 * Giuseppe Ferrara
 * Claudio Dotani
 * Gianfranco Foscardi
 * Fabrizio Cappella
 * 
 * Progetto OpenCV per l'esame di Sistemi Multimediali
 * 
 * 
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.JTextField;

public class View {

	private JFrame frame;
	private String foto = new String();
	private String filtro = new String("Negativo");
	private JTextField textField;
	private JTextField textField_1;
	private String c = new String("1");
	private String var = new String("1");
	/**
	 * Launch the application.
	 */
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 947, 552);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBenvenutoInDarkroom = new JLabel("Benvenuto in Dark-Room");
		lblBenvenutoInDarkroom.setBounds(360, 11, 170, 14);
		frame.getContentPane().add(lblBenvenutoInDarkroom);
		
		JButton btnScegliUnaFoto = new JButton("Scegli una foto"); //scelgo il file
		btnScegliUnaFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("pulsante Scegli premuto");
				JFileChooser file = new JFileChooser();
		          file.setCurrentDirectory(new File(System.getProperty("user.home")));
		          System.out.println("attesa selezione utente");
		          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
		          file.addChoosableFileFilter(filter);
		          int result = file.showSaveDialog(null);
		          //file selezionato
		          if(result == JFileChooser.APPROVE_OPTION){
		        	  frame.getContentPane().getComponent(7).setVisible(false);
		        	  System.out.println("logo eliminato");
		              File selectedFile = file.getSelectedFile();
		              String path = selectedFile.getAbsolutePath();
		              foto = path; //salvo l'indirizzo della foto
		              System.out.println("il path della fotoselezionata e': " + path);
		              frame.getContentPane().getComponent(5).setVisible(false);
		              System.out.println("Intro Rimossa");
		              Controller controller = new Controller();
		              JLabel lbl = new JLabel(""); //foto iniziale
		      		  lbl.setBounds(10, 60, 430, 378);
		      		  frame.getContentPane().add(lbl);
		              controller.mostraFoto(lbl,path);
		          }
		          //nessun file selezionato
		          else if(result == JFileChooser.CANCEL_OPTION){
		              System.out.println("Nessun file selezionato");
		          }
			}
		});
		
		btnScegliUnaFoto.setBounds(10, 479, 120, 23);
		frame.getContentPane().add(btnScegliUnaFoto);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Negativo", "Logaritmico", "Potenza", "Brightness", "Contrast", "Gamma"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(202, 480, 170, 20);
		frame.getContentPane().add(comboBox);
		comboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				filtro = (String) comboBox.getSelectedItem();
				System.out.println("il filtro selezionato e': " + filtro);
				
			}
			
		});
		
		JLabel lblScegliUnFiltro = new JLabel("Scegli un filtro da applicare");
		lblScegliUnFiltro.setBounds(202, 455, 170, 14);
		frame.getContentPane().add(lblScegliUnFiltro);
		
		JButton btnApplica = new JButton("Applica");
		btnApplica.setBounds(819, 477, 89, 23);
		frame.getContentPane().add(btnApplica);
		btnApplica.addActionListener(new ActionListener() { //applica

			@Override
			public void actionPerformed(ActionEvent e) {
				//aggiorno il valore delle textfield
				c = textField.getText();
				var = textField_1.getText();
				Controller controller = new Controller();
				controller.mostraFoto((JLabel)frame.getContentPane().getComponent(6), foto, filtro, c, var);
				System.out.println("foto finale mostrata");
				
				
			}
			
			
		});
		
		JLabel lblNewLabel = new JLabel(""); //foto iniziale
		lblNewLabel.setBounds(10, 60, 430, 378);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(""); //foto finale
		lblNewLabel_1.setBounds(478, 60, 430, 378);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblIntro = new JLabel(new ImageIcon("./grafica/logo.jpg")); //logo
		lblIntro.setBounds(85, 54, 766, 316);
		frame.getContentPane().add(lblIntro);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				c = textField.getText();
				System.out.println("il valore di c e': " + c);
				
			}
		});
		textField.setText("1");
		textField.setBounds(423, 480, 131, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				var = textField_1.getText();
				System.out.println("il valore di var e': " + var);
			}
		});
		textField_1.setText("1");
		textField_1.setBounds(632, 480, 131, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblInserisciIlValore = new JLabel("Inserisci il valore della costante c");
		lblInserisciIlValore.setBounds(396, 455, 210, 14);
		frame.getContentPane().add(lblInserisciIlValore);
		
		JLabel lblInserisciIlValore_1 = new JLabel("Inserisci il valore del filtro");
		lblInserisciIlValore_1.setBounds(630, 455, 149, 14);
		frame.getContentPane().add(lblInserisciIlValore_1);
		
	}
}
