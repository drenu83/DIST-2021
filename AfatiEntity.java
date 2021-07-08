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
