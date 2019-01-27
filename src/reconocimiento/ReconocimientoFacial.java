package reconocimiento;

import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;

import java.util.ArrayList;
import java.util.List;
//import static org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import personas.Persona;

public class ReconocimientoFacial {
	
	private  OpenCVFrameConverter.ToIplImage converter;
	 
    private String RutaDelCascade = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt2.xml";
    private CascadeClassifier Cascade;
    
    private MatOfRect rostros;//Guarda los rostros que va capturando
    private List<Rect> facesArray ;
    
    private List<Persona> personas;
    
    int highConfidenceLevel = 70;
    
    public ReconocimientoFacial(){
    	this.converter = new OpenCVFrameConverter.ToIplImage();
    	
    	this.Cascade = new CascadeClassifier(RutaDelCascade);
    	this.rostros = new MatOfRect();
    	this.facesArray = new ArrayList<Rect>();
    	
    	this.personas = new ArrayList<Persona>();
    	
    	inicializarPersonas();
    }
    
    private void inicializarPersonas(){
    	String nombre1 = "ADRIAN";
    	String nombre2 = "CHUS";
    	
		Persona persona1 = new Persona(nombre1);
		Persona persona2 = new Persona(nombre2);
		
		personas.add(persona1);
		personas.add(persona2);
    }
    
	public void reconocer(Mat frame, Mat frame_gray) throws Exception{
		
		Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGR2GRAY);//Colvierte la imagene a color a blanco y negro
        Imgproc.equalizeHist(frame_gray, frame_gray);//Valanzeamos los tonos grises
        double w = frame.width();
        double h = frame.height();
        //El 1.1 -> factor de escala de reducci�n de imagen (cuanto m�s grande menos preciso va a ser)
        //new Size(30, 30) -> tama�o minimo de caras a detectar
        //new Size(w, h) -> tama�o m�ximo de caras a detectar
        Cascade.detectMultiScale(frame_gray, rostros, 1.1, 2, 0|CASCADE_SCALE_IMAGE, new Size(30, 30), new Size(w, h));
        Rect[] rostrosLista = rostros.toArray();
        
        Rect rectCrop = new Rect();
		
        for (Rect rostro : rostrosLista) {
        	facesArray.add(rostro);
    
    		//String nombre = "persona"+facesArray.size();
    		String rutaImagen = "img/"+"persona"+".jpg";
    	    
    		//Se recorta la imagen
    		rectCrop = new Rect(rostro.x, rostro.y, rostro.width, rostro.height); 
    		frame = new Mat(frame,rectCrop);
    		//
    		//Se guarda la imagen
    		Imgcodecs.imwrite(rutaImagen, frame);
    		
    		for (Persona persona : personas) {
    			FeatureExtractionImage feaExtImgActual = new FeatureExtractionImage2(rutaImagen);
       		 
        		boolean mismaPersona = new FeatureDescriptionImage(persona.getFeatureExtractionImage(), feaExtImgActual).describeFeature();
        		
        		if(mismaPersona){
        			System.out.println("ESTA PERSONA ES -> "+persona.getNombre());
        		}
        		else{
        			System.out.println("No se ha encontrado a la persona");
        		}
        		//System.out.println("� PERSONA"+(facesArray.size()-1)+" Y "+"PERSONA"+facesArray.size()+" SON LA MISMA PERSONA? -> "+mismaPersona);
			}
    		
        }
        
        //System.out.println("Rostros detectados: "+facesArray.size());//CANTIDAD DE CARAS ENCONTRADAS
        
        
       
	}
	 
}

