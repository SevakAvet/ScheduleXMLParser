import java.util.ArrayList;
import java.util.List;

public class VKWikiMarkup {
	private static final String date =
			  "|%s || %d - %d || %d - %d\n"
			+ "|-\n"
			+ "|%s || %d - %d || %d - %d\n"
			+ "|-\n"
			+ "|%s || %d - %d || %d - %d\n"
			+ "|-\n"
			+ "|%s || %d - %d || %d - %d\n";
	
	private static final String line = " || %s || %s || %s || %s || %s || %s \n";
	
	private static final String table = 
			"|День/Время || пн || вт || ср || чт || пт || суб\n"
			+ "|-\n"
			+ "|8.20 - 9.50" + line
			+ "|-\n"
			+ "|10.00 - 11.35" + line
			+ "|-\n"
			+ "|12.05 - 13.40" + line
			+ "|-\n"
			+ "|13.50 - 15.25" + line
			+ "|-\n"
			+ "|15.35 - 17.10" + line
			+ "|-\n"
			+ "|17.20 - 18.40" + line
			+ "|-\n"
			+ "|18.45 - 20:10" + line
			+ "|-\n"
			+ "|20:10 - 21:30" + line;
	
	private static final String schedule =
			"ЧИСЛИТЕЛЬ\n"
			+ "{|\n"
			+ "|-\n"
			+ date
			+ "|}\n"
			+ "{|\n"
			+ "|-\n"
			+ table
			+ "|}\n"
			+ "ЗНАМЕНАТЕЛЬ\n"
			+ "{|\n"
			+ "|-\n"
			+ date
			+ "|}\n"
			+ "{|\n"
			+ "|-\n"
			+ table
			+ "|}\n";
	
	public static void printSchedule() {
		System.out.println(schedule);
	}
	
	public static void printTable(String[][] table) {
		String[] result = new String[48];
		int index = 0;
		
		for(String[] rows : table) {
			for(String cell : rows) {
				result[index++] = cell;
			}
		}
		
		System.out.println(String.format(VKWikiMarkup.table, result));
	}
}