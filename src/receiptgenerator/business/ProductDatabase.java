package receiptgenerator.business;

import java.util.HashMap;

public class ProductDatabase extends HashMap<String, Product>{
	public void put(Product product) {
		put(product.getName(), product);
	}
}
