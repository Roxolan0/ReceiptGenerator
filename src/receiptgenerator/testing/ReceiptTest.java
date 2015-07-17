package receiptgenerator.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import receiptgenerator.business.Item;
import receiptgenerator.business.ItemDatabase;
import receiptgenerator.business.Receipt;

/* 
 * If this was a real project, there could be more tests here, testing for edge cases, error handling etc.
 */
public class ReceiptTest {
	private static final double EPSILON = 1e-15;
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

	/*
	 * Task 1 tests
	 */
	@Test
	public void parseTillOutput_Task1_Test1() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt receipt = new Receipt();
		receipt.parseTillOutput(testTillOutput);

		assertEquals(receipt.size(), 4);
		assertTrue(receipt.containsKey("Orange"));
		assertEquals((int) receipt.get("Orange"), 1);
		assertTrue(receipt.containsKey("Tomato"));
		assertEquals((int) receipt.get("Tomato"), 3);
		assertTrue(receipt.containsKey("Frozen pizza"));
		assertEquals((int) receipt.get("Frozen pizza"), 3);
		assertTrue(receipt.containsKey("Box of cereal"));
		assertEquals((int) receipt.get("Box of cereal"), 2);
		assertFalse(receipt.containsKey("Banana"));
	}

	@Test
	public void parseTillOutput_Task1_Test2() {
		Receipt receipt = new Receipt("Loaf of bread|1\n");

		assertEquals(receipt.size(), 1);
		assertTrue(receipt.containsKey("Loaf of bread"));
		assertEquals((int) receipt.get("Loaf of bread"), 1);
		assertFalse(receipt.containsKey("Banana"));
	}

	@Test
	public void parseTillOutput_Task1_Test3() {
		Receipt receipt = new Receipt("");

		assertEquals(receipt.size(), 0);
		assertFalse(receipt.containsKey("Banana"));
	}
	
	@Test
	public void put_Task1_Test1() {
		Receipt receipt = new Receipt();
		receipt.put("Banana", 8);
		receipt.put("Tomato", 0);
		receipt.put("Orange", -2);
		receipt.put("Banana", -3);
		receipt.put("Orange", 1);
		
		assertEquals(receipt.size(), 2);
		assertTrue(receipt.containsKey("Banana"));
		assertEquals((int) receipt.get("Banana"), 5);
		assertTrue(receipt.containsKey("Orange"));
		assertEquals((int) receipt.get("Orange"), 1);
		assertFalse(receipt.containsKey("Tomato"));
	}

	@Test
	public void totalCostForItem_Task1_Test1() {
		Receipt receipt = new Receipt("Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n");

		assertEquals(receipt.totalCostForItem("Orange", itemDatabaseTask1), 0.4, EPSILON);
		assertEquals(receipt.totalCostForItem("Tomato", itemDatabaseTask1), 0.6, EPSILON);
		assertEquals(receipt.totalCostForItem("Box of cereal", itemDatabaseTask1), 3.6, EPSILON);
		assertEquals(receipt.totalCostForItem("Frozen pizza", itemDatabaseTask1), 7.5, EPSILON);
		assertEquals(receipt.totalCostForItem("Banana", itemDatabaseTask1), 0.0, EPSILON);
	}

	@Test
	public void totalCost_Task1_Test1() {
		Receipt receipt = new Receipt("Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n");

		assertEquals(receipt.totalCost(itemDatabaseTask1), 12.1, EPSILON);
	}

	/*
	 * Task 2 tests
	 */
	@Test
	public void discout2For1Item_Task2_Test1() {
		Receipt receipt = new Receipt("Orange|7\nFrozen pizza|7\n");
		
		assertEquals(receipt.discount2For1("Frozen pizza", itemDatabaseTask2), 7.5, EPSILON);
		assertEquals(receipt.discount2For1("Orange", itemDatabaseTask2), 0.0, EPSILON);
	}
	
	@Test
	public void discout2For1Item_Task2_Test2() {
		Receipt receipt = new Receipt("Orange|7\nFrozen pizza|1\n");
		
		assertEquals(receipt.discount2For1("Frozen pizza", itemDatabaseTask2), 0.0, EPSILON);
	}
	
	@Test
	public void totalCost_Task2_Test1() {
		Receipt receipt = new Receipt("Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n");

		assertEquals(receipt.totalCost(itemDatabaseTask2), 9.6, EPSILON);
	}

	@Test
	public void totalCostWithoutDiscounts_Task2_Test1() {
		Receipt receipt = new Receipt("Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n");

		assertEquals(receipt.totalCostWithoutDiscounts(itemDatabaseTask2), 12.1, EPSILON);
	}


	/*
	 * Task 3 tests
	 */
	
	@Test
	public void separateFreeFruitVeggie_Task3_Test1() {
		Receipt receipt = new Receipt("Frozen pizza|3\nBanana|3\nBox of cereal|2\n");
		receipt.separateFreeFruitVeggie(itemDatabaseTask3);
		
		assertTrue(receipt.getFreeFruitVeggie() != null);
		assertEquals(receipt.getFreeFruitVeggie().size(), 1);
		assertTrue(receipt.getFreeFruitVeggie().containsKey("Banana"));
		assertEquals((int) receipt.getFreeFruitVeggie().get("Banana"), 1);
	}
	
	@Test
	public void separateFreeFruitVeggie_Task3_Test2() {
		Receipt receipt = new Receipt("Frozen pizza|3\nBanana|2\nBox of cereal|2\n");
		receipt.separateFreeFruitVeggie(itemDatabaseTask3);
		
		assertTrue(receipt.getFreeFruitVeggie() != null);
		assertEquals(receipt.getFreeFruitVeggie().size(), 0);
	}

	@Test
	public void separateFreeFruitVeggie_Task3_Test3() {
		Receipt receipt = new Receipt("Tomato|2\nBox of cereal|3\nBanana|3\nOrange|17\n");
		receipt.separateFreeFruitVeggie(itemDatabaseTask3);
		
		assertEquals(receipt.getFreeFruitVeggie().size(), 3);
		assertTrue(receipt.getFreeFruitVeggie().containsKey("Tomato"));
		assertEquals((int) receipt.getFreeFruitVeggie().get("Tomato"), 2);
		assertTrue(receipt.getFreeFruitVeggie().containsKey("Banana"));
		assertEquals((int) receipt.getFreeFruitVeggie().get("Banana"), 3);
		assertTrue(receipt.getFreeFruitVeggie().containsKey("Orange"));
		assertEquals((int) receipt.getFreeFruitVeggie().get("Orange"), 2);
		assertFalse(receipt.getFreeFruitVeggie().containsKey("Box of cereal"));
	}
	
	@Test
	public void findCheapestFruitVeggie_Task3_Test1() {
		Receipt receipt = new Receipt("Tomato|2\nBox of cereal|3\nBanana|3\nOrange|17\n");
		
		assertEquals(receipt.findCheapestFruitVeggie(itemDatabaseTask3), "Banana");
	}
	
	@Test
	public void totalCost_Task3_Test1() {
		Receipt receipt = new Receipt("Banana|2\nTomato|3\nBox of cereal|2\nOrange|6\n");
		
		assertEquals(receipt.totalCost(itemDatabaseTask3), 6.4, EPSILON);
	}

	@Test
	public void totalCostWithoutDiscounts_Task3_Test1() {
		Receipt receipt = new Receipt("Banana|2\nTomato|3\nBox of cereal|2\nOrange|6\n");
		
		assertEquals(receipt.totalCostWithoutDiscounts(itemDatabaseTask3), 6.84, EPSILON);		
	}
}
