package Projekti;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class connDB {
	//objekti per lidhje me databaze
	Connection conn = null;

	public static Connection lidhuMeDB() {
		final String USERNAME="root";
		final String PASSWORD="root";
		final String CONNECTDB="jdbc:mysql://localhost:3306/orari";
		try {
			Connection conn;
			conn=(Connection) DriverManager.getConnection(CONNECTDB,USERNAME,PASSWORD);
			
			// perdoret per marrjen e Driverit per lidhje
			//Class.forName("com.mysql.jdbc.Driver");
	
			//Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/orari?user=root/useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
			
			return conn;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Errori :" + e.getMessage());
			return null;
		}
	}

}
