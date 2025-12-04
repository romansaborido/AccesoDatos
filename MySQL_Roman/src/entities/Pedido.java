package entities;

public class Pedido {

	private int idFactura;
	private int idProducto;
	private int cantidad;
	
	public Pedido(int idFactura, int idProducto, int cantidad) {
		if (idFactura > 0) { this.idFactura = idFactura; }
		if (idProducto > 0) { this.idProducto = idProducto; }
		if (cantidad > 0) { this.cantidad = cantidad; }
	}
	
	public int idFactura() {
		return this.idFactura;
	}
	
	public int idProducto() {
		return this.idProducto;
	}
	
	public int cantidad() {
		return this.cantidad;
	}
}
