import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;
import com.mysql.jdbc.PreparedStatement;

public class CrearEscuela {

	public static void main(String[] args) {
		Connection con = null;
		try 
		{
			Properties p = new Properties();
			System.out.println("Working Directory = " +
		              System.getProperty("user.dir"));
			p.load(new FileInputStream("src/config/config.properties"));
			String driver = p.getProperty("driver");
			String url = p.getProperty("url");
			String usuario = p.getProperty("usuario");
			String clave = p.getProperty("clave");
			
			
			Class.forName(driver);
			con = DriverManager.getConnection(url,usuario,clave);
			
			/* Crer la Base de datos */
			System.out.println("Se va a crear la base de datos!");
			String crearBase = "CREATE DATABASE IF NOT EXISTS Escuela";
						PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(crearBase);
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("Base de datos creada!");
			
			/* Crer la tabla Personas */
			 String personas = "CREATE TABLE Escuela.Personas " +
					 			"(cedula INT NOT NULL PRIMARY KEY, " +
								 " nombre VARCHAR(45) NOT NULL, " +
								 " apellido VARCHAR(45) NOT NULL)";
			 pstmt = (PreparedStatement) con.prepareStatement(personas);
			 System.out.println("Se va a crear la  tabla Personas!");
			 pstmt.executeUpdate();
			 pstmt.close();
			 System.out.println("Tabla Personas creada!");
			 
			 /* Crear tabla Maestras */
			 
			 String maestras = "CREATE TABLE Escuela.Maestras " +
							 "(cedula INT NOT NULL PRIMARY KEY, " +
							 " grupo VARCHAR(45) NOT NULL," +
							 " FOREIGN KEY (cedula) REFERENCES" +
							 " Escuela.personas(cedula))";
			 pstmt = (PreparedStatement) con.prepareStatement(maestras);
			 System.out.println("Se va a crear la  tabla Maestras!");
			 pstmt.executeUpdate();
			 pstmt.close();
			 System.out.println("Tabla Maestras creada!");
			 
			 /* creo la tabla Alumnos */
			 String alumnos = "CREATE TABLE Escuela.Alumnos " +
							 "(cedula INT NOT NULL PRIMARY KEY, " +
							 " cedulaMaestra INT NOT NULL," +
							 " FOREIGN KEY (cedula) REFERENCES" +
							 " Escuela.personas(cedula)," +
							 " FOREIGN KEY (cedulaMaestra) REFERENCES" +
							 " Escuela.maestras(cedula))";
			 pstmt = (PreparedStatement) con.prepareStatement(alumnos);
			 System.out.println("Se va a crear la  tabla Alumnos!");
			 pstmt.executeUpdate();
			 pstmt.close(); 
			 System.out.println("Tabla Alumnos creada!"); 
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if (con != null)
					con.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

	}

}
