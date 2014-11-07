package PartieANOUARetANAS;

public class GroundState implements IDroneState {

	// avancer
	public void forward(Controller controller) {
		System.out.println("Warning : Drone on the groud.");
	}

	// Reculer
	public void backward(Controller controller) {
		System.out.println("Warning : Drone on the groud.");
	}

	// Deplacement horizontal a droite
	public void horizontalRight(Controller controller) {
		System.out.println("Warning : Drone on the groud.");
	}

	// Deplacement horizontal a gauche
	public void horizontalLeft(Controller controller) {
		System.out.println("Warning : Drone on the groud.");
	}

	// Decoller
	public void takeOff(Controller controller) {
		String message = Commands.takeOff(controller.getSeq());
		controller.sendMessage(message);

	}

	// Atterir
	public void landing(Controller controller) {
		System.out.println("Warning : Drone on the groud.");
	}

	// Pivoter a droite
	public void rotateRight(Controller controller) {
		System.out.println("Warning : Drone on the groud.");
	}

	// Pivoter a gauche
	public void rotateLeft(Controller controller) {
		System.out.println("Warning : Drone on the groud.");
	}

	// Descendre
	public void goDown(Controller controller) {
		System.out.println("Warning : Drone on the groud.");
	}

	// Monter
	public void goUp(Controller controller) {
		System.out.println("Warning : Drone on the groud.");
	}
	
	//Calibrer
	public void calibrate(Controller controller){
		System.out.println("Warning : Drone on the groud.");
	}
}
