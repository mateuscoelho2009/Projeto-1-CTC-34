package Thompson;
import java.util.ArrayList;
import java.util.Collection;


public class Edge {
	
	private String s_;
	private int ini_, fim_;

	public Edge(String string, int ini, int fim) {
		ini_ = ini;
		fim_ = fim;
		s_ = string;
	}

	public ArrayList<Edge> simplify(int numStates) {
		ArrayList <Edge> res = new ArrayList<Edge>();
		
		// Casos base
		if (s_.equals("") || s_ == null)
			return res;
		
		if (s_.length() == 1) {
			res.add(this);
			
			return res;
		}
		
		if (s_.length() == 2 && s_.startsWith("*")) {			
			Edge e = new Edge(s_.substring(0, s_.length()-1), numStates, numStates);
			
			res.add(new Edge("&", ini_, numStates));
			res.add(new Edge("&", numStates, fim_));
			
			numStates ++;
			
			res.addAll(e.simplify(numStates));
			
			return res;
		}
		
		// Recursão
		if (s_.startsWith("(") && s_.endsWith(")") && CheckRightExpression(s_.substring(1, s_.length()-1))) {
			Edge e = new Edge(s_.substring(1, s_.length()-1), ini_, fim_);
			
			res = e.simplify(numStates);
			
			return res;
		}
		
		ArrayList <Integer> pluses = new ArrayList<Integer> ();
		getAllOutParentesisPlus (pluses);
		
		if (pluses.size() != 0) {
			int iString = 0;
			
			for (int i = 0; i < pluses.size(); i++) {
				int plus = pluses.get(i);
				
				res.add(new Edge(s_.substring(iString, plus), ini_, fim_));
				
				iString = plus + 1;
			}
			
			res.add(new Edge(s_.substring(iString), ini_, fim_));
			
			ArrayList<Edge> auxRes = new ArrayList<Edge> ();
			
			for (int i = 0; i < res.size(); i++) {
				auxRes.addAll(res.get(i).simplify(numStates));
				
				for (int j = 0; j < auxRes.size(); j++) {
					Edge edge = auxRes.get(j);
					
					if (edge.getFim() >= numStates) numStates = edge.getFim() + 1;
					if (edge.getIni() >= numStates) numStates = edge.getIni() + 1;
				}
			}
			
			res = auxRes;
			
			return res;
		}
		
		// Regra da composição
		ArrayList <Integer> elements = new ArrayList<Integer> ();
		getAllElements (elements);
		
		if (elements.size() > 1) {
			int iString = 0;
			
			int auxIni = ini_, auxFim = numStates;
			
			for (int i = 0; i < elements.size() - 1; i++) {
				int element = elements.get(i);
				
				res.add(new Edge(s_.substring(iString, element), auxIni, auxFim));
				
				numStates ++;
				
				auxIni = auxFim;
				auxFim = numStates;
				
				iString = element;
			}
			
			res.add(new Edge(s_.substring(iString), auxIni, fim_));
			
			ArrayList<Edge> auxRes = new ArrayList<Edge> ();
			
			for (int i = 0; i < res.size(); i++) {
				auxRes.addAll(res.get(i).simplify(numStates));
				
				for (int j = 0; j < auxRes.size(); j++) {
					Edge edge = auxRes.get(j);
					
					if (edge.getFim() >= numStates) numStates = edge.getFim() + 1;
					if (edge.getIni() >= numStates) numStates = edge.getIni() + 1;
				}
			}
			
			res = auxRes;
			
			return res;
		}
		
		if (s_.endsWith("*") && CheckRightExpression(s_.substring(0, s_.length()-1))) {			
			Edge e = new Edge(s_.substring(0, s_.length()-1), numStates, numStates);
			
			res.add(new Edge("&", ini_, numStates));
			res.add(new Edge("&", numStates, fim_));
			
			numStates ++;
			
			res.addAll(e.simplify(numStates));
			
			return res;
		}
		
		res.add(this);
		return res;
	}

	private ArrayList<Edge> simplifyKleene(int numStates) {
		ArrayList <Edge> res = new ArrayList<Edge>();
		
		numStates ++;
		Edge e = new Edge(s_.substring(0, s_.length()-1), numStates, numStates);
		
		res.add(new Edge("&", ini_, numStates));
		res.add(new Edge("&", numStates, fim_));
		
		res.addAll(e.simplify(numStates));
		
		return null;
	}

	private void getAllOutParentesisPlus(ArrayList<Integer> pluses) {
		String aux = new String(s_);
		int parentesisCount = 0;
		int atuIndex = 0;
		
		while (aux != null && !aux.equals("")) {
			// Há ocorrência de parentesis anteriormente
			if (parentesisCount > 0) {
				 if (aux.contains ("(")) {
					 int fLeftParentesis = aux.indexOf("(");
					 int fRightParentesis = aux.indexOf(")");
					 
					 if (fLeftParentesis < fRightParentesis) {
						 aux = aux.substring(fLeftParentesis + 1);
						 atuIndex += fLeftParentesis + 1;
						 parentesisCount ++;
					 } else {
						 aux = aux.substring(fRightParentesis + 1);
						 atuIndex += fRightParentesis + 1;
						 parentesisCount --;
					 }
				 } else {
					 int fRightParentesis = aux.indexOf(")");
					 
					 if (fRightParentesis + 1 >= aux.length())
						 aux = "";
					 else {
						 aux = aux.substring(fRightParentesis + 1);
						 atuIndex += fRightParentesis + 1;
						 parentesisCount --;
					 }
				 }
				 
				 continue;
			} else {
				if (aux.contains("(") && aux.contains("+")) {
					int fLeftParentesis = aux.indexOf("(");
					int plus = aux.indexOf("+");
					
					if (plus < fLeftParentesis) {
						aux = aux.substring(plus+1);
						pluses.add(plus + atuIndex);
						atuIndex += plus + 1;
					} else {
						aux = aux.substring(fLeftParentesis + 1);
						atuIndex += fLeftParentesis + 1;
						parentesisCount ++;
					}
				} else if (aux.contains("+")) {
					int plus = aux.indexOf("+");
					
					aux = aux.substring(plus+1);
					pluses.add(plus + atuIndex);
					atuIndex += plus + 1;
				} else {
					aux = "";
				}
			}
		}
	}
	
	private void getAllElements(ArrayList<Integer> elements) {
		String aux = new String(s_);
		int parentesisCount = 0;
		int atuIndex = 0;
		
		while (aux != null && !aux.equals("")) {
			// Há ocorrência de parentesis anteriormente
			if (parentesisCount > 0) {
				 if (aux.contains ("(")) {
					 int fLeftParentesis = aux.indexOf("(");
					 int fRightParentesis = aux.indexOf(")");
					 
					 if (fLeftParentesis < fRightParentesis) {
						 aux = aux.substring(fLeftParentesis + 1);
						 atuIndex += fLeftParentesis + 1;
						 parentesisCount ++;
					 } else {
						 aux = aux.substring(fRightParentesis + 1);
						 atuIndex += fRightParentesis + 1;
						 parentesisCount --;
					 }
				 } else {
					 int fRightParentesis = aux.indexOf(")");
					 
					 if (fRightParentesis + 1 >= aux.length()) {
						 aux = "";
						 atuIndex += fRightParentesis + 1;
						 elements.add(atuIndex);
					 }
					 else {
						 aux = aux.substring(fRightParentesis + 1);
						 atuIndex += fRightParentesis + 1;
						 parentesisCount --;
					 }
				 }
				 
				 if (parentesisCount == 0) {
					 if (aux.startsWith("*")) {
						 aux = aux.substring(1);
						 atuIndex ++;
						 elements.add(atuIndex);
					 } else {
						 elements.add(atuIndex);
					 }
				 }
			} else {
				if (aux.startsWith("(")) {
					aux = aux.substring(1);
					atuIndex++;
					parentesisCount++;
				} else if (aux.length() > 1) {
					String s = aux.substring(1);
					
					if (s.startsWith("*")) {
						aux = aux.substring(2);
						atuIndex +=2;
						elements.add(atuIndex);
					} else {
						aux = aux.substring(1);
						atuIndex ++;
						elements.add(atuIndex);
					}
				} else {
					aux = "";
					atuIndex++;
					elements.add(atuIndex);
				}
			}
		}
	}
	
	public boolean isKleene () {
		return s_.endsWith("*") && CheckRightExpression(s_.substring(0, s_.length()-1));
	}
	
	public boolean CheckRightExpression (String s) {
		String aux = new String(s);
		int parentesisCount = 0;
		
		while (aux != null && !aux.equals("") && parentesisCount >= 0) {
			if (aux.contains ("(") && aux.contains(")")) {
				 int fLeftParentesis = aux.indexOf("(");
				 int fRightParentesis = aux.indexOf(")");
				 
				 if (fLeftParentesis < fRightParentesis) {
					 aux = aux.substring(fLeftParentesis + 1);
					 parentesisCount ++;
				 } else {
					 aux = aux.substring(fRightParentesis + 1);
					 parentesisCount --;
				 }
			 } else if (aux.contains(")")) {
				 int fRightParentesis = aux.indexOf(")");
				 
				 if (fRightParentesis + 1 >= aux.length()) {
					 aux = "";
					 parentesisCount --;
				 }
				 else {
					 aux = aux.substring(fRightParentesis + 1);
					 parentesisCount --;
				 }
			 } else break;
		}
		
		if (parentesisCount != 0)
			return false;
		
		return true;
	}

	public int getIni() {return ini_;}
	public int getFim() {return fim_;}
	public String getCost() {return s_;}
	
	public void print() {
		System.out.println(ini_ + "->" + fim_ + ":" + s_);
	}
}
