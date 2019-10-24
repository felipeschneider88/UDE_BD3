package poolConexiones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.mysql.jdbc.*;
import com.sun.xml.internal.org.jvnet.mimepull.MIMEConfig;

public class PoolConexiones {
	private int nivelTransaccional;
	private int tamano;
	private int creadas;
	private int tope;
	private String driver;
	private String usuario;
	private String clave;
	private String url;
	private ArrayList<Connection> conexiones;

	public PoolConexiones() throws FileNotFoundException, IOException {
		conexiones = new ArrayList<Connection>();
		tope = conexiones.size();
		creadas=0;
		
		Properties p = new Properties();
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		p.load(new FileInputStream("src/poolConexiones/config.properties"));
		driver = p.getProperty("driver");
		url = p.getProperty("url");
		usuario = p.getProperty("usuario");
		clave = p.getProperty("clave");
		
	}
	
	//TODO:Ver de corregir 
	public IConexion obtenerConexiones(boolean modificaDatos) throws ClassNotFoundException, SQLException {
		Connection resu = (Connection) new Conexion(Connection.TRANSACTION_READ_COMMITTED, driver, url, this.usuario, clave);
		return (IConexion) resu;
		/*
		 * 
		 * 
		 */
	}

}
