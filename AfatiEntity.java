package Projekti;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class AfatiEntity {
	private int ID;

    private String afati;
	
	private LocalDate start_Date; 

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getAfati() {
		return afati;
	}

	public void setAfati(String afati) {
		this.afati = afati;
	}
	public LocalDate getStart_Date() {
		return start_Date;
	}

	public void setStart_Date(LocalDate start_Date) {
		this.start_Date = start_Date;
	}

	public int getViti() {
		return start_Date.getYear();
	}
	
	

}

