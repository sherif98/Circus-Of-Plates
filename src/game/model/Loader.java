package game.model;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.log4j.Logger;

public class Loader {
	public static ArrayList<Class<?>> loadClass(String pathToJar, Class<?> myClass) throws Exception {
		Logger logger = Logger.getLogger(Logger.class);
		ArrayList<Class<?>> ans = new ArrayList<>();

		//JarFile j_file = new JarFile(pathToJar);
		JarInputStream jStream = new JarInputStream(new FileInputStream(new File(pathToJar)));
		URL[] urls = { new URL("jar:file:" + pathToJar + "!/") };
		URLClassLoader l = URLClassLoader.newInstance(urls);

		JarEntry e = jStream.getNextJarEntry();
		while (e != null) {
			String class_name = e.getName();
			if (class_name.matches(".+\\.class")) {
				class_name = class_name.replaceAll("(.+)\\.class", "$1");
				class_name = class_name.replaceAll("/", "\\.");

				Class<?> c = l.loadClass(class_name);
				if (myClass.isAssignableFrom(c)) {
					ans.add(c);
				}
			}
			e = jStream.getNextJarEntry();
		}
		jStream.close();
		logger.info("load classes from "+pathToJar+"which implements"+myClass.getName());
		logger.info("classes are loaded"+ans.toString());
		return ans;
	}
}
