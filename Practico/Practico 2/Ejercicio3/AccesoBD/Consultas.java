import java.io.FileInputStream;
import java.util.List;

public class Consultas {
	
	public String existeTemp() {
		String query = "SELECT nroTemp FROM Temporadas where nroTemp = ? ";
		return query;
	}
	
	public String existeDragQueen() {
		
		String query = "SELECT nombre FROM Temporadas where nroPart = ? ";
		return query;
	}
	
	public String insertarTemp() {
		
		String query = "INSERT INTO Temporadas (nroTemp, anio, cantCapitulos) VALUES (?,?,?)";
		return query;
	}
	
	public String insertarDragQueen() {
		
		String query = "INSERT INTO DragQueens (nroTemp, anio, cantCapitulos) VALUES (?,?,?,?)";
		return query;
	}
	
	//Ordenadas por numero de temporada
	public String listarTemporadas() {
		
		String query =  "SELECT T.nroTemp FROM Temporadas" +
						"ORDER BY nroTemp";
		return query;
	}
	
	//incluir cant victorias de cada Drag en esa temporada, ordenar por numero de participante
	public String listarDragQueens() {
		
		String query =  "SELECT D.nroPart, D.nombre, D.cantVictorias FROM DragQueens D" + 
						"WHERE D.nroTemp = ? " +
						"ORDER BY nroPart";
						
		return query;
	}
	
	//Si existen dos iguales, devolver la que tenga numero mas alto de temporada
	public String tempMasParticipantes() {
		
		String query = "SELECT T.nroTemp, T.anio, T.cantCapitulos, MAX(count(D.nroPart)) as cantParticipantes" +
					   "FROM Temporadas T, DragQueens D" +
				       "WHERE T.nroTemp = D.nroTemp" +
					   "GROUP BY D.nroTemp"+
					"ORDER BY T.nroTemp desc" +
					"LIMIT 1";
		return query;
	}
	
	
	//ES NECESARIO TAMBIEN TENER EL NUMERO DE LA DRAGQUEEN? NO ENTIENDO LA IDEA A LA HORA DE INSERTAR O MODIFICAR DRAGS
	public String registrarVictoria(){
		
		String query = "UPDATE DragQueens SET cantVictorias = ? WHERE nroTemp = ? and nroPart = ?";
		return query;
	}
	
	public String obtenerGanadora() {
		
		String query = "SELECT nroPart, MAX(cantVictorias) as cantVictorias" +
				   "FROM DragQueens " +
			         "WHERE nroTemp = ? " +		
				"ORDER BY nroPart desc" +
					"LIMIT 1";
		return query;
	}
	
	Public String obtenerMaxnroPartTemporada() {
		String query = "SELECT MAX(D.nroPart) FROM Temporadas T, DragQueens D"+
			"WHERE T.nroTemp = (?) and T.nroTemp = D.nroTemp ";
		return query;
	
	
}
