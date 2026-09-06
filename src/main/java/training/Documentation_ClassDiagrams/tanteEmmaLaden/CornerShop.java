package training.Documentation_ClassDiagrams.tanteEmmaLaden;

import java.util.Map;
import java.util.Optional;

public record CornerShop(String name, Map<Goods, Integer> store) {

	public Optional<Integer> getAmountByDescription(String description){
		return null;
	}
	
	public void buyGoods(Goods goods, int amount) {
		
	}
	
	public void sellGoods(Goods goods, int amount) {
		
	}

}
