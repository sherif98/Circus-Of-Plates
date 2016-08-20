package game.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import game.controller.XMLParser;

public class Resources {

	private static Resources instance = null;
	private MyLinkedList<Shape> shapes = new MyLinkedList<>();
	private ArrayList<Color> colors = new ArrayList<>();
	private Map<String, String> themes = new LinkedHashMap<>();
	private Theme defaultTheme = null;
	private String themePath = "Themes.xml";
	private String pathToShapes = "ShapesConfig.xml";
	private ImageFactory factory = new ImageFactory();
	private Logger logger = Logger.getLogger(Resources.class);

	private Resources() {
		colors.add(Color.YELLOW);
		colors.add(Color.BLUE);
		colors.add(Color.RED);
		colors.add(Color.CYAN);
		colors.add(Color.green);
		colors.add(Color.ORANGE);
		logger.info("put colors in color ArrayList to color shapes ");
	}

	public synchronized static Resources getInstance() {
		if (instance == null) {
			instance = new Resources();
		}
		return instance;
	}

	public void loadAllThemes() {
		Map<String, String> ans = null;
		try {
			ans = XMLParser.read(themePath, "Themes");

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ans != null) {
			themes.putAll(ans);
		}
		logger.info("load all themes");
	}

	public void loadAllImages() {
		Random rand = new Random();
		String name = "", val = "";
		int index = rand.nextInt(themes.size());
		int cnt = 0;
		for (Map.Entry<String, String> e : themes.entrySet()) {
			if (cnt == index) {
				name = e.getKey();
				val = e.getValue();
			}
			cnt++;
		}
		if (val.equals("Default")) {
			try {
				defaultTheme = (Theme) Class.forName(name).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				ArrayList<Class<?>> themes = Loader.loadClass(val, Theme.class);
				for (Class<?> c : themes) {
					defaultTheme = (Theme) c.newInstance();
				}
				logger.info("load a randam theme in class");
			} catch (Exception e) {
				logger.fatal("can't load theme");
				e.printStackTrace();
			}
		}
		if (defaultTheme != null) {
			ArrayList<MyImage> images = new ArrayList<>();
			images = defaultTheme.loadAllImages();
			factory.setImages(images);
		}
		logger.info("load all Images");
	}

	public void loadAllShapes() {
		Map<String, String> temp = null;
		try {
			temp = XMLParser.read(pathToShapes, "Shapes");
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<Class<?>> ans = null;
		for (Map.Entry<String, String> entry : temp.entrySet()) {
			try {
				ans = Loader.loadClass(entry.getValue(), Shape.class);
				for (Class<?> c : ans) {
					for (Color col : colors) {
						try {
							Shape tempShape = (Shape) c.newInstance();
							tempShape.setFillColor(col);
							shapes.add(tempShape);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			} catch (Exception e) {
				logger.fatal("Can't load shapes in class");
				e.printStackTrace();
			}
		}
		logger.info("load all shapes and color them");
	}

	public Shape getShape() {
		/*
		 * this is prototype design pattern
		 */
		Random rand = new Random();
		int index = rand.nextInt(shapes.size());
		Shape ans = (Shape) shapes.get(index).clone();
		return ans;
		
	}

	public BufferedImage[] getImage(String name) {
		return factory.getImage(name);
	}
}
