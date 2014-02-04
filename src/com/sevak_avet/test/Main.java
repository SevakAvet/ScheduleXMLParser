package com.sevak_avet.test;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sevak_avet.ScheduleParser.DOMParser;
import com.sevak_avet.ScheduleParser.ScheduleMarkup;

public class Main {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, OperationNotSupportedException {
		DOMParser dp = DOMParser.getInstance();
		URL scheduleURL = new URL("http://www.sgu.ru/schedule/knt/do/251/lesson");
		String scheduleFilePath = new File(".").getAbsolutePath() + "schedule.xls";
		String[][] table = dp.parse(scheduleURL, scheduleFilePath);	
		
		ScheduleMarkup vk = new ScheduleMarkup(table, 
				new Object[]{"Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1},
				new Object[]{"Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1, "Сентябрь", 1, 1, 1, 1});
		System.out.println(vk.getSchedule());
	}

}
