package userinterface;

import java.awt.Rectangle;

public class MainMenu extends UserInterface{
	
	public Rectangle getBounds(){
		Rectangle r;
		r = new Rectangle(getX(),getY()+5,165,50);
		
		return r;
	}
}
