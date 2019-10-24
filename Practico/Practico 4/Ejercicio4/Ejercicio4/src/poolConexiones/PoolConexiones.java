package poolConexiones;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.mysql.jdbc.*;

public class PoolConexiones {
	private int nivelTransaccional;
	private int tamano;
	private int creadas;
	private int tope;
	private ArrayList<Connection> conexiones;

	public PoolConexiones() {
		conexiones = new ArrayList<Connection>();
		tope = conexiones.size();
		creadas=0;
		
		Properties p = new Properties();
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		p.load(new FileInputStream("src/poolConexiones/config.properties"));
		String driver = p.getProperty("driver");
		String url = p.getProperty("url");
		String usuario = p.getProperty("usuario");
		String clave = p.getProperty("clave");
		
	}
	
	public 

}
