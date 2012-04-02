package com.clicker.admin.desktop.QuestionBuilder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.clicker.admin.desktop.CommunicationHub;

public class QuestionLaunchpad extends JFrame{
	
	private JDialog adhoc;
	private CommunicationHub hub;
	
	public QuestionLaunchpad(){
		super();
		this.setTitle("Question Launcher");
		setSize(600,600);
		JTabbedPane tabPane = new JTabbedPane();

		hub = CommunicationHub.getInstance();
		
		adhoc = new JDialog(this, "Build an adhoc question", true);
		adhoc.setSize(400,400);
		JLabel adhocText = new JLabel("Existing tools are loaded into tabs within this tab");
		adhoc.add(adhocText);
		
		JPanel stock = new JPanel();
        stock.setLayout(new GridLayout(12,1));
        JButton multipleChoiceOption = new JButton("Multiple Choice");
        multipleChoiceOption.addActionListener(new QuestionActionListener("Open`/;34`/;`/;B`/:A`/:0`/,B`/:B`/:0`/,B`/:C`/:0`/,B`/:D`/:0"));
        JButton trueFalseOption = new JButton("True/False");
        trueFalseOption.addActionListener(new QuestionActionListener("Open`/;40`/;`/;B`/:True`/:0`/,B`/:False`/:0"));
        JButton toggleOption = new JButton("Toggle");
        toggleOption.addActionListener(new QuestionActionListener("Open`/;35`/;`/;TOG`/:A`/:0`/,TOG`/:B`/:0`/,TOG`/:C`/:0`/,TOG`/:D`/:0"));
        JButton singleComboOption = new JButton("Single Combobox");
        singleComboOption.addActionListener(new QuestionActionListener("Open`/;37`/;A`/;COMBO`/:Combo 1`/:a`/~b`/~c`/~d`/:0"));
        JButton singleSliderOption = new JButton("Single Slider");
        singleSliderOption.addActionListener(new QuestionActionListener("Open`/;36`/;P`/;SLIDE`/:Slider 1`/:0`/:50`/:25"));
        JButton singleFreeText = new JButton("Single Free Text");
        singleFreeText.addActionListener(new QuestionActionListener("Open`/;38`/;`/;TEXTBOX`/:Enter text`/: "));
        JButton mnouseControlOption = new JButton("Mouse Control");
        mnouseControlOption.addActionListener(new QuestionActionListener("OpenClickPad`/;39`/;`/;TEXTBOX`/:Enter text`/: "));
        JButton adhocQuestionOption = new JButton("Open Adhoc question");
        adhocQuestionOption.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showAdhoc();				
			}});
		JButton closeQuestionOption = new JButton("Close Question");
		closeQuestionOption.addActionListener(new QuestionActionListener("Close"));
		JLabel stockText = new JLabel("Existing tools are loaded into tabs within this tab");
		//TODO new heading for the stock tab
		stock.add(stockText);
		stock.add(multipleChoiceOption);
		stock.add(trueFalseOption);
		stock.add(toggleOption);
		stock.add(singleComboOption);
		stock.add(singleSliderOption);
		stock.add(singleFreeText);
		stock.add(mnouseControlOption);
		stock.add(adhocQuestionOption);
		stock.add(closeQuestionOption);
		
	
		
		JPanel lecture = new JPanel();
		JLabel lectureText = new JLabel("Existing tools are loaded into tabs within this tab");
		lecture.add(lectureText);
		
		JPanel classroomManagement = new JPanel();
		JLabel classroomManagementText = new JLabel("Existing tools are loaded into tabs within this tab");
		classroomManagement.add(classroomManagementText);
		//*****************************
		tabPane.addTab("Stock Questions", stock);
		//tabPane.addTab("Ad hoc", adhoc);
		tabPane.addTab("Lecture Style Questions", lecture);
		//tabPane.addTab("Quiz Style", quiz);
		tabPane.addTab("Classroom Managment", classroomManagement);
		
		this.add("Center", tabPane);
		this.setVisible(true);
	}
	
	private void showAdhoc(){
		adhoc.setVisible(true);
	}
	
	/////Inner Class
	private class QuestionActionListener implements ActionListener{
		
		private String commandString;
		public QuestionActionListener(String s){
			commandString = s;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			hub.sendQuestion(commandString);
		}
		
	}
}
