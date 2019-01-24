package gui;

public class Invoker {
	public static void main(String[] args) {
    	Recorder recorder = new Recorder();
    	Thread thRecorder = new Thread(recorder);
    	
    	Reader reader = new Reader();
    	Thread thReader = new Thread(reader);
        
        //thReader.run();
    	thRecorder.run();
    }
}
