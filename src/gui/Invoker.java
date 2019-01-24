package gui;

public class Invoker {
	public static void main(String[] args) {
		System.load("D:\\opencv\\build\\java\\x64\\opencv_java400.dll");
		
    	Recorder recorder = new Recorder();
    	Thread thRecorder = new Thread(recorder);
    	
    	Reader reader = new Reader();
    	Thread thReader = new Thread(reader);
        
        thReader.run();
    	//thRecorder.run();
    }
}
