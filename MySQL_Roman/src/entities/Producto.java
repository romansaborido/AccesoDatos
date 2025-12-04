package entities;

public class Producto {

	private String denominacion;
	private double precio;
	
	public Producto(String denominacion, double precio) {
		if (denominacion != null && !denominacion.isBlank()) { this.denominacion = denominacion; }
		if (precio > 0) { this.precio = precio; }
	}
	
	public String denominacion() {
		return this.denominacion;
	}
	
	public double precio() {
		return this.precio;
	}
}
