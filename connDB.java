package Projekti;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class connDB {
	
	Connection conn = null;    //objekti per lidhje me databaze

	public static Connection lidhuMeDB() {
		final String USERNAME="root";
		final String PASSWORD="root";
		final String CONNECTDB="jdbc:mysql://localhost:3306/orari";
		try {
			Connection conn;
			conn=(Connection) DriverManager.getConnection(CONNECTDB,USERNAME,PASSWORD);
			
			return conn;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Errori :" + e.getMessage());
			return null;
		}
	}

}
