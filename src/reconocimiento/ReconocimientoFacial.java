package reconocimiento;

import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import personas.Persona;

public class ReconocimientoFacial {
	
    private String RutaDelCascade = "D:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt2.xml";
    private CascadeClassifier Cascade;
    
    private MatOfRect rostros;//Guarda los rostros que va capturando
    private List<Rect> facesArray ;
    
    private List<Persona> personas;
    
    public ReconocimientoFacial(){
    	this.Cascade = new CascadeClassifier(RutaDelCascade);
    	this.rostros = new MatOfRect();
    	this.facesArray = new ArrayList<Rect>();
    	
    	this.personas = new ArrayList<Persona>();
    }
    
	public void reconocer(Mat frame, Mat frame_gray){
		Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGR2GRAY);//Colvierte la imagene a color a blanco y negro
        Imgproc.equalizeHist(frame_gray, frame_gray);//Valanzeamos los tonos grises
        double w = frame.width();
        double h = frame.height();
        Cascade.detectMultiScale(frame_gray, rostros, 1.2, 2, 0|CASCADE_SCALE_IMAGE, new Size(30, 30), new Size(w, h));
        Rect[] rostrosLista = rostros.toArray();
   
        for (Rect rostro : rostrosLista) {
        	for (Persona p : personas) {
				Rect rostroPersona = p.getRostro();
				if(rostro.equals(rostroPersona)){
					System.out.println("NOMBRE -> "+p.getNombre());
				}
			}
        	if(!facesArray.contains(rostro)){
        		facesArray.add(rostro);
        		String nombre = "persona"+facesArray.size();
        		String rutaImagen = "img/"+nombre+".jpg";
        		//Imgcodecs.imwrite(rutaImagen, frame);
        		
        		Persona persona = new Persona(nombre, rostro, rutaImagen);
        		this.personas.add(persona);
        	}
        }
        System.out.println("Se Detecto un Rostro: "+facesArray.size());//CANTIDAD DE CARAS ENCONTRADAS
	}
}
