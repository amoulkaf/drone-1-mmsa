import guiView.MainWindowGUI;


public class Main {

	public static void main(String[] args) {
		new MainWindowGUI();
		
		Drone ardrone = new Drone("192.168.1.1", 5556, "\r", "AR-Drone");
		Drone localhost = new Drone("localhost", 7000, "\n", "localhost");
		  
		//ardrone.start();
		//localhost.start();
	}
}
