package Thompson;
import java.util.ArrayList;

import Part2.Graph;
import Part2.GraphEdge;
import Part2.GraphNode;


public class AFN {

	ArrayList <Edge> edges_;
	int numStates_;
	ArrayList<Boolean> stableStates_ = new ArrayList<Boolean>();
	ArrayList<Boolean> starterStates_ = new ArrayList<Boolean>();
	
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
		
		for (int i = 0; i < numStates_; i++){
			stableStates_.add(false);
			starterStates_.add(false);
		}
		stableStates_.remove(1);
		stableStates_.add(1, true);
		starterStates_.remove(0);
		starterStates_.add(0, false);
	}
	
	public AFN(ArrayList<Edge> edges, int numStates){
		edges_ = edges;
		numStates_ = numStates;
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
	
	public void removeEpsilonTransitions(){
		for (int k = 0; k < edges_.size(); k++){
			if (edges_.get(k).getCost().equals("&")){
				int init = edges_.get(k).getIni();
				int end = edges_.get(k).getFim();
				for (int i = 0; i < edges_.size(); i++){
					if (edges_.get(i).getIni() == end){
						Edge newedge = new Edge(edges_.get(i).getCost(), init, edges_.get(i).getFim());
						edges_.add(newedge);
					}
				}
				edges_.remove(k);
			}
		}
		
		// Mais uma vez, para lidar com loops
		for (int k = 0; k < edges_.size(); k++){
			if (edges_.get(k).getCost().equals("&")){
				int init = edges_.get(k).getIni();
				int end = edges_.get(k).getFim();
				for (int i = 0; i < edges_.size(); i++){
					if (edges_.get(i).getIni() == end){
						Edge newedge = new Edge(edges_.get(i).getCost(), init, edges_.get(i).getFim());
						edges_.add(newedge);
					}
				}
				edges_.remove(k);
			}
		}
	}
	
	public static void main(String[] args){
		ArrayList<Edge> a = new ArrayList<Edge>();
		/*a.add(new Edge("&",0,4));
		a.add(new Edge("a",4,4));
		a.add(new Edge("&",4,2));
		a.add(new Edge("b",2,1));
		a.add(new Edge("&",0,5));
		a.add(new Edge("b",5,5));
		a.add(new Edge("&",5,3));
		a.add(new Edge("a",3,1));*/
		
		a.add(new Edge("&",0,2));
		a.add(new Edge("&",2,1));
		a.add(new Edge("b,c",3,2));
		a.add(new Edge("a",2,3));
		
		/*a.add(new Edge("&",0,3));
		a.add(new Edge("a,b",3,3));
		a.add(new Edge("&",3,2));
		a.add(new Edge("b",2,4));
		a.add(new Edge("b",4,5));
		a.add(new Edge("&",5,6));
		a.add(new Edge("a,b",6,6));
		a.add(new Edge("&",6,1));*/
				
		AFN afn = new AFN(a,7);
		afn.removeEpsilonTransitions();
		afn.print();
	}

}
