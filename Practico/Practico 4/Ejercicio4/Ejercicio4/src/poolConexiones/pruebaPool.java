package poolConexiones;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class pruebaPool {

	public static void main(String[] args) {
		try {
			IPoolConexiones miPool = new PoolConexiones();
			IConexion miCon = miPool.obtenerConexion(true);
			/* Crer la Base de datos */
			System.out.println("Se va a crear la base de datos!");
			String crearBase = "CREATE DATABASE IF NOT EXISTS felipeschneider";
			PreparedStatement pstmt;
			try {
				Connection temp = miCon.getConnection();
				pstmt = (PreparedStatement) temp.prepareStatement(crearBase);
				pstmt.executeUpdate();
				pstmt.close();
				System.out.println("Base de datos creada!");
				miCon.cerrarConexion(true);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		} catch (PoolConexionesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
