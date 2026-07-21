package training.repetition2;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class X03_ExamtasakA {
	
	public static void main(String[] args) {
		
		Performance p1 = new Performance("Die Ärtzte", LocalTime.of(12,0,0), MusicGenre.METAL);
		
		Stage s1 = new Stage("Rockbühne", StageSize.LARGE);
		
		Map<Performance, Stage> schedule = new HashMap<>();
		
		Festival festival = new Festival("Rock&Pop 2026", schedule);
		
		try {
			festival.addPerformance(p1,  s1);
		} catch(DuplicatePerformanceException e) {
			System.err.println(e.getMessage());
		}
		
		System.out.println(festival.getStageByBandName("Miley Cyrus"));
		System.out.println(festival.getPerformancesByGenre(MusicGenre.METAL));
		
	}
	
}
