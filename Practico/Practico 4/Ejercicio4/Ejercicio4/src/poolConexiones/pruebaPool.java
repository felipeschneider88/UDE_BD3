package poolConexiones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class pruebaPool {
	static final PrintStream out = System.out;
	static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		try {
			IPoolConexiones miPool = new PoolConexiones();
			IConexion miCon = miPool.obtenerConexion(true);
			IConexion miCon2 = miPool.obtenerConexion(true);
			/* Crer la Base de datos */
			System.out.println("Se va a crear la base de datos!");
			String crearBase = "CREATE DATABASE IF NOT EXISTS felipeschneider";

			String crearBase2 = "CREATE DATABASE IF NOT EXISTS felipeschneider2";
			PreparedStatement pstmt;
			PreparedStatement pstmt2;
			try {
				//Primera conexion
				Connection temp = miCon.getConnection();
				pstmt = (PreparedStatement) temp.prepareStatement(crearBase);
				pstmt.executeUpdate();
				pstmt.close();
				//Segunda conexion
				Connection temp2 = miCon2.getConnection();
				pstmt2 = (PreparedStatement) temp2.prepareStatement(crearBase2);
				pstmt2.executeUpdate();
				pstmt2.close();
				out.println("Base de datos creada!");
				out.println("ATENCIÓN: Presione la tecla ENTER para MATAR el servidor!");
				in.readLine();
				miCon.cerrarConexion(true);
				miCon2.cerrarConexion(true);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
		} catch (PoolConexionesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
