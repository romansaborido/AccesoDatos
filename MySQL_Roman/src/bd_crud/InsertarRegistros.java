package bd_crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Factura;
import entities.Mesa;
import entities.Pedido;
import entities.Producto;
import utils.Utils;

public class InsertarRegistros {

    public static void insertarRegistros(Connection con, Scanner reader) throws SQLException {
    	
    	// Variables para almacenar la opcion que seleccione el usuario y controlar la salida
        int opcion;
        boolean exit = false;
        

        do {
        	
        	// Imprimimos el menu
            menuInsertarRegistros();

            // Pedimos al usuario que inserte una opcion
            System.out.print("\nSeleccione una opcion -> ");
            opcion = reader.nextInt();
            reader.nextLine();
            
            // Switcheamos la opcion del menu
            switch (opcion) {
                case 1 -> insertarMesa(con, reader);
                case 2 -> insertarFactura(con, reader);
                case 3 -> insertarPedido(con, reader);
                case 4 -> insertarProducto(con, reader);
                case 5 -> exit = true;
                default -> System.out.println("Opción no válida");
            }
        } while (!exit);
    }

    
    private static void menuInsertarRegistros() {
        System.out.println("\n¿En qué tablas quieres insertar?");
        System.out.println("--------------------------");
        System.out.println("1. Mesa");
        System.out.println("2. Factura");
        System.out.println("3. Pedido");
        System.out.println("4. Productos");
        System.out.println("5. Volver");
    }


    private static void insertarMesa(Connection conn, Scanner reader) {

    	// Creamos la mesa
    	Mesa mesa = Utils.crearMesa(reader);

    	// Creamos la consulta para insertar la mesa
        String sql = "INSERT INTO mesa (numComensales, reserva) VALUES (" + mesa.numComensales() + ", " + mesa.reserva() + ")";

        // Ejecutamos la consulta
        ejecutarSQL(conn, sql, "Mesa");
    }

    
    private static void insertarFactura(Connection conn, Scanner reader) {
    	
    	// Mostramos las mesas existentes y obtenemos sus IDs
        ArrayList<Integer> mesas = mostrarMesas(conn);

        // Creamos la factura
        Factura factura = Utils.crearFactura(reader, mesas);
        
        // Creamos la consulta sql para insertar la factura
        String sql = "INSERT INTO factura (idMesa, tipoPago, importe) VALUES ("
                + factura.idMesa() + ", '" + factura.tipoPago() + "', " + factura.importe() + ")";
        
        // Ejecutamos la consulta
        ejecutarSQL(conn, sql, "Factura");
    }

    
    private static void insertarPedido(Connection conn, Scanner reader) {
    	
    	// Mostramos las facturas existentes
    	ArrayList<Integer> facturas = mostrarFacturas(conn);
        
        // Mostramos los productos existentes
    	ArrayList<Integer> productos = mostrarProductos(conn);

        // Creamos el pedido
        Pedido pedido = Utils.crearPedido(reader, facturas, productos);

        // Creamos la consulta sql para insertar la factura
        String sql = "INSERT INTO pedido (idFactura, idProducto, cantidad) VALUES ("
                + pedido.idFactura() + ", " + pedido.idProducto() + ", " + pedido.cantidad() + ")";

        // Ejecutamos la consulta
        ejecutarSQL(conn, sql, "Pedido");
    }

    
    private static void insertarProducto(Connection conn, Scanner reader) {

    	// Creamos el producto
    	Producto producto = Utils.crearProducto(reader);

    	// Creamos la consulta sql para insertar el producto
        String sql = "INSERT INTO productos (denominacion, precio) VALUES ('" + producto.denominacion() + "', " + producto.precio() + ")";

        // Ejecutamos la consulta
        ejecutarSQL(conn, sql, "Producto");
    }

    
    private static void ejecutarSQL(Connection conn, String sql, String nombreTabla) {
    	
    	// Creamos el statement
        try (Statement st = conn.createStatement()) {
        	
        	// Ejecutamos la consulta y obtenemos las filas afectadas
            int filas = st.executeUpdate(sql);
            
            // Imprimimos el resultado
            System.out.println("\n" + nombreTabla + " se inserto correctamente. Filas afectadas: " + filas);
        
        // Capturamos excepciones y mostramos un mensaje de error
        } catch (SQLException e) {
            System.out.println("Error al insertar " + nombreTabla);
        }
    }

    
    private static ArrayList<Integer> mostrarMesas(Connection conn) {
    	
    	// Creamos una lista de enteros
        ArrayList<Integer> mesas = new ArrayList<>();
        
        // Creamos el statement
        try (Statement st = conn.createStatement()) {
        	
        	// Ejecutamos la consulta y obtenemos su resultado con el ResultSet
            ResultSet rs = st.executeQuery("SELECT idMesa, numComensales, reserva FROM mesa");
            
            // Recorremos la respuesta
            while (rs.next()) {
                int id = rs.getInt("idMesa");
                System.out.println(id + ": Comensales=" + rs.getInt("numComensales") + ", Reserva=" + rs.getInt("reserva"));
                mesas.add(id);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar mesas");
        }
        
        // Devolvemos el arrayList con los IDs
        return mesas;
    }

   
    private static ArrayList<Integer> mostrarFacturas(Connection conn) {
    	
    	// Creamos una lista de enteros
    	ArrayList<Integer> facturas = new ArrayList<>();
    	
    	// Creamos el statement
        try (Statement st = conn.createStatement()) {
        	
        	// Ejecutamos la consulta y obtenemos su resultado con el ResultSet
            ResultSet rs = st.executeQuery("SELECT idFactura, idMesa, importe FROM factura");
            
            // Recorremos la respuesta
            while (rs.next()) {
                int id = rs.getInt("idFactura");
                System.out.println(id + ": Mesa=" + rs.getInt("idMesa") + ", Importe=" + rs.getDouble("importe"));
                facturas.add(id);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar facturas");
        }
        
        // Devolvemos el arrayList con los IDs
        return facturas;
    }

    
    private static ArrayList<Integer> mostrarProductos(Connection conn) {
    	
    	// Creamos una lista de enteros
    	ArrayList<Integer> productos = new ArrayList<>();
    	
    	// Creamos el statement
        try (Statement st = conn.createStatement()) {
        	
        	// Ejecutamos la consulta y obtenemos su resultado con el ResultSet
            ResultSet rs = st.executeQuery("SELECT idProducto, denominacion, precio FROM productos");
            
            // Recorremos la respuesta
            while (rs.next()) {
                int id = rs.getInt("idProducto");
                System.out.println(id + ": " + rs.getString("denominacion") + ", Precio=" + rs.getDouble("precio"));
                productos.add(id);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos:");
        }
        
        // Devolvemos el arrayList con los IDs
        return productos;
    }
}
