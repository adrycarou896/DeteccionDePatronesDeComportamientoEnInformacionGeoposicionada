package gui;

import java.io.IOException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import QR.Read_QR;
import reconocimiento.ReconocimientoFacial;

public class Reader implements Runnable{

	private Read_QR qr;
    private ReconocimientoFacial reconocimientoFacial;
    
	public Reader(Entrenar entrenamiento){
		this.qr = new Read_QR();
		this.reconocimientoFacial = new ReconocimientoFacial(entrenamiento);
	}
	
	@Override
	public void run() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		VideoCapture camera=new VideoCapture(0);
         
		if(!camera.isOpened()){
			System.out.println("Error");
		}
		else{
			Mat frame = new Mat();
			Mat frame_gray = new Mat();
			while(true){
				if(camera.read(frame)){
					
					try {
						this.reconocimientoFacial.reconocer(frame, frame_gray);
					} catch (Exception e) {
						e.printStackTrace();
					}
					/*String filePath = "img/camera.jpg";
					Imgcodecs.imwrite(filePath, frame);
					try {
						qr.reconocer(filePath);
						try {
							reconocimientoFacial.reconocerConSURF(frame, frame_gray);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (NotFoundException | WriterException | IOException e) {
						e.printStackTrace();
					}*/
				}
			}
		}
		camera.release();
		
	}
}