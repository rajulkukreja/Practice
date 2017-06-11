/**
 * 
 */
package rateblock;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * @author praveenk
 *
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class OracleCon {
	
	public static Connection  getConnection() {
		Connection con = null;
		Properties prop = new Properties();
		InputStream input = null;
		
		
		try {
			
			input = new FileInputStream("/home/rajulkukreja/workspace/RoomRateBlock/src/dbconfig.properties");
			// load a properties file
			prop.load(input);
			String userName = prop.getProperty("dbuser");
			String password = prop.getProperty("dbpassword");
			String ipAddress = prop.getProperty("ipaddress");
			String port = prop.getProperty("port");
			String serviceName = prop.getProperty("serviceName");
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//con = DriverManager.getConnection("jdbc:oracle:thin:@10.10.12.16:1521:Cleardb", "product", "product");
			con = DriverManager.getConnection("jdbc:oracle:thin:@" +ipAddress + ":" + port + ":" + serviceName, userName, password);
			
		}catch (Exception e) {
			System.out.println(e);
			try {
				if (con != null) {
					con.close();
					con = null;
				}	
				if (input != null) {
					input.close();
				}
			} catch (SQLException sqle) {
				// TODO Auto-generated catch block
				sqle.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return con;
	}
	
	
}
