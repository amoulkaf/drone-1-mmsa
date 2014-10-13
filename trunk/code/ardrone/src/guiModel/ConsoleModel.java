package guiModel;

import java.util.Observable;

public class ConsoleModel extends  Observable{
	private String _text;
	
	public ConsoleModel() {
		_text = "";
	}
	
	public void addText(String txt){
		//boucler dans un premier temps pour qu'il n'y ait que 15lignes a afficher
		//dans un second temps, faire un JScrollPane
		_text += txt;
	}
	
}
