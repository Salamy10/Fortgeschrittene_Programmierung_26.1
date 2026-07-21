package training.repetition2;

import java.util.List;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public record Festival(String name, Map<Performance, Stage> schedule) { 
	
	public void addPerformance(Performance performance, Stage stage) throws DuplicatePerformanceException {
		
		if (schedule.containsKey(performance)) {
			throw new DuplicatePerformanceException(performance);
		}
		
		schedule.put(performance, stage);
		
	}
	
	public Optional<Stage> getStageByBandName(String bandName){
		Optional<Stage> stage = Optional.empty();
		
		for (Entry<Performance, Stage> entry : schedule.entrySet()) {
			Performance p =entry.getKey();
			Stage s = entry.getValue();
			
			if (p.bandName().equals(bandName)) {
				stage = Optional.of(s);
				break; //rausspringen weil weitere angucken dumm | optional
			}
		}
		
		return stage;
	}
	
	public List<Performance> getPerformancesByGenre(MusicGenre genre) {
		
		List<Performance> performances = new ArrayList<>();
		
		for (Performance p : schedule.keySet()) {	// if (p.getGenre == genre) geht auch
			if (p.getGenre().equals(genre)){
				performances.add(p);
			}
		}
		
		Collections.sort(performances); //performances.sort(null);
		
		return performances;
		
	}

}