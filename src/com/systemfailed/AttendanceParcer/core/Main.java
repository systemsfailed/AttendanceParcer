/**
 * @author Rob Massina
 * @email Rmassina@albany.edu/Systemsfailed@gmail.com
 * Command line execution method of the AttendanceParcer program
 * Provides commands to create a new log file from a base student file, as wel
 * as the needed commands to parse log files into pre-existing participation files.
 * 
 */

package com.systemfailed.AttendanceParcer.core;

import java.io.File;

public class Main 
{
	public static void main(String[] args)
	{
		if(args[0].toLowerCase().equals("-h"))
			System.out.printf("This program will parse a today'smeet chat log"
					+" and take attendance based on which usernames participated in"
					+" the chat.\nThe following commands can be used with this program\n"
					+ "Commands:\n-b filename outfilename Will create a new attendance record from an"
					+ "input file containing students names one per line, spaces are allowed\n"
					+ "-p filename logfilename Will take an existing attendance record, filename,"
					+ " and parse a new chat log into it, adding attendance to those students who"
					+ " participated in the new chat log. It will then overwrite the given chat log"
					+ " with the new totals");
		
		else if(args[0].toLowerCase().equals("-b"))
		{ 
			if(args.length < 3)
			{
				System.out.printf("Error not enough arguments for build, proper command syntax is"
						+ "-b filename outfilename");
				System.exit(1);
			}
			System.out.printf("Creating new class from " + args[1] +"\n");
			Class group = new Class();
			try
			{
			group.buildClassInitial(new File(args[1]));
			}catch(Exception e)
			{
				System.out.printf("Error opening log file with username " + args[1] + "\n");
				e.printStackTrace();
			}
			System.out.printf("Attendance record created, please input filename for output: ");
			
			group.writeFile(args[2]);
			System.out.printf("New record sucessfully written\n");
		}
		
		else if(args[0].toLowerCase().equals("-p"))
		{
			if(args.length < 3)
			{
				System.out.printf("Error not enough arguments for build, proper command syntax is"
						+ "-p recordfilename logfilename");
				System.exit(1);
			}
			
			System.out.printf("Opening record file from " + args[1] + "...\n");
			Class group = new Class();
			try
			{
			group.buildClassFromFile(new File(args[1]));
			InputParser.Parse(group, new File(args[2]));
			System.out.printf("Sucessfully parsed input file, writing new record file...\n");
			group.writeFile(args[1]);
			System.out.printf("Sucessfully wrote new record file...\n");
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		else
		{
			System.out.printf("Invalid  arguments, use -h for help text\n");
		}
	}
}
