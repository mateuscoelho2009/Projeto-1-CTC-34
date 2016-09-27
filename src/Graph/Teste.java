package Graph;

import static org.junit.Assert.*;

import org.junit.Test;

import Thompson.AFN;

public class Teste {
	
	@Test
	public void WhenRightStringExpectTrue() {
		AFN afn = new AFN("a+(b)");
		Graph g = afn.generateGraph();
		assertEquals (true, g.verifyExpression("a"));
		
		afn = new AFN("(ab)+(ca)*");
		g = afn.generateGraph();
		assertEquals (true, g.verifyExpression("ab"));
		assertEquals (true, g.verifyExpression("cacaca"));
		assertEquals (true, g.verifyExpression(""));
	}
	
	@Test
	public void WhenWrongStringExpectTrue() {
		AFN afn = new AFN("a+(b)");
		Graph g = afn.generateGraph();
		assertEquals (false, g.verifyExpression("c"));
		
		afn = new AFN("(ab)+(ca)*");
		g = afn.generateGraph();
		assertEquals (false, g.verifyExpression("aba"));
		assertEquals (false, g.verifyExpression("cacacaa"));
		assertEquals (false, g.verifyExpression("ac"));
	}

}
