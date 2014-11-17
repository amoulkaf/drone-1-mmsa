package PartieANOUARetANAS;

public class DroneStateContext {
	private IDroneState _currentState;
	private GroundState _ground;
	private AirState _airInDoor;
	private OutDoorState _airOutDoor;
	private Controller _controller;
	boolean state;

	public final static boolean INDOOR = true;
	public final static boolean OUTDOOR = false;

	// changement d etat -> outdoorState purement manuel

	public DroneStateContext(Controller controller) {
		_airOutDoor = new OutDoorState();
		_ground = new GroundState();
		_airInDoor = new AirState();
		_currentState = _ground;
		_controller = controller;
		state = INDOOR;
	}

	// avancer
	public void forward() {
		System.out.println("drone context forward called\n");
		_currentState.forward(_controller);

	}

	// Reculer
	public void backward() {
		_currentState.backward(_controller);
	}

	// Deplacement horizontal a droite
	public void horizontalRight() {
		_currentState.horizontalRight(_controller);
	}

	// Deplacement horizontal a gauche
	public void horizontalLeft() {
		_currentState.horizontalLeft(_controller);
	}

	// Decoller
	public void takeOff() {
		_currentState.takeOff(_controller);
		_currentState = _airInDoor;
	}

	// Atterir
	public void landing() {
		_currentState.landing(_controller);
		_currentState = _ground;
	}

	// Pivoter a droite
	public void rotateRight() {
		_currentState.rotateRight(_controller);
	}

	// Pivoter a gauche
	public void rotateLeft() {
		_currentState.rotateLeft(_controller);
	}

	// Descendre
	public void goDown() {
		_currentState.goDown(_controller);
	}

	// Monter
	public void goUp() {
		_currentState.goUp(_controller);
	}

	// Calibrer
	public void calibrate() {
		_currentState.calibrate(_controller);
	}

	public void frontFlip() {
		_currentState.frontFlip(_controller);
	}

	// Flip en arriere
	public void backFlip() {
		_currentState.backFlip(_controller);

	}

	// Flip a gauche
	public void leftFlip() {
		_currentState.leftFlip(_controller);

	}

	// Flip a droit
	public void rightFlip() {
		_currentState.rightFlip(_controller);
	}

	// Switch state to OutDoor/InDoor
	public void switchState() {
		if (state == INDOOR){
			state = OUTDOOR;
			_currentState = _airOutDoor;
			System.out.println("State changed to OUTDOOR\n");
		}
		else{
			state = INDOOR;
			_currentState = _airInDoor;
			System.out.println("State changed to INDOOR\n");
		}
	}

}
