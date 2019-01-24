package gui;

import org.bytedeco.javacv.*;

import QR.Read_QR;

import static org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvFlip;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

public class Recorder implements Runnable{
    final int INTERVAL = 100;
    CanvasFrame canvas = new CanvasFrame("Web Cam");

    public Recorder() {
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }
	
	@Override
    public void run() {

        FrameGrabber grabber = new VideoInputFrameGrabber(0);
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        IplImage img;
		
        try {
            grabber.start();
            Read_QR qr=new Read_QR();
            //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            //VideoCapture camera=new VideoCapture(0);
            
        	/*if(!camera.isOpened()){
    			System.out.println("Error");
    		}
        	else{
        		Read_QR qr=new Read_QR();
                Mat frameM = new Mat();*/
    			while (true) {
    				
    				String filePath = "img/camera.jpg";

					Frame frame = grabber.grab();
					img = converter.convert(frame);
					
                    //the grabbed frame will be flipped, re-flip to make it right
                    cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
                    
                    //if(camera.read(frameM)){
    					
    					//Imgcodecs.imwrite(filePath, frameM);
                    cvSaveImage(filePath, img);
                    	
                    qr.reconocer(filePath);
    				//}
              
                    canvas.showImage(converter.convert(img));
                    
                    Thread.sleep(INTERVAL);
                }
        		
        	//}
        	//camera.release();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
