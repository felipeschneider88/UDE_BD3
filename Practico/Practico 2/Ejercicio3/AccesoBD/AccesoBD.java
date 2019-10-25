import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.mysql.jdbc.PreparedStatement;

import poolConexiones.Conexion;


//TODO: FALTARIA ENLAZAR LA CLASE CONSULTAS QUE COMO NO ESTA EN NINGUN PACK NO PUEDE SER IMPORTADA, PERO ESO SE UNE TOOODO CUANDO HAGAMOS LOS PACKAGES CORRESPONDIENTES
public class AccesoBD {
	
	//VERIFICA QUE EXISTA UNA TEMPORADA
	//TODO: AGREGAR CLASE PERSISTENCIA EXCEPTION
	//TODO: NO SE RECONOCE EL MEDOTO PREPARESTATEMENT QUE SE DERIVA DE LA VARIABLE CON, HABRIA QUE VER DE CREARLO MISMO EN EL POOL DE CONEXIONES
	public boolean existeTemp(Conexion con, int nroTemp) throws PersistenciaException {
		boolean existeTemp = false;
		try
		{ 
			Consultas consultas= new Consultas(); 
			String query= consultas.existeTemp();
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setInt(1, nroTemp); 
			ResultSet rs = pstmt1.executeQuery(); 
			if(rs.next()) 
				existeTemp = true; 
			
			rs.close();  
			pstmt1.close();
	
		} catch(SQLException e) 
		{
			//TODO: ESTA BIEN LANZAR ESTA EXCEPCION AQUI? Y QUE PARAMETRO SE LE PASARIA EN CASO AFIRMATIVO, OSEA UNA VARIABLE CON UN MENSAJE A LA CAPA DE ARRIBA, LA FACHADA CONCRETAMENTE
			throw new PersistenciaException();
		}
		return existeTemp;
	}
	
	//VERIFICA QUE EXISTA UNA DRAGQUEEN
	//TODO: DUDAS DE SI SE PASA MENSAJE A LA CLASE PERSISTENCIAEXCEPTION AL ENCONTRAR UN ERROR TRATANDO DE INSERTAR UN REGISTRO
	public boolean existeDragQueen(Conexion con, int nroDrag) throws PersistenciaException {
		
		boolean existeDrag = false;
		try
		{ 
			Consultas consultas= new Consultas(); 
			String query= consultas.existeTemp();
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setInt(1, nroDrag); 
			ResultSet rs = pstmt1.executeQuery(); 
			if(rs.next()) 
				existeDrag = true; 
			
			rs.close();  
			pstmt1.close();
	
		} catch(SQLException e) 
		{
			throw new PersistenciaException();
		}
		return existeDrag;
	}
	
	//REGISTRA TEMPORADA VERIFICANDO QUE NO EXISTA ANTES
	//TODO: DUDAS DE SI SE PASA MENSAJE A LA CLASE PERSISTENCIAEXCEPTION AL ENCONTRAR UN ERROR TRATANDO DE INSERTAR UN REGISTRO
	public void insertarTemp(Conexion con, int nroTemp, int anio, int cantCapitulos) throws PersistenciaException {
		
		try
		{ 
			Consultas consultas= new Consultas(); 
			String query= consultas.existeTemp();
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setInt(1, nroTemp);
			pstmt1.setInt(2, anio);
			pstmt1.setInt(3, cantCapitulos);
			ResultSet rs = pstmt1.executeQuery(); 
			
			rs.close();  
			pstmt1.close();
	
		} catch(SQLException e) 
		{
			throw new PersistenciaException();
		}
	}
	
