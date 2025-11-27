package entities;

public class Mesa {

	private int numComensales;
	private int reserva;
	
	public Mesa(int numComensales, int reserva) {
		if (numComensales > 0) { this.numComensales = numComensales; }
		if (reserva > 0) { this.reserva = reserva; }
	}
	
	public int numComensales() {
		return this.numComensales;
	}
	
	public int reserva() {
		return this.reserva;
	}
}
