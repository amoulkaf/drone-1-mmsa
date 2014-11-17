package PartieANOUARetANAS;

public class AirState implements IDroneState {

	// avancer
	public void forward(Controller controller) {
		String message = Commands.forward(controller.getSeq());
		controller.sendMessage(message);
	}

	// Reculer
	public void backward(Controller controller) {
		String message = Commands.backward(controller.getSeq());
		controller.sendMessage(message);
	}

	// Deplacement horizontal a droite
	public void horizontalRight(Controller controller) {
		String message = Commands.horizontalRight(controller.getSeq());
		controller.sendMessage(message);
	}

	// Deplacement horizontal a gauche
	public void horizontalLeft(Controller controller) {
		String message = Commands.horizontalLeft(controller.getSeq());
		controller.sendMessage(message);

	}

	// Decoller
	public void takeOff(Controller controller) {
		System.out.println("Warning : Drone in the air.");
	}

	// Atterir
	public void landing(Controller controller) {
		String message = Commands.landing(controller.getSeq());
		controller.sendMessage(message);
	}

	// Pivoter a droite
	public void rotateRight(Controller controller) {
		String message = Commands.rotateRight(controller.getSeq());
		controller.sendMessage(message);
	}

	// Pivoter a gauche
	public void rotateLeft(Controller controller) {
		String message = Commands.rotateLeft(controller.getSeq());
		controller.sendMessage(message);
	}

	// Descendre
	public void goDown(Controller controller) {
		String message = Commands.goDown(controller.getSeq());
		controller.sendMessage(message);
	}

	// Monter
	public void goUp(Controller controller) {
		String message = Commands.goUp(controller.getSeq());
		controller.sendMessage(message);
	}

	// Calibrer
	public void calibrate(Controller controller) {
		String message = Commands.calibrate(controller.getSeq());
		controller.sendMessage(message);
	}

	// Flip en avant

	public void frontFlip(Controller controller) {
		System.out.println("Command cannot be called indoor\n");
	}

	// Flip en arriere
	public void backFlip(Controller controller) {

		System.out.println("Command cannot be called indoor\n");
	}

	// Flip a gauche
	public void leftFlip(Controller controller) {

		System.out.println("Command cannot be called indoor\n");
	}

	// Flip a droit
	public void rightFlip(Controller controller) {

		System.out.println("Command cannot be called indoor\n");
	}

}
