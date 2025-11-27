package entities;

public class Factura {

	private int idMesa;
	private String tipoPago;
	private int importe;
	
	public Factura(int idMesa, String tipoPago, int importe) {
		if (idMesa > 0) { this.idMesa = idMesa; }
		if (importe > 0) { this.importe = importe; }
		if (tipoPago != null && !tipoPago.isBlank()) { this.tipoPago = tipoPago; }
	}
	
	public int idMesa() {
		return this.idMesa;
	}
	
	public int importe() {
		return this.importe;
	}
	
	public String tipoPago() {
		return this.tipoPago;
	}
}
