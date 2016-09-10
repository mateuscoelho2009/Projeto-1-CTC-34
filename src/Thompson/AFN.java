package Thompson;
import java.util.ArrayList;

import Part2.Graph;
import Part2.GraphEdge;
import Part2.GraphNode;


public class AFN {

	ArrayList <Edge> edges_;
	int numStates_;
	
	public AFN(String string) {
		edges_ = new ArrayList<Edge>();
		
		edges_.add(new Edge(string, 0, 1));
		
		numStates_ = 2;
		
		edges_ = edges_.get(0).simplify(numStates_);
		
		for (int j = 0; j < edges_.size(); j++) {
			Edge edge = edges_.get(j);
			
			if (edge.getFim() >= numStates_) numStates_ = edge.getFim() + 1;
			if (edge.getIni() >= numStates_) numStates_ = edge.getIni() + 1;
		}
	}

	public int getNumEdges() {
		return edges_.size();
	}

	public int getNumStates() {
		return numStates_;
	}
	
	public void print() {
		for (int i = 0; i < edges_.size(); i++)
			edges_.get(i).print();
	}
	
	public Graph generateGraph () {
		ArrayList <GraphNode> nodes = new ArrayList <GraphNode> ();
		ArrayList <GraphEdge> edges = new ArrayList <GraphEdge> ();
		
		// Gerar Nós e Arestas
		GraphNode nn = new GraphNode(0, false, true);
		nodes.add(nn);
		nn = new GraphNode(1, true, false);
		nodes.add(nn);
		
		for (int i = 2; i < numStates_; i++) {
			nn = new GraphNode(i, false, false);
			nodes.add(nn);
		}
		
		for (int i = 0; i < edges_.size(); i ++) {
			Edge e = edges_.get(i);
			
			edges.add(new GraphEdge(nodes.get(e.getIni()), 
									nodes.get(e.getFim()), 
									e.getCost()));
		}
		
		return new Graph (nodes, edges);
	}

}
