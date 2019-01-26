package reconocimiento;

import static org.bytedeco.javacpp.opencv_highgui.destroyAllWindows;
import static org.bytedeco.javacpp.opencv_highgui.imshow;
import static org.bytedeco.javacpp.opencv_highgui.waitKey;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.FONT_HERSHEY_PLAIN;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;
import static org.bytedeco.javacpp.opencv_imgproc.putText;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;

import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
//import org.opencv.objdetect.CascadeClassifier;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

import personas.Persona;

public class ReconocimientoFacial {
	
    private String RutaDelCascade = "C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt2.xml";
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
		/*
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
        		//Con el converter pasar el frame a img
        		//Imgcodecs.imwrite(rutaImagen, frame);
        		
        		Persona persona = new Persona(nombre, rostro, rutaImagen);
        		this.personas.add(persona);
        	}
        }
       
        System.out.println("Se Detecto un Rostro: "+facesArray.size());//CANTIDAD DE CARAS ENCONTRADAS
        */
        //2ºParte
		cvtColor(frame, frame_gray, COLOR_BGRA2GRAY);
        equalizeHist(frame_gray, frame_gray);
        
        Point p = new Point();
        RectVector faces = new RectVector();
        // Find the faces in the frame:
        Cascade.detectMultiScale(frame_gray, faces);

        // At this point you have the position of the faces in
        // faces. Now we'll get the faces, make a prediction and
        // annotate it in the video. Cool or what?
        for (int i = 0; i < faces.size(); i++) {
            Rect face_i = faces.get(i);

            Mat face = new Mat(videoMatGray, face_i);
            // If fisher face recognizer is used, the face need to be
            // resized.
            // resize(face, face_resized, new Size(im_width, im_height),
            // 1.0, 1.0, INTER_CUBIC);

            // Now perform the prediction, see how easy that is:
            IntPointer label = new IntPointer(1);
            DoublePointer confidence = new DoublePointer(1);
            lbphFaceRecognizer.predict(face, label, confidence);
            int prediction = label.get(0);

            // And finally write all we've found out to the original image!
            // First of all draw a green rectangle around the detected face:
            rectangle(videoMat, face_i, new Scalar(0, 255, 0, 1));

            // Create the text we will annotate the box with:
            String box_text = "Prediction = " + prediction;
            // Calculate the position for annotated text (make sure we don't
            // put illegal values in there):
            int pos_x = Math.max(face_i.tl().x() - 10, 0);
            int pos_y = Math.max(face_i.tl().y() - 10, 0);
            // And now put it into the image:
            putText(videoMat, box_text, new Point(pos_x, pos_y),
                    FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0, 2.0));
        }
        
        // Show the result:
        imshow("face_recognizer", videoMat);

        char key = (char) waitKey(20);
        // Exit this loop on escape:
        if (key == 27) {
            destroyAllWindows();
            break;
        }
	}
}
