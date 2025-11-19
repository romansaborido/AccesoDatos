package utils;

public class Utils {

	// Credenciales
	public static final String URL = "jdbc:mysql://dns11036.phdns11.es:3306/ad2526_roman_saborido";
	public static final String USER = "ad2526_roman_saborido";
	public static final String PASSWORD = "12345";
	
	// Tablas
	public static final String MESA = "CREATE TABLE IF NOT EXISTS mesa ("
			+ "idMesa INT AUTO_INCREMENT PRIMARY KEY,"
			+ "numComensales INT,"
			+ "reserva TINYINT"
			+ ")";
	
	public static final String PRODUCTOS = "CREATE TABLE IF NOT EXISTS productos ("
			+ "idProducto INT AUTO_INCREMENT PRIMARY KEY,"
			+ "denominacion VARCHAR(45),"
			+ "precio DECIMAL(10,2)"
			+ ")";
	
	public static final String FACTURA = "CREATE TABLE IF NOT EXISTS factura ("
			+ "idFactura INT AUTO_INCREMENT PRIMARY KEY,"
			+ "idMesa INT NOT NULL,"
			+ "tipoPago VARCHAR(45),"
			+ "importe DECIMAL(10,2),"
			+ "FOREIGN KEY (idMesa) REFERENCES mesa (idMesa)"
			+ ")";
	
	public static final String PEDIDO = "CREATE TABLE IF NOT EXISTS pedido ("
			+ "idPedido INT AUTO_INCREMENT PRIMARY KEY,"
			+ "idFactura INT NOT NULL,"
			+ "idProducto INT NOT NULL,"
			+ "cantidad INT,"
			+ "FOREIGN KEY (idFactura) REFERENCES factura (idFactura),"
			+ "FOREIGN KEY (idProducto) REFERENCES productos (idProducto)"
			+ ")";
}
