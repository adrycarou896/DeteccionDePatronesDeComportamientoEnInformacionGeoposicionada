package gui;

public class Invoker {
	public static void main(String[] args) {	
		/*
		System.load("C:\\opencv\\build\\java\\x64\\opencv_java400.dll");
		
    	Recorder recorder = new Recorder();
    	Thread thRecorder = new Thread(recorder);
    	
    	Reader reader = new Reader();
    	Thread thReader = new Thread(reader);
        
        thReader.run();*/
    	//thRecorder.run();
		
		System.load("C:\\opencv\\build\\java\\x64\\opencv_java400.dll");
		
		Entrenar entrenamiento = new Entrenar();
		entrenamiento.run();
		
		Reader reader = new Reader(entrenamiento);
		reader.run();
		
    }
	
	
}
