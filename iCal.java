package iCal;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ICal
{
	public static final String NL = "\r\n";
	public static final String ICAL_BEGIN = "BEGIN:VCALENDAR" + NL;
	public static final String ICAL_END = "END:VCALENDAR" + NL;
	public static final String ICAL_VERSION = "VERSION:2.0" + NL;
	
	public static void main(String args[])
	{
		try
		{
			//Ask if user want to create a .ics file or read in files to compute GCD(great circle distance)
			System.out.println("Menu: ");
			System.out.println("1. Create a new .ics file");
			System.out.println("2. Read in .ics files and compute GCD(greate circle distance)");
			Scanner sc = new Scanner(System.in);
			int userInput = sc.nextInt();
			sc.nextLine();
			if(userInput == 1)
			{
				boolean done = false;
				int fileCount = 0;
				while(!done)
				{
					//Ask user to give a name for the .ics file
					System.out.println("Please input the name of the file to be created. e.g. event1.ics");
					System.out.println("If finished, type \"done\"");
					String fileName = sc.nextLine();
					if(fileName.equals("done"))
					{
						done = true;
					}
					else if(fileName.endsWith(".ics"))
					{
						//Ask user to give a new file name and check if it already exist
						File file = new File(fileName);
						if(file.createNewFile())
						{
							System.out.println(fileName + " created succesfully!");

							//create an event, call getFields() to fill in the required fields
							ICalEvent event = new ICalEvent();
							event.getFields();
							
							//Create a buffered writer to write to the file
							BufferedWriter writer = new BufferedWriter(new FileWriter(file));
							writer.write(ICAL_BEGIN);
							writer.write(event.toString());
							writer.write(ICAL_END);
							writer.close();
							
							System.out.println("Operation Sucessful..............");
							fileCount++;
						}
						else
						{
							System.out.println("ERROR: file with name: " + fileName + " already exist.");
						}
					}
					else
					{
						System.out.println("ERROR: file name has to end with .ics");
					}
				}
				System.out.println(fileCount + " event files are successfully created.");
			}
			else if(userInput == 2)
			{
				int fileCount = 0;
				boolean done = false;
				ArrayList<Event> eventList = new ArrayList<Event>();
				while(!done)
				{
					System.out.println("Please input the name of the file to read from, e.g. calender.ics");
					System.out.println("If finished, type \"done\"");
					String input = sc.nextLine();
					if(input.equals("done"))
					{
						done = true;
						System.out.println("Successfully read in " + fileCount + " files");
					}
					else
					{
						fileCount++;
						try
						{
							BufferedReader reader = new BufferedReader(new FileReader(input));
							boolean hasGeo = false;
							float longitude = 0;
							float latitude = 0;
							String line = null;
							int startHour = -1;
							int startMinute = -1;
							int endHour = -1;
							int endMinute = -1;
							while((line = reader.readLine()) != null)
							{
								if(line.startsWith("DTSTART"))
								{
									Pattern p = Pattern.compile("T\\d\\d\\d\\d\\d\\d");
									Matcher m = p.matcher(line);
									if(m.find())
									{
										String sh = m.group().substring(1, 3);
										String sm = m.group().substring(3, 5);
										startHour = Integer.parseInt(sh);
										startMinute = Integer.parseInt(sm);
									}
								}
								else if(line.startsWith("DTEND"))
								{
									Pattern p = Pattern.compile("T\\d\\d\\d\\d\\d\\d");
									Matcher m = p.matcher(line);
									if(m.find())
									{
										String eh = m.group().substring(1, 3);
										String em = m.group().substring(3, 5);
										endHour = Integer.parseInt(eh);
										endMinute = Integer.parseInt(em);
									}
								}
								else if(line.startsWith("GEO"))
								{
									String subString = line.substring(4);
									String[] parts = subString.split(";");
									latitude = Float.parseFloat(parts[0]);
									longitude = Float.parseFloat(parts[1]);
									hasGeo = true;
								}
							}
							Event event = new Event();
							event.startHour = startHour;
							event.startMinute = startMinute;
							event.endHour = endHour;
							event.endMinute = endMinute;
							if(hasGeo)
							{
								event.containGeoLocation = true;
								event.latitude = latitude;
								event.longitude = longitude;
							}
							eventList.add(event);
							reader.close();
						}
						catch(FileNotFoundException e)
						{
							System.out.println("Unable to open \"" + input + "\" , file not found");
						}
					}
				}
				//after user are done reading, use bubble sort to sort the array list
				bubbleSort(eventList);
				
				
				for(int i = 0; i < eventList.size(); i++)
				{
					Event e = eventList.get(i);
					String parts[] = null;
					if(i < eventList.size() - 1)
					{
						Event ePlusOne = eventList.get(i + 1);
						String distance = getDistance(e.longitude, e.latitude, ePlusOne.longitude, ePlusOne.latitude);
						parts = distance.split(";");
					}
					String sh = String.format("%02d", e.startHour);
					String sm = String.format("%02d", e.startMinute);
					String eh = String.format("%02d", e.endHour);
					String em = String.format("%02d", e.endMinute);
					System.out.println("Event " + i + ": " + sh + ":" + sm + "-" + eh + ":" + em);
					if(i < eventList.size() - 1)
					{
						DecimalFormat df = new DecimalFormat("0.00##");
						String miles = df.format(Double.parseDouble(parts[0]));
						String kilometers = df.format(Double.parseDouble(parts[1]));
						System.out.println("Distance to next event: " + miles + " Miles, or " + kilometers + " Kilometers.\n");
					}
				}
			}
			else
			{
				System.out.println("ERROR: please input number 1 or 2!");
			}
			sc.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void bubbleSort(ArrayList<Event> eventList)
	{
		int i;
		boolean flag = true;
		Event temp; //holding variable
		Event temp2;//holding variable
		
		while(flag)
		{
			flag = false;
			for(i = 0; i < eventList.size() - 1; i++)
			{
				if(eventList.get(i).startHour > eventList.get(i + 1).startHour)
				{
					temp = eventList.get(i);
					temp2 = eventList.get(i + 1);
					eventList.set(i, temp2); 
					eventList.set(i + 1, temp);
					flag = true;
				}
				else if(eventList.get(i).startHour == eventList.get(i + 1).startHour)
				{
					if(eventList.get(i).startMinute > eventList.get(i + 1).startMinute)
					{
						temp = eventList.get(i);
						temp2 = eventList.get(i + 1);
						eventList.set(i, temp2); 
						eventList.set(i + 1, temp);
						flag = true;
					}
				}
			}
		}
	}
	
	public static String getDistance(double longitude1, double latitude1, double longitude2, double latitude2)
	{
		double d, haverSin, firstSin, secondSin, miles, kilometers;
		longitude1 = Math.toRadians(longitude1);
		latitude1 = Math.toRadians(latitude1);
		longitude2 = Math.toRadians(longitude2);
		latitude2 = Math.toRadians(latitude2);
		firstSin = Math.sin((latitude2 - latitude1) / 2);
		secondSin = Math.sin((longitude2 - longitude1) / 2);
		haverSin = (Math.pow(firstSin, 2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(secondSin, 2));
		haverSin = 2 * Math.asin(Math.min(1,Math.sqrt(haverSin)));
		d = Math.toDegrees(haverSin);
		miles = d * 69.0468;
		kilometers = miles * 1.60934;
		String result = String.valueOf(miles) + ";" + String.valueOf(kilometers);
		return result;
	}
}