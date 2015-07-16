package receiptgenerator.business;

import java.util.HashMap;

public class ReceiptGenerator {
	/**
	 * @param tillOutput The string created by the till; formatted as a series of "string|integer\n"
	 * @return A HashMap<String, Integer> where String are product names and Integer their quantities.
	 */
	public HashMap<String, Integer> parseTillOutput(String tillOutput)
	{
		HashMap<String, Integer> tillOutputByProduct = new HashMap<String, Integer>();
		for(String tillOutputLine : tillOutput.split("\n"))
		{
			String productName = tillOutputLine.split("|")[0];
			int productQuantity = Integer.parseInt(tillOutputLine.split("|")[1]);
			if(tillOutputByProduct.containsKey(productName))
			{
				tillOutputByProduct.put(productName, tillOutputByProduct.get(productName) + productQuantity);
			} else {
				tillOutputByProduct.put(productName, productQuantity);
			}
		}
		return tillOutputByProduct;
	}
}
