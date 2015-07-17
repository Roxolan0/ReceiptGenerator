package receiptgenerator.testing;

import java.util.HashMap;

import receiptgenerator.business.Receipt;
import receiptgenerator.view.ReceiptDisplay;

public class Task1 {
	private static final HashMap<String, Double> priceList;
	
	static {
		priceList = new HashMap<String, Double>();
		priceList.put("Orange", 0.4);
		priceList.put("Banana", 0.12);
		priceList.put("Tomato", 0.2);
		priceList.put("Box of cereal", 1.8);
		priceList.put("Loaf of bread", 0.8);
		priceList.put("Frozen pizza", 2.5);
	}
	
	public static void main(String[] args) {
		Receipt receipt;
		if(args.length > 0) {
			receipt = new Receipt(args[0]);
			ReceiptDisplay.printReceipt(receipt, priceList);
		} else {
			receipt = new Receipt("Orange|5\nBanana|3\n");
			ReceiptDisplay.printReceipt(receipt, priceList);
			receipt = new Receipt("Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n");
			ReceiptDisplay.printReceipt(receipt, priceList);
			receipt = new Receipt("Loaf of bread|1\n");
			ReceiptDisplay.printReceipt(receipt, priceList);
		}
	}
	
}
