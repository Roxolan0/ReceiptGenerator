package receiptgenerator.business;

import java.util.HashMap;

public class ReceiptGenerator {
	/**
	 * @param tillOutput The string created by the till; formatted as a series of "string|integer\n"
	 * @return A HashMap<String, Integer> where String are product names and Integer their quantities.
	 */
	public static HashMap<String, Integer> parseTillOutput(String tillOutput) {
		String lineDelimiter = "\n";
		String sectionDelimiter = "\\|";
		
		HashMap<String, Integer> tillOutputByProduct = new HashMap<String, Integer>();
		
		for(String tillOutputLine : tillOutput.split(lineDelimiter)) {
			String productName = tillOutputLine.split(sectionDelimiter)[0];
			int productQuantity = Integer.parseInt(tillOutputLine.split(sectionDelimiter)[1]);
			if(tillOutputByProduct.containsKey(productName)) {
				tillOutputByProduct.put(productName, tillOutputByProduct.get(productName) + productQuantity);
			} else {
				tillOutputByProduct.put(productName, productQuantity);
			}
		}
		return tillOutputByProduct;
	}
	
}
