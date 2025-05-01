package data;

public class EmployeeData{
	
	private String nameFirst;
	private String nameLast;
	private String stations;
	private String availSun;
	private String availMon;
	private String availTue;
	private String availWed;
	private String availThur;
	private String availFri;
	private String availSat;
	
	public EmployeeData(String nameFirst, String nameLast, String stations, String availSun, String availMon, String availTue, String availWed, String availThur, String availFri, String availSat){
		this.nameFirst = nameFirst;
		this.nameLast = nameLast;
		this.stations = stations;
		this.availSun = availSun;
		this.availMon = availMon;
		this.availTue = availTue;
		this.availWed = availWed;
		this.availThur = availThur;
		this.availFri = availFri;
		this.availSat = availSat;
		
	}
	
	public String getNameFirst(){
		return this.nameFirst;
	}
	
	public String getNameLast(){
		return this.nameLast;
	}
	
	public String getStations(){
		return this.stations;
	}
	
	public String getAvailSun(){
		return this.availSun;
	}
	
	public String getAvailMon(){
		return this.availMon;
	}
	
	public String getAvailTue(){
		return this.availTue;
	}
	
	public String getAvailWed(){
		return this.availWed;
	}
	
	public String getAvailThur(){
		return this.availThur;
	}
	
	public String getAvailFri(){
		return this.availFri;
	}
	
	public String getAvailSat(){
		return this.availSat;
	}
}