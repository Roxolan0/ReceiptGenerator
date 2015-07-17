package receiptgenerator;

import receiptgenerator.business.Item;
import receiptgenerator.business.ItemDatabase;
import receiptgenerator.business.Receipt;
import receiptgenerator.view.ReceiptDisplay;

public class Main {
	/*
	* In a serious project, I might want an *actual* database for this.
	*/
	public static final ItemDatabase itemDatabaseTask1;
	public static final ItemDatabase itemDatabaseTask2;
	public static final ItemDatabase itemDatabaseTask3;
	static {
		itemDatabaseTask1 = new ItemDatabase();
		itemDatabaseTask1.put(new Item("Orange", 0.4, false, false));
		itemDatabaseTask1.put(new Item("Banana", 0.12, false, false));
		itemDatabaseTask1.put(new Item("Tomato", 0.2, false, false));
		itemDatabaseTask1.put(new Item("Box of cereal", 1.8, false, false));
		itemDatabaseTask1.put(new Item("Loaf of bread", 0.8, false, false));
		itemDatabaseTask1.put(new Item("Frozen pizza", 2.5, false, false));
		
		itemDatabaseTask2 = new ItemDatabase();
		itemDatabaseTask2.put(new Item("Orange", 0.4, false, false));
		itemDatabaseTask2.put(new Item("Banana", 0.12, false, false));
		itemDatabaseTask2.put(new Item("Tomato", 0.2, false, false));
		itemDatabaseTask2.put(new Item("Box of cereal", 1.8, false, false));
		itemDatabaseTask2.put(new Item("Loaf of bread", 0.8, false, false));
		itemDatabaseTask2.put(new Item("Frozen pizza", 2.5, true, false));
		
		itemDatabaseTask3 = new ItemDatabase();
		itemDatabaseTask3.put(new Item("Orange", 0.4, false, true));
		itemDatabaseTask3.put(new Item("Banana", 0.12, false, true));
		itemDatabaseTask3.put(new Item("Tomato", 0.2, false, true));
		itemDatabaseTask3.put(new Item("Box of cereal", 1.8, false, false));
		itemDatabaseTask3.put(new Item("Loaf of bread", 0.8, false, false));
		itemDatabaseTask3.put(new Item("Frozen pizza", 2.5, true, false));
	}
	
	public static void main(String[] args) {
		Receipt receipt;
		if(args.length > 0) {		//Till output as command-line argument
			receipt = new Receipt(args[0]);
			ReceiptDisplay.printReceipt(receipt, itemDatabaseTask3);
		} else {					//Examples from the specifications document.
			System.out.print("***** Task 1: \n\n");
			receipt = new Receipt("Orange|5\nBanana|3\n");
			ReceiptDisplay.printReceipt(receipt, itemDatabaseTask1);
			System.out.print("\n");
			receipt = new Receipt("Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n");
			ReceiptDisplay.printReceipt(receipt, itemDatabaseTask1);
			System.out.print("\n");
			receipt = new Receipt("Loaf of bread|1\n");
			ReceiptDisplay.printReceipt(receipt, itemDatabaseTask1);
			System.out.print("\n");
			
			System.out.print("\n ***** Task 2: \n\n");
			receipt = new Receipt("Frozen pizza|2\nBanana|2\n");
			ReceiptDisplay.printReceipt(receipt, itemDatabaseTask2);
			System.out.print("\n");
			receipt = new Receipt("Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n");
			ReceiptDisplay.printReceipt(receipt, itemDatabaseTask2);
			System.out.print("\n");
			
			System.out.print("\n ***** Task 3: \n\n");
			receipt = new Receipt("Orange|3\n");
			ReceiptDisplay.printReceipt(receipt, itemDatabaseTask3);
			System.out.print("\n");
			receipt = new Receipt("Orange|5\nBanana|3\n");
			ReceiptDisplay.printReceipt(receipt, itemDatabaseTask3);
			System.out.print("\n");
			receipt = new Receipt("Banana|2\nTomato|3\nBox of cereal|2\nOrange|6\n");
			ReceiptDisplay.printReceipt(receipt, itemDatabaseTask3);
			System.out.print("\n");
		}
	}
}
