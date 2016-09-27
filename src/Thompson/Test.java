package Thompson;
import static org.junit.Assert.*;

import java.util.ArrayList;

import Part2.Graph;


public class Test {
	
	@org.junit.Test
	public void WhenBasicStringAFNHasJustTwoStatesAndOneEdge() {
		AFN afn = new AFN ("a");
		assertEquals(afn.getNumEdges(), 1);
		assertEquals(afn.getNumStates(), 2);
	}
	
	@org.junit.Test
	public void WhenParentesisAFNHasJustTwoStagesAndOneEdge() {
		AFN afn = new AFN ("(a)");
		assertEquals(afn.getNumEdges(), 1);
		assertEquals(afn.getNumStates(), 2);
	}

	@org.junit.Test
	public void WhenPlusAFNHasTwoStagesAndThreeEdges() {
		AFN afn = new AFN ("a+b+c");
		assertEquals(afn.getNumEdges(), 3);
		assertEquals(afn.getNumStates(), 2);
	}
	
	@org.junit.Test
	public void WhenPlusAndParentasisAFNHasTwoStagesAndTwoEdges() {
		AFN afn = new AFN ("a+(b)");
		assertEquals(afn.getNumEdges(), 2);
		assertEquals(afn.getNumStates(), 2);
	}
	
	@org.junit.Test
	public void WhenPlusAndParentesisWithPlusAFNExpectRightNumberOfStatesAndEdges() {
		AFN afn = new AFN ("a+(b+c)");
		assertEquals(afn.getNumEdges(), 3);
		assertEquals(afn.getNumStates(), 2);
	}
	
	@org.junit.Test
	public void WhenComplexPlusAndParentesisAFNExpectRightNumberOfStatesAndEdges() {
		AFN afn = new AFN ("((a)+(b+c)+p+((e+g)))");
		assertEquals(afn.getNumEdges(), 6);
		assertEquals(afn.getNumStates(), 2);
	}
	
	@org.junit.Test
	public void WhenKleeneAFNExpectRightNumberOfStatesAndEdges() {
		AFN afn = new AFN ("a*");
		assertEquals(afn.getNumEdges(), 3);
		assertEquals(afn.getNumStates(), 3);
	}
	
	@org.junit.Test
	public void WhenKleeneAndPlusAFNExpectRightNumberOfStatesAndEdges() {
		AFN afn = new AFN ("a+b*");
		assertEquals(afn.getNumEdges(), 4);
		assertEquals(afn.getNumStates(), 3);
		
		afn = new AFN ("a+(p+b)*");
		assertEquals(afn.getNumEdges(), 5);
		assertEquals(afn.getNumStates(), 3);
		
		afn = new AFN ("a*+(p+b)*");
		assertEquals(afn.getNumEdges(), 7);
		assertEquals(afn.getNumStates(), 4);
		
		afn = new AFN ("(a*+(p+b)*)*");
		assertEquals(afn.getNumEdges(), 9);
		assertEquals(afn.getNumStates(), 5);
	}
	
	@org.junit.Test
	public void WhenBasicSequenceRightNumberOfStatesAndEdges() {
		AFN afn = new AFN ("ab");
		assertEquals(afn.getNumEdges(), 2);
		assertEquals(afn.getNumStates(), 3);
	}
	
	@org.junit.Test
	public void WhenSequenceAndPlusRightNumberOfStatesAndEdges() {
		AFN afn = new AFN ("(ab)+(ca)*");
		assertEquals(afn.getNumEdges(), 6);
		assertEquals(afn.getNumStates(), 5);
		
		afn = new AFN ("ab+c");
		assertEquals(afn.getNumEdges(), 3);
		assertEquals(afn.getNumStates(), 3);
		
		afn = new AFN ("(ab)+(ca)+(g(f+e))");
		assertEquals(afn.getNumEdges(), 7);
		assertEquals(afn.getNumStates(), 5);
	}
	
	@org.junit.Test
	public void WhenSequenceAndPlusAndKleeneRightNumberOfStatesAndEdges() {
		AFN afn = new AFN ("ab+c");
		assertEquals(afn.getNumEdges(), 3);
		assertEquals(afn.getNumStates(), 3);
	}
	
	@org.junit.Test
	public void WhenAllOperandsRightNumberOfStatesAndEdges() {
		AFN afn = new AFN ("(ab)+(ca)+(g(f+e)*)");
		assertEquals(afn.getNumEdges(), 9);
		assertEquals(afn.getNumStates(), 6);
	}
	
	@org.junit.Test
	public void MigSonTestes() {
		//AFN afn = new AFN("a*b*c*");
		//Graph gOld = afn.generateGraph();
		//System.out.println(gOld.generateGraphviz());
		//System.out.println();
		
		//afn.removeEpsilonTransitions();
		
		//Graph g = afn.generateGraph();
		//System.out.println(g.generateGraphviz());
	}
	
	@org.junit.Test
	public void RegexTest(){
		AFN afn = new AFN("a*b*c*");
		System.out.println(afn.generateGraph().generateGraphviz());
		assertEquals("a*b*c*",afn.getRegex());
	}
}
