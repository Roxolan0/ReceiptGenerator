package receiptgenerator.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import receiptgenerator.business.Item;
import receiptgenerator.business.ItemDatabase;
import receiptgenerator.business.Receipt;

/* 
 * If this was a real project, there would be a lot more tests here, testing for edge cases, errors etc.
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
		itemDatabaseTask1
				.put(new Item("Box of cereal", 1.8, false, false));
		itemDatabaseTask1
				.put(new Item("Loaf of bread", 0.8, false, false));
		itemDatabaseTask1
				.put(new Item("Frozen pizza", 2.5, false, false));

		itemDatabaseTask2 = new ItemDatabase();
		itemDatabaseTask2.put(new Item("Orange", 0.4, false, false));
		itemDatabaseTask2.put(new Item("Banana", 0.12, false, false));
		itemDatabaseTask2.put(new Item("Tomato", 0.2, false, false));
		itemDatabaseTask2
				.put(new Item("Box of cereal", 1.8, false, false));
		itemDatabaseTask2
				.put(new Item("Loaf of bread", 0.8, false, false));
		itemDatabaseTask2.put(new Item("Frozen pizza", 2.5, true, false));

		itemDatabaseTask3 = new ItemDatabase();
		itemDatabaseTask3.put(new Item("Orange", 0.4, false, true));
		itemDatabaseTask3.put(new Item("Banana", 0.12, false, true));
		itemDatabaseTask3.put(new Item("Tomato", 0.2, false, true));
		itemDatabaseTask3
				.put(new Item("Box of cereal", 1.8, false, false));
		itemDatabaseTask3
				.put(new Item("Loaf of bread", 0.8, false, false));
		itemDatabaseTask3.put(new Item("Frozen pizza", 2.5, true, false));
	}

	/*
	 * Task 1 tests
	 */
	@Test
	public void parseTillOutputTask1Test1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);

		assertEquals(testReceipt.size(), 2);
		assertTrue(testReceipt.containsKey("Orange"));
		assertEquals((int) testReceipt.get("Orange"), 5);
		assertTrue(testReceipt.containsKey("Banana"));
		assertEquals((int) testReceipt.get("Banana"), 3);
	}

	@Test
	public void parseTillOutputTask1Test2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);

		assertEquals(testReceipt.size(), 4);
		assertTrue(testReceipt.containsKey("Orange"));
		assertEquals((int) testReceipt.get("Orange"), 1);
		assertTrue(testReceipt.containsKey("Tomato"));
		assertEquals((int) testReceipt.get("Tomato"), 3);
		assertTrue(testReceipt.containsKey("Frozen pizza"));
		assertEquals((int) testReceipt.get("Frozen pizza"), 3);
		assertTrue(testReceipt.containsKey("Box of cereal"));
		assertEquals((int) testReceipt.get("Box of cereal"), 2);
	}

	@Test
	public void parseTillOutputTask1Test3() {
		String testTillOutput = "Loaf of bread|1\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);

		assertEquals(testReceipt.size(), 1);
		assertTrue(testReceipt.containsKey("Loaf of bread"));
		assertEquals((int) testReceipt.get("Loaf of bread"), 1);
	}

	@Test
	public void totalCostForItemTask1Test1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);

		assertEquals(testReceipt.totalCostForItem("Orange",
				itemDatabaseTask1), 2.0, EPSILON);
		assertEquals(testReceipt.totalCostForItem("Banana",
				itemDatabaseTask1), 0.36, EPSILON);
	}

	@Test
	public void totalCostForItemTask1Test2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);

		assertEquals(testReceipt.totalCostForItem("Orange",
				itemDatabaseTask1), 0.4, EPSILON);
		assertEquals(testReceipt.totalCostForItem("Tomato",
				itemDatabaseTask1), 0.6, EPSILON);
		assertEquals(testReceipt.totalCostForItem("Box of cereal",
				itemDatabaseTask1), 3.6, EPSILON);
		assertEquals(testReceipt.totalCostForItem("Frozen pizza",
				itemDatabaseTask1), 7.5, EPSILON);
	}

	@Test
	public void totalCostTask1Test1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);

		assertEquals(testReceipt.totalCost(itemDatabaseTask1), 2.36,
				EPSILON);
	}

	@Test
	public void totalCostTask1Test2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);

		assertEquals(testReceipt.totalCost(itemDatabaseTask1), 12.1,
				EPSILON);
	}

	/*
	 * Task 2 tests
	 */
	@Test
	public void totalCostTask2Test1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);

		assertEquals(testReceipt.totalCost(itemDatabaseTask2), 2.36,
				EPSILON);
	}

	@Test
	public void totalCostTask2Test2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);

		assertEquals(testReceipt.totalCost(itemDatabaseTask2), 9.6, EPSILON);
	}
}
