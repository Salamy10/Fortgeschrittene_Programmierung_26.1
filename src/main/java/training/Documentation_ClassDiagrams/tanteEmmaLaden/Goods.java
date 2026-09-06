package training.Documentation_ClassDiagrams.tanteEmmaLaden;
import training.Documentation_ClassDiagrams.tanteEmmaLaden.ClassOfGoods;

public class Goods extends Product implements Comparable<Goods> {
	
	private final ClassOfGoods classOfGoods;
	private final String unit;
	
	public Goods(String description, double price, String currency, ClassOfGoods classOfGoods, String unit){
		super(description, price, currency);
		this.classOfGoods = classOfGoods;
		this.unit = unit;
	}
	
	@Override
	public int compareTo(Goods o) {
		return this.getDescription().compareTo(o.getDescription());
	}
	
}
