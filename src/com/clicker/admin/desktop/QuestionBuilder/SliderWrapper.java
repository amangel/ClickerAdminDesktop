package com.clicker.admin.desktop.QuestionBuilder;
import javax.swing.JSlider;


public class SliderWrapper extends JSlider implements WidgetInterface {

	private String label;
	private int min;
	private int max;
	private int defaultValue;
	
	public SliderWrapper(){
		super();
		label = "";
		min = 50;
		max = 0;
		defaultValue = 25;
	}
	
	@Override
	public void setMaxWidgetValue(String value) {
		max = Integer.parseInt(value);
		this.setMaximum(max);
	}
	
	@Override
	public String getMaxWidgetValue() {		
		return (""+max);
	}
	@Override
	public void setMinWidgetValue(String value) {
		min = Integer.parseInt(value);	
		this.setMinimum(min);
	}
	
	@Override
	public String getMinWidgetValue() {		
		return (""+min);	
	}
	
	@Override
	public void setWidgetLabel(String label) {
		this.label = label;
	}
	
	@Override
	public String getWidgetLabel() {
		return label;
	}
	
	@Override
	public void setWidgetDefaultValue(String value) {
		defaultValue = Integer.parseInt(value);
		this.setValue(defaultValue);
	}
	
	@Override
	public String getWidgetDefaultValue() {
		return ""+defaultValue;
	}
	
	@Override
	public void setWidgetOptions(String options) {
		//do nothing		
	}
	
	@Override
	public String getWidgetOptions() {
		return "";
	}
}
