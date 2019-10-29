package poolConexiones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.mysql.jdbc.*;
import com.sun.xml.internal.org.jvnet.mimepull.MIMEConfig;

public class PoolConexiones implements IPoolConexiones{
	private int nivelTransaccional;
	private int tamano;
	private int creadas;
	private int tope;
	private String driver;
	private String usuario;
	private String clave;
	private String url;
	private ArrayList<IConexion> conexiones;
	private static PoolConexiones instance;

	
	public static PoolConexiones getInstance() throws FileNotFoundException, IOException {
		if (instance == null) {
			instance = new PoolConexiones();
		}
		return instance;
	}
	
	public PoolConexiones() throws FileNotFoundException, IOException {
		conexiones = new ArrayList<IConexion>();
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
		tamano = new Integer(p.getProperty("tamano"));
		//TODO: Ver si esto anda o hay que averiguar los valores de Integer
		nivelTransaccional = new Integer(p.getProperty("nivelTransaccional"));
		//Connection.TRANSACTION_NONE
		//Connection.TRANSACTION_READ_COMMITTED
		//Connection.TRANSACTION_READ_UNCOMMITTED
		//Connection.TRANSACTION_REPEATABLE_READ
		//Connection.TRANSACTION_SERIALIZABLE
	}
	public int getTope() {
		return tope;
	}
	
	private void addConexion() {
		if (tope < tamano) {
			Conexion newCon;
			try {
				newCon = new Conexion(this.nivelTransaccional, this.driver, this.url, this.usuario, this.clave);
				conexiones.add(newCon);
				tope = tope + 1;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void removeConexion(IConexion con) {
		try {
			con.cerrarConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con = null;
	}
	
	void liberarConexion (IConexion con, boolean huboModificaciones) {
		
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
