import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DOMParser {
	public static String[][] parse(File file) throws ParserConfigurationException, SAXException, IOException {
		String[][] table = new String[8][6];
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		
		doc.getDocumentElement().normalize();		
		NodeList nList = doc.getElementsByTagName("Data");
		
		int rowIndex = 0;
		int columnIndex = 0;
		int count = 0;
		
		for(int i=0; i<nList.getLength(); ++i) {
			if(i > 7 && !((i - 14) % 7 == 0)) {
				++count;
				Node node = nList.item(i);
				String line = node.getTextContent().replaceAll("\\t+", " ");
				line = line.replace("\n", " ");
				//System.out.println(line);
				
				if(columnIndex >= 6) {
					columnIndex = 0;
					++rowIndex;
				}
				
				table[rowIndex][columnIndex++] = line;
			}
		}
		System.out.println(count);
		return table;
	}
}
