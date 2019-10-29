package poolConexiones;

/*
 * Seccion critica de lectura y escritura implementada de forma secuencial.
 */
public class Monitor {
	int cantidadLectores;
	boolean seEstaEscribiendo;

	public Monitor() {
		cantidadLectores = 0;
		seEstaEscribiendo = false;
	}

	public synchronized void comienzoEscritura() throws InterruptedException {
		// Esperar que todos terminen de leer
		while (cantidadLectores > 0) {
			wait();
		}

		// Esperar que se termine de escribir
		while (seEstaEscribiendo) {
			wait();
		}

		// Comezar a escribir
		seEstaEscribiendo = true;
	}

	public synchronized void terminoEscritura() throws InterruptedException {
		seEstaEscribiendo = false;
		notify();
	}

	public synchronized void comienzoLectura() throws InterruptedException {
		// Espero que se termine de escribir
		while (seEstaEscribiendo) {
			wait();
		}

		// Comienzo a leer
		cantidadLectores++;
	}

	public synchronized void terminoLectura() {
		// Termin de leer
		cantidadLectores--;
		notify();
	}
}
