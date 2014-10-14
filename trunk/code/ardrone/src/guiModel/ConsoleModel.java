package guiModel;

import java.util.Observable;

public class ConsoleModel extends  Observable{
	private String _text;
	
	public ConsoleModel() {
		_text = "";
	}
	
	public final void addText(final String txt){
		//boucler dans un premier temps pour qu'il n'y ait que 15lignes a afficher
		//dans un second temps, faire un JScrollPane
		//dans un troisieme temps, lire fichier texte .log
		_text += txt;
		int nbOfLines = 1;
		for (int i = 0; i < txt.length(); i++){
			if (txt.charAt(i) == '\n'){
				nbOfLines++;
			}
		}
	
		System.out.println("[ConsoleModel] Number of lines in log : " + nbOfLines);
		
		setChanged();
		notifyObservers();
	}
	
	public String getText(){
		return _text;
	}
	
}
