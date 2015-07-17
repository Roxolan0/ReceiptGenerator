package receiptgenerator.testing;

import java.util.HashMap;

import static org.junit.Assert.*;

import org.junit.Test;

import receiptgenerator.business.Product;
import receiptgenerator.business.ProductDatabase;
import receiptgenerator.business.Receipt;

/* 
 * If this was a real project, there would be a lot more tests here, testing for edge cases, errors etc.
 */
public class ReceiptTest {
	private static final double EPSILON = 1e-15;
	public static final ProductDatabase productDatabaseTask1;
	public static final ProductDatabase productDatabaseTask2;
	public static final ProductDatabase productDatabaseTask3;
	static {
		productDatabaseTask1 = new ProductDatabase();
		productDatabaseTask1.put(new Product("Orange", 0.4, false, false));
		productDatabaseTask1.put(new Product("Banana", 0.12, false, false));
		productDatabaseTask1.put(new Product("Tomato", 0.2, false, false));
		productDatabaseTask1.put(new Product("Box of cereal", 1.8, false, false));
		productDatabaseTask1.put(new Product("Loaf of bread", 0.8, false, false));
		productDatabaseTask1.put(new Product("Frozen pizza", 2.5, false, false));
		
		productDatabaseTask2 = new ProductDatabase();
		productDatabaseTask2.put(new Product("Orange", 0.4, false, false));
		productDatabaseTask2.put(new Product("Banana", 0.12, false, false));
		productDatabaseTask2.put(new Product("Tomato", 0.2, false, false));
		productDatabaseTask2.put(new Product("Box of cereal", 1.8, false, false));
		productDatabaseTask2.put(new Product("Loaf of bread", 0.8, false, false));
		productDatabaseTask2.put(new Product("Frozen pizza", 2.5, true, false));
		
		productDatabaseTask3 = new ProductDatabase();
		productDatabaseTask3.put(new Product("Orange", 0.4, false, true));
		productDatabaseTask3.put(new Product("Banana", 0.12, false, true));
		productDatabaseTask3.put(new Product("Tomato", 0.2, false, true));
		productDatabaseTask3.put(new Product("Box of cereal", 1.8, false, false));
		productDatabaseTask3.put(new Product("Loaf of bread", 0.8, false, false));
		productDatabaseTask3.put(new Product("Frozen pizza", 2.5, true, false));
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
	public void totalPriceForProductTask1Test1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.totalPriceForProduct("Orange", productDatabaseTask1), 2.0, EPSILON);
		assertEquals(testReceipt.totalPriceForProduct("Banana", productDatabaseTask1), 0.36, EPSILON);
	}
	
	@Test
	public void totalPriceForProductTask1Test2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.totalPriceForProduct("Orange", productDatabaseTask1), 0.4, EPSILON);
		assertEquals(testReceipt.totalPriceForProduct("Tomato", productDatabaseTask1), 0.6, EPSILON);
		assertEquals(testReceipt.totalPriceForProduct("Box of cereal", productDatabaseTask1),3.6, EPSILON);
		assertEquals(testReceipt.totalPriceForProduct("Frozen pizza", productDatabaseTask1), 7.5, EPSILON);
	}
	
	@Test
	public void totalPriceTask1Test1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.totalPrice(productDatabaseTask1), 2.36, EPSILON);
	}
	
	@Test
	public void totalPriceTask1Test2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.totalPrice(productDatabaseTask1), 12.1, EPSILON);
	}
	
	/*
	 * Task 2 tests
	 */
	@Test
	public void totalPriceTask2Test1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.totalPrice(productDatabaseTask2), 2.36, EPSILON);
	}
	
	@Test
	public void totalPriceTask2Test2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.totalPrice(productDatabaseTask2), 9.6, EPSILON);
	}
}
