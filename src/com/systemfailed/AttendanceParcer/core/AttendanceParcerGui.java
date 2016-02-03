/**
 * @author Rob Massina
 * @email Rmassina@albany.edu/Systemsfailed@gmail.com
 * GUI implementation of Attendance Parser program. Provides an interface
 * to load a participation file, a blank student file and load a chat log 
 * file from todaysmeet.
 * 
 */

package com.systemfailed.AttendanceParcer.core;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;


public class AttendanceParcerGui {

	private JFrame frmAttendanceParcer;
	static Class group;
	private File logFile;
	private String fileName;
	private static boolean loadedClass, loadedLog;
	private JLabel lblClassStatus, lblLogStatus;
	private static JFileChooser fc;
	private JTextPane consolePane;
	private final Action loadStudentList = new LoadStudentList();
	private final Action loadParticipationFile = new LoadParticipationFile();
	private final Action loadLogFile = new loadLogFile();
	private final Action parseLog = new ParseLog();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AttendanceParcerGui window = new AttendanceParcerGui();
					window.frmAttendanceParcer.setVisible(true);
					loadedClass = loadedLog = false;
					group = new Class();
					fc = new JFileChooser();
					

				
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AttendanceParcerGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAttendanceParcer = new JFrame();
		frmAttendanceParcer.setTitle("Attendance Parcer");
		frmAttendanceParcer.setBounds(100, 100, 500, 500);
		frmAttendanceParcer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		consolePane = new JTextPane();
		consolePane.setEditable(false);
		
		JLabel lblClassFile = new JLabel("Class File :");
		 lblClassStatus = new JLabel("Not Loaded");
		JLabel lblLogFile = new JLabel("Log File :");
		 lblLogStatus = new JLabel("Not Loaded");
		JButton btnParse = new JButton("Parse");
		
		btnParse.setAction(parseLog);
		 
		GroupLayout groupLayout = new GroupLayout(frmAttendanceParcer.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(consolePane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblClassFile)
						.addComponent(lblLogFile))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblClassStatus)
						.addComponent(lblLogStatus))
					.addGap(171))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(205, Short.MAX_VALUE)
					.addComponent(btnParse)
					.addGap(200))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblClassStatus)
						.addComponent(lblClassFile))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLogStatus)
						.addComponent(lblLogFile))
					.addGap(18)
					.addComponent(btnParse)
					.addPreferredGap(ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
					.addComponent(consolePane, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE))
		);
		frmAttendanceParcer.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frmAttendanceParcer.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLoadStudents = new JMenuItem("Load Student List");
		mntmLoadStudents.setAction(loadStudentList);
		mnFile.add(mntmLoadStudents);
		
		JMenuItem mntmLoadParticipation = new JMenuItem("Load Participation File");
		mntmLoadParticipation.setAction(loadParticipationFile);
		mnFile.add(mntmLoadParticipation);
		
		JMenuItem mntmLoadLogFile = new JMenuItem("Load");
		mntmLoadLogFile.setAction(loadLogFile);
		mnFile.add(mntmLoadLogFile);
		
	}

	private class LoadStudentList extends AbstractAction {
		public LoadStudentList() {
			putValue(NAME, "Load_Student_List");
			putValue(SHORT_DESCRIPTION, "Loads a student list from a selected file");
		}
		public void actionPerformed(ActionEvent e) 
		{
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
	            File file = fc.getSelectedFile();
	            try
	            {
	            group.buildClassFromFile(file);
	            consolePane.setText(consolePane.getText() + "Student List Loaded\n");
	            lblClassStatus.setText("True");
	            loadedClass = true;
	            fileName = "Participation.txt";
	            }catch(Exception ex)
	            {
	            	consolePane.setText(consolePane.getText() + ex.getMessage() + "\n");
	            }
	        }
		}
	}
	private class LoadParticipationFile extends AbstractAction {
		public LoadParticipationFile() {
			putValue(NAME, "Load_Participation_File");
			putValue(SHORT_DESCRIPTION, "Loads a class from a given file");
		}
		public void actionPerformed(ActionEvent e) 
		{
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
	            File file = fc.getSelectedFile();
	            try
	            {
	            	group.buildClassFromFile(file);
	            	consolePane.setText(consolePane.getText() + "Class file loaded\n");
	            	lblClassStatus.setText("True");
		            loadedClass = true;
		            fileName = file.getName();
	            }catch(Exception ex)
	            {
	            	consolePane.setText(consolePane.getText()+ ex.getMessage() + "\n");
	            }
			}
		}
	}
	private class loadLogFile extends AbstractAction {
		public loadLogFile() {
			putValue(NAME, "Load Log File");
			putValue(SHORT_DESCRIPTION, "Loads a todaysmeet chat log into memory for parsing");
		}
		public void actionPerformed(ActionEvent e) 
		{
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
	            logFile = fc.getSelectedFile();
	            consolePane.setText(consolePane.getText() + "Loaded Log File\n");
	            lblLogStatus.setText("True");
	            loadedLog = true;

			}
		}
	}
	private class ParseLog extends AbstractAction {
		public ParseLog() {
			putValue(NAME, "Parse Log");
			putValue(SHORT_DESCRIPTION, "Parses the loaded log file into the attendance record");
		}
		public void actionPerformed(ActionEvent e) 
		{
			if(loadedLog & loadedClass)
			{
				try
				{
					InputParser.Parse(group, logFile);
					consolePane.setText(consolePane.getText() + "Log Parsed and added to record\n");
					try
					{
						group.writeFile(fileName);
						consolePane.setText(consolePane.getText() + "Participation log sucessfully written\n");
					}catch(Exception ex)
					{
						consolePane.setText(consolePane.getText() + "Error writing participation log\n" + 
					ex.getMessage() + "\n");
					}
				}catch(Exception ex)
				{
					consolePane.setText(consolePane.getText() + "Invalid Log File!\n" + ex.getMessage() + 
							"\n");
				}
				logFile = null;
				loadedLog = false;
				lblLogStatus.setText("False");
			}
			
			else
				consolePane.setText(consolePane.getText() + "Error, both a class file and a log need to be"
						+ " loaded before you can parse!\n");
		}
	}
}
