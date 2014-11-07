package PartieANOUARetANAS;

public class DroneStateContext {
	private IDroneState _currentState;
	private GroundState _ground;
	private AirState _air;
	private Controller _controller;

	// changement d etat -> outdoorState purement manuel

	public DroneStateContext(Controller controller) {

		_ground = new GroundState();
		_air = new AirState();
		_currentState = _ground;
		_controller = controller;
	}

	// avancer
	public void forward() {
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
		_currentState = _air;
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
		_currentState.rotateRight(_controller);
	}

	// Descendre
	public void goDown() {
		_currentState.goDown(_controller);
	}

	// Monter
	public void goUp() {
		_currentState.goUp(_controller);
	}
	
	//Calibrer
	public void calibrate(){
		_currentState.calibrate(_controller);
	}

}
