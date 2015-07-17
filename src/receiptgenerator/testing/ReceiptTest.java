package receiptgenerator.testing;

import java.util.HashMap;
import static org.junit.Assert.*;
import org.junit.Test;
import receiptgenerator.business.Receipt;

/* 
 * If this was a real project, there would be a lot more tests here, testing for edge cases, errors etc.
 */
public class ReceiptTest {
	private static final double EPSILON = 1e-15;
	
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
		HashMap<String, Double> testPriceList = new HashMap<String, Double>();
		testPriceList.put("Orange", 0.4);
		testPriceList.put("Banana", 0.12);
		
		assertEquals(testReceipt.totalPriceForProduct("Orange", testPriceList), 2.0, EPSILON);
		assertEquals(testReceipt.totalPriceForProduct("Banana", testPriceList), 0.36, EPSILON);
	}
	
	@Test
	public void totalPriceTest1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		HashMap<String, Double> testPriceList = new HashMap<String, Double>();
		testPriceList.put("Orange", 0.4);
		testPriceList.put("Banana", 0.12);
		
		assertEquals(testReceipt.totalPrice(testPriceList), 2.36, EPSILON);
	}
}
