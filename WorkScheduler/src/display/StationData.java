package display;

import main.TimeConverter;

public class StationData{
	
	private String name;
	private float timeOpen;
	private float timeClose;
	private String busyHours;
	private String quietHours;
	private int minNum;
	private int maxNum;
	private int effNum;
	
	public StationData(String name, String timeOpen, String timeClose, String busyHours, String quietHours, int minNum, int maxNum, int effNum){
		this.name = name;
		this.timeOpen = TimeConverter.converterToFloat(timeOpen);
		this.timeClose = TimeConverter.converterToFloat(timeClose);
		this.busyHours = busyHours;
		this.minNum = minNum;
		this.maxNum = maxNum;
		this.effNum = effNum;
	}
	
	public String getname(){
		return this.name;
	}
	
	public float getTimeOpen(){
		return this.timeOpen;
	}
	
	public float getTimeClose(){
		return this.timeClose;
	}
	
	public String getBusyHours(){
		return this.busyHours;
	}
	
	public String getQuietHours(){
		return this.quietHours;
	}
	
	public int getMinNum(){
		return this.minNum;
	}
	
	public int getMaxNum(){
		return this.maxNum;
	}
	
	public int getEffNum(){
		return this.effNum;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setTimeOpen(String timeOpen){
		this.timeOpen = TimeConverter.converterToFloat(timeOpen);
	}
	
	public void setTimeClose(String timeClose){
		this.timeClose = TimeConverter.converterToFloat(timeClose);
	}
	
	public void setBusyHours(String busyHours){
		this.busyHours = busyHours;
	}
	
	public void setQuietHours(String quietHours){
		this.quietHours = quietHours;
	}
	
	public void setMinNum(int minNum){
		this.minNum = minNum;
	}
	
	public void setMaxNum(int maxNum){
		this.maxNum = maxNum;
	}
	
	public void setName(int effNum){
		this.effNum = effNum;
	}
	
}