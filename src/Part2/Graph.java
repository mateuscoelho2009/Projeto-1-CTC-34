package Part2;

import java.util.ArrayList;

public class Graph {
	ArrayList <GraphNode> nodes_;
	ArrayList <GraphEdge> edges_;
	
	public Graph (ArrayList <GraphNode> ns, ArrayList <GraphEdge> es) {
		nodes_ = ns;
		edges_ = es;
	}
	
	public boolean verifyExpression (String s) {
		boolean isOk = false;
		
		for (int i = 0; i < nodes_.size(); i++) {
			if (nodes_.get(i).isInitial()) {
				isOk = RecResolveVerifyExpression(s, i);
				
				if (isOk)
					return isOk;
			}
		}
		
		return isOk;
	}
	
	/*
	 * TODO: Tem um "subitem " do item 2 que pede apenas os estados computados.
	 * 		Para isso, basta uma lista passada na verifyExpression e ir inserindo nela
	 * 		os estados (id's) dos nós que não pertencem a ela.
	 */
	private boolean RecResolveVerifyExpression (String s, int atuNode) {
		boolean isOk = false;
		
		// Condições de parada
		if (s == null) {
			if (nodes_.get(atuNode).isStable()) {
				return true;
			}
			
			return false;
		}
		
		// Recursão
		for (int i = 0; i < edges_.size(); i++) {
			// Arestas que iniciam no nó atual
			if (edges_.get(i).getBeginNodeId() == atuNode) {
				GraphEdge e = edges_.get(i);
				String cost = e.getCost();
				
				if (s.startsWith(cost)) {
					isOk = RecResolveVerifyExpression(s.substring(1), e.getEndNodeId());
					
					if (isOk)
						return isOk;
				} else if (cost.equals("&")) {
					isOk = RecResolveVerifyExpression(s, e.getEndNodeId());
					
					if (isOk)
						return isOk;
				}
			}
		}
		
		if (s.equals("")) {
			if (nodes_.get(atuNode).isStable()) {
				return true;
			}
			
			return false;
		}
		
		return isOk;
	}
}
