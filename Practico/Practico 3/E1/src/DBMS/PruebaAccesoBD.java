import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaAccesoBD
{
	public static void main (String[] args)
	{
		try
		{
			/* primer programa de prueba para ejemplo de acceso a MySQL desde Java */
			/* accede a una base de datos de MySQL llamada Prueba que contiene una tabla llamada Personas */
			/* dentro de dicha tabla hay una columna llamada nombre */

			/* 1. cargo dinamicamente el driver de MySQL */
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);

			/* 2. una vez cargado el driver, me conecto con la base de datos */
			String url = "jdbc:mysql://localhost:3306/Prueba";
			Connection con = DriverManager.getConnection(url, "root", "root");

			/* 3. creo un PreparedStatement para insertar una persona en base de datos */
			String insert = "INSERT INTO Personas VALUES (?)";
			PreparedStatement pstmt = con.prepareStatement(insert);
			pstmt.setString(1, "Julia");

			/* 4. ejecuto la sentencia de insercion y cierro el PreparedStatement */
			int cant = pstmt.executeUpdate();
			pstmt.close();
			System.out.print("Resultado de " + insert + ": ");
			System.out.println(cant + " filas afectadas");

			/* 5. creo un Statement para listar todas las personas de la base de datos */
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM Personas";

			/* 6. ejecuto la consulta, listo las personas y cierro el ResultSet y el Statement */
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Resultado de " + query);
			while (rs.next())
			{
				System.out.println("Nombre = " + rs.getString("nombre").trim());
			}
			rs.close();
			stmt.close();

			/* 7. por ultimo, cierro la conexion con la base de datos */
			con.close();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}	
	}
}
