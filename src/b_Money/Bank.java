package b_Money;

import java.util.Hashtable;

public class Bank {
    private Hashtable<String, Account> accountlist = new Hashtable<String, Account>();
    private String name;
    private Currency currency;

    /**
     * New Bank
     *
     * @param name     Name of this bank
     * @param currency Base currency of this bank (If this is a Swedish bank, this might be a currency class representing SEK)
     */
    Bank(String name, Currency currency) {
        this.name = name;
        this.currency = currency;
    }

    /**
     * Get the name of this bank
     *
     * @return Name of this bank
     */
    public String getName() {
        return name;
    }

    /**
     * Get the Currency of this bank
     *
     * @return The Currency of this bank
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Open an account at this bank.
     *
     * @param accountid The ID of the account
     * @throws AccountExistsException If the account already exists
     */
    public void openAccount(String accountid) throws AccountExistsException {
		/*
		This method is bugged.
		It is supposed to throw an AccountExistsException, when given account already exists, but it doesn't happen.
		It is due to the fact that in else statement (so if Bank doesn't have an account with given id) instead of creating new account and adding it to list,
		get() method is executed, which should return Account object for given id (not even looking at the fact that method is of return type void)
		I found the bug using BankTest.testOpenAccount method, which is a test where I attempt to create open new account, and then attempt to do it once again.
		Test should have thrown AccountExistsException, but it didn't.
		 */
        if (accountlist.containsKey(accountid)) {
            throw new AccountExistsException();
        } else {
//            accountlist.get(accountid); The bug was here
            accountlist.put(accountid, new Account(accountid, currency));
        }
    }

    /**
     * Deposit money to an account
     *
     * @param accountid Account to deposit to
     * @param money     Money to deposit.
     * @throws AccountDoesNotExistException If the account does not exist
     */
    public void deposit(String accountid, Money money) throws AccountDoesNotExistException {
        /*
        This method is bugged.
        Bug noticed at first in BankTest.testDeposit method, which results in NullPointerException being thrown (ref. A).
        This indicated that account object reference is null, meaning no Account for such ID exists.
        Upon closer method inspection it seems the main culprit is the if statement.
        Its conditions are reversed, as in AccountDoesNotExistException is thrown if account exists (Account object is in accoutlist for given key).

        Among further testing of other methods and classes I have found this bugged method also hinders testing of all Account class methods,
        due to the fact that setup phase of AccountTest uses deposit method for one of its testing accounts (Alice).
         */

        // if (accountlist.containsKey(accountid)) The bug was here
        if (!accountlist.containsKey(accountid)) {
            throw new AccountDoesNotExistException();
        } else {
            Account account = accountlist.get(accountid);
            account.deposit(money); // <-- ref. A
        }
    }

    /**
     * Withdraw money from an account
     *
     * @param accountid Account to withdraw from
     * @param money     Money to withdraw
     * @throws AccountDoesNotExistException If the account does not exist
     */
    public void withdraw(String accountid, Money money) throws AccountDoesNotExistException {
        /*
        This method is bugged.
        Bug noticed at first in BankTest.testWithdraw method, which results in AccountDoesNotExistException being thrown.
        While the exception behaviour is correct, the test setUp uses openAccount method, which as stated previously is bugged.
        Because of that the test, which should have passed for account 'Bob' didn't pass because neither his, nor any other account was created in BankTest.setUp
        Upon manual inspection while writing this comment I noticed in else statement part that withdraw (this method) calls deposit instead of withdraw method from Account class
         */

        if (!accountlist.containsKey(accountid)) {
            throw new AccountDoesNotExistException();
        } else {
            Account account = accountlist.get(accountid);
//            account.deposit(money); The bug was here
            account.withdraw(money);
        }
    }

