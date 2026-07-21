package training.repetition2;

public class DuplicatePerformanceException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public DuplicatePerformanceException(Performance p) {
		super("Performance " + p + " is already existent!");
	}
	
}
