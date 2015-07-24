package iCal;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class ICalEvent 
{	
	public final String NL = "\r\n";
	public final String EVENT_BEGIN = "BEGIN:VEVENT" + NL;
	public final String EVENT_END = "END:VEVENT" + NL;
	public final String TZ = "Pacific/Honolulu";
	public final String TZID = "TZID:" + TZ + NL;
	public String Event_DTStart = null;
	public String Event_DTEnd = null;
	public String Event_Summary = null;
	public String Event_Classification = null;
	public String Event_Priority = null;
	public String Event_GeoLocation = null;
	
	public String toString()
	{
		String wholeEvent = EVENT_BEGIN + TZID + Event_DTStart + Event_DTEnd + Event_Classification
				+ Event_Summary + Event_Priority + Event_GeoLocation + EVENT_END;
		return wholeEvent;
	}
	
	public void getFields()
	{
		Scanner sc = new Scanner(System.in);
		Event_Summary = getEventSummary(sc);
		Event_DTStart = getDTStart(sc);
		Event_DTEnd = getDTEnd(sc);
		Event_Classification = getClassification(sc);
		Event_Priority = getPriority(sc);
		Event_GeoLocation = getGeoLocation(sc);
	}
	
	private String getDTStart(Scanner sc)
	{
		System.out.println("Please input the event start time in the following format: ");
		System.out.println("year,month,day,hour,minute     e.g. 2015,03,10,23,09 means 2015/03/10 11:09 pm");
		String time = sc.nextLine();
		String[] parts = time.split(",");
		String DTStart = "DTSTART;TZID=\"" + TZ + "\":" + parts[0] + parts[1] + parts[2] + "T" + parts[3] + parts[4] + "00"; 
		return DTStart + NL;
	}
	
	private String getDTEnd(Scanner sc)
	{
		System.out.println("Please input the event end time in the following format: ");
		System.out.println("year,month,day,hour,minute     e.g. 2015,03,10,23,09 means 2015/03/10 11:09 pm");
		String time = sc.nextLine();
		String[] parts = time.split(",");
		String DTEnd = "DTEND;TZID=\"" + TZ + "\":" + parts[0] + parts[1] + parts[2] + "T" + parts[3] + parts[4] + "00"; 
		return DTEnd + NL;
	}
	
	private String getEventSummary(Scanner sc)
	{
		System.out.println("Please input the event summary: ");
		String summary = "SUMMARY:" + sc.nextLine();
		return summary + NL;
	}
	
	private String getClassification(Scanner sc)
	{
		System.out.println("Please input the event classification: PUBLIC, PRIVATE or CONFIDENTIAL");
		String classification = "CLASS:" + sc.nextLine();
		return classification + NL;
	}
	
	private String getPriority(Scanner sc)
	{
		System.out.println("Please input the event priority: Number from 0-9");
		String priority = "PRIORITY:" + sc.nextLine();
		return priority + NL;
	}
	
	private String getGeoLocation(Scanner sc)
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
		return geoLocation + NL;
	}
}
