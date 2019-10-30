package poolConexiones;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public interface IConexion {

	//Devuelve la conexion 
	public Connection getConnection();
	
	//TODO: Hay que manejar correctamente el posible SQLException
	public void cerrarConexion(boolean huboModificaciones) throws SQLException;
	
}
