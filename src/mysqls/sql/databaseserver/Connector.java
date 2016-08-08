package mysqls.sql.databaseserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Administrator连接server
 *
 */
public class Connector {
	public static void main(String[] args) throws SQLException {
		try {
			runTest();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

public static void runTest() throws SQLException, ClassNotFoundException{
	Class.forName(" com.mysql.jdbc.Driver");
	Connection connection = DriverManager.getConnection("jdbc::mysql:world", "root","123456");
	Statement  statement = connection.createStatement();
	ResultSet result = statement.executeQuery("SELECT * FROM CITY");
	if(result.next()){
		System.out.println(result.getString(1));
	}
	result.close();
	connection.close();
	}

//public static Connection getConnection() throws SQLException{
//	Properties props = new Properties();
//	
//	try {
//		FileInputStream in = new FileInputStream("D:\\360data\\重要数据\\桌面\\学习软件\\database.properties\\database.properties.txt");
//		try {
//			props.load(in);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			in.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	String drivers = props.getProperty("jdbc.drives");
//	if(drivers !=null)
//		System.setProperty("jdbc.drives", drivers);
//	String url = props.getProperty("jdbc.url");
//	String username = props.getProperty("jdbc.username");
//	String password = props.getProperty("jdbc.password");
//	
//	return DriverManager.getConnection(url, username, password);
//	}
}
