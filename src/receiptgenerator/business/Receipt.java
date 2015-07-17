package receiptgenerator.business;

import java.util.HashMap;
import java.util.Map;

public class Receipt extends HashMap<String, Integer>{
	
	private Receipt freeFruitVeggie;
	
	public Receipt() {
	}
	
	public Receipt(String tillOutput) {
		parseTillOutput(tillOutput);
	}
	
	public String[] getPurchasedProductNames() {
		String[] array = new String[size()];
		return keySet().toArray(array);
	}
	
	public int getAmount(String productName) {
		return get(productName);
	}
	
	public Integer put(String productName, Integer productQuantity) {
		Integer returnValue;
		if(containsKey(productName)) {
			returnValue = super.put(productName, get(productName) + productQuantity);
		} else {
			returnValue = super.put(productName, productQuantity);
		}
		return returnValue;
	}
	
	/**
	 * @param tillOutput The string created by the till; formatted as a series of "string|integer\n"
	 */
	public void parseTillOutput(String tillOutput) {
		String lineDelimiter = "\n|\\\\n";
		String sectionDelimiter = "\\|";
		
		
		clear();
		for(String tillOutputLine : tillOutput.split(lineDelimiter)) {
			String productName = tillOutputLine.split(sectionDelimiter)[0];
			String productQuantityString = tillOutputLine.split(sectionDelimiter)[1];
			int productQuantity = Integer.parseInt(productQuantityString);
			put(productName, productQuantity);
		}
	}
	
	public double totalPriceForProduct(String productName, ProductDatabase productDatabase) {
		double total = 0.0;
		
		if(containsKey(productName) && productDatabase.containsKey(productName)) {
			total = get(productName) * productDatabase.get(productName).getPrice();
		}
		
		return total;
	}
	
	public double discount2For1ForProduct(String productName, ProductDatabase productDatabase) {
		double discount = 0.0;
		
		if(containsKey(productName) 
				&& productDatabase.containsKey(productName)
				&& productDatabase.get(productName).is2for1()) {
			discount = get(productName)/2 * productDatabase.get(productName).getPrice();
		}
		
		return discount;
	}
	
	public String findCheapestFruitVeggie(ProductDatabase productDatabase) {
		String cheapestFruitVeggie = "";
		double cheapestCost = 10e307;
		
		for(String productName : keySet()) {
			if(productDatabase.get(productName).isFruitOrVegetable()
				&& productDatabase.get(productName).getPrice() < cheapestCost) {
				cheapestFruitVeggie = productName;
				cheapestCost = productDatabase.get(productName).getPrice();
			}
		}
		
		return cheapestFruitVeggie;
	}
	
	public void computeFreeFruitVeggie(ProductDatabase productDatabase) {
		freeFruitVeggie = new Receipt();
		
		int nbFreeFruitVeggie = 0;
		for(String productName : keySet()) {
			if(productDatabase.get(productName).isFruitOrVegetable()) {
				nbFreeFruitVeggie += get(productName);
			}
		}
		nbFreeFruitVeggie /= 3;
		
		while(nbFreeFruitVeggie > 0) {
			String cheapestFruitVeggie = findCheapestFruitVeggie(productDatabase);
			put(cheapestFruitVeggie, - 1);
			freeFruitVeggie.put(cheapestFruitVeggie, 1);
			--nbFreeFruitVeggie;
		}
	}
	
	public double totalPrice(ProductDatabase productDatabase) {
		double total = 0.0;
		
		for(String productName : keySet()) {
			total += totalPriceForProduct(productName, productDatabase);
			total -= discount2For1ForProduct(productName, productDatabase);
		}
		
		return total;
	}
}
