package Projekti;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
//Via Java Serialization you can stream your Java object to a sequence of byte and restore these objects from this stream of bytes. per bartje nga serveri 
// te klient out.writeObject(lendet_list);
public class LendaEntity implements Serializable{

	private int ID;
	private String Lenda;
	private int Viti;
	private int Dita;
	private int Java;
	private LocalDate data_Provimit;
	private String ora; 
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getLenda() {
		return Lenda;
	}
	public void setLenda(String lenda) {
		Lenda = lenda;
	}
	public int getViti() {
		return Viti;
	}
	public void setViti(int viti) {
		Viti = viti;
	}
	public int getDita() {
		return Dita;
	}
	public void setDita(int dita) {
		Dita = dita;
	}
	public int getJava() {
		return Java;
	}
	public void setJava(int java) {
		Java = java;
	}
	public LocalDate getData_Provimit() {
		return data_Provimit;
	}
	public void setData_Provimit(LocalDate data_Provimit) {
		this.data_Provimit = data_Provimit;
	}
	public String getOra() {
		return ora;
	}
	public void setOra(String ora) {
		this.ora = ora;
	}
	
}
