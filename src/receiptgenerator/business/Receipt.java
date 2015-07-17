package receiptgenerator.business;

import java.util.HashMap;

public class Receipt {
	
	private HashMap<String, Integer> purchasedProducts;
	
	public Receipt() {
		
	}
	
	public Receipt(String tillOutput) {
		parseTillOutput(tillOutput);
	}
	
	public HashMap<String, Integer> getPurchasedProducts() {
		return purchasedProducts;
	}
	
	public String[] getPurchasedProductNames() {
		String[] array = new String[purchasedProducts.size()];
		return purchasedProducts.keySet().toArray(array);
	}
	
	public int getAmount(String productName) {
		return purchasedProducts.get(productName);
	}
	
	/**
	 * @param tillOutput The string created by the till; formatted as a series of "string|integer\n"
	 */
	public void parseTillOutput(String tillOutput) {
		String lineDelimiter = "\n|\\\\n";
		String sectionDelimiter = "\\|";
		
		purchasedProducts = new HashMap<String, Integer>();
		
		for(String tillOutputLine : tillOutput.split(lineDelimiter)) {
			String productName = tillOutputLine.split(sectionDelimiter)[0];
			String productQuantityString = tillOutputLine.split(sectionDelimiter)[1];
			int productQuantity = Integer.parseInt(productQuantityString);
			if(getPurchasedProducts().containsKey(productName)) {
				getPurchasedProducts().put(productName, getPurchasedProducts().get(productName) + productQuantity);
			} else {
				getPurchasedProducts().put(productName, productQuantity);
			}
		}
	}
	
	public double totalPriceForProduct(String productName, ProductDatabase productDatabase) {
		double total = 0.0;
		
		if(purchasedProducts.containsKey(productName) && productDatabase.containsKey(productName)) {
			total = purchasedProducts.get(productName) * productDatabase.get(productName).getPrice();
		}
		
		return total;
	}
	
	public double totalPrice(ProductDatabase productDatabase) {
		double total = 0.0;
		
		for(String productName : purchasedProducts.keySet()) {
			total += totalPriceForProduct(productName, productDatabase);
			if(productDatabase.get(productName).is2for1()) {
				total -= purchasedProducts.get(productName)/2 * productDatabase.get(productName).getPrice();
			}
		}
		
		return total;
	}
}
