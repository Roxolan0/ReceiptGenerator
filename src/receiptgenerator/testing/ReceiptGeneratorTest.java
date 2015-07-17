package receiptgenerator.testing;

import java.util.HashMap;
import static org.junit.Assert.*;
import org.junit.Test;
import receiptgenerator.business.ReceiptGenerator;

/* 
 * If this was a real project, there would be a lot more tests here, testing for edge cases, errors etc.
 */
public class ReceiptGeneratorTest {
	
	@Test
	public void parseTillOutputTest1() {
		String testTillOutput = "Orange|5\nBanana|3\n";
		HashMap<String, Integer> parsedOutput = ReceiptGenerator.parseTillOutput(testTillOutput);
		assertEquals(parsedOutput.size(), 2);
		assertTrue(parsedOutput.containsKey("Orange"));
		assertSame(parsedOutput.get("Orange"), 5);
		assertTrue(parsedOutput.containsKey("Banana"));
		assertSame(parsedOutput.get("Banana"), 3);
	}

	@Test
	public void parseTillOutputTest2() {
		String testTillOutput = "Orange|1\nTomato|3\nFrozen pizza|3\nBox of cereal|2\n";
		HashMap<String, Integer> parsedOutput = ReceiptGenerator.parseTillOutput(testTillOutput);
		assertEquals(parsedOutput.size(), 4);
		assertTrue(parsedOutput.containsKey("Orange"));
		assertSame(parsedOutput.get("Orange"), 1);
		assertTrue(parsedOutput.containsKey("Tomato"));
		assertSame(parsedOutput.get("Tomato"), 3);
		assertTrue(parsedOutput.containsKey("Frozen pizza"));
		assertSame(parsedOutput.get("Frozen pizza"), 3);
		assertTrue(parsedOutput.containsKey("Box of cereal"));
		assertSame(parsedOutput.get("Box of cereal"), 2);
	}

	@Test
	public void parseTillOutputTest3() {
		String testTillOutput = "Loaf of bread|1\n";
		HashMap<String, Integer> parsedOutput = ReceiptGenerator.parseTillOutput(testTillOutput);
		assertEquals(parsedOutput.size(), 1);
		assertTrue(parsedOutput.containsKey("Loaf of bread"));
		assertSame(parsedOutput.get("Loaf of bread"), 1);
	}
}
