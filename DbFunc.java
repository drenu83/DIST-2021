package Projekti;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class DbFunc {

	private String insert_Afat = "INSERT INTO afatet (afati, start_date,viti) VALUES (?,?,?)";
	private String insert_Lend = "INSERT INTO lendet (lenda, java, dita, viti,ora) VALUES (?,?,?,?,?)";
	private String select_ALL = "SELECT * FROM afatet";
	private String selectAfati = "SELECT * FROM afatet WHERE afati = ? and viti =?";
	private String selectLendet = "SELECT * FROM lendet WHERE viti = ?";
	private String select_ALLLendet = "SELECT * FROM lendet";
	private String delete_Lenden = "DELETE FROM lendet WHERE id = ?";
	private String delete_Afatin = "DELETE FROM afatet WHERE id =?";
	private String update_Lenden = "UPDATE lendet SET lenda = ?, java= ?,dita = ?, viti = ?, ora = ? WHERE id = ?";
	private String update_Afatin = "UPDATE afatet SET afati = ?,start_date = ?, viti = ? WHERE id = ?";

	public AfatiEntity getAfati(Connection connection,String afati, int viti) throws SQLException{
		PreparedStatement pst = connection.prepareStatement(selectAfati);
		pst.setString(1, afati);
		pst.setInt(2, viti);
		AfatiEntity af = new AfatiEntity();
		ResultSet rs = pst.executeQuery();		// egzekuto querin, kthen 1 rreshta ose 0 te cilet e plotesojn kushtin 
			while(rs.next())
			{				
				af.setID(rs.getInt("id"));
				af.setAfati(rs.getString("afati"));
				af.setStart_Date(rs.getDate("start_date").toLocalDate());				
			}	
		
		return af;		
	}

	
public List<AfatiEntity> returnAfatet(Connection connection) throws SQLException{
		List<AfatiEntity> afatet = new ArrayList<AfatiEntity>();
		PreparedStatement pst = connection.prepareStatement(select_ALL); // kthen te gjithe rreshtat nga tablea Afatet
		ResultSet rs = pst.executeQuery();		
			while(rs.next())
			{
				AfatiEntity af = new AfatiEntity();
				af.setID(rs.getInt("id"));
				af.setAfati(rs.getString("afati"));
				af.setStart_Date(rs.getDate("start_date").toLocalDate());
				afatet.add(af); // krejt afatet qe jane te table i bejm add ne listen afatet
			}	
		
		return afatet;		
	}
	
	
	public List<LendaEntity> getLendet(Connection connection,int viti) throws SQLException{
		List<LendaEntity> lendet = new ArrayList<LendaEntity>();
		PreparedStatement pst = connection.prepareStatement(selectLendet); // kthen te gjitha lendet e nje viti te caktuar pshe 1,2,3
		pst.setInt(1, viti);	
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			LendaEntity le = new LendaEntity();
			le.setID(rs.getInt("id"));
			le.setLenda(rs.getString("lenda"));
			le.setJava(rs.getInt("java"));
			le.setDita(rs.getInt("dita"));
			le.setViti(viti);
			le.setOra(rs.getString("ora"));
			lendet.add(le); // per secilen lend psh te viti te pare i merr te dhenat dhe i bon add ne listen lendet
		}
		return lendet;
		
public List<LendaEntity> returnLendet(Connection connection) throws SQLException{
		List<LendaEntity> lendet = new ArrayList<LendaEntity>();
		PreparedStatement pst = connection.prepareStatement(select_ALLLendet); // kthen te gjitha lendet qe gjinden ne tabelen lendet
	
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			LendaEntity le = new LendaEntity();
			le.setID(rs.getInt("id"));
			le.setLenda(rs.getString("lenda"));
			le.setJava(rs.getInt("java"));
			le.setDita(rs.getInt("dita"));
			le.setViti(rs.getInt("viti"));
			le.setOra(rs.getString("ora"));
			lendet.add(le);
		}
		return lendet;
	}
