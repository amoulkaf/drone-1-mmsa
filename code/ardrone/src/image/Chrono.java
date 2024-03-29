package image;
// got on http://codes-sources.commentcamarche.net/source/20015-calculer-le-temps-d-execution-d-une-tache

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Chrono {
		Calendar m_start = new GregorianCalendar();
		Calendar m_stop = new GregorianCalendar();
		
		public Chrono() {		
		}
		
		//Lance le chronomètre
		public void start() {
			m_start.setTime(new Date());
		}
		
		//Arrète le chronomètre
		public void stop() {
			m_stop.setTime(new Date());
		}
		
		//Retourne le nombre de millisecondes séparant l'appel des méthode start() et stop()
		public long getMilliSec() {
			return (m_stop.getTimeInMillis() - m_start.getTimeInMillis());
		}
		
		//Affiche le nombre de millisecondes séparant l'appel des méthode start() et stop() sur la sortie standard
		public void printMilliSec() {
			if(getMilliSec() <= 0) {
				System.out.println("Chronometer not stopped");
			} else {
				System.out.println("Exec time : " + getMilliSec() + " ms");
			}
		}
	}