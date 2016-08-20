package game.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
	public static Map<String, String> read(String path, String tagName) throws Exception {

		 
		Map<String, String> ans = new LinkedHashMap<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(path);
		NodeList gameObjectNodes = doc.getElementsByTagName(tagName);

		Node game = gameObjectNodes.item(0);
		if (game.getNodeType() == Node.ELEMENT_NODE) {
			Element gameObjectTag = (Element) game;
			NodeList gameObjects = gameObjectTag.getChildNodes();
			for (int i = 0; i < gameObjects.getLength(); i++) {
				Node objectNode = gameObjects.item(i);
				if (objectNode.getNodeType() == Node.ELEMENT_NODE) {
					Element obj = (Element) objectNode;
					String name = obj.getAttribute("name");
					String val = obj.getAttribute("path");
					ans.put(name, val);
				}
			}
		}
		Logger.getLogger(XMLParser.class).info("Load all themes name from xml");
		return ans;
	}
}
