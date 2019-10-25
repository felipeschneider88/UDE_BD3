import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import poolConexiones.Conexion;
import poolConexiones.IConexion;

public class Fachada {
	
	public Fachada(){
		
	}

	//TODO: NO VA A ENCONTRAR LA CLASE ACCESOBD YA QUE PASA LO MISMO QUE EN FACHADA, DICHA CLASE AUN NO ESTA ENVUELTA EN UN MISMO PACKAGE
	//TODO: TAMPOCO ENCUENTRA LA INTERFAZ DE POOL DE CONEXIONES YA QUE AUN NO TENEMOS TOODO COMPACTADO EN UN MISMO LUGAR, LO MISMO PARA LA CLASE DE VOTEMPORADA
	//TODO: TAMBIEN ENLAZAR LAS CLASES DE EXCEPCIONES DE TIPO PERSISTENCIAEXCEPTION, TEMPORADAEXCEPTION, DRAGQUEENEXCEPTION EN UN PRINCIPIO
	public void nuevaTemporada(VOTemporada voT) throws PersistenciaException, TemporadaException{
		
		 IPoolConexiones pool;
		 IConexion icon= pool.obtenerConexion(true);
		 String msgError;
		 boolean errorPersistencia;
		 boolean existeTemporada;
		 
		 try 
		 {  
			 int nroTemp = voT.getNroTemporada(); 
			 int anio = voT.getAnio(); 
			 int cantCapitulos = voT.getCantCapitulos(); 
			
			 AccesoBD abd= new AccesoBD();
			 existeTemporada= abd.existeTemp(icon, nroTemp);
			 if(!existeTemporada) 
				 abd.insertarTemp(icon, nroTemp, anio, cantCapitulos); 
			 else 
				 msgError= "TEMPORADA YA EXISTENTE";
			 
			 pool.liberarConexion(icon, true);
			 
		 } 
		 catch(Exception e)
		 {
		    pool.liberarConexion(icon, false); 
		   	errorPersistencia= true; 
		 	msgError = "ERROR AL INTENTAR ACCEDER A LOS DATOS";
	 	 }
		 finally
		 { 
			 if(existeTemporada) 
				 throw new TemporadaException(msgError); 
			 
			 if(errorPersistencia) 
				 throw new PersistenciaException(msgError); 
		 }
    }
	
	public void inscribirDragQueen(VODragQueen voD) {
		
		 IPoolConexiones pool;
		 IConexion icon= pool.obtenerConexion(true);
		 String msgError;
		 boolean errorPersistencia;
		 boolean existeTemporada;
		 
		 try 
		 {  
			 
			 String nombre = voD.getNombre(); 
			 int nroTemp = voD.getNroTemporada();  
			
			 AccesoBD abd= new AccesoBD();
			 existeTemporada = abd.existeTemp(icon, nroTemp);
			 if(existeTemporada) 
				 abd.insertarDragQueen(icon, nombre, nroTemp);
			 else 
				 msgError= "NO EXISTE TEMPORADA A LA QUE ASIGNAR DICHA DRAGQUEEN";
			 
			 pool.liberarConexion(icon, true);
			 
		 }
		 catch(Exception e)
		 {
			 	pool.liberarConexion(icon, false); 
		 		errorPersistencia= true; 
		 		msgError = "ERROR AL INTENTAR ACCEDER A LOS DATOS";
	 	 }
		 finally
		 { 
			 if(!existeTemporada) 
				 throw new TemporadaException(msgError); 
			 
			 if(errorPersistencia) 
				 throw new PersistenciaException(msgError); 
		 }
	}
	
	public List<VOTemporada> listarTemporadas(){
		
		 IPoolConexiones pool;
		 IConexion icon= pool.obtenerConexion(true);
		 String msgError;
		 boolean errorPersistencia;
		 
		 try 
		 { 
			 AccesoBD abd= new AccesoBD();
			 
			 List<VOTemporada> listaTemporadas = new ArrayList<VOTemporada>();
			 
			 //TODO: NO SE SI SE PUEDEN ASIGNAR LISTAS DE ESTA MANERA EN JAVA, O HABRIA QUE RECORRER LA LISTA QUE DEVUELVE LISTARTEMPORADAS PARA ARMAR UNA NUEVA LISTA PARA RETORNAR
			 listaTemporadas = abd.listarTemporadas(icon);
			 
			 pool.liberarConexion(icon, true);
			 
			 return listaTemporadas;
			 
		 }
		 catch(Exception e)
		 {
			 	pool.liberarConexion(icon, false); 
		 		errorPersistencia= true; 
		 		msgError = "ERROR AL INTENTAR ACCEDER A LOS DATOS";
	 	 }
		 finally
		 { 
			 if(errorPersistencia) 
				 throw new PersistenciaException(msgError); 
		 }
		
	}
	
	public List<VODragQueenVictorias> listarDragQueens(int nroTemp){}
	
	public VOTempMaxParts tempMasParticipantes() {}
	
	public void registrarVictoria(int nroTemp, int nroPart) {}
	
	public VODragQueenVictorias obtenerGanadora(int nroTemp) {}

}