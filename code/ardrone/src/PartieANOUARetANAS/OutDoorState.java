package PartieANOUARetANAS;

public class OutDoorState extends AirState {

	@Override
	// Flip en avant
	public void frontFlip(Controller controller) {
		String message = Commands.frontFlip(controller.getSeq());
		controller.sendMessage(message);
	}

	// Flip en arriere
	@Override
	public void backFlip(Controller controller) {
		String message = Commands.backFlip(controller.getSeq());
		controller.sendMessage(message);

	}

	// Flip a gauche
	@Override
	public void leftFlip(Controller controller) {
		String message = Commands.leftFlip(controller.getSeq());
		controller.sendMessage(message);

	}

	// Flip a droit
	@Override
	public void rightFlip(Controller controller) {
		String message = Commands.rightFlip(controller.getSeq());
		controller.sendMessage(message);

	}

}
