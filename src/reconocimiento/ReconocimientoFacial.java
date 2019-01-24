package reconocimiento;

import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class ReconocimientoFacial {
	
    String RutaDelCascade = "D:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt2.xml";
    CascadeClassifier Cascade = new CascadeClassifier(RutaDelCascade);
    
	public void reconocer(Mat frame, Mat frame_gray, MatOfRect rostros, Rect[] facesArray){
		Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGR2GRAY);//Colvierte la imagene a color a blanco y negro
        Imgproc.equalizeHist(frame_gray, frame_gray);//Valanzeamos los tonos grises
        double w = frame.width();
        double h = frame.height();
        Cascade.detectMultiScale(frame_gray, rostros, 1.2, 2, 0|CASCADE_SCALE_IMAGE, new Size(30, 30), new Size(w, h));
        facesArray = rostros.toArray();
        System.out.println("Se Detecto un Rostro: "+facesArray.length);//CANTIDAD DE CARAS ENCONTRADAS
	}
}
