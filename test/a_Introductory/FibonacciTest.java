package a_Introductory;

import static org.junit.Assert.*;

import org.junit.Test;

import a_Introductory.Fibonacci;

public class FibonacciTest {

	@Test
	public void testFib() {
		/*
		Originally test for fib(2) returned 2, but expected 1.
		After removing '+ 1' from return statement in Fibonacci.fib method all test passed.
		Before removal all tests for fib(n) with n >= 2 would fail due to added 1 to proper result.
		 */
		Fibonacci tester = new Fibonacci();
		assertEquals("0", 0, tester.fib(0));
		assertEquals("1", 1, tester.fib(1));
		assertEquals("2", 1, tester.fib(2)); //failed
		assertEquals("3", 2, tester.fib(3));
		assertEquals("4", 3, tester.fib(4));
		assertEquals("5", 5, tester.fib(5));
		assertEquals("6", 8, tester.fib(6));
		assertEquals("7", 13, tester.fib(7));
	}

}
