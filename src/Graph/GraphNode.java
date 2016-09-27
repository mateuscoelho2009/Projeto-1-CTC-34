package Graph;

import org.hamcrest.core.IsInstanceOf;

public class GraphNode {
	private boolean isStable_;
	private boolean isInitial_;
	
	int id_;
	
	public GraphNode (int id, boolean isStable, boolean isInitial) {
		isStable_ = isStable;
		isInitial_ = isInitial;
		id_ = id;
	}
	
	boolean isStable () {
		return isStable_;
	}
	
	boolean isInitial () {
		return isInitial_;
	}
	
	@Override
	public boolean equals (Object o) {
		if (o == null)
			return false;
		if (o instanceof GraphNode) {
			GraphNode gn = (GraphNode) o;
			
			if (gn.getId() == id_)
				return true;
		}
		
		return false;
	}

	public int getId() {
		return id_;
	}
}
