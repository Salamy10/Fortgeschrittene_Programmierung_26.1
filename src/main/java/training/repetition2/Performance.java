package training.repetition2.A1;

import java.time.LocalTime;

public record Performance(String bandName, LocalTime startTime, MusicGenre genre) implements Comparable<Performance> { //interface Comparable = Standard-Klasse -> nicht programmieren
	
	public int compareTo(Performance o)	{
		return startTime.compareTo(o.startTime);		
	}

}