    /**
     * Get the balance of an account
     *
     * @param accountid Account to get balance from
     * @return Balance of the account
     * @throws AccountDoesNotExistException If the account does not exist
     */
    public Double getBalance(String accountid) throws AccountDoesNotExistException {
        /*
        Bug occurred while using this method.
        Bug noticed at first in BankTest.testGetBalance method, which results in AccountDoesNotExistException being thrown.
        Like with previous method AccountDoesNotExistException is thrown correctly and the issue again stems from problems with bugged openAccount method,
        which as observed in BankTest.setUp and BankTest.testOpenAccount account object isn't really created.
         */
        //After fixing openAccount method it works without any issue.
        if (!accountlist.containsKey(accountid)) {
            throw new AccountDoesNotExistException();
        } else {
            return accountlist.get(accountid).getBalance().getAmount();
        }
    }

    /**
     * Transfer money between two accounts
     *
     * @param fromaccount Id of account to deduct from in this Bank
     * @param tobank      Bank where receiving account resides
     * @param toaccount   Id of receiving account
     * @param amount      Amount of Money to transfer
     * @throws AccountDoesNotExistException If one of the accounts do not exist
     */
    public void transfer(String fromaccount, Bank tobank, String toaccount, Money amount) throws AccountDoesNotExistException {
        /*
        Bug occurred while using this method.
        Bug noticed at first in BankTest.testTransfer method, which results in AccountDoesNotExistException being thrown.
        Like with previous method AccountDoesNotExistException is thrown correctly and the issue again stems from problems with bugged openAccount method,
        which as observed in BankTest.setUp and BankTest.testOpenAccount account object isn't really created.
         */
        //After fixing openAccount method it works without any issue.
        if (!accountlist.containsKey(fromaccount) || !tobank.accountlist.containsKey(toaccount)) {
            throw new AccountDoesNotExistException();
        } else {
            accountlist.get(fromaccount).withdraw(amount);
            tobank.accountlist.get(toaccount).deposit(amount);
        }
    }

    /**
     * Transfer money between two accounts on the same bank
     *
     * @param fromaccount Id of account to deduct from
     * @param toaccount   Id of receiving account
     * @param amount      Amount of Money to transfer
     * @throws AccountDoesNotExistException If one of the accounts do not exist
     */
    public void transfer(String fromaccount, String toaccount, Money amount) throws AccountDoesNotExistException {
        /*
        This method is bugged.
        Since this method in an overloaded version of above one it shares same issue, especially since it calls its other version with fixed tobank parameter.
        However, it has an issue of its own. Parameter toaccount isn't used (in call to other version of transfer method fromaccount is supplied instead of toaccount),
        which will result in one account essentially transferring its own money to itself, meaning ultimately no changes would occur.
         */
//        transfer(fromaccount, this, fromaccount, amount); The bug was here
        transfer(fromaccount, this, toaccount, amount);
    }

    /**
     * Add a timed payment
     *
     * @param accountid Id of account to deduct from in this Bank
     * @param payid     Id of timed payment
     * @param interval  Number of ticks between payments
     * @param next      Number of ticks till first payment
     * @param amount    Amount of Money to transfer each payment
     * @param tobank    Bank where receiving account resides
     * @param toaccount Id of receiving account
     */
    public void addTimedPayment(String accountid, String payid, Integer interval, Integer next, Money amount, Bank tobank, String toaccount) {
        /*
        Bug occurred while using this method.
        Bug noticed in BankTest.testTimedPayment method, which results in NullPointerException being thrown.
        This indicated that account object reference is null, meaning no Account for such ID exists.
        No account operations can be performed on null, so TimedPayment is never added.
         */
        //After fixing openAccount method it works without any issue.
        Account account = accountlist.get(accountid);
        account.addTimedPayment(payid, interval, next, amount, tobank, toaccount);
    }

    /**
     * Remove a timed payment
     *
     * @param accountid Id of account to remove timed payment from
     * @param id        Id of timed payment
     */
    public void removeTimedPayment(String accountid, String id) {
        /*
        Bug occurred while using this method.
        Bug noticed in BankTest.testTimedPayment method, which results in NullPointerException being thrown.
        This indicated that account object reference is null, meaning no Account for such ID exists.
         */
        //After fixing openAccount method it works without any issue.
        Account account = accountlist.get(accountid);
        account.removeTimedPayment(id);
    }

    /**
     * A time unit passes in the system
     */
    public void tick() throws AccountDoesNotExistException {
        for (Account account : accountlist.values()) {
            account.tick();
        }
    }
}
