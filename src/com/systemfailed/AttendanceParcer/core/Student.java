/**
 * @author Rob Massina
 * @email Rmassina@albany.edu/Systemsfailed@gmail.com
 */

package com.systemfailed.AttendanceParcer.core;

public class Student 
{

	private int participation;
	private String name;
	
	public String getName()
	{
		return name;
	}
	
	
	private int getParticipations()
	{
		return participation;
	}
	
	public void participated()
	{
		participation++;
	}
	
	Student(String name)
	{
		this.name = name.toLowerCase();
	}
	
	Student(String name, int participation)
	{
		this.name = name.toLowerCase();
		this.participation = participation;
	}

	
	public String toString()
	{
		return name + " " + participation + "\n";
	}
	
}
