package com.johngori.games.ahiru.entities;

import java.awt.Rectangle;

public class Ice extends Ammo{

	public Ice(int xPos, int yPos) {
		super(xPos, yPos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rectangle getBounds(){
		Rectangle r;
		r = new Rectangle(getX(), getY(), getRectangleWidth(), getRectangleHeight());
		return r;
	}
}
