package org.fernandez.clothetype;

public abstract class AbstractClothe {
	protected boolean selected;
	public String name;
	private char label;
	public final  static String NOT_AVAILABLE = "Clothe not available!";
	
	public AbstractClothe(){
		this.name = "nel";
		this.selected = false;
	}
	
	public void setLabel(char label){
		this.label = label;
	}
	
	public char getLabel(){
		return this.label;
	}
	
	public boolean isSelected(){
		return this.selected;
	}
	
	public void setSelected(boolean sel){
		this.selected = sel;
	}
}
