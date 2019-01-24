package QR;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import org.opencv.core.Mat;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class Read_QR {
	 
	 public void reconocer(String filePath) throws NotFoundException, WriterException, IOException{
		 String info = readQRCode(filePath);
			if(info!=null){
				System.out.println("Frame Obtained");
				System.out.println("OK -> "+info);
			}
	 }
	 
	 private String readQRCode(String filePath) throws WriterException, IOException, NotFoundException{
		 String info = null;
		 try {
             Map < EncodeHintType, ErrorCorrectionLevel > hintMap = new HashMap < EncodeHintType, ErrorCorrectionLevel > ();
             hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
             info = getQRText(filePath, hintMap);
             System.out.println("Data read from QR Code: " + info);
         } catch (Exception e) {
            //System.out.println("Error");
         }
		 return info;
	 }
	 
	 private String getQRText(String filePath, Map hintMap) throws FileNotFoundException, IOException, NotFoundException {
		 
		 BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
	         new BufferedImageLuminanceSource(
	             ImageIO.read(new FileInputStream(filePath)))));
	     
	     Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
	     return qrCodeResult.getText();
	 }
}
