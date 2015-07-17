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
	public static final ProductDatabase productDatabase;
	static {
		productDatabase = new ProductDatabase();
		productDatabase.put(new Product("Orange", 0.4, false, true));
		productDatabase.put(new Product("Banana", 0.12, false, true));
		productDatabase.put(new Product("Tomato", 0.2, false, true));
		productDatabase.put(new Product("Box of cereal", 1.8, false, false));
		productDatabase.put(new Product("Loaf of bread", 0.8, false, false));
		productDatabase.put(new Product("Frozen pizza", 2.5, true, false));
	}
	
	@Test
	public void parseTillOutputTest1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.getPurchasedProducts().size(), 2);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Orange"));
		assertEquals((int) testReceipt.getPurchasedProducts().get("Orange"), 5);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Banana"));
		assertEquals((int) testReceipt.getPurchasedProducts().get("Banana"), 3);
	}

	@Test
	public void parseTillOutputTest2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.getPurchasedProducts().size(), 4);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Orange"));
		assertEquals((int) testReceipt.getPurchasedProducts().get("Orange"), 1);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Tomato"));
		assertEquals((int) testReceipt.getPurchasedProducts().get("Tomato"), 3);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Frozen pizza"));
		assertEquals((int) testReceipt.getPurchasedProducts().get("Frozen pizza"), 3);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Box of cereal"));
		assertEquals((int) testReceipt.getPurchasedProducts().get("Box of cereal"), 2);
	}

	@Test
	public void parseTillOutputTest3() {
		String testTillOutput = "Loaf of bread|1\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.getPurchasedProducts().size(), 1);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Loaf of bread"));
		assertEquals((int) testReceipt.getPurchasedProducts().get("Loaf of bread"), 1);
	}
	
	@Test
	public void totalPriceForProductTest1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.totalPriceForProduct("Orange", productDatabase), 2.0, EPSILON);
		assertEquals(testReceipt.totalPriceForProduct("Banana", productDatabase), 0.36, EPSILON);
	}
	
	@Test
	public void totalPriceForProductTest2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.totalPriceForProduct("Orange", productDatabase), 0.4, EPSILON);
		assertEquals(testReceipt.totalPriceForProduct("Tomato", productDatabase), 0.6, EPSILON);
		assertEquals(testReceipt.totalPriceForProduct("Box of cereal", productDatabase),3.6, EPSILON);
		assertEquals(testReceipt.totalPriceForProduct("Frozen pizza", productDatabase), 7.5, EPSILON);
	}
	
	@Test
	public void totalPriceTest1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.totalPrice(productDatabase), 2.36, EPSILON);
	}
	
	@Test
	public void totalPriceTest2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.totalPrice(productDatabase), 12.1, EPSILON);
	}

}
