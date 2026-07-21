package training.repetition2;

import java.awt.List;
import java.util.Map;
import java.util.Optional;

public record Festival(String name, Map<Performance, Stage> schedule) { 
	
	public void addPerformance(Performance performance, Stage stage) throws DuplicatePerformanceException {
		if () {
			throw new DuplicatePerformanceException();			
		} else {
			return;
		}
	}
	
	public Optional<Stage> getStageByBandName(String bandName){
		
	}
	
	public List<Performance> getPerformancesByGenre(MusicGenre genre) {
		
	}

}