package util;

public class TableTile {
	private String row;
	private String col;
	private String val;
	
	public TableTile(String row, String col, String val) {
		this.row = row;
		this.col = col;
		this.val = val;
	}
	
	public String row(){
		return row;
	}
	
	public String col(){
		return col;
	}
	
	public String val(){
		return val;
	}
	
	public void setVal(String v){
		val = v;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean b1 = super.equals(obj);
		TableTile t = (TableTile) obj;
		boolean b2 = t.col.equals(col) && t.row.equals(row);
		return b1 || b2;
		
	}
}
