package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	/**
	 * Tests both addTimedPayment and removeTimedPayment methods
	 */
	@Test
	public void testAddRemoveTimedPayment() {
		/*
		 Test originally failed at setUp(), due to NullPointerException from Bank.deposit method
		 */
		testAccount.addTimedPayment("TST1", 20, 5, new Money(50000, SEK), SweBank, "Alice");
		testAccount.removeTimedPayment("TST1");
	}

	/**
	 * Tests if timedPaymentExists method works correctly
	 */
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		/*
		 Test originally failed at setUp(), due to NullPointerException from Bank.deposit method
		 */
		assertFalse(testAccount.timedPaymentExists("TST1"));
		testAccount.addTimedPayment("TST1", 20, 100, new Money(50000, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("TST1"));
	}

	/**
	 * Tests both deposit and withdraw methods
	 */
	@Test
	public void testAddWithdraw() {
		/*
		 Test originally failed at setUp(), due to NullPointerException from Bank.deposit method
		 */
		testAccount.deposit(new Money(45000, SEK));
		testAccount.withdraw(new Money(10000, SEK));
	}

	/**
	 * Tests if getBalance method works correctly
	 */
	@Test
	public void testGetBalance() {
		/*
		 Test originally failed at setUp(), due to NullPointerException from Bank.deposit method
		 */
		assertEquals(new Money(66666666, SEK).getAmount(), testAccount.getBalance().getAmount(), 0);
	}
}
