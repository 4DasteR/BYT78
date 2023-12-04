package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BankTest {
    Currency SEK, DKK;
    Bank SweBank, Nordea, DanskeBank;

    @Before
    public void setUp() throws Exception {
        DKK = new Currency("DKK", 0.20);
        SEK = new Currency("SEK", 0.15);
        SweBank = new Bank("SweBank", SEK);
        Nordea = new Bank("Nordea", SEK);
        DanskeBank = new Bank("DanskeBank", DKK);
        SweBank.openAccount("Ulrika");
        SweBank.openAccount("Bob");
        Nordea.openAccount("Bob");
        DanskeBank.openAccount("Gertrud");
    }

    /**
     * Tests whether getName method works correctly
     */
    @Test
    public void testGetName() {
        assertEquals("SweBank", SweBank.getName());
        assertEquals("Nordea", Nordea.getName());
        assertEquals("DanskeBank", DanskeBank.getName());
    }

    /**
     * Tests if getCurrency method works correctly
     */
    @Test
    public void testGetCurrency() {
        assertEquals(SEK, Nordea.getCurrency());
        assertEquals(SEK, SweBank.getCurrency());
        assertEquals(DKK, DanskeBank.getCurrency());
    }

    /**
     * Tests if openAccount method works correctly
     */
    @Test(expected = AccountExistsException.class)
    public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        /*
        This test failed originally. It is supposed to throw exception if account for given id already exists.
         */
        SweBank.openAccount("Kamil"); //should open new account
        SweBank.openAccount("Kamil"); //should throw an exception
    }

    /**
     * Tests if deposit method works correctly
     */
    @Test
    public void testDeposit() throws AccountDoesNotExistException {
        /*
        Test failed originally, due to NullPointerException with Account object reference
         */
        SweBank.deposit("Bob", new Money(100, SweBank.getCurrency()));
    }

    /**
     * Tests if withdraw method works correctly
     */
    @Test
    public void testWithdraw() throws AccountDoesNotExistException {
        /*
        Test failed originally, due to AccountDoesNotExistException
         */
        SweBank.withdraw("Bob", new Money(100, SweBank.getCurrency()));
    }

    /**
     * Tests if getBalance method works correctly
     */
    @Test
    public void testGetBalance() throws AccountDoesNotExistException {
        /*
        Test failed originally, due to AccountDoesNotExistException
         */
        DanskeBank.getBalance("Gertrud");
        SweBank.getBalance("Bob");
        SweBank.getBalance("Ulrika");
    }

    /**
     * Tests if transfer methods work correctly
     */
    @Test
    public void testTransfer() throws AccountDoesNotExistException {
        /*
        Test failed originally, due to AccountDoesNotExistException
         */
        SweBank.transfer("Bob", DanskeBank, "Gertrud", new Money(1000, SweBank.getCurrency()));
        SweBank.transfer("Bob", "Ulrika", new Money(1000, SweBank.getCurrency()));
    }

    /**
     * Tests if timedPayment methods work correctly
     */
    @Test
    public void testTimedPayment() throws AccountDoesNotExistException {
        /*
        Test failed originally, due to NullPointerException
         */
        SweBank.addTimedPayment("Bob", "SE", 50, 0, new Money(1000, SEK), SweBank, "Ulrika");
        SweBank.removeTimedPayment("Bob", "SE");
    }
}
