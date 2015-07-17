package receiptgenerator.business;

import java.util.HashMap;

public class Receipt {
	
	private HashMap<String, Integer> purchasedProducts;
	
	/**
	 * @param tillOutput The string created by the till; formatted as a series of "string|integer\n"
	 */
	public void parseTillOutput(String tillOutput) {
		String lineDelimiter = "\n";
		String sectionDelimiter = "\\|";
		
		purchasedProducts = new HashMap<String, Integer>();
		
		for(String tillOutputLine : tillOutput.split(lineDelimiter)) {
			String productName = tillOutputLine.split(sectionDelimiter)[0];
			int productQuantity = Integer.parseInt(tillOutputLine.split(sectionDelimiter)[1]);
			if(getPurchasedProducts().containsKey(productName)) {
				getPurchasedProducts().put(productName, getPurchasedProducts().get(productName) + productQuantity);
			} else {
				getPurchasedProducts().put(productName, productQuantity);
			}
		}
	}

	public HashMap<String, Integer> getPurchasedProducts() {
		return purchasedProducts;
	}
}
