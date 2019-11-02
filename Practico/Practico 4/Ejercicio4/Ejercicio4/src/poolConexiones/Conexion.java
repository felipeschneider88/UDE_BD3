package poolConexiones;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.*;



public class Conexion implements IConexion {
	Connection myCon = null;
	
	public Conexion(int tranLevel, String driver, String url, String usuario, String clave, boolean autoCommit) 
			throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		myCon = (Connection) DriverManager.getConnection(url,usuario,clave);
		myCon.setAutoCommit(autoCommit);
		myCon.setTransactionIsolation(tranLevel);
		/*
		System.out.println("El nivel de transaccion none: "+ myCon.TRANSACTION_NONE);
		System.out.println("El nivel de transaccion READ_UNCOMMITTED: "+ myCon.TRANSACTION_READ_UNCOMMITTED);
		System.out.println("El nivel de transaccion READ_COMMITTED: "+ myCon.TRANSACTION_READ_COMMITTED);
		System.out.println("El nivel de transaccion REPEATABLE_READ: "+ myCon.TRANSACTION_REPEATABLE_READ);
		System.out.println("El nivel de transaccion SERIALIZABLE: "+ myCon.TRANSACTION_SERIALIZABLE);
		*/
	}
	
	public Connection getConnection() {
		return myCon;
	}
	
	//metodo a llamar antes de hacer Conexion con = null
	public void cerrarConexion(boolean huboModificaciones) throws SQLException{
		if (myCon != null) {
			if (huboModificaciones) {
				if (!myCon.getAutoCommit())
					myCon.commit();
			}
			else {
				if (!myCon.getAutoCommit())
					myCon.rollback();
			}
			myCon.close();
			myCon = null;
		}
	}
	
	

}
