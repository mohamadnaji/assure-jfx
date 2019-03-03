package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public static Connection connect() {
		Connection con = null;
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String m_Connection = "jdbc:sqlserver://localhost:1433;databaseName=Project_Prototype;user=pro;password=123;";
			//String m_Connection = "jdbc:sqlserver://Mohamad\\MOHAMADSQL:1433;databaseName=Project_Prototype;user=mohamad;password=123;";

			con = DriverManager.getConnection(m_Connection);

		} catch (Exception e) {
			AlertController.alert("Database Connection Error","Error");
		}
		return con;

	}

}
