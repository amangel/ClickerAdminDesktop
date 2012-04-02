package com.clicker.admin.desktop.QuestionBuilder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListModel;


public class BuilderDisplay extends JFrame{
	//private JPanel frame;
	private JPanel builderBody;
	private JPanel loaderBody;
	private JPanel panel;
	//private JPanel activeBuildingPanel;
	private JPanel buildingPanel;
	private JPanel previewPanel;
	private JLabel questionStringLabel;
	//private QuestionBuilder builder;
	//private ArrayList<JPanel> panels;
	
	private ArrayList<JComponent> widgetList;
	
	private JPanel labelPanel;
	private JLabel widgetLabel;
	private JTextField widgetLabelText;
	
	private JPanel minValuePanel;
	private JLabel widgetMinValueLabel;
	private JTextField widgetMinValueText;	
	
	private JPanel maxValuePanel;
	private JLabel widgetMaxValueLabel;
	private JTextField widgetMaxValueText;
	
	private JPanel defaultValuePanel;
	private JLabel widgetDefaultValueLabel;
	private JTextField widgetDefaultValueText;
	private JCheckBox toggleCheckBox;
	
	//private JPanel addSaveDeleteButtonPanel;
	private JButton widgetSaveButton;
	private JButton widgetAddButton;
	private JButton widgetDeleteButton;
	
	private boolean newWidget;
	
	private PreviewPanelAdapter previewAdapter;
	private int currentActiveWidgetNumber;
	private JComponent currentWidget;
	private boolean editingExisting;
	
	private String currentQuestionString;
	//private JLabel questionLabel;
	
	private ActionListener builtButtonListener;
	
