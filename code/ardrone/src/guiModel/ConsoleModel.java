package guiModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;

public class ConsoleModel extends Observable{
	private String _text;
	private final static String LOG_PATH = "file.log";
	
	public ConsoleModel() {
		_text = "";
		WatchingLog log = new WatchingLog();
		log.start();
		readInFile();
	}
	
	public final void addText(final String txt){
		_text += txt + "\n";
		
		setChanged();
		notifyObservers();
	}
	
	public String getText(){
		return _text;
	}
	
	public void writeInFile(String txt){
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	String time = sdf.format(cal.getTime());
    	
		FileWriter fw;
		try {
			fw = new FileWriter(LOG_PATH);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(getText() + time +"> " + txt);
			bw.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readInFile(){
		try {
			FileReader logFile = new FileReader(LOG_PATH);
			BufferedReader br = new BufferedReader(logFile);
			String s;
			try {
				while((s = br.readLine()) != null){
						addText(s);
				}
				br.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Error while openning file");
		} 
	}
	
	public class WatchingLog extends Thread{
		public void run(){
			try {
				final Path path = FileSystems.getDefault().getPath(new File( "." ).getCanonicalPath(), "");
				System.out.println(path);
				final WatchService watchService = FileSystems.getDefault().newWatchService();
				path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
				while (true) {
				    WatchKey wk;
					try {
						wk = watchService.take();
						for (WatchEvent<?> event : wk.pollEvents()) {
					        final Path changed = (Path) event.context();
					        if (changed.endsWith(LOG_PATH)) {
					            try {
					    			FileReader logFile = new FileReader(LOG_PATH);
					    			BufferedReader br = new BufferedReader(logFile);
					    			String s;
					    			try {
					    				int currentLine = 0;
					    				int lastLine = 0;
					    				while((s = br.readLine()) != null){
					    					lastLine++;
					    				}
					    				br.close();
					    				logFile = new FileReader(LOG_PATH);
					    				br = new BufferedReader(logFile);
					    				while((s = br.readLine()) != null){
					    					currentLine++;
					    					if(currentLine == lastLine){
					    						addText(s);
					    						break;
					    					}
					    				}
					    				br.close();
					    			} 
					    			catch (IOException e) {
					    				e.printStackTrace();
					    			}
					    		} 
					    		catch (FileNotFoundException e) {
					    			System.out.println("Error while openning file");
					    		} 
					        }
					    }
					    // reset the key
					    boolean valid = wk.reset();
					    if (!valid) {
					        System.out.println("Key has been unregisterede");
					    }
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} 
			catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
