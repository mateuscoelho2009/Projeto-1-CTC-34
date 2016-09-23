package TestesForster;

import static org.junit.Assert.*;

import org.junit.Test;

import Part2.Graph;
import Thompson.AFN;

public class TestesForster {

	@Test
	public void FirstTest() {
		AFN afn = new AFN("(a+b)*bb(b+a)*");
		Graph g = afn.generateGraph();
		assertEquals (false, g.verifyExpression("ab"));
		assertEquals (true, g.verifyExpression("abb"));
		assertEquals (true, g.verifyExpression("bba"));
		assertEquals (true, g.verifyExpression("abba"));
	}

	@Test
	public void SecondTest() {
		AFN afn = new AFN("(a(b+c))*");
		Graph g = afn.generateGraph();
		assertEquals (true, g.verifyExpression("ab"));
		assertEquals (false, g.verifyExpression("abb"));
		assertEquals (false, g.verifyExpression("bba"));
		assertEquals (false, g.verifyExpression("abba"));
	}
	
	@Test
	public void ThirdTest() {
		AFN afn = new AFN("a*b+b*a");
		Graph g = afn.generateGraph();
		assertEquals (true, g.verifyExpression("ab"));
		assertEquals (false, g.verifyExpression("abb"));
		assertEquals (true, g.verifyExpression("bba"));
		assertEquals (false, g.verifyExpression("abba"));
	}
	
	@Test
	public void FourthTest() {
		AFN afn = new AFN("a*b*c*");
		Graph g = afn.generateGraph();
		assertEquals (true, g.verifyExpression("ab"));
		assertEquals (true, g.verifyExpression("abb"));
		assertEquals (false, g.verifyExpression("bba"));
		assertEquals (false, g.verifyExpression("abba"));
		
		System.out.println(g.generateGraphviz());
	}
}
