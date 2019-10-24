import java.io.FileInputStream;
import java.util.List;

public class Fachada {
	
	public Fachada(){
		
	}


	public void nuevaTemporada(VOTemporada voT){}
	
	public void inscribirDragQueen(VODragQueen voD) {}
	
	public List<VOTemporada> listarTemporadas(){}
	
	public List<VODragQueenVictorias> listarDragQueens(int nroTemp){}
	
	public VOTempMaxParts tempMasParticipantes() {}
	
	public void registrarVictoria(int nroTemp, int nroPart) {}
	
	public VODragQueenVictorias obtenerGanadora(int nroTemp) {}

}