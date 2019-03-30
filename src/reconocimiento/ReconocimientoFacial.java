package reconocimiento;

import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;

import java.util.ArrayList;
import java.util.List;

//import static org.bytedeco.javacpp.opencv_core.IplImage;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import gui.Entrenar;
import personas.PersonSIFT;
import personas.PersonSURF;
import reconocimiento.reconocimientoSIFT.FeatureDescriptionImage;
import reconocimiento.reconocimientoSIFT.FeatureExtractionImage;
import reconocimiento.reconocimientoSIFT.FeatureExtractionImage2;
import reconocimiento.reconocimientoSURF.SurfCompare;

public class ReconocimientoFacial {
	 
    private String RutaDelCascade = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt2.xml";
    private CascadeClassifier Cascade;
    
    private MatOfRect rostros;//Guarda los rostros que va capturando
    private List<Rect> facesArray ;
    
    int highConfidenceLevel = 70;
    
    private List<PersonSIFT> personsSIFT;
    
    private SurfCompare surfCompare;
    private List<PersonSURF> personsSURF;
    
    private Entrenar entrenamiento;
    
    public ReconocimientoFacial(){
    	this.Cascade = new CascadeClassifier(RutaDelCascade);
    	this.rostros = new MatOfRect();
    	this.facesArray = new ArrayList<Rect>();

    	this.personsSIFT = new ArrayList<PersonSIFT>();
    	
    	this.surfCompare = new SurfCompare();
    	this.personsSURF = new ArrayList<PersonSURF>();
    }
    
    public ReconocimientoFacial(Entrenar entrenamiento){
    	this.entrenamiento = entrenamiento;
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
        	//String rutaImagen = "img/"+nombre+".jpg";
    		String rutaImagen = "img/"+"persona"+".jpg";
    	    
    		//Se recorta la imagen
    		rectCrop = new Rect(rostro.x, rostro.y, rostro.width, rostro.height); 
    		frame = new Mat(frame,rectCrop);
    		//
    		//Se guarda la imagen
    		Imgcodecs.imwrite(rutaImagen, frame);
    		
    		this.entrenamiento.test(rutaImagen);
    		
        } 
    }
        
	public void reconocerConSIFT(Mat frame, Mat frame_gray) throws Exception{
		
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
        	//String rutaImagen = "img/"+nombre+".jpg";
    		String rutaImagen = "img/"+"persona"+".jpg";
    	    
    		//Se recorta la imagen
    		rectCrop = new Rect(rostro.x, rostro.y, rostro.width, rostro.height); 
    		frame = new Mat(frame,rectCrop);
    		//
    		//Se guarda la imagen
    		Imgcodecs.imwrite(rutaImagen, frame);
    		
    		FeatureExtractionImage feaExtImgActual = new FeatureExtractionImage2(rutaImagen);
    		boolean coincideConPersona = false;
    		for (PersonSIFT person : this.personsSIFT) {
    			List<FeatureExtractionImage> featureExtractionImageLista = person.getFeatureExtractionImageLista();
    			for (FeatureExtractionImage featureExtractionImage : featureExtractionImageLista) {
    				boolean mismaPersona = new FeatureDescriptionImage(featureExtractionImage, feaExtImgActual).describeFeature();
        			if(mismaPersona){
        				person.addFeatureExtractionImage(rutaImagen, frame);
        				coincideConPersona = true;
        				System.out.println("ESTA PERSONA ES -> "+person.getNombre());
        				break;
        			}
    			}
			}
    		if(!coincideConPersona){
    			PersonSIFT person = new PersonSIFT("persona"+this.personsSIFT.size());
    			person.addFeatureExtractionImage(rutaImagen, frame);
    			this.personsSIFT.add(person);
    			System.out.println("Se ha encontrado una nueva persona");
    		}
    		
        } 
	}
	
	public void reconocerConSURF(Mat frame, Mat frame_gray) throws Exception{
		
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
        	//String rutaImagen = "img/surf/"+nombre+".jpg";
    		String rutaImagenActual = "img/"+"persona"+".jpg";
    	    
    		//Se recorta la imagen
    		rectCrop = new Rect(rostro.x, rostro.y, rostro.width, rostro.height); 
    		frame = new Mat(frame,rectCrop);
    		//
    		//Se guarda la imagen
    		Imgcodecs.imwrite(rutaImagenActual, frame);
    		
			boolean coincideConPersona = false;
			for (PersonSURF person : this.personsSURF) {
				List<String> imagenes = person.getImagenes();
				for (String imagen : imagenes) {
					surfCompare.getNumPointsAB(rutaImagenActual, imagen);
	    			boolean mismaPersona = surfCompare.sonElMismoRostro();
	    			if(mismaPersona){
	    				coincideConPersona = true;
	    				person.addImagen(rutaImagenActual, frame);
	    				System.out.println("ESTA PERSONA ES -> "+person.getNombre());
	    				break;
	    			}
				}
			}
			if(!coincideConPersona){
				PersonSURF person = new PersonSURF("persona"+this.personsSURF.size());
				person.addImagen(rutaImagenActual, frame);
				this.personsSURF.add(person);
				System.out.println("Se ha encontrado una nueva persona");
			}
    		
        } 
	}
	 
}

