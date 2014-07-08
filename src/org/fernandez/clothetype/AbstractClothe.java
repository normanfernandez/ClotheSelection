package org.fernandez.clothetype;

public abstract class AbstractClothe {
	protected boolean selected;
	public String name;
	public final  static String NOT_AVAILABLE = "Clothe not available!";
	
	public AbstractClothe(){
		this.name = "nel";
		this.selected = false;
	}
	
	public boolean isSelected(){
		return this.selected;
	}
	
	void setSelected(boolean sel){
		this.selected = sel;
	}
}
