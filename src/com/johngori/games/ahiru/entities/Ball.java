package com.johngori.games.ahiru.entities;

import java.awt.Rectangle;

public class Ball extends Ammo{
	
	public Ball(int xPos, int yPos) {
		super(xPos, yPos);
	}
	
	@Override
	public Rectangle getBounds(){
		Rectangle r;
		r = new Rectangle(getX(), getY(), getRectangleWidth(), getRectangleHeight());
		return r;
	}
	
}
