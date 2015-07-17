package receiptgenerator.business;

import java.util.HashMap;

public class ItemDatabase extends HashMap<String, Item> {
	private static final long serialVersionUID = -3035724235788924740L;

	/**
	 * A shorter version of HashMap.put, since the key will always be the name of the Item.
	 * @param item The item to add.
	 */
	public void put(Item item) {
		put(item.getName(), item);
	}
}
