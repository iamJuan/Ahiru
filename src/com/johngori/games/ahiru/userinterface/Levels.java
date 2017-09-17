package com.johngori.games.ahiru.userinterface;

import java.awt.Rectangle;
public class Levels extends UserInterface{
	
	public Rectangle getBounds(){
		Rectangle r;
		r = new Rectangle(getX()+10,getY()+10,120,135);
		
		return r;
	}

}