	//INSERTA DRAGQUEEN EN UNA TEMPORADA, VERIFICANDO QUE LA TEMPORADA QUE LE CORRESPONDE ESTE REGISTRADA
	//TODO: DUDAS DE SI SE PASA MENSAJE A LA CLASE PERSISTENCIAEXCEPTION AL ENCONTRAR UN ERROR TRATANDO DE INSERTAR UN REGISTRO
	public void insertarDragQueen(Conexion con, int nroTemp, String nombre) throws PersistenciaException {
		
		try
		{ 
			Consultas consultas= new Consultas(); 
			String query= consultas.existeTemp();
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setInt(1, nroTemp);
			pstmt1.setString(2, nombre);
			ResultSet rs = pstmt1.executeQuery(); 
			
			rs.close();  
			pstmt1.close();
	
		} catch(SQLException e) 
		{
			throw new PersistenciaException();
		}
		
	}
	
	
	//DEVUELVE LSITADO DE TEMPORADAS ORDENADAS POR NUMERO DE TEMPORADA
	//TODO: ME GENERA DUDA QUE SE HACE CON EL CAMPO SECUENCIA DE LA TABLA TEMPORADA
	public List<VOTemporada> listarTemporadas(Conexion con) throws PersistenciaException {
		
		try
		{ 
			List<VOTemporada> listaTemporadas = new ArrayList<VOTemporada>();
			VOTemporada VOT;
			
			Consultas consultas= new Consultas(); 
			String query= consultas.existeTemp();
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			ResultSet rs = pstmt1.executeQuery(); 
			
			while(rs.next()) {
				int nroTemp = 0;
				int anio = 0;
				int cantCapitulos = 0;
				
				nroTemp = rs.getInt("nroTemp");
				anio = rs.getInt("anio");
				cantCapitulos = rs.getInt("cantCapitulos");
				
				VOT = new VOTemporada(nroTemp, anio, cantCapitulos);
				
				listaTemporadas.add(VOT);
			}
			
			rs.close();  
			pstmt1.close();
	
			return listaTemporadas;
					
		} catch(SQLException e) 
		{
			throw new PersistenciaException();
		}
	}
	
	
	//DEVUELVE LISTADO DE DE TODAS LAS DRAGQUEENS DE UNA TEMPORADA
	public List<VODragQueen> listarDragQueens(Conexion con, int nroTemp) throws PersistenciaException {
		
		//TODO: PARA RESOLVER
		return null;
	}
	
	//DEVUELVE LA TEMPORADA CON MAS PARTICIPANTES Y NUMERO CORRESPONDIENTE DE PARTICIPANTES, EN CASO DE EXISTIR DOS IGUALES DEVUELVE LA TEMP. DE NUMERO MAS ALTA
	public VOTemporada tempMasParticipantes(Conexion con) throws PersistenciaException {
		
		try
		{ 
			VOTemporada VOT;
			Consultas consultas= new Consultas(); 
			String query= consultas.existeTemp();
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			ResultSet rs = pstmt1.executeQuery(); 
			
			if(rs.next()) {
				int nroTemp = 0;
				int anio = 0;
				int cantCapitulos = 0;
				
				nroTemp = rs.getInt("nroTemp");
				anio = rs.getInt("anio");
				cantCapitulos = rs.getInt("cantCapitulos");
				
				 VOT = new VOTemporada(nroTemp, anio, cantCapitulos);
				
			}
			
			rs.close();  
			pstmt1.close();
	
			return VOT;
					
		} catch(SQLException e) 
		{
			throw new PersistenciaException();
		}
	}
	
	//AGREGA UNA VICTORIA A LA DRAGQUEEN CORRESPONDIENTE AL NUMERO DE TEMPORADA INDICADO
	public void registrarVictoria(Conexion con, int nroTemp, int nroPart) throws PersistenciaException {
		
		try
		{ 
			Consultas consultas= new Consultas(); 
			String query= consultas.existeTemp();
			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setInt(1, nroTemp);
			pstmt1.setInt(2, nroPart);
			ResultSet rs = pstmt1.executeQuery(); 
			
			rs.close();  
			pstmt1.close();
	
		} catch(SQLException e) 
		{
			throw new PersistenciaException();
		}
		
	}

	//DEVUELVE LA DRAGQUEEN GANADORA DE UNA TEMPORADA, TAMBIEN LA CANTIDAD DE VICTORIAS DE LA GANADORA.
	public VODragQueen obtenerGanadora(Conexion con) throws PersistenciaException {
		
		//TODO: PARA RESOLVER
		return null;
	}

}