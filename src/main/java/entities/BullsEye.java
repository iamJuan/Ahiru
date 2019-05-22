package entities;

import java.awt.Rectangle;

public class BullsEye extends Entities{
		
	public BullsEye(){
		setX(300);
		setY(400);
	}

	public Rectangle getBounds(){
		Rectangle r;
		r = new Rectangle(getX(), getY(), 25,25);
		return r;
	}
}
