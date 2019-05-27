package entities;

import java.awt.Rectangle;

public class AmmoAP extends Ammo{

	public AmmoAP(int xPos, int yPos) {
		super(xPos, yPos);
	}

	@Override
	public Rectangle getBounds(){
		Rectangle r;
		r = new Rectangle(getX(), getY(), getRectangleWidth(), getRectangleHeight());
		return r;
	}
}
