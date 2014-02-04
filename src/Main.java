import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Main {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, OperationNotSupportedException {
		DOMParser dp = DOMParser.getInstance();
		URL scheduleURL = new URL("http://www.sgu.ru/schedule/knt/do/151/lesson");
		String scheduleFilePath = new File(".").getAbsolutePath() + "schedule.xls";
		String[][] table = dp.parse(scheduleURL, scheduleFilePath);	
		
		ScheduleMarkup vk = new ScheduleMarkup(table, 
				new Object[]{"Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1},
				new Object[]{"Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1});
		System.out.println(vk.getSchedule());
	}

}
