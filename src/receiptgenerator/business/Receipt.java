package receiptgenerator.business;

import java.util.LinkedHashMap;

public class Receipt extends LinkedHashMap<String, Integer> {
	private static final long serialVersionUID = -1971454617533432427L;
	private Receipt freeFruitVeggie = null;

	public Receipt() {
	}

	public Receipt(String tillOutput) {
		parseTillOutput(tillOutput);
	}

	public Receipt getFreeFruitVeggie() {
		return freeFruitVeggie;
	}

	/* 
	 * Override of LinkedHashMap.put that sums values of identical keys 
	 * and removes keys of value <= 0.
	 */
	public Integer put(String itemName, Integer itemQuantity) {
		Integer returnValue;
		if (containsKey(itemName)) {
			returnValue = super.put(itemName, get(itemName) + itemQuantity);
		} else {
			returnValue = super.put(itemName, itemQuantity);
		}
		if (get(itemName) <= 0) {
			returnValue = remove(itemName);
		}
		return returnValue;
	}

	/**
	 * Turns the till output String into a LinkedHashMap of items and their amounts.
	 * @param tillOutput A string formatted as a series of "String|int\n"
	 */
	public void parseTillOutput(String tillOutput) {
		String lineDelimiter = "\n|\\\\n";
		String sectionDelimiter = "\\|";

		clear();
		if(tillOutput != null && tillOutput.length() > 0) {
			try {
				for (String tillOutputLine : tillOutput.split(lineDelimiter)) {		//For each item
					String itemName = tillOutputLine.split(sectionDelimiter)[0];
					String itemQuantityString = tillOutputLine.split(sectionDelimiter)[1];
					int itemQuantity = Integer.parseInt(itemQuantityString);
					put(itemName, itemQuantity);
				}
			} catch (Exception ex) {
				System.err.println("Error: " + tillOutput + " does not use the till output format.");
			}
		}
	}

	/**
	 * @param itemName The name of an item.
	 * @param itemDatabase An item database from which to get extra information.
	 * @return The total cost to pay for all purchased items of that name.
	 */
	public double totalCostForItem(String itemName, ItemDatabase itemDatabase) {
		double total = 0.0;

		if (!itemDatabase.containsKey(itemName)) {
			System.err.println("Error: No " + itemName + " in the item database.");
		} else if (containsKey(itemName)){
			total = get(itemName) * itemDatabase.get(itemName).getCost();
		}

		return total;
	}

	/**
	 * @param itemName The name of an item.
	 * @param itemDatabase An item database from which to get extra information.
	 * @return How much (if anything) should be deduced from the total cost because
	 * of a 2-for-1 discount.
	 */
	public double discount2For1(String itemName, ItemDatabase itemDatabase) {
		double discount = 0.0;

		if(!itemDatabase.containsKey(itemName)) {
			System.err.println("Error: No " + itemName + " in the item database.");
		} else if(containsKey(itemName) && itemDatabase.get(itemName).is2for1()) {
			discount = get(itemName) / 2 * itemDatabase.get(itemName).getCost();
		}

		return discount;
	}

	/**
	 * @param itemDatabase An item database from which to get extra information.
	 * @return The name of the cheapest fruit or vegetable on this receipt, or an
	 * empty string if there is none.
	 */
	public String findCheapestFruitVeggie(ItemDatabase itemDatabase) {
		String cheapestFruitVeggie = "";
		double cheapestCost = 10e307;

		for (String itemName : keySet()) {
			if(!itemDatabase.containsKey(itemName)) {
				System.err.println("Error: No " + itemName + " in the item database.");
			} else if (itemDatabase.get(itemName).isFruitOrVegetable()
					&& itemDatabase.get(itemName).getCost() < cheapestCost) {
				cheapestFruitVeggie = itemName;
				cheapestCost = itemDatabase.get(itemName).getCost();
			}
		}

		return cheapestFruitVeggie;
	}

	/**
	 * Combines two receipts together.
	 * @param receipt A receipt to add to this one.
	 */
	public void addReceipt(Receipt receipt) {
		if (receipt != null) {
			for (String itemName : receipt.keySet()) {
				put(itemName, receipt.get(itemName));
			}
		}
	}

	/**
	 * Finds out how many and which (if any) fruits or vegetables should be 
	 * free because of the 3-for-2 discount, removes them from this receipt,
	 * and adds them to the freeFruitVeggie receipt attribute.
	 * @param itemDatabase An item database from which to get extra information.
	 */
	public void separateFreeFruitVeggie(ItemDatabase itemDatabase) {
		freeFruitVeggie = new Receipt();

		int nbFreeFruitVeggie = 0;
		for (String itemName : keySet()) {
			if(!itemDatabase.containsKey(itemName)) {
				System.err.println("Error: No " + itemName + " in the item database.");
			} else if (itemDatabase.get(itemName).isFruitOrVegetable()) {
				nbFreeFruitVeggie += get(itemName);
			}
		}
		nbFreeFruitVeggie /= 3;

		while (nbFreeFruitVeggie > 0) {
			String cheapestFruitVeggie = findCheapestFruitVeggie(itemDatabase);
			put(cheapestFruitVeggie, -1);
			freeFruitVeggie.put(cheapestFruitVeggie, 1);
			--nbFreeFruitVeggie;
		}
	}

	/**
	 * @param itemDatabase An item database from which to get extra information.
	 * @return The total cost of this receipt when not taking discounts into account.
	 */
	public double totalCostWithoutDiscounts(ItemDatabase itemDatabase) {
		double total = 0.0;

		for (String itemName : keySet()) {
			total += totalCostForItem(itemName, itemDatabase);
		}

		return total;
	}

	/**
	 * @param itemDatabase An item database from which to get extra information.
	 * @return The total cost of this receipt, after discounts.
	 */
	public double totalCost(ItemDatabase itemDatabase) {
		addReceipt(freeFruitVeggie);
		double total = totalCostWithoutDiscounts(itemDatabase);	//Initial cost

		for (String itemName : keySet()) {						//2-for-1 discount
			total -= discount2For1(itemName, itemDatabase);
		}

		separateFreeFruitVeggie(itemDatabase);					//3-for-2 discount
		total -= freeFruitVeggie.totalCostWithoutDiscounts(itemDatabase);

		return total;
	}
}
