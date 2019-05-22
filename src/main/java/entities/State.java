package entities;

import java.awt.image.BufferedImage;

import main.WorldCreation;
import utilities.ImageLoader;

public class State {

	private BufferedImage easyDuckLeftFallen1;
	private BufferedImage easyDuckLeftFallen2;
    private ImageLoader imgLoader;
    
	public State(){
		imgLoader = WorldCreation.getImageLoader();
		setState();
	}
	
	private void setState(){
		
		easyDuckLeftFallen1 = imgLoader.getImage("easyLeftFallen1");
		easyDuckLeftFallen2 = imgLoader.getImage("easyLeftFallen2");
	}
	
	public BufferedImage getDuckFallen1() {
		return easyDuckLeftFallen1;
	}	
	
	public BufferedImage getDuckFallen2() {
		return easyDuckLeftFallen2;
	}	
}
