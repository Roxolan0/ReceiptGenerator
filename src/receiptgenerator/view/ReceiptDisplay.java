package receiptgenerator.view;

import java.text.DecimalFormat;
import java.util.Arrays;

import receiptgenerator.business.ProductDatabase;
import receiptgenerator.business.Receipt;


public class ReceiptDisplay {
	
	private static final DecimalFormat COST_FORMAT = new DecimalFormat("#0.00");
	private static final int ITEM_LENGTH = 21;
	private static final int QUANTITY_LENGTH = 10;
	private static final int COST_LENGTH = 7;
	private static final int TOTAL_LENGTH = ITEM_LENGTH + QUANTITY_LENGTH + COST_LENGTH;
	
	private static final String TITLE = "Customer Receipt";
	private static final String ITEM = "Item";
	private static final String QUANTITY = "Quantity";
	private static final String COST = "Cost";
	private static final String TOTAL_COST = "Total";
	private static final String CONCLUSION = "Thank you for shopping at SupaMarket";
	private static final String DISCOUNT_2FOR1 = "  2 for 1 discount";
	private static final String DISCOUNT_3FOR2 = "  3 for 2 offer";
	
	private static String repeatChar(char repeatedChar, int length) {
		if(length < 0) {
			length = 0;
		}
		char[] array = new char[length];
		Arrays.fill(array, repeatedChar);
		return new String(array);
	}
	
	public static void printReceipt(Receipt receipt, ProductDatabase productDatabase) {		
		System.out.print(repeatChar(' ', (TOTAL_LENGTH - TITLE.length())/2) + TITLE + "\n");
		System.out.print("\n");
		
		String headerLine = "";
		headerLine += ITEM + repeatChar(' ', ITEM_LENGTH - ITEM.length());
		headerLine += QUANTITY;
		headerLine += repeatChar(' ', TOTAL_LENGTH - headerLine.length() - COST.length());
		headerLine += COST;
		System.out.print(headerLine + "\n");
		System.out.print(repeatChar('-', TOTAL_LENGTH) + "\n");
		
		for(String productName : receipt.getPurchasedProductNames()) {
			String quantity = Integer.toString(receipt.getAmount(productName));
			String price = COST_FORMAT.format(receipt.totalPriceForProduct(productName, productDatabase));
			String receiptLine = "";
			receiptLine += productName + repeatChar(' ', ITEM_LENGTH - productName.length());
			receiptLine += quantity;
			receiptLine += repeatChar(' ', TOTAL_LENGTH - receiptLine.length() - price.length());
			receiptLine += price;
			System.out.print(receiptLine + "\n");
			
			double discount2for1 = receipt.discount2For1ForProduct(productName, productDatabase);
			if(discount2for1 > 0) {
				String discount2for1String = "-" + COST_FORMAT.format(discount2for1);
				System.out.print(DISCOUNT_2FOR1 
						+ repeatChar(' ', TOTAL_LENGTH - DISCOUNT_2FOR1.length() - discount2for1String.length())
						+ discount2for1String
						+ "\n");
			}
		}
		
		receipt.separateFreeFruitVeggie(productDatabase);
		if(receipt.getFreeFruitVeggie() != null) {
			for(String productName : receipt.getFreeFruitVeggie().keySet()) {
				String discount3for2String = "-" + COST_FORMAT.format(productDatabase.get(productName).getPrice());
				for(int i = 0; i < receipt.getFreeFruitVeggie().get(productName); ++i) {
					System.out.print(DISCOUNT_3FOR2
							+ repeatChar(' ', TOTAL_LENGTH - DISCOUNT_3FOR2.length() - discount3for2String.length())
							+ discount3for2String
							+ "\n");
				}
			}
		}
		
		System.out.print(repeatChar('-', TOTAL_LENGTH) + "\n");
		
		String totalCost = "£" + COST_FORMAT.format(receipt.totalPrice(productDatabase));
		System.out.print(TOTAL_COST 
				+ repeatChar(' ', TOTAL_LENGTH - TOTAL_COST.length() - totalCost.length()) 
				+ totalCost
				+ "\n");
		System.out.print("\n");
		System.out.print(repeatChar(' ', (TOTAL_LENGTH - CONCLUSION.length())/2) + CONCLUSION + "\n");
	}
}
