package poolConexiones;

public interface IPoolConexiones {
	
	//Libera la conexion del pool
	void liberarConexion (IConexion con, boolean huboModificaciones);
	
	//Solcita al pool una nueva conexion
	IConexion obtenerConexion (boolean modificada);

}
