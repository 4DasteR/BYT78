package a_Introductory;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {
	Point p1, p2, p3;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Point(7, 9);
		p2 = new Point(-3, -30);
		p3 = new Point(-10, 3);
	}

	@Test
	public void testAdd() {
		/*
		 Added Test annotation to make it a test
		 Added delta, otherwise methods were incorrect
		 Last test was fixed by changing from res2.x to res2.y
		*/
		Point res1 = p1.add(p2);
		Point res2 = p1.add(p3);
		
		assertEquals(4, res1.x, 0);
		assertEquals(-21, res1.y, 0);
		assertEquals(-3, res2.x, 0);
		assertEquals(12, res2.y, 0); //failed
	}

	@Test
	public void testSub() {
		/*
		 Added Test annotation to make it a test
		 Added delta, otherwise methods were incorrect
		 Last test had res2.x instead of res2.y
		 In this case all tests will fail, due to incorrect expected values,
		 expected values were the same as for adding which cannot happen with subtraction.
		 Fixed by changing expected values to correct ones.
		*/
		Point res1 = p1.sub(p2);
		Point res2 = p1.sub(p3);
		
		assertEquals(10, res1.x, 0); //originally failed
		assertEquals(39, res1.y, 0);
		assertEquals(17, res2.x, 0);
		assertEquals(6, res2.y, 0);
	}

}
