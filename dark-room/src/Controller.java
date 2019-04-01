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



import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;



public class Controller {
	
	
	
	//legge una foto da un path e la inserisce nel label
	public void mostraFoto(JLabel label, String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat frame = Imgcodecs.imread(path);
		System.out.println("immagine " + path + " letta");
		
		BufferedImage Bimg = bufferedImage(frame);
		ImageIcon imgI = new ImageIcon(Bimg);
		label.setIcon((Icon) imgI);
		
		System.out.println("Immagine inserita");
		
	
	}
	
	
	public void mostraFoto(JLabel label, String path, String filtro, String c, String var) {
		Mat img = this.applicaFiltro(filtro, path, c, var);
		this.frame = img;
		BufferedImage Bimg = bufferedImage(img);
		ImageIcon imgI = new ImageIcon(Bimg);
		label.setIcon((Icon) imgI);
		
		System.out.println("Immagine inserita");
	}
	
	public Mat applicaFiltro(String filtro, String path, String c, String var) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = Imgcodecs.imread(path);
		Mat frame = img;
		
		if(filtro.equals("Negativo")) { //filtro negativo
			System.out.println("Applico il filtro negativo");
			for(int i=0;i<img.rows();i++) {
				for(int j=0;j<img.cols();j++) {
					double[] buff = img.get(i, j);
					for(int h=0;h<buff.length;h++) {
						buff[h] = 255.0 - buff[h];
					}
					frame.put(i, j, buff);
				}
			}
		
			System.out.println("Applico inversa");
		}
		
		if(filtro.equals("Logaritmico")) { //filtro logaritmico
			System.out.println("Applico il filtro logaritmico");
			//converto c in double
			double cc = Double.parseDouble(var);
			for(int i=0;i<img.rows();i++) {
				for(int j=0;j<img.cols();j++) {
					double[] buff = img.get(i, j);
					for(int h=0;h<buff.length;h++) {
						buff[h]=cc*Math.log(1+buff[h]);
						if(buff[h]>255) buff[h] = 255.0;
						if(buff[h]<0) buff[h] = 0.0;
					}
					frame.put(i, j, buff);
				}
			}
		}
		
		if(filtro.equals("Potenza")) {//filtro potenza
			System.out.println("Applico il filtro potenza");
			//converto c e var in double
			double cc = Double.parseDouble(c);
			double gamma = Double.parseDouble(var);
			if(gamma<0) {
				System.out.println("Parametro gamma non valido");
				JOptionPane.showMessageDialog(null, "Il parametro gamma deve essere positivo!");
			}
			for(int i=0;i<img.rows();i++) {
				for(int j=0;j<img.cols();j++) {
					double[] buff = img.get(i, j);
					for(int h=0;h<buff.length;h++) {
						buff[h]=cc*Math.pow(buff[h],gamma);
						if(buff[h]>255) buff[h] = 255.0;
						if(buff[h]<0) buff[h] = 0.0;
					}
					frame.put(i, j, buff);
				}
			}
		}
		
		if(filtro.equals("Brightness")) { //filtro per correzione luminosità
			System.out.println("Applico il filtro luminosita");
			//converto var in double
			double g = Double.parseDouble(var);
			for(int i=0;i<img.rows();i++) {
				for(int j=0;j<img.cols();j++) {
					double[] buff = img.get(i, j);
					for(int h=0;h<buff.length;h++) {
						buff[h]=buff[h] + g;
						if(buff[h]>255) buff[h] = 255.0;
						if(buff[h]<0) buff[h] = 0.0;
					}
					frame.put(i, j, buff);
				}
			}
			
		}
		
		if(filtro.equals("Contrast")) {//modifico il contrasto
			System.out.println("Applico il filtro contrasto");
			//converto var in double
			double m = Double.parseDouble(var);
			if(m<0) {
				System.out.println("Errore nel parametro m");
				JOptionPane.showMessageDialog(null, "Il parametro deve essere maggiore o uguale a 0");
			}
			for(int i=0;i<img.rows();i++) {
				for(int j=0;j<img.cols();j++) {
					double[] buff = img.get(i, j);
					for(int h=0;h<buff.length;h++) {
						buff[h]= m*(buff[h]-127) +127;
						if(buff[h]>255) buff[h] = 255;
						if(buff[h]<0) buff[h] = 0.0;
					}
					frame.put(i, j, buff);
				}
			}
		}
		
		if(filtro.equals("Gamma")) {//modifico la gamma
			System.out.println("Applico il filtro gamma");
			//converto var in double
			double gamma = Double.parseDouble(var);
			for(int i=0;i<img.rows();i++) {
				for(int j=0;j<img.cols();j++) {
					double[] buff = img.get(i, j);
					for(int h=0;h<buff.length;h++) {
						double buf = buff[h];
						buf = buf/255.0;
						buff[h]= 255.0*(Math.pow(buf, 1/gamma));
						if(buff[h]>255) buff[h] = 255.0;
						if(buff[h]<0) buff[h] = 0.0;
					}
					frame.put(i, j, buff);
				}
			}
		}
		
		if(filtro.equals("Laplaciano")) {
			System.out.println("Applico il filtro laplaciano");
			//converto i parametri
			double scale = Integer.parseInt(c);
			int ksize = Integer.parseInt(var);
			try {
				Imgproc.Laplacian(img, frame, img.depth(), ksize, scale);
			}catch(Exception e) {
				System.out.println("Errore nel filtro Laplaciano: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Parametri del filtro non validi!");
			}
		}
		
		if(filtro.equals("Gaussian")) {
			System.out.println("applico il filtro Gaussiano");
			double x = Double.parseDouble(var);
			double y = x;
			int s = Integer.parseInt(c);
			System.out.println("i parametri sono: c -> " + c + " var -> " + var);
			try {
				Imgproc.GaussianBlur(img, frame, new Size(s,s), x, y);
			} catch(Exception e) {
				System.out.println("Errore nel filtro gaussiano: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Parametri del filtro non validi!");
			}
		}
		
		if(filtro.equals("B/N")){
			System.out.println("Applico il filtro Bianco e nero");
			Imgproc.cvtColor(img, frame, Imgproc.COLOR_BGR2GRAY);
		}
		
		if(filtro.equals("Misto")) {
			System.out.println("Applico il filtro Misto");
			int m = Integer.parseInt(var);
			for(int i=0;i<img.rows();i++) {
				for(int j=0;j<img.cols();j++) {
					double[] buff = img.get(i, j);
					if((i+j)%m==0) {
						for(int h=0;h<buff.length;h++) {
							buff[h] = 255.0 - buff[h];
						}
					}
					frame.put(i, j, buff);
				}
			}
		}
		
		return frame;
	}
	
	
	public void SalvaFoto(String path) {
		Imgcodecs.imwrite(path,this.frame);
	}
	
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BufferedImage bufferedImage(Mat m) {
	    int type = BufferedImage.TYPE_BYTE_GRAY;
	    if ( m.channels() > 1 ) { //se l'immagine è a colori
	        type = BufferedImage.TYPE_3BYTE_BGR;
	    }
	    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
	    m.get(0,0,((DataBufferByte)image.getRaster().getDataBuffer()).getData()); // get all the pixels
	    return image;
	}
	
	private Mat frame;

}