	public BuilderDisplay(){
		editingExisting = false;
		widgetList = new ArrayList<JComponent>(10);
		currentQuestionString = "";
		newWidget = false;
		panel = new JPanel();
		builderBody = new JPanel();
		loaderBody = new JPanel();
		//activeBuildingPanel = new JPanel();
		buildingPanel = new JPanel();
		previewPanel = new JPanel();
		previewAdapter = new PreviewPanelAdapter();
		
		JPanel contentPanel = new JPanel();
		
		previewPanel.setLayout(new GridLayout(8,1));
		previewPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		builderBody.setSize(600,300);
		builderBody.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Loader setup
		loaderBody.setSize(600,300);
		loaderBody.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel loaderPanel = new JPanel();
		loaderPanel.setSize(600,300);
		loaderPanel.setLayout(new GridLayout(2,1));
		JPanel leftLoaderPanel = new JPanel();
		JPanel rightLoaderPanel = new JPanel();
		rightLoaderPanel.setSize(300,300);
		leftLoaderPanel.setLayout(new GridLayout(4,1));
		leftLoaderPanel.setSize(300,300);
		JButton loaderAddFollowupButton = new JButton("Add Followup Question");
		JButton loaderSaveQuestionSetButton = new JButton("Save Question Set");
		JButton loaderLoadQuestionSetButton = new JButton("Load Question Set");
		JButton loaderNewQuestionSetButton = new JButton("New Question Set");
		leftLoaderPanel.add(loaderAddFollowupButton);
		leftLoaderPanel.add(loaderSaveQuestionSetButton);
		leftLoaderPanel.add(loaderLoadQuestionSetButton);
		leftLoaderPanel.add(loaderNewQuestionSetButton);
		rightLoaderPanel.add("Center", new JLabel("this is a placeholder"));
		loaderBody.add(leftLoaderPanel);
		loaderBody.add(rightLoaderPanel);
		//loader setup finished
		
		
		this.setSize(600,600);
		this.setLayout(new GridLayout(2,1));
		this.add(builderBody);
		this.add(loaderBody);

		GridLayout twoColumnGrid = new GridLayout(1,2);
		
		labelPanel = new JPanel();
		labelPanel.setLayout(twoColumnGrid);
		widgetLabel = new JLabel("Label:");
		widgetLabelText = new JTextField();
		labelPanel.add(widgetLabel);
		labelPanel.add(widgetLabelText);
		
		minValuePanel = new JPanel();
		minValuePanel.setLayout(twoColumnGrid);
		widgetMinValueLabel = new JLabel("Minimum value:");
		widgetMinValueText = new JTextField();
		minValuePanel.add(widgetMinValueLabel);
		minValuePanel.add(widgetMinValueText);
		
		maxValuePanel = new JPanel();
		maxValuePanel.setLayout(twoColumnGrid);
		widgetMaxValueLabel = new JLabel("Maximum value:");
		widgetMaxValueText = new JTextField();
		maxValuePanel.add(widgetMaxValueLabel);
		maxValuePanel.add(widgetMaxValueText);
		
		defaultValuePanel = new JPanel();
		defaultValuePanel.setLayout(twoColumnGrid);
		widgetDefaultValueLabel = new JLabel("Default Value:");
		widgetDefaultValueText =new JTextField();
		defaultValuePanel.add(widgetDefaultValueLabel);
		defaultValuePanel.add(widgetDefaultValueText);
		
		widgetAddButton = new JButton("Add widget");
		widgetSaveButton = new JButton("Save widget");
		widgetDeleteButton = new JButton("Delete widget");
		
		buildingPanel.setLayout(new GridLayout(7,1));
		buildingPanel.add(labelPanel);
		buildingPanel.add(minValuePanel);
		buildingPanel.add(maxValuePanel);
		buildingPanel.add(defaultValuePanel);
		//buildingPanel.add(widgetLabelText);
		buildingPanel.add(widgetAddButton);
		
		/////////ADD BUTTON/////////////////////////////////////////////////////
		widgetAddButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				previewPanel.setVisible(false);

				if(currentWidget instanceof JButton){
					JButton buttonTemp = new JButton();
					buttonTemp = (JButton) currentWidget;
					widgetList.add(buttonTemp);
					disableComponentAndAddMouseListener(buttonTemp);
					buttonTemp.setText(widgetLabelText.getText());
				} else if(currentWidget instanceof SliderWrapper){
					SliderWrapper sliderTemp = new SliderWrapper();
					sliderTemp = (SliderWrapper) currentWidget;
					widgetList.add(sliderTemp);
					disableComponentAndAddMouseListener(sliderTemp);
					//sliderTemp.setText(widgetLabelText.getText());
					sliderTemp.setMaximum(Integer.parseInt(widgetMaxValueText.getText()));
					sliderTemp.setMinimum(Integer.parseInt(widgetMinValueText.getText()));
					sliderTemp.setValue(Integer.parseInt(widgetDefaultValueText.getText()));
				} else if(currentWidget instanceof JToggleButton){
					JToggleButton buttonTemp = new JToggleButton();
					buttonTemp = (JToggleButton) currentWidget;
					widgetList.add(buttonTemp);
					disableComponentAndAddMouseListener(buttonTemp);
					buttonTemp.setText(widgetLabelText.getText());
					buttonTemp.setSelected(widgetDefaultValueText.equals("1"));
				}
				
				

				generatePreviewPanel();
				buildingPanel.setVisible(false);	
			}});
		
		
		//SAVE BUTTON*******************************************************
		widgetSaveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(currentWidget instanceof JButton){
					System.out.println("is button in save");
					JButton temp = (JButton)currentWidget;
					temp.setText(widgetLabelText.getText());
					disableComponentAndAddMouseListener(temp);
					widgetList.set(currentActiveWidgetNumber, temp);
				} else if(currentWidget instanceof SliderWrapper){
					System.out.println("is slider in save");
					SliderWrapper temp = (SliderWrapper)currentWidget;
					temp.setWidgetLabel(widgetLabelText.getText());
					temp.setMaxWidgetValue(widgetMaxValueText.getText());
					temp.setMinWidgetValue(widgetMinValueText.getText());
					temp.setWidgetDefaultValue(widgetDefaultValueText.getText());
					
					disableComponentAndAddMouseListener(temp);
					widgetList.set(currentActiveWidgetNumber, temp);
				} else if(currentWidget instanceof JToggleButton){
					System.out.println("is toggle in save");
					JToggleButton temp = (JToggleButton)currentWidget;
					temp.setText(widgetLabelText.getText());
					temp.setSelected(widgetDefaultValueText.getText().equals("1"));
					
					disableComponentAndAddMouseListener(temp);
					widgetList.set(currentActiveWidgetNumber, temp);
				}
				generatePreviewPanel();
			}});
		
		//DELETE BUTTON============================================================
		widgetDeleteButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				widgetList.remove(currentActiveWidgetNumber);
				generatePreviewPanel();
				clearBuilderObjects();
			}
			
		});
		
		JList list = new JList(new AbstractListModel() {
		
		      String[] widgets = {"Button", "Toggle Button", "Jeopardy Button", "Slider", "Textbox", "Text Question", "QR Text", "Combo Box", "TextView with Button", "TextView"};
		      @Override
		      public int getSize() {
		      return widgets.length;
		      }

		      @Override
		      public Object getElementAt(int index) {
		      return widgets[index];
		      }
		      });
		
		list.addMouseListener(new ActionJList(list));
		panel.add(list);
		builderBody.add("Center", contentPanel);
		
		contentPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		GridLayout gl = new GridLayout(1,3);
		
		contentPanel.setLayout(new GridLayout(1,3));
		contentPanel.setSize(600,300);
		contentPanel.add( panel);
		contentPanel.add( buildingPanel);
		contentPanel.add(previewPanel);

		buildingPanel.setVisible(false);
		this.setVisible(true);
	}
	
	private void disableComponentAndAddMouseListener(JComponent comp){
		comp.setEnabled(false);
		comp.addMouseListener(previewAdapter);
	}
	
	private void generatePreviewPanel(){
		previewPanel.setVisible(false);
		previewPanel.removeAll();
		for(JComponent component : widgetList){
			previewPanel.add(component);
		}
		previewPanel.setVisible(true);
	}

	private boolean isNewWidget(){
		if(newWidget){
			addingNewWidget();
		}
		return newWidget;
	}
	
	private void showBuildingPanel(){
		buildingPanel.setVisible(true);
	}
	
	////////////////////////////////Widget setup
	private void setupWidget(){
		
	}
	
	private void setupButton(){
		if(isNewWidget()){
			JButton tempJButton = new JButton();
			currentWidget = tempJButton;
		}
		buildingPanelAddLabelAndDefault();
		JButton temp = (JButton)currentWidget;
		widgetLabelText.setText(temp.getText());
	}
	
	private void setupToggleButton(){
		if(isNewWidget()){
			JToggleButton temp = new JToggleButton();
			currentWidget = temp;
		}
		buildingPanelAddLabelAndDefault();
		JToggleButton temp = (JToggleButton)currentWidget;
		widgetLabelText.setText(temp.getText());
		String str="";
		if(temp.isSelected()){
			str = "1";
		} else {
			str = "0";
		}
		widgetDefaultValueText.setText(str);

	}
	
	private void setupSlider(){
		SliderWrapper temp = null;
		if(isNewWidget()){
			temp = new SliderWrapper();
			currentWidget = temp;
		}
		buildingPanel.add(labelPanel);
		buildingPanel.add(minValuePanel);
		buildingPanel.add(maxValuePanel);
		buildingPanel.add(defaultValuePanel);
		temp = (SliderWrapper) currentWidget;
		//widgetLabelText.setText(temp.getText());
		widgetMinValueText.setText(""+temp.getMinimum());
		widgetMaxValueText.setText(""+temp.getMaximum());
		widgetDefaultValueText.setText(""+temp.getValue());
		
	}

	private void setupJeoButton(){
		if(isNewWidget()){
			JButton tempJButton = new JButton();
			currentWidget = tempJButton;
		}
		buildingPanel.add(labelPanel);
		JButton temp = (JButton)currentWidget;
		widgetLabelText.setText(temp.getText());
	}
	
	private void setupTextView() {
		// TODO Auto-generated method stub
		
	}

	private void setupTVButton() {
		// TODO Auto-generated method stub
		
	}

	private void setupComboBox() {
		// TODO Auto-generated method stub
		
	}

	private void setupQRText() {
		// TODO Auto-generated method stub
		
	}

	private void setupTextQuestion() {
		// TODO Auto-generated method stub
		
	}

	private void setupTextbox() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	private void buildingPanelAddLabelAndDefault(){
		buildingPanel.add(labelPanel);
		buildingPanel.add(defaultValuePanel);
	}
	
	private void addingNewWidget(){
		newWidget = true;
		buildingPanel.add(widgetAddButton);
		showBuildingPanel();
	}
	
	private void edittingExistingWidget(){
		buildingPanel.add(widgetSaveButton);
		buildingPanel.add(widgetDeleteButton);
		showBuildingPanel();
	}
	
	private void clearBuilderObjects(){
		buildingPanel.removeAll();
		buildingPanel.setVisible(false);
	}

	private void promptFor(String item){
		clearBuilderObjects();
		if(item.equals("Button")){
			setupButton();
		} else if(item.equals("Jeopardy Button")){
			setupJeoButton();
		} else if(item.equals("Toggle Button")){
			setupToggleButton();
		} else if(item.equals("Slider")){
			setupSlider();
		} else if(item.equals("Textbox")){
			setupTextbox();
		} else if(item.equals("Text Question")){
			setupTextQuestion();
		} else if(item.equals("QR Text")){
			setupQRText();
		} else if(item.equals("Combo Box")){
			setupComboBox();
		} else if(item.equals("TextView with Button")){
			setupTVButton();
		} else if(item.equals("TextView")){
			setupTextView();
		}
		addingNewWidget();
	}

	//TODO
	//use isInstanceOf to determine which jcomponent each widget is, and add it's command based on what it is
	
	private void buildListeners(){
		builtButtonListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {}
		};
	}
	
	
	/**************************************
	 * Embedded classes 
	 **************************************/
	class ActionJList extends MouseAdapter{
		  protected JList list;
		    
		  public ActionJList(JList l){
		   list = l;
		   }
		    
		  public void mouseClicked(MouseEvent e){
		   if(e.getClickCount() >= 2){
			 newWidget = true;
		     int index = list.locationToIndex(e.getPoint());
		     ListModel dlm = list.getModel();
		     Object item = dlm.getElementAt(index);;
		     list.ensureIndexIsVisible(index);
		     //System.out.println("Double clicked on " + item);
		     promptFor(""+item);
		   } 
		   } 
	}
	
	class PreviewPanelAdapter extends MouseAdapter{
		 
		  public PreviewPanelAdapter(){
		   }
		    
		  public void mouseClicked(MouseEvent e){
			  //System.out.println("mouse clicked!");
		   if(widgetList.contains(e.getComponent())){
			   newWidget=false;
			   clearBuilderObjects();
			   JComponent widget = (JComponent) e.getComponent();
			   currentActiveWidgetNumber = widgetList.indexOf(widget);
			   if (widget instanceof JButton){
				  // System.out.println("is button!");
				   currentWidget = (JButton)e.getSource();
				   setupButton();
				   edittingExistingWidget();
				   //setupToggleBuilder((JToggleButton)currentWidget);
			   } else if (widget instanceof SliderWrapper){
				   //System.out.println("is slider!");
				   currentWidget = (SliderWrapper)e.getSource();
				   //SliderWrapper slideTemp = (SliderWrapper)e.getSource();
				   //System.out.println("slider label: "+slideTemp.getText()+".");
				   setupSlider();
				   edittingExistingWidget();
				   
			   } else if (widget instanceof JToggleButton){
				   currentWidget = (JToggleButton)e.getSource();
				   setupToggleButton();
				   edittingExistingWidget();
		       } else {
			   
				   System.out.println("in previewAdapter else: "+e.getSource().toString());
			   }
		   }
		   } 
	}
}
