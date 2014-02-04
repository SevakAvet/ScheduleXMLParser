import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Main {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		DOMParser dp = new DOMParser();
		String[][] table = DOMParser.parse(new File("schedule_do_151.xml"));

		String[][] numerator = getEmptyTable();
		String[][] denominator = getEmptyTable();

		boolean isNumerator = false;
		boolean isDenominator = false;

		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 6; ++j) {
				String s = table[i][j];

				isNumerator = isDenominator = false;
				System.out.println(s);

				if (s.contains("чис.")) {
					isNumerator = true;
				}

				if (s.contains("знам.")) {
					isDenominator = true;
				}

				if (!s.isEmpty()) {					
					if (isNumerator) {
						if (isDenominator) {
							int denomIndex = s.indexOf("знам.");
							numerator[i][j] = s.substring(4, denomIndex);
							denominator[i][j] = s.substring(denomIndex + 5, s.length());
						} else {
							numerator[i][j] = s.substring(4, s.length());
						}
					} else {
						if(isDenominator) {
							denominator[i][j] = s.substring(5, s.length());
						} else {
							numerator[i][j] = s;
							denominator[i][j] = s;
						}
					}
				}
			}
		}		
		
		VKWikiMarkup.printTable(denominator);
	}

	private static String[][] getEmptyTable() {
		String[][] table = new String[8][6];

		for (int i = 0; i < table.length; ++i) {
			for (int j = 0; j < table[i].length; ++j) {
				table[i][j] = "";
			}
		}

		return table;
	}
}
