package poolConexiones;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.*;



public class Conexion implements IConexion {
	Connection myCon = null;
	
	public Conexion(int tranLevel, String driver, String url, String usuario, String clave) 
			throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		myCon = (Connection) DriverManager.getConnection(url,usuario,clave);
		myCon.setTransactionIsolation(tranLevel);
	}
	
	public Connection getConnection() {
		return myCon;
	}
	
	//metodo a llamar antes de hacer Conexion con = null
	public void cerrarConexion() throws SQLException{
		if (myCon != null) {
			myCon.close();
			myCon = null;
		}
	}
	
	

}
