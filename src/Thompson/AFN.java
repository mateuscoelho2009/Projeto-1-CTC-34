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
	ArrayList<Boolean> visitedEdges_;
	
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
		starterStates_.add(0, true);
		
		visitedEdges_ = new ArrayList<Boolean>();
		for (int i = 0; i < edges_.size(); i++){
			visitedEdges_.add(false);
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
		for (int i = 0; i < numStates_; i++) {
			GraphNode nn = new GraphNode(i, stableStates_.get(i), starterStates_.get(i));
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
		atuStableStates();
		
		for (int k = 0; k < edges_.size(); k++){
			if (edges_.get(k).getCost().equals("&")){
				int init = edges_.get(k).getIni();
				int end = edges_.get(k).getFim();
				boolean hasLoopBefore = false;
				String LoopBeforeCost = "";
				for (int i = 0; i < edges_.size(); i++){
					if (edges_.get(i).getIni() == end){
						Edge newedge = new Edge(edges_.get(i).getCost(), init, edges_.get(i).getFim());
						edges_.add(newedge);
					}
					if (edges_.get(i).getIni() == init && edges_.get(i).getFim() == init){
						hasLoopBefore = true;
						LoopBeforeCost = edges_.get(i).getCost();
					}
				}
				
				if (hasLoopBefore)
					edges_.add(new Edge(LoopBeforeCost,init,end));
				
				edges_.remove(k);
			}
		}
		
		// Mais uma vez, para lidar com loops de transicao epsilon
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
		
		atuStableStates();
	}
	
	public String getRegex(){
		String regex = "";
		
		for (Edge e : edges_){
			if (e.getIni() == 0){
				regex += recursionRegex(e, "");
				regex += "+";
			}
		}
		regex = regex.substring(0,regex.length()-1);
		return regex;
	}
	
	private String recursionRegex(Edge e, String s) {
		if (e.getFim() == 1)
			return e.getCost();
		Edge newedge = null;
		for (Edge it : edges_){
			int i = edges_.indexOf(it);
			if (it.getIni() == e.getFim() && !visitedEdges_.get(i)){
				visitedEdges_.remove(i);
				visitedEdges_.add(i, true);
				
				if (it.getIni() == it.getFim())
					s += it.getCost() + "*";
				
				else{
					newedge = it;
					break;
				}
			}
		}
		if (!newedge.getCost().equals("&"))
			s = s + newedge.getCost();
		return s + recursionRegex(newedge, s);
	}
		
	private void atuStableStates() {
		for (int i = 0; i < numStates_; i++) {
			checkStability(i);
		}
	}

	private boolean checkStability(int index) {		
		for (int i = 0; i < edges_.size(); i++) {			
			if (edges_.get(i).getIni() == index &&
					edges_.get(i).getCost().equals("&")) {
				if (checkStability(edges_.get(i).getFim())) {
					stableStates_.remove(index);
					stableStates_.add(index, true);
					
					return true;
				}
			}
		}
		
		if (stableStates_.get(index)) {
			for (int i = 0; i < edges_.size(); i++) {			
				if (edges_.get(i).getIni() == index ||
						edges_.get(i).getFim() == index) {
					return true;
				}
			}
			
			stableStates_.remove(index);
			stableStates_.add(index, false);
			
			return false;
		}
		
		return false;
	}

}
