package bd_crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.Factura;
import entities.Mesa;
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

    // Pinta el menu
    private static void menuInsertarRegistros() {
        System.out.println("\n¿En qué tablas quieres insertar?");
        System.out.println("--------------------------");
        System.out.println("1. Mesa");
        System.out.println("2. Factura");
        System.out.println("3. Pedido");
        System.out.println("4. Productos");
        System.out.println("5. Salir");
    }


    // Pide e inserta los datos de una mesa
    private static void insertarMesa(Connection conn, Scanner reader) {

    	Mesa mesa = Utils.crearMesa(reader);

        String sql = "INSERT INTO mesa (numComensales, reserva) VALUES (" + mesa.numComensales() + ", " + mesa.reserva() + ")";

        ejecutarSQL(conn, sql, "Mesa");
    }

    // Pide e inserta los datos de una factura
    private static void insertarFactura(Connection conn, Scanner reader) {
        mostrarMesas(conn);

        Factura factura = Utils.crearFactura(reader);
        
        String sql = "INSERT INTO factura (idMesa, tipoPago, importe) VALUES ("
                + factura.idMesa() + ", '" + factura.tipoPago() + "', " + factura.importe() + ")";

        ejecutarSQL(conn, sql, "Factura");
    }

    // Pide e inserta los datos de un pedido
    private static void insertarPedido(Connection conn, Scanner reader) {
        int idFactura = seleccionarFactura(conn, reader);
        int idProducto = seleccionarProducto(conn, reader);

        System.out.print("Introduzca la cantidad: ");
        int cantidad = reader.nextInt();
        reader.nextLine();

        String sql = "INSERT INTO pedido (idFactura, idProducto, cantidad) VALUES ("
                + idFactura + ", " + idProducto + ", " + cantidad + ")";

        ejecutarSQL(conn, sql, "Pedido");
    }

    // Pide e inserta los datos de un producto
    private static void insertarProducto(Connection conn, Scanner reader) {
        System.out.print("Introduzca la denominación del producto: ");
        String denominacion = reader.nextLine();

        System.out.print("Introduzca el precio: ");
        double precio = reader.nextDouble();
        reader.nextLine();

        String sql = "INSERT INTO productos (denominacion, precio) VALUES ('" + denominacion + "', " + precio + ")";

        ejecutarSQL(conn, sql, "Producto");
    }


    // Ejecuta las sentencias sql, es llamada en cada una de las funciones anteriores
    private static void ejecutarSQL(Connection conn, String sql, String nombreTabla) {
        try (Statement st = conn.createStatement()) {
            int filas = st.executeUpdate(sql);
            System.out.println(nombreTabla + " insertado correctamente. Filas afectadas: " + filas);
        } catch (SQLException e) {
            System.out.println("Error al insertar " + nombreTabla + ": " + e.getMessage());
        }
    }

    // Muestra los id de las mesas y pide al usuario que seleccione uno
    private static void mostrarMesas(Connection conn) {
        List<Integer> mesas = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT idMesa, numComensales, reserva FROM mesa");
            while (rs.next()) {
                int id = rs.getInt("idMesa");
                System.out.println(id + ": Comensales=" + rs.getInt("numComensales") + ", Reserva=" + rs.getInt("reserva"));
                mesas.add(id);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar mesas: " + e.getMessage());
        }
    }

    // Muestra los id de las facturas y pide al usuario que introduzca uno
    private static int seleccionarFactura(Connection conn, Scanner reader) {
        List<Integer> facturas = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT idFactura, idMesa, importe FROM factura");
            while (rs.next()) {
                int id = rs.getInt("idFactura");
                System.out.println(id + ": Mesa=" + rs.getInt("idMesa") + ", Importe=" + rs.getDouble("importe"));
                facturas.add(id);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar facturas: " + e.getMessage());
        }

        int idFactura;
        do {
            System.out.print("Seleccione el ID de la factura: ");
            idFactura = reader.nextInt();
            reader.nextLine();
            if (!facturas.contains(idFactura)) {
                System.out.println("ID no válido, intente de nuevo.");
            }
        } while (!facturas.contains(idFactura));

        return idFactura;
    }

    // Muestra los id de los productos y pide al usuario que introduzca uno
    private static int seleccionarProducto(Connection conn, Scanner reader) {
        List<Integer> productos = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT idProducto, denominacion, precio FROM productos");
            while (rs.next()) {
                int id = rs.getInt("idProducto");
                System.out.println(id + ": " + rs.getString("denominacion") + ", Precio=" + rs.getDouble("precio"));
                productos.add(id);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        int idProducto;
        do {
            System.out.print("Seleccione el ID del producto: ");
            idProducto = reader.nextInt();
            reader.nextLine();
            if (!productos.contains(idProducto)) {
                System.out.println("ID no válido, intente de nuevo.");
            }
        } while (!productos.contains(idProducto));

        return idProducto;
    }
}
