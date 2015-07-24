package iCal;

import java.io.*;
import java.util.Scanner;

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
						try
						{
							BufferedReader reader = new BufferedReader(new FileReader(input));
							String line = null;
						}
						catch(FileNotFoundException e)
						{
							System.out.println("Unable to open \"" + input + "\" , file not found");
						}
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
}