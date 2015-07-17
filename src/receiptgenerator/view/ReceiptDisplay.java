package receiptgenerator.view;

import java.util.HashMap;

import receiptgenerator.business.Receipt;

public class ReceiptDisplay {
	
	public static void printReceipt(Receipt receipt, HashMap<String, Double> priceList) {
		
		System.out.print("          Customer Receipt\n"
				+ "\n"
				+ "Item          Quantity          Cost\n"
				+ "нннннннннннннннннннннннннннннннннннн\n");
		for(String productName : receipt.getPurchasedProductNames()) {
			System.out.print(productName + "               " 
					+ receipt.getAmount(productName) + "          "
					+ receipt.totalPriceForProduct(productName, priceList)
					+ "\n");
		}
		System.out.print("нннннннннннннннннннннннннннннннннннн\n"
				+ "Total                          г" + receipt.totalPrice(priceList) + "\n"
				+ "\n"
				+ "Thank you for shopping at SupaMarket\n");
	}
}
