package testyt2;

import javax.swing.*;

import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class test {

	public static void main(String[] args) {
		//cr�ation fenetre
		JFrame frame = new JFrame("Titre");
		//se ferme si on ferme la page
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//dimension fenetre
		frame.setSize(500,500);
		//appel de la class FacePanel
		FacePanel facePanel = new FacePanel();
		//met le layout facepanel en prioritaire
		frame.setContentPane(facePanel);
		
		MatToBufImg matToBufferedImageConverter = new MatToBufImg();
		//chargement librairie opencv
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//capture video webcam
		VideoCapture webCam = new VideoCapture(0);
		//si la cam�ra n'est pas connect�e
		if(!webCam.isOpened()) {
			System.out.println(" Cam�ra non connect�e");
		}
		else System.out.println("cam�ra trouv�e"+ webCam.toString());//si cam�ra connect�
		//fenetre visible
		frame.setVisible(true);
		//enregistrement de l'image webacm
		Mat webcam_image = new Mat();
		
		//si cam�ra ouverte
		if(webCam.isOpened()) {
			try {
				Thread.sleep(5000);//arr�te un thread pendant 500 millisecondes
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while( true ) {//boucle infinie
				webCam.read(webcam_image);
				if( !webcam_image.empty() ) {//si l'image est pleine
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					matToBufferedImageConverter.setMAtrix(webcam_image, ".jpg"); //converti MAt en JPG
					BufferedImage bufImg = matToBufferedImageConverter.getBuffreredImage();  //appelle la version JPG de l'image
					
					facePanel.setFace(bufImg); //ajoute le JPG au JPanel
					
				}
				else {//si l'image est vide
					System.out.println("rien trouv� a partir de la cam�ra");
					break;
				}
			}//fin while
			webCam.release();
		}
		

	}

}
