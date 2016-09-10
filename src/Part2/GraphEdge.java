package Part2;

public class GraphEdge {
	private GraphNode beg_, end_;
	private String cost_;
	
	public GraphEdge (GraphNode beg, GraphNode end, String cost) {
		beg_ = beg;
		end_ = end;
		cost_ = cost;
	}
	
	public int getBeginNodeId () {
		return beg_.id_;
	}
	
	public int getEndNodeId () {
		return end_.id_;
	}
	
	public String getCost () {
		return cost_;
	}
}
