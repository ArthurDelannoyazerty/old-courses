package cameratest;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
public class CameraSnapshotJavaFX extends Application{

	   Mat matrix = null;

	   public void start(Stage stage) throws FileNotFoundException, IOException {//Exception=erreur
	      // la cam�ra tourne et l'on prend certaine images de la vid�o
	      CameraSnapshotJavaFX obj = new CameraSnapshotJavaFX();
	      CameraSnapshotJavaFX objface = new CameraSnapshotJavaFX();
	      
	      //l'image est cod�e en WritableImage qui peut �tre lue par JavaFX
	      //appel de la m�thode captureSnapshot() qui cr�e un WritableImage a partir de Mat/matrice
	      WritableImage writableImage = obj.capureSnapShot();
	      WritableImage writableImageface = objface.faceDetection();
	      // sauvegarde de l'image
	      //appel de la classe saveImage() sans retour
	      obj.saveImage();
	      objface.saveImage();
	      
	      //permet de gerer les param�tres de l'image writableImage
	      ImageView imageView = new ImageView(writableImage);
	      ImageView imageViewface = new ImageView(writableImageface);
	      
	      //hauteur = 400px et largeur = 600px
	      imageView.setFitHeight(400);
	      imageView.setFitWidth(600);
	      imageViewface.setFitHeight(400);
	      imageViewface.setFitWidth(600);

	      // pas de d�formation de l'image
	      imageView.setPreserveRatio(true);
	      imageViewface.setPreserveRatio(true);

	      //class abstraite contenant tous les �l�ments graphiques permettant la mie en place de Scene
	      //le groupe root contient l'image writableImage
	      Group root = new Group(imageView);
	      Group rootface = new Group(imageViewface);

	      // cr�ation de Scene(conteneur d'�l�ments sur la fen�tre
	      Scene scene = new Scene(root, 600, 400);
	      Scene sceneface = new Scene(rootface, 600, 400);
	      
	      //stage = frame dans JPanel mais dans javaFX
	      //titre de la fenetre
	      stage.setTitle("Capturing an image");

	      //stage est le conteneur d'objets dans javaFX
	      //ajout de Scene dans Stage (et donc de writable image dans Stage)
	      stage.setScene(scene);
	      stage.setScene(sceneface);

	      // visibilit� = OK
	      stage.show();
	}
	private WritableImage faceDetection() {
		//initialisation de WritableImage
	      WritableImage WritableImageface = null;

	      // chargement librairies opencv
	      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

	      // initialisation de videoCapture
	      VideoCapture capture = new VideoCapture(0);

	      // lecture de l'image
	      Mat matrix = new Mat();
	      capture.read(matrix);

	      // si la cam�ra est ouverte alors:
	      if( capture.isOpened()) {
	         // Si il y a bien une image qui nous parvient alors:
	         if (capture.read(matrix)) {
	        	 
	        	 String file = "xml/haarcascade_frontalface_alt.xml";
	        	 CascadeClassifier classifier = new CascadeClassifier(file);
	        	 
	        	 MatOfRect faceDetections = new MatOfRect();
	        	 classifier.detectMultiScale(matrix, faceDetections);
	        	 System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
	        	 //carr�s
	        	 for (Rect rect : faceDetections.toArray()) {
	                 Imgproc.rectangle(
	                    matrix,                                   //dessinner carr� dans matrix
	                    new Point(rect.x, rect.y),                            //bas gauche
	                    new Point(rect.x + rect.width, rect.y + rect.height), //haut droite
	                    new Scalar(0, 0, 255)                                 //couleur carr�
	                 );
	              }
	        	 
	        	 
	            // cr�ation de BufferedImage a l'aide d'une matrice
	            BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
	            
	            WritableRaster raster = image.getRaster();
	            DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
	            byte[] data = dataBuffer.getData();
	            matrix.get(0, 0, data);
	            this.matrix = matrix;
	            
	            //cr�ation de WritableImage
	            WritableImageface = SwingFXUtils.toFXImage(image, null);
	         }
	      }
	      //retour WritableImage
	      return WritableImageface;
	}
	public WritableImage capureSnapShot() {
		   //initialisation de WritableImage
	      WritableImage WritableImage = null;

	      // chargement librairies opencv
	      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

	      // initialisation de videoCapture
	      VideoCapture capture = new VideoCapture(0);

	      // lecture de l'image
	      Mat matrix = new Mat();
	      capture.read(matrix);

	      // si la cam�ra est ouverte alors:
	      if( capture.isOpened()) {
	         // Si il y a bien une image qui nous parvient alors:
	         if (capture.read(matrix)) {
	            // cr�ation de BufferedImage a l'aide d'une matrice
	            BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
	            
	            WritableRaster raster = image.getRaster();
	            DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
	            byte[] data = dataBuffer.getData();
	            matrix.get(0, 0, data);
	            this.matrix = matrix;
	            
	            //cr�ation de WritableImage
	            WritableImage = SwingFXUtils.toFXImage(image, null);
	         }
	      }
	      //retour WritableImage
	      return WritableImage;
	   }
	   
	   public void saveImage() {
	      // s�lection du dossier de sauvegarde
	      String file = "E:/OpenCV/chap22/sanpshot.jpg";

	      // instanciation de imagecodecs
	     //Imgcodecs imageCodecs = new Imgcodecs();

	      // sauvegarde
	      //dossier de sauvegarde, type de dossier sauvegard�
	      Imgcodecs.imwrite(file, matrix);
	   }
	   public static void main(String args[]) {
	      launch(args);//la m�thode main est ignor�e
	   }
	
	

}
