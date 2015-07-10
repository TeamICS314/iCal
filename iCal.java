package iCal;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class iCal
{
	public static final String FILE_NAME = "calendar.ics";
	public static final String NL = "\r\n";
	public static final String ICAL_BEGIN = "BEGIN:VCALENDAR" + NL;
	public static final String ICAL_END = "END:VCALENDAR" + NL;
	public static final String ICAL_VERSION = "VERSION:2.0" + NL;
	public static final String EVENT_BEGIN = "BEGIN:VEVENT" + NL;
	public static final String EVENT_END = "END:VEVENT" + NL;
	public static final String TZ = "Pacific/Honolulu";
	public static final String TZID = "TZID:" + TZ + NL;
	public static String Event_DTStart = null;
	public static String Event_DTEnd = null;
	public static String Event_Summary = null;
	public static String Event_Classification = null;
	public static String Event_Priority = null;
	public static String Event_GeoLocation = null;
	
	public static void main(String args[])
	{
		try
		{
			//Create a file with FILE_NAME and check if it already exist
			File file = new File(FILE_NAME);
			if(file.createNewFile())
			{
				System.out.println(FILE_NAME + " created succesfully!");
			}
			else
			{
				System.out.println("ERROR: file with name: " + FILE_NAME + " already exist.");
			}
			
			//get all field for event
			Scanner sc = new Scanner(System.in);
			Event_Summary = getEventSummary(sc) + NL;
			Event_DTStart = getDTStart(sc) + NL;
			Event_DTEnd = getDTEnd(sc) + NL;
			Event_Classification = getClassification(sc) + NL;
			Event_Priority = getPriority(sc) + NL;
			Event_GeoLocation = getGeoLocation(sc) + NL;
			sc.close();
			
			//Create a buffered writer to write to the file
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(ICAL_BEGIN);
			writer.write(ICAL_VERSION);
			writer.write(EVENT_BEGIN);
			writer.write(TZID);
			writer.write(Event_DTStart);
			writer.write(Event_DTEnd);
			writer.write(Event_Classification);
			writer.write(Event_Summary);
			writer.write(Event_Priority);
			writer.write(Event_GeoLocation);
			writer.write(EVENT_END);
			writer.write(ICAL_END);
			writer.close();
			
			System.out.println("DONE!!!!!!!");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static String getDTStart(Scanner sc)
	{
		System.out.println("Please input the event start time in the following format: ");
		System.out.println("year,month,day,hour,minute     e.g. 2015,03,10,23,09 means 2015/03/10 11:09 pm");
		String time = sc.nextLine();
		String[] parts = time.split(",");
		String DTStart = "DTSTART;TZID=\"" + TZ + "\":" + parts[0] + parts[1] + parts[2] + "T" + parts[3] + parts[4] + "00"; 
		return DTStart;
	}
	
	public static String getDTEnd(Scanner sc)
	{
		System.out.println("Please input the event end time in the following format: ");
		System.out.println("year,month,day,hour,minute     e.g. 2015,03,10,23,09 means 2015/03/10 11:09 pm");
		String time = sc.nextLine();
		String[] parts = time.split(",");
		String DTEnd = "DTEND;TZID=\"" + TZ + "\":" + parts[0] + parts[1] + parts[2] + "T" + parts[3] + parts[4] + "00"; 
		return DTEnd;
	}
	
	public static String getEventSummary(Scanner sc)
	{
		System.out.println("Please input the event summary: ");
		String summary = "SUMMARY:" + sc.nextLine();
		return summary;
	}
	
	public static String getClassification(Scanner sc)
	{
		System.out.println("Please input the event classification: PUBLIC, PRIVATE or CONFIDENTIAL");
		String classification = "CLASS:" + sc.nextLine();
		return classification;
	}
	
	public static String getPriority(Scanner sc)
	{
		System.out.println("Please input the event priority: Number from 0-9");
		String priority = "PRIORITY:" + sc.nextLine();
		return priority;
	}
	
	public static String getGeoLocation(Scanner sc)
	{
		System.out.println("Please input the event geographic location in Latitude and Longitude: ");
		System.out.println("Latitude(Max 6 decimal): e.g. 37.386013");
		float latitude = sc.nextFloat();
		sc.nextLine();
		System.out.println("Longitude(Max 6 decimal): e.g. -122.082932");
		float longitude = sc.nextFloat();
		sc.nextLine();
		
		DecimalFormat df = new DecimalFormat(".######");
		df.setRoundingMode(RoundingMode.UP);
		String lat = df.format(latitude);
		String lon = df.format(longitude);
		
		String geoLocation = "GEO:" + lat + ";" + lon;
		return geoLocation;
	}
}