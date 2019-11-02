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
	//private int creadas;
	private int tope;
	private String driver;
	private String usuario;
	private String clave;
	private String url;
	private boolean autoCommit;
	private ArrayList<IConexion> conexiones;
	private static PoolConexiones instance;
	//Queremos que sea un unico monitor para todo el Pool
	//private static Monitor miMonitor = new Monitor();
 

	
	public static PoolConexiones getInstance() throws PoolConexionesException {
		if (instance == null) {
			instance = new PoolConexiones();
		}
		return instance;
	}
	
	public PoolConexiones() throws PoolConexionesException {
		conexiones = new ArrayList<IConexion>();
		tope = conexiones.size();
		//creadas=0;
		
		Properties p = new Properties();
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		try {
			p.load(new FileInputStream("src/poolConexiones/config.properties"));
		} catch (FileNotFoundException e) {
			throw new PoolConexionesException("No existe el archivo de configuracion");
		} catch (IOException e) {
			throw new PoolConexionesException("Error de IO: " + e.getMessage());
		}
		driver = p.getProperty("driver");
		url = p.getProperty("url");
		usuario = p.getProperty("usuario");
		clave = p.getProperty("clave");
		tamano = new Integer(p.getProperty("tamano"));
		autoCommit = new Boolean(p.getProperty("autoCommit"));
		//TODO: Ver si esto anda o hay que averiguar los valores de Integer
		nivelTransaccional = new Integer(p.getProperty("nivelTransaccional"));
		/*
		 * El nivel de transaccion none: 0
		 * El nivel de transaccion READ_UNCOMMITTED: 1
		 * El nivel de transaccion READ_COMMITTED: 2
		 * El nivel de transaccion REPEATABLE_READ: 4
		 * El nivel de transaccion SERIALIZABLE: 8
		 */
	}
	public int getTope() {
		return tope;
	}
	
	private int addConexion() throws ClassNotFoundException, SQLException {
		int resu = -1;
		if (tope < tamano) {
			Conexion newCon;
			newCon = new Conexion(this.nivelTransaccional, this.driver, this.url, this.usuario, this.clave);
			conexiones.add(newCon);
			tope = tope + 1;		
			resu = conexiones.indexOf(newCon);
		}
		return resu;
	}
	
	public synchronized  IConexion obtenerConexion (boolean modificada) throws PoolConexionesException {
		//Se configura -1 como valor por defecto en caso de que no obtenga ninguna conexion
		int indice = -1;
		try {
			indice = addConexion();
		} catch (ClassNotFoundException e) {
			throw new PoolConexionesException("No existe la clase que define el dirver en la config");	
		} catch (SQLException e) {
			throw new PoolConexionesException("Error SQL: " + e.getErrorCode() + " ---" + e.getMessage());
		}
		/*
		try {
			if (modificada)
				miMonitor.comienzoEscritura();
			else
				miMonitor.comienzoLectura();
		} catch (InterruptedException e) {
			throw new PoolConexionesException("Error bloqueando monitor en metodo obtenerConexion");
		}
		*/
		if (indice == -1)
			throw new PoolConexionesException("No hay conexiones disponibles");
		else
			return conexiones.get(indice);
	}
	
	//Metodo privado que dada una conexion, la remueve del arreglo, la cierra y la deja en null para el GC
	private void removeConexion(IConexion con, boolean huboModificaciones) throws SQLException {
		//Quito el objeto del arreglo
		conexiones.remove(conexiones.indexOf(con));
		//Cierro la conexion
		con.cerrarConexion(huboModificaciones);
		con = null;
	}
	
	public synchronized void liberarConexion (IConexion con, boolean huboModificaciones) throws PoolConexionesException  {
		/*
		if(huboModificaciones) {
			try {
				miMonitor.terminoEscritura();
			} catch (InterruptedException e) {
				throw new PoolConexionesException("Error liberando monitor escritura en metodo liberarConexion");
			}
		}else {
			miMonitor.terminoLectura();
		}
		*/
		try {
			removeConexion(con, huboModificaciones);
		} catch (SQLException e) {
			throw new PoolConexionesException("Error SQL: " + e.getErrorCode() + " ---" + e.getMessage());
		}		
	}
}
