package receiptgenerator.view;

import java.util.HashMap;
import java.util.Map;

public class ReceiptDisplay {
	
	public static void printReceipt(HashMap<String, Integer> receiptMap, HashMap<String, Double> priceList) {
		
		System.out.print("          Customer Receipt\n"
				+ "\n"
				+ "Item          Quantity          Cost\n"
				+ "нннннннннннннннннннннннннннннннннннн\n");
		for(Map.Entry<String, Integer> entry : receiptMap.entrySet()) {
			if(priceList.containsKey(entry.getKey())) {
				System.out.print(entry.getKey() + "               " 
						+ entry.getValue() + "          "
						+ priceList.get(entry.getKey())
						+ "\n");
			}
		}
		System.out.print("нннннннннннннннннннннннннннннннннннн\n");
	}
}
