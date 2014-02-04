import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
	private static DOMParser parser;
	private File file;
	private String path;
	
	private DOMParser() {}
	
	public static DOMParser getInstance() {
		if(parser == null) {
			parser = new DOMParser();
		}
		
		return parser;
	}
	
	private File downloadFile(URL url, String path) throws IOException {
		this.path = path;
		file = new File(path);
		
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		return file;
	}
	
	public String[][] parse(URL url, String path) throws IOException, ParserConfigurationException, SAXException {
		return parse(downloadFile(url, path));
	}
	
	public String[][] parse(File file) throws ParserConfigurationException, SAXException, IOException {		
		String[][] table = new String[8][6];
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		
		doc.getDocumentElement().normalize();		
		NodeList nodeList = doc.getElementsByTagName("Data");
		
		int rowIndex = 0;
		int columnIndex = 0;
		
		for (int i = 0; i < nodeList.getLength(); ++i) {
			if(i > 7 && !((i - 14) % 7 == 0)) { // Пропускаем дни недели и строки с часами пар
				Node node = nodeList.item(i);
				String line = node.getTextContent().replaceAll("\\t+", " "); // убираем множественную табуляцию
				line = line.replace("\n", " ");
				
				if(columnIndex >= 6) {
					columnIndex = 0;
					++rowIndex;
				}
				
				table[rowIndex][columnIndex++] = line;
			}
		}
		
		return table;
	}
}
