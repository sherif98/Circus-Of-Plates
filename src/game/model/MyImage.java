package game.model;

import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

public class MyImage {
	private String name;
	private BufferedImage[] image;
	
	public MyImage(String name, BufferedImage[] image) {
		this.name = name;
		this.image = image;
		Logger.getLogger(MyImage.class).debug("Create MyImage object with name "+name);
	}
	
	public boolean match(String req) {
		return name.equals(req);
	}
	
	public  BufferedImage[] getImage() {
		return image;
	}

}
