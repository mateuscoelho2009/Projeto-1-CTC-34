package util;

import java.util.ArrayList;

public class Table {
	private ArrayList<String> rowElems = new ArrayList<String>();
	private ArrayList<String> colElems = new ArrayList<String>();
	private ArrayList<TableTile> tiles = new ArrayList<TableTile>();
	
	public void addRow(String row){
		rowElems.add(row);
	}
	
	public void addColumn(String col){
		colElems.add(col);
	}
	
	public void addTile(TableTile t){
		tiles.add(t);
	}
	
	public boolean containsRow(String row){
		return rowElems.contains(row);
	}
	
	public boolean containsColumn(String col){
		return colElems.contains(col);
	}
	
	public boolean containsTile(TableTile t){
		return tiles.contains(t);
	}
	
	public TableTile getTile(String row, String col){
		for (TableTile t : tiles){
			if (t.col().equals(col) && t.row().equals(row))
				return t;
		}
		return null;
	}
}
