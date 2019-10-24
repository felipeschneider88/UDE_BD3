package poolConexiones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.*;



public class Conexion implements IConexion {
	Connection myCon = null;
	
	public Conexion(int tranLevel, String driver, String usuario, String clave) 
			throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		myCon = (Connection) DriverManager.getConnection(url,usuario,clave);
		myCon.setTransactionIsolation(tranLevel);
	}
	
	public Connection getConnection() {
		return myCon;
	}
	

}
