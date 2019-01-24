package gui;

import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;

import java.io.IOException;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import QR.Read_QR;

public class Reader implements Runnable{
	
	private Read_QR qr;
	
    String RutaDelCascade = "D:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt2.xml";
    CascadeClassifier Cascade = new CascadeClassifier(RutaDelCascade);
    
	public Reader(){
		this.qr = new Read_QR();
	}
	
	@Override
	public void run() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		VideoCapture camera=new VideoCapture(0);
		
		 MatOfRect rostros = new MatOfRect();//Guarda los rostros que va capturando
         MatOfByte mem = new MatOfByte();//Se encarga de hacerlo binario
         
         Rect[] facesArray = null;
         
		if(!camera.isOpened()){
			System.out.println("Error");
		}
		else{
			Mat frame = new Mat();
			Mat frame_gray = new Mat();
			while(true){
				if(camera.read(frame)){
					String filePath = "img/camera.jpg";
					Imgcodecs.imwrite(filePath, frame);
					try {
						readQR(filePath, frame);
						reconocerCara(frame, frame_gray, rostros, facesArray);
					} catch (NotFoundException | WriterException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		camera.release();
	}
	
	private void readQR(String filePath, Mat frame) throws NotFoundException, WriterException, IOException{
		String info = qr.readQRCode(filePath);
		if(info!=null){
			System.out.println("Frame Obtained");
			System.out.println("Captured Frame Width "+
			frame.width() + "Heigh "+frame.height());
			System.out.println("OK -> "+info);
		}
	}
	
	private void reconocerCara(Mat frame, Mat frame_gray, MatOfRect rostros, Rect[] facesArray){
		Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGR2GRAY);//Colvierte la imagene a color a blanco y negro
        Imgproc.equalizeHist(frame_gray, frame_gray);//Valanzeamos los tonos grises
        double w = frame.width();
        double h = frame.height();
        Cascade.detectMultiScale(frame_gray, rostros, 1.2, 2, 0|CASCADE_SCALE_IMAGE, new Size(30, 30), new Size(w, h));
        facesArray = rostros.toArray();
        System.out.println("Se Detecto un Rostro: "+facesArray.length);//CANTIDAD DE CARAS ENCONTRADAS
	}
}