package receiptgenerator.business;

import java.util.HashMap;
import java.util.Map;

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
	
	public double totalPriceForProduct(String productName, HashMap<String, Double> priceList) {
		double total = 0.0;
		
		if(purchasedProducts.containsKey(productName) && priceList.containsKey(productName)) {
			total = purchasedProducts.get(productName) * priceList.get(productName);
		}
		
		return total;
	}
	
	public double totalPrice(HashMap<String, Double> priceList) {
		double total = 0.0;
		
		for(String product : purchasedProducts.keySet()) {
			total += totalPriceForProduct(product, priceList);
		}
		
		return total;
	}
}
