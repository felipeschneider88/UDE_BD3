package poolConexiones;

public interface IPoolConexiones {
	
	//Libera la conexion del pool
	public void liberarConexion (IConexion con, boolean huboModificaciones) throws PoolConexionesException;
	
	//Solcita al pool una nueva conexion
	public IConexion obtenerConexion (boolean modificada) throws PoolConexionesException;

}
