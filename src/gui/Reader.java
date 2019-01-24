package gui;

import java.io.IOException;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import QR.Read_QR;

public class Reader implements Runnable{
	
	private Read_QR qr;
	
	public Reader(){
		this.qr = new Read_QR();
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
			while(true){
				if(camera.read(frame)){
					String filePath = "img/camera.jpg";
					Imgcodecs.imwrite(filePath, frame);
					try {
						readQR(filePath, frame);
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
}