package utils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import entities.Factura;
import entities.Mesa;
import entities.Pedido;
import entities.Producto;

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
	
	
	// Mesa
	public static Mesa crearMesa(Scanner reader) {
		
		// numComensales
		int numComensales = 0;
		
		// reserva
		int reserva = 0;
		
		// Controla la salida de las interacciones con el usuario
		boolean valido = false;
		
		
		// numComensales
		do {
			try {
				// Pedimos el numero de comensales
				System.out.print("\nComensales -> ");
				numComensales = reader.nextInt();
				reader.nextLine();
				
				// Si numComensales es valido
				if (numComensales > 0) {
					valido = true;
				} else {
					System.out.println("El numero de comensales tiene que ser > a 0");
					valido = false;
				}
				
			// Capturamos la excepcion y mostramos un mensaje de error	
			} catch (InputMismatchException e) {
				System.out.println("Entrada no válida. Debe ser un número entero");
				reader.nextLine();
			}
		} while (!valido);
		
		
		// reserva
		do {
			try {
				// Pedimos el numero de reserva
				System.out.print("Reserva -> ");
				reserva = reader.nextInt();
				reader.nextLine();
				
				// Comprobamos que sea > a 0
				if (reserva > 0) {
					valido = true;
				} else {
					System.out.println("El numero de reserva tiene que ser > a 0");
					valido = false;
				}
				
			// Capturamos la excepcion y mostramos un mensaje de error	
			} catch (InputMismatchException e) {
				System.out.println("Entrada no válida. Debe ser un número entero");
				reader.nextLine();
			}
		} while (!valido);
		
		
		// Creamos la mesa
		return new Mesa(numComensales, reserva);
	}
	
	
	// Factura
	public static Factura crearFactura(Scanner reader, ArrayList<Integer> mesas) {
		
		// idMesa
	    int idMesa = 0;
	    
	    // tipoPago
	    String tipoPago;
	    
	    // importe
	    double importe = 0;
	
	    // Controla la salida de las interacciones con el usuario
	    boolean valido = false;

	    
	    // idMesa
	    do {
	        try {
	        	
	        	// Pedimos el ID de la mesa
	            System.out.print("\nID Mesa -> ");
	            idMesa = reader.nextInt();
	            reader.nextLine();

	            // Comprobar si el idMesa está en el array de mesas
	            if (mesas.contains(idMesa)) {
	            	valido = true;
	            } else {
	            	System.out.println("La mesa con el ID " + idMesa + " no existe");
	            	valido = false;
	            }
	        
	        // Capturamos la excepcion y mostramos el mensaje de error    
	        } catch (InputMismatchException e) {
	            System.out.println("Entrada no válida. Debe ser un número entero");
	            reader.nextLine();
	        }
	    } while (!valido);

	    
	    // tipoPago
	    do {
	    	// Pedimos el Tipo de pago
	        System.out.print("Tipo de pago -> ");
	        tipoPago = reader.nextLine().trim();
	        
	        // Comprobar que no esta vacio
	        if (tipoPago.isEmpty()) {
	            System.out.println("Debe ingresar un tipo de pago válido");
	        }
	    } while (tipoPago.isEmpty());

	    
	    // importe
	    do {
	        try {
	        	
	        	// Pedimos el Importe
	            System.out.print("Importe -> ");
	            importe = reader.nextDouble();
	            reader.nextLine();
	            
	            // Si el importe es valido
	            if (importe > 0) {
	            	valido = true;
	            } else {
	            	System.out.println("El importe tiene que ser > a 0");
	            	valido = false;
	            }
	            
	        // Capturamos la excepcion y mostramos el mensaje de error    
	        } catch (InputMismatchException e) {
	            System.out.println("Entrada no válida. Debe ser un número entero o decimal");
	            reader.nextLine();
	        }
	    } while (!valido);

	    
	    // Creamos la factura
	    return new Factura(idMesa, tipoPago, importe);
	}

	
	// Pedido
	public static Pedido crearPedido(Scanner reader, ArrayList<Integer> facturas, ArrayList<Integer> productos) {
		
		// idFactura
		int idFactura = 0;
		
		// idProducto
		int idProducto = 0;
		
		// cantidad
		int cantidad = 0;
		
	    // Controla la salida de las interacciones con el usuario
	    boolean valido = false;
		
		
	    // idFactura
	    do {
	        try {
	        	
	        	// Pedimos el ID de la factura
	            System.out.print("\nID Factura -> ");
	            idFactura = reader.nextInt();
	            reader.nextLine();

	            // Comprobar si el idFactura está en el array de facturas
	            if (facturas.contains(idFactura)) {
	            	valido = true;
	            } else {
	            	System.out.println("La factura con el ID " + idFactura + " no existe");
	            	valido = false;
	            }
	        
	        // Capturamos la excepcion y mostramos el mensaje de error    
	        } catch (InputMismatchException e) {
	            System.out.println("Entrada no válida. Debe ser un número entero");
	            reader.nextLine();
	        }
	    } while (!valido);
	    
	    
	    // idProducto
	    do {
	        try {
	        	
	        	// Pedimos el ID del producto
	            System.out.print("ID Producto -> ");
	            idProducto = reader.nextInt();
	            reader.nextLine();

	            // Comprobar si el idProducto está en el array de productos
	            if (productos.contains(idProducto)) {
	            	valido = true;
	            } else {
	            	System.out.println("El producto con el ID " + idProducto + " no existe");
	            	valido = false;
	            }
	        
	        // Capturamos la excepcion y mostramos el mensaje de error    
	        } catch (InputMismatchException e) {
	            System.out.println("Entrada no válida. Debe ser un número entero");
	            reader.nextLine();
	        }
	    } while (!valido);
	    
	    
	    // cantidad
	    do {
	        try {
	        	
	        	// Pedimos la cantidad
	            System.out.print("Cantidad -> ");
	            cantidad = reader.nextInt();
	            reader.nextLine();
	            
	            // Si la cantidad es valida
	            if (cantidad > 0) {
	            	valido = true;
	            } else {
	            	System.out.println("La cantidad tiene que ser > a 0");
	            	valido = false;
	            }
	            
	        // Capturamos la excepcion y mostramos el mensaje de error    
	        } catch (InputMismatchException e) {
	            System.out.println("Entrada no válida. Debe ser un número entero");
	            reader.nextLine();
	        }
	    } while (!valido);
		
	    
	    // Creamos el pedido
		return new Pedido(idFactura, idProducto, cantidad);
	}
	
	
	// Producto
	public static Producto crearProducto(Scanner reader) {
		
		// denominacion
		String denominacion;
		
		// precio
		double precio = 0;
		
	    // Controla la salida de las interacciones con el usuario
	    boolean valido = false;
		
	    
	    // denominacion
	    do {
	    	// Pedimos la Denominacion
	        System.out.print("\nDenominacion -> ");
	        denominacion = reader.nextLine().trim();
	        
	        // Comprobar que no esta vacio
	        if (denominacion.isEmpty()) {
	            System.out.println("Debe ingresar una denominacion de pago válido");
	        }
	    } while (denominacion.isEmpty());
	    
	    
	    // precio
	    do {
	        try {
	        	
	        	// Pedimos el Precio
	            System.out.print("Precio -> ");
	            precio = reader.nextDouble();
	            reader.nextLine();
	            
	            // Si el precio es valido
	            if (precio > 0) {
	            	valido = true;
	            } else {
	            	System.out.println("El precio tiene que ser > a 0");
	            	valido = false;
	            }
	            
	        // Capturamos la excepcion y mostramos el mensaje de error    
	        } catch (InputMismatchException e) {
	            System.out.println("Entrada no válida. Debe ser un número entero o decimal");
	            reader.nextLine();
	        }
	    } while (!valido);
		
	    
	    // Creamos el producto
		return new Producto(denominacion, precio);
	}
}

