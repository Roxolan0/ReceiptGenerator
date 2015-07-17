package receiptgenerator.testing;

import java.util.HashMap;

import receiptgenerator.business.Product;
import receiptgenerator.business.ProductDatabase;
import receiptgenerator.business.Receipt;
import receiptgenerator.view.ReceiptDisplay;

public class Task1 {
	/*
	* In a serious project, you'd likely use an *actual* database for this.
	*/
	public static final ProductDatabase productDatabase;
	static {
		productDatabase = new ProductDatabase();
		productDatabase.put(new Product("Orange", 0.4, false, true));
		productDatabase.put(new Product("Banana", 0.12, false, true));
		productDatabase.put(new Product("Tomato", 0.2, false, true));
		productDatabase.put(new Product("Box of cereal", 1.8, false, false));
		productDatabase.put(new Product("Loaf of bread", 0.8, false, false));
		productDatabase.put(new Product("Frozen pizza", 2.5, true, false));
	}
	
	public static void main(String[] args) {
		Receipt receipt;
		if(args.length > 0) {
			receipt = new Receipt(args[0]);
			ReceiptDisplay.printReceipt(receipt, productDatabase);
		} else {
			receipt = new Receipt("Orange|5\nBanana|3\n");
			ReceiptDisplay.printReceipt(receipt, productDatabase);
			receipt = new Receipt("Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n");
			ReceiptDisplay.printReceipt(receipt, productDatabase);
			receipt = new Receipt("Loaf of bread|1\n");
			ReceiptDisplay.printReceipt(receipt, productDatabase);
		}
	}
	
}
