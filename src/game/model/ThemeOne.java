package game.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import game.controller.XMLParser;

public class ThemeOne implements Theme {

	private static final String path = "config.xml";

	@Override
	public ArrayList<MyImage> loadAllImages() {
		Logger logger = Logger.getLogger(ThemeOne.class);
		
		/**
		 * each theme has his own configuration file which it will read from
		 * reading from the theme's configuration file will give me the location 
		 * of the theme's images.
		 * then I will load these images and return it back to the resource class
		 */
		
		
		Map<String, String> imagesLocation = null;
		ArrayList<MyImage> images = new ArrayList<>();
		try {
			imagesLocation = XMLParser.read(path, "GameObjects");
		} catch (Exception e) {
			logger.fatal("cannot locate images location");
			e.printStackTrace();
			
		}
		if (imagesLocation == null) {
			logger.warn("image location for themes is null");
			return images;
			
		}

		for (Map.Entry<String, String> e : imagesLocation.entrySet()) {
			BufferedImage buffer[] = new BufferedImage[1];
			try {//getClass().getResourceAsStream(e.getValue())
				buffer[0] = ImageIO.read(new FileInputStream(new File(e.getValue())));
			} catch (Exception x) {
				logger.fatal("cannot read buffered image");
				x.printStackTrace();
			}
			MyImage image = new MyImage(e.getKey(), buffer);
			images.add(image);
		}
		logger.info("images was read in array list");
		return images;
	}
}
