package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	/**
	 * Tests if returned name of currency is correct
	 */
	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}

	/**
	 * Tests if rate value is returned correctly
	 */
	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0);
		assertEquals(0.20, DKK.getRate(), 0);
		assertEquals(1.5, EUR.getRate(), 0);
	}

	/**
	 * Tests if setRate method works correctly
	 */
	@Test
	public void testSetRate() {
		SEK.setRate(0.5);
		assertEquals(0.5, SEK.getRate(), 0);
		assertNotEquals(0.15, SEK.getRate(), 0);
	}

	/**
	 * Tests if testGlobalValue works correctly
	 */
	@Test
	public void testGlobalValue() {
		assertEquals(9, DKK.universalValue(45), 0);
		assertEquals(150, EUR.universalValue(100), 0);
	}

	/**
	 * Tests if valueInThisCurrency works correctly
	 */
	@Test
	public void testValueInThisCurrency() {
		assertEquals(14, EUR.valueInThisCurrency(150, SEK), 0);
	}

}
