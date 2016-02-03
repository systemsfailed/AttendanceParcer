/**
 * @author Rob Massina
 * @email Rmassina@albany.edu/Systemsfailed@gmail.com
 * Provides a static method that takes in a chat log and a Class object as arguments,
 * and populates the class object with participation points based on the usernames of the
 * students that posted in the given chatlog
 */

package com.systemfailed.AttendanceParcer.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

public class InputParser 
{

	/**
	 * 
	 * @param group
	 * A class object to write the participation marks to
	 * @param inFile
	 * The log file of the Today'sMeet session in text format
	 */
	public static void Parse(Class group, File inFile)
	{
		HashMap<String, Student> inAttendance = new HashMap<String, Student>();
		ArrayList<String> list = new ArrayList<String>();

		try 
		{
			Scanner reader = new Scanner(inFile);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String line;//Holding string to parse and search each line
			String[]tmp;//Holding array for line splits to extract username
			
			while(reader.hasNextLine())
			{
				line = reader.nextLine();
				if(line.contains(year + " by "))//Chesk for indication of a username
				{
					tmp = line.split(year + " by ");//Splits the string into by and username
					if(!list.contains(tmp[1].replaceAll(" ", "").toLowerCase()))//Checks if the user was already counted for attendance
						list.add(tmp[1].replaceAll(" ", "").toLowerCase());
				}
			}
			
			for(String s : list)//Iterates the list of those in attendance and gives participation to them
			{
				group.addParticipation(s);
			}
		} catch (FileNotFoundException e) 
		  {
			System.out.printf("%s Error in opening Log file!");
			e.printStackTrace();
		}
		
		
		
	}
	
}
