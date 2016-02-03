/**
 * @author Rob Massina
 * @email Rmassina@albany.edu/Systemsfailed@gmail.com
 * The class object contains two HashMap maps for storing the participaion scores of students
 * as well as names that don't appear in the class roster for manual sorting
 * 
 * Provides all of th emethods needed to create a roster and write the roster and partcipation points
 * to an external file
 */
package com.systemfailed.AttendanceParcer.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Class 
{
	private int numStudents, numUnknowns;
	private HashMap<String, Student> students, unknowns;
	
	Class()
	{
		students = new HashMap<String, Student>();
		unknowns = new HashMap<String, Student>();
		numStudents = numUnknowns = 0;
	}
	
	
	/**
	 * 
	 * @param name
	 * The name of the student to return
	 * @return
	 * Returns the student object for the given student name
	 * @throws Exception
	 * Exception will be thrown if the name given does not match any known student or unknown
	 */
	public Student getStudent(String name) throws Exception
	{
		if(students.containsKey(name.toLowerCase()))
			return students.get(name.toLowerCase());
		else if(unknowns.containsKey(name.toLowerCase()))
			return unknowns.get(name.toLowerCase());
		else
			throw new Exception("Student not found!");
	}
	
	/**
	 * @param s
	 * A new student object to be added to the class roster
	 */
	public void addStudent(Student s)
	{
		students.put(s.getName(), s);
		numStudents++;
	}
	
	
	/**
	 * 
	 * @return
	 * Returns an interator object for the student's map
	 */
	public Iterator getStudentIter()
	{
		return students.keySet().iterator();
	}
	
	/**
	 * 
	 * @return
	 * Returns an iterator for the unknowns map
	 */
	public Iterator getUnknownIter()
	{
		return unknowns.keySet().iterator();
	}
	
	/**
	 * 
	 * @param name
	 * The name of the student who's participation score is being
	 * incremented.
	 */
	public void addParticipation(String name)
	{
		//Checks if student exists if yes adds a participation mark
		if(students.containsKey(name))
			students.get(name).participated();
		
		else//If not checks if student is in unkowns table
		{
			if(unknowns.containsKey(name))
				unknowns.get(name).participated();
			else//If not a new student is added and given a mark
			{
				unknowns.put(name, new Student(name));
				unknowns.get(name).participated();
				numUnknowns++;
			}
		}
	}
	
	
	/**
	 * Builds the class object from an initial file containing students name and 
	 * student ID number
	 * 
	 * @param inFile
	 * Initial build file containing one student's name per line
	 */
	public void buildClassInitial(File inFile)throws Exception
	{
		Scanner reader;
		numStudents = 0;
		numUnknowns = 0;
		reader = new Scanner(inFile);
		String line;
		while(reader.hasNextLine())
		{
			line = reader.nextLine();
			Student s = new Student(line.replaceAll(" ", ""));
			students.put(s.getName(), s);
			numStudents++;
		}
	}
	
	/**
	 * 
	 * @param inFile
	 * A file generated from a previous running of this program. Contains students
	 * names and running participation score.
	 */
	public void buildClassFromFile(File inFile) throws Exception
	{
			Scanner reader = new Scanner(inFile);
			String tmp;//String to hold the line to check for "Unknowns"
			reader.nextLine();//Skips the "Students" line
			numStudents = 0;
			numUnknowns = 0;
			
			while(reader.hasNext())
			{
				tmp = reader.next();
				if(tmp.equals("Unknowns"))
					break;
				
				Student s = new Student(tmp, reader.nextInt());
				students.put(s.getName(), s);
				numStudents++;
			}
			
			if(reader.hasNextLine())
			{
				reader.nextLine();
				
				while(reader.hasNextLine())
				{
					Student s = new Student(reader.next(), reader.nextInt());
					unknowns.put(s.getName(), s);
					numUnknowns++;
				}
			}
		
	}
	
	/**
	 * Writes a file containing the name and participation scores of all
	 * students currently in the database.
	 */
	public void writeFile(String fileName)
	{
		try 
		{
			Iterator it = students.entrySet().iterator();
			
			FileWriter writer = new FileWriter(fileName);
			writer.write("Students\n");
			while(it.hasNext())
			{
				Map.Entry pair = (Map.Entry)it.next();
				writer.write(pair.getValue().toString());
			}
			
			writer.write("\n\nUnknowns\n");
			
			it = unknowns.entrySet().iterator();
			while(it.hasNext())
			{
				Map.Entry pair = (Map.Entry)it.next();
				writer.write(pair.getValue().toString());
			}
			
			writer.close();
			
		} catch (IOException e) 
		{
			System.out.printf("Error writing to file " + fileName + "\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a file containing only the names of all students in the database
	 * Provided to create a base file from a previous log.
	 */
	public void writeFileWithoutParticipation()
	{
		try 
		{			
			FileWriter writer = new FileWriter("Participation.txt");
			writer.write("Students\n");
			
			Iterator it = students.keySet().iterator();
			while(it.hasNext())
			{
				writer.write(it.next().toString() + "\n");
			}
			
			it = unknowns.keySet().iterator();
			while(it.hasNext())
			{
				writer.write(it.next().toString() + "\n");
			}
			
			writer.close();
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
}
