/**
 * @author Rob Massina
 * @email Rmassina@albany.edu/Systemsfailed@gmail.com
 */

package com.systemfailed.AttendanceParcer.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
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
		try 
		{
			Scanner reader = new Scanner(inFile);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String line;
			String[]tmp;
			
			while(reader.hasNextLine())
			{
				line = reader.nextLine();
				if(line.contains(year + " by "))
				{
					tmp = line.split(year + " by ");
					group.addParticipation(tmp[1].replaceAll(" ", "").toLowerCase());
				}
			}
		} catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
