package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	/**
	 * Tests whether the getAmount method returns value in proper double form.
	 */
	@Test
	public void testGetAmount() {
		assertEquals(100, SEK100.getAmount(), 0.00001);
		assertEquals(10, EUR10.getAmount(), 0.00001);
	}

	/**
	 * Tests whether the getCurrency returns proper Currency object for corresponding Money object
	 */
	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
	}

	/**
	 * Tests if toString method returns properly formatted String value for Money object
	 */
	@Test
	public void testToString() {
		assertEquals("0.0 EUR", EUR0.toString());
		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("-100.0 SEK", SEKn100.toString());
	}

	/**
	 * Tests if global value is calculated correctly
	 */
	@Test
	public void testGlobalValue() {
		assertEquals(1500, EUR10.universalValue(), 0);
		assertEquals(3000, SEK200.universalValue(), 0);
	}

	/**
	 * Tests whether the value of two moneys is the same despite their currencies
	 */
	@Test
	public void testEqualsMoney() {
		assertTrue(SEK0.equals(EUR0));
		assertFalse(SEK200.equals(EUR20));
	}

	/**
	 * Tests if adding money with different currencies works
	 */
	@Test
	public void testAdd() {
		assertEquals(SEK100.getAmount(), SEK100.add(SEK0).getAmount());
		assertEquals(166.66, SEK100.add(EUR10).getAmount(), 0);
		assertNotEquals(EUR20.getAmount(), EUR10.add(EUR10).getAmount());
	}

	/**
	 * Tests if subtracting money with different currencies works
	 */
	@Test
	public void testSub() {
		assertEquals(SEK100.getAmount(), SEK100.sub(SEK0).getAmount());
		assertEquals(33.33, SEK100.sub(EUR10).getAmount(), 0.00001);
		assertNotEquals(EUR0.getAmount(), EUR10.sub(EUR10).getAmount());
	}

	/**
	 * Tests if value of Money is 0.0
	 */
	@Test
	public void testIsZero() {
		assertTrue("Should be zero", EUR0.isZero());
		assertFalse("Shouldn't be zero", EUR20.isZero());
		assertFalse("Shouldn't be zero", SEK100.isZero());
		assertTrue("Should be zero", SEK0.isZero());
	}

	/**
	 * Tests if negate method works properly
	 */
	@Test
	public void testNegate() {
		assertEquals(SEKn100.getAmount(), SEK100.negate().getAmount());
	}

	/**
	 * Tests whether the compareTo method works properly
	 */
	@Test
	public void testCompareTo() {
		assertEquals(0, SEK0.compareTo(EUR0));
		assertEquals(0, EUR20.compareTo(SEK200));
		assertEquals(0, EUR10.compareTo(SEK100));
		assertEquals(-1, EUR10.compareTo(SEK200));
	}
}
