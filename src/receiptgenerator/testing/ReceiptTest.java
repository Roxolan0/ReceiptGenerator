package receiptgenerator.testing;

import java.util.HashMap;
import static org.junit.Assert.*;
import org.junit.Test;
import receiptgenerator.business.Receipt;

/* 
 * If this was a real project, there would be a lot more tests here, testing for edge cases, errors etc.
 */
public class ReceiptTest {
	
	@Test
	public void parseTillOutputTest1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.getPurchasedProducts().size(), 2);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Orange"));
		assertSame(testReceipt.getPurchasedProducts().get("Orange"), 5);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Banana"));
		assertSame(testReceipt.getPurchasedProducts().get("Banana"), 3);
	}

	@Test
	public void parseTillOutputTest2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.getPurchasedProducts().size(), 4);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Orange"));
		assertSame(testReceipt.getPurchasedProducts().get("Orange"), 1);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Tomato"));
		assertSame(testReceipt.getPurchasedProducts().get("Tomato"), 3);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Frozen pizza"));
		assertSame(testReceipt.getPurchasedProducts().get("Frozen pizza"), 3);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Box of cereal"));
		assertSame(testReceipt.getPurchasedProducts().get("Box of cereal"), 2);
	}

	@Test
	public void parseTillOutputTest3() {
		String testTillOutput = "Loaf of bread|1\n";
		Receipt testReceipt = new Receipt();
		testReceipt.parseTillOutput(testTillOutput);
		
		assertEquals(testReceipt.getPurchasedProducts().size(), 1);
		assertTrue(testReceipt.getPurchasedProducts().containsKey("Loaf of bread"));
		assertSame(testReceipt.getPurchasedProducts().get("Loaf of bread"), 1);
	}
}
