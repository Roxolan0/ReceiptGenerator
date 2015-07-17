package receiptgenerator.view;

import java.text.DecimalFormat;
import java.util.Arrays;

import receiptgenerator.business.ItemDatabase;
import receiptgenerator.business.Receipt;

public class ReceiptDisplay {

	//Dimensions and number format of the printed receipt
	private static final DecimalFormat COST_FORMAT = new DecimalFormat("#0.00");
	private static final int ITEM_LENGTH = 21;
	private static final int QUANTITY_LENGTH = 10;
	private static final int COST_LENGTH = 7;
	private static final int TOTAL_LENGTH = ITEM_LENGTH + QUANTITY_LENGTH + COST_LENGTH;

	//English-language strings to print
	private static final String TITLE = "Customer Receipt";
	private static final String ITEM = "Item";
	private static final String QUANTITY = "Quantity";
	private static final String COST = "Cost";
	private static final String TOTAL_COST = "Total";
	private static final String CONCLUSION = "Thank you for shopping at SupaMarket";
	private static final String DISCOUNT_2FOR1 = "  2 for 1 discount";
	private static final String DISCOUNT_3FOR2 = "  3 for 2 offer";

	/**
	 * Creates a String containing a single character repeated a number of times.
	 * @param repeatedChar The repeated character.
	 * @param length The number of repetitions.
	 * @return
	 */
	private static String repeatChar(char repeatedChar, int length) {
		if (length < 0) {
			length = 0;
		}
		char[] array = new char[length];
		Arrays.fill(array, repeatedChar);
		return new String(array);
	}

	/**
	 * Prints a properly-formated receipt.
	 * @param receipt The receipt data to print.
	 * @param itemDatabase An item database from which to get extra information.
	 */
	public static void printReceipt(Receipt receipt, ItemDatabase itemDatabase) {
		//Title
		System.out.print(repeatChar(' ', (TOTAL_LENGTH - TITLE.length()) / 2) + TITLE + "\n");
		System.out.print("\n");
		
		//Column headers
		String headerLine = "";
		headerLine += ITEM + repeatChar(' ', ITEM_LENGTH - ITEM.length());
		headerLine += QUANTITY;
		headerLine += repeatChar(' ', TOTAL_LENGTH - headerLine.length() - COST.length());
		headerLine += COST;
		System.out.print(headerLine + "\n");
		System.out.print(repeatChar('-', TOTAL_LENGTH) + "\n");

		//One line per item
		for (String itemName : receipt.keySet()) {
			String quantity = Integer.toString(receipt.get(itemName));
			String cost = COST_FORMAT.format(receipt.totalCostForItem(itemName, itemDatabase));
			String receiptLine = "";
			receiptLine += itemName + repeatChar(' ', ITEM_LENGTH - itemName.length());
			receiptLine += quantity;
			receiptLine += repeatChar(' ', TOTAL_LENGTH - receiptLine.length() - cost.length());
			receiptLine += cost;
			System.out.print(receiptLine + "\n");
			
			//2-for-1 discount (if any)
			double discount2for1 = receipt.discount2For1(itemName, itemDatabase);
			if (discount2for1 > 0) {
				String discount2for1String = "-" + COST_FORMAT.format(discount2for1);
				System.out.print(DISCOUNT_2FOR1
					+ repeatChar(' ', TOTAL_LENGTH - DISCOUNT_2FOR1.length() - discount2for1String.length())
					+ discount2for1String + "\n");
			}
		}

		//3-for-2 discounts (if any)
		receipt.separateFreeFruitVeggie(itemDatabase);
		if (receipt.getFreeFruitVeggie() != null) {
			for (String itemName : receipt.getFreeFruitVeggie().keySet()) {
				String discount3For2String = "-" + COST_FORMAT.format(itemDatabase.get(itemName).getCost());
				for (int i = 0; i < receipt.getFreeFruitVeggie().get(itemName); ++i) {
					System.out.print(DISCOUNT_3FOR2 
						+ repeatChar(' ', TOTAL_LENGTH - DISCOUNT_3FOR2.length() - discount3For2String.length())
						+ discount3For2String + "\n");
				}
			}
		}

		System.out.print(repeatChar('-', TOTAL_LENGTH) + "\n");

		//Total
		String totalCost = "£" + COST_FORMAT.format(receipt.totalCost(itemDatabase));
		System.out.print(TOTAL_COST
				+ repeatChar(' ', TOTAL_LENGTH - TOTAL_COST.length() - totalCost.length()) + totalCost + "\n");
		System.out.print("\n");
		
		//Goodbye
		System.out.print(repeatChar(' ', (TOTAL_LENGTH - CONCLUSION.length()) / 2) + CONCLUSION + "\n");
	}
}
