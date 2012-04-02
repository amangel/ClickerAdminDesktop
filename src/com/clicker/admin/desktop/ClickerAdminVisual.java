package com.clicker.admin.desktop;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.clicker.admin.desktop.QuestionBuilder.BuilderDisplay;
import com.clicker.admin.desktop.QuestionBuilder.QuestionLaunchpad;



public class ClickerAdminVisual {
    
    JTextField loginIPText;
    JFrame loginFrame;
    JFrame interactionFrame;
    private CommunicationHub hub;
    private ClassModel classModel;
    private JDialog existingOrBuilderDialog;
    
    public ClickerAdminVisual() {
        classModel = ClassModel.getInstance();
        hub = CommunicationHub.getInstance();
        buildLoginFrame();
//        buildInteractionFrame();
        buildExistingOrBuilderDialog();
    }
    
    private void buildInteractionFrame() {
        interactionFrame = new JFrame();
        interactionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        interactionFrame.setSize(600,400);
        interactionFrame.setLayout(new GridLayout(5,1));

        //open a mc question
        JButton mcButton = new JButton("Multiple choice question");
        mcButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                hub.sendQuestion("Open`/;34`/;`/;B`/:A`/:0`/,B`/:B`/:0`/,B`/:C`/:0`/,B`/:D`/:0");
            }});
        
        //close a mc question
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                hub.sendMessage("ClientCommand`/`Close`/;Everyone`/,");
            }});
        
        //get question sets
        
        //get class list
        
        //get groups
        JButton groupsButton = new JButton("Get Groups");
        groupsButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                hub.sendMessage("GetClientList`/`");
            }});
        
        interactionFrame.add(mcButton);
        interactionFrame.add(closeButton);
        interactionFrame.add(groupsButton);
    }

    private void buildLoginFrame() {
        loginFrame = new JFrame();
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400,400);
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3,1));
        JPanel loginIPPanel = new JPanel();
        JPanel loginPortPanel = new JPanel();
        GridLayout oneByTwoGridLayout = new GridLayout(1,2);
        loginIPPanel.setLayout(oneByTwoGridLayout);
        loginPortPanel.setLayout(oneByTwoGridLayout);
        
        JLabel loginIPLabel = new JLabel("Login IP: ");
        // JLabel loginPortLabel = new JLabel("Login port: ");
        loginIPText = new JTextField();
        //  JTextField loginPortText = new JTextField();
        
        loginIPText.setText("134.161.43.255");
        //   loginPortText.setText("7700");
        
        loginIPPanel.add(loginIPLabel);
        loginIPPanel.add(loginIPText);
        
        //    loginPortPanel.add(loginPortLabel);
        //    loginPortPanel.add(loginPortText);
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                doLogin(loginIPText.getText() );
            }});
        
        loginPanel.add(loginIPPanel);
        loginPanel.add(loginPortPanel);
        loginPanel.add(loginButton);
        //loginPanel.add(loadConsumersButton);
        
        //loadConsumersFromSubdirectory();
        
        loginFrame.add(loginPanel);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    private void buildExistingOrBuilderDialog() {
        existingOrBuilderDialog = new JDialog();
        existingOrBuilderDialog.setSize(300,200);
        existingOrBuilderDialog.setLayout(new GridLayout(2,1));
        JButton useExisting = new JButton("Use Existing Questions");
        useExisting.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                startQuestionLaunchpad();
            }});
        JButton useBuilder = new JButton("Launch Builder");
        useBuilder.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                startBuilder();
            }});
        existingOrBuilderDialog.add(useExisting);
        existingOrBuilderDialog.add(useBuilder);
        existingOrBuilderDialog.setVisible(false);
    
        
    }

    protected void startBuilder() {
        BuilderDisplay builder = new BuilderDisplay();
        builder.setVisible(true);
    }

    protected void startQuestionLaunchpad() {
        QuestionLaunchpad launchpad = new QuestionLaunchpad();
        launchpad.setVisible(true);
    }

    protected void doLogin(String text) {
        int adminPort = 7700; 
        hub.setIp(text); 
        loginFrame.setVisible(false);
        //interactionFrame.setVisible(true);
        existingOrBuilderDialog.setVisible(true);
    }
    
    
}
