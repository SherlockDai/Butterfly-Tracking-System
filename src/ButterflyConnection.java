import java.sql.*;
 
public class ButterflyConnection {
 
	private Connection connection = null;
 
	public Connection getConnection() {
		return connection;
	}

	public ButterflyConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://104.128.51.140:3306/butterfly_tracking_system","web","2WsX3EdC4RfV");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finalize(){
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
}