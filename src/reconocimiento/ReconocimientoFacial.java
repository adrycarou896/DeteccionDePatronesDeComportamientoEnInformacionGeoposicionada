package reconocimiento;

import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.math3.util.Pair;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import gui.Entrenar;
import personas.Person;
import personas.PersonSURF;
import reconocimiento.reconocimientoSURF.SurfCompare;

public class ReconocimientoFacial {
	 
    private String RutaDelCascade = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt2.xml";
    private CascadeClassifier Cascade;
    
    private MatOfRect rostros;//Guarda los rostros que va capturando
    private List<Rect> facesArray ;
    
    int highConfidenceLevel = 70;
    
    private SurfCompare surfCompare;
    private List<PersonSURF> personsSURF;
    
    private Entrenar entrenamiento;
    private List<Person> persons;
    
    private int cont = 95;
    
    
    public ReconocimientoFacial(){
    	this.Cascade = new CascadeClassifier(RutaDelCascade);
    	this.rostros = new MatOfRect();
    	this.facesArray = new ArrayList<Rect>();
    	
    	this.surfCompare = new SurfCompare();
    	this.personsSURF = new ArrayList<PersonSURF>();
    }
    
    public ReconocimientoFacial(Entrenar entrenamiento){
    	this.Cascade = new CascadeClassifier(RutaDelCascade);
    	this.rostros = new MatOfRect();
    	
    	this.entrenamiento = entrenamiento;
    	this.persons = new ArrayList<Person>();
    }
    
    public void saveFaceOfPerson(Mat frame, Mat frame_gray,String rutaImagenNueva)throws Exception{
    	Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGR2GRAY);//Colvierte la imagene a color a blanco y negro
        Imgproc.equalizeHist(frame_gray, frame_gray);//Valanzeamos los tonos grises
        double w = frame.width();
        double h = frame.height();
        
        Cascade.detectMultiScale(frame_gray, rostros, 1.1, 2, 0|CASCADE_SCALE_IMAGE, new Size(30, 30), new Size(w, h));
        Rect[] rostrosLista = rostros.toArray();
        
        Rect rectCrop = new Rect();

        for (Rect rostro : rostrosLista) {
    		String rutaImagen = "img/persona.jpg";
    	    
    		//Se recorta la imagen
    		rectCrop = new Rect(rostro.x, rostro.y, rostro.width, rostro.height); 
    		frame = new Mat(frame,rectCrop);
    		
    		//Se guarda la imagen
    		Imgcodecs.imwrite(rutaImagen, frame);
    		
    		InputStream input = new FileInputStream(rutaImagen);
    		String srcSalida=rutaImagenNueva;
			OutputStream output = new FileOutputStream(srcSalida);
			resize(input, output, 607, 607);
        }
    }
    
    public void reconocer(Mat frame, Mat frame_gray) throws Exception{
		
		Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGR2GRAY);//Colvierte la imagene a color a blanco y negro
        Imgproc.equalizeHist(frame_gray, frame_gray);//Valanzeamos los tonos grises
        double w = frame.width();
        double h = frame.height();
        
        Cascade.detectMultiScale(frame_gray, rostros, 1.1, 2, 0|CASCADE_SCALE_IMAGE, new Size(30, 30), new Size(w, h));
        Rect[] rostrosLista = rostros.toArray();
        
        Rect rectCrop = new Rect();

        for (Rect rostro : rostrosLista) {
    		String rutaImagen = "img/persona.jpg";
    	    
    		//Se recorta la imagen
    		rectCrop = new Rect(rostro.x, rostro.y, rostro.width, rostro.height); 
    		frame = new Mat(frame,rectCrop);
    		
    		//Se guarda la imagen
    		Imgcodecs.imwrite(rutaImagen, frame);
    		
    		InputStream input = new FileInputStream(rutaImagen);
    		String srcSalida="img/test.jpg";
			OutputStream output = new FileOutputStream(srcSalida);
			resize(input, output, 607, 607);
			
			/*input = new FileInputStream(srcSalida);
			output = new FileOutputStream("img/usuario0/img"+cont+".jpg");
			resize(input, output, 607, 607);
			cont++;*/
			
    		Pair<Integer, Double> personPair = this.entrenamiento.test(srcSalida);
    		if(personPair!=null){
    			int personLabel = personPair.getFirst();
    			Person person = getPerson(personLabel);
    			if(person==null){
    				person = new Person(personLabel, System.currentTimeMillis());
    				this.persons.add(person);
    				System.out.println("El usuario que aparece en camara es el " + person.getNombre());
    		  	    System.out.println("        *Confidencia: "+personPair.getSecond());
    			}
    			else if(!person.isEnClase()){//Si existe ya la persona y la detecta fuera de clase
    				long ultimaVezDetectado = System.currentTimeMillis();
    				person.setUltimaVezDetectado(ultimaVezDetectado);
    			}
    			else if(person.isEnClase()){
    				double momentoDeLaDeteccion=System.currentTimeMillis();
    	        	double tiempoTranscurrido = (double)((momentoDeLaDeteccion - person.getUltimaVezDetectado())/1000);
    	        	System.out.println("Time->"+tiempoTranscurrido+" segundos = "+(momentoDeLaDeteccion - person.getUltimaVezDetectado())+" milisegundos");
    	        	person.setDetectado(true);
    	        	person.setEnClase(false);
    	        	person.setUltimaVezDetectado((long)momentoDeLaDeteccion);
    	        	System.out.println("Sale clase");
    			}
    		}
    		
        } 
        
        for (Person person : persons) {
        	long ultimaVezDetectado = person.getUltimaVezDetectado();
			long momentoActual = System.currentTimeMillis();
			long tiempoTranscurrido = momentoActual - ultimaVezDetectado;
			if(tiempoTranscurrido>5000 && !person.isEnClase()){
				person.setDetectado(false);
				person.setEnClase(true);
				System.out.println("Entra en clase");
			}
		}
        
    }
    
    private Person getPerson(int label){
    	for (Person person : persons) {
			if(person.getLabel()==label){
				return person;
			}
		}
    	return null;
    }
    
	public static void resize(InputStream input, OutputStream output, int width, int height) throws Exception {
	    BufferedImage src = ImageIO.read(input);
	    BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = dest.createGraphics();
	    AffineTransform at = AffineTransform.getScaleInstance
	    		((double)width / src.getWidth(), 
	    				(double)height / src.getHeight());
	    g.drawRenderedImage(src, at);
	    ImageIO.write(dest, "JPG", output);
	    output.close();
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

