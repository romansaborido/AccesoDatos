package bd_crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InsertarRegistros {

    public static void insertarRegistros(Connection con, Scanner reader) throws SQLException {

        int opcion;
        boolean exit = false;

        do {
            menuInsertarRegistros();

            System.out.print("\nSeleccione una opcion -> ");
            opcion = reader.nextInt();
            reader.nextLine(); // limpiar buffer

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
        System.out.println("5. Salir");
    }

    // ------------------- METODOS DE INSERCION -------------------

    private static void insertarMesa(Connection conn, Scanner reader) {
        System.out.print("Introduzca el número de comensales: ");
        int numComensales = reader.nextInt();

        System.out.print("Introduzca el número de reserva: ");
        int reserva = reader.nextInt();
        reader.nextLine();

        String sql = "INSERT INTO mesa (numComensales, reserva) VALUES (" + numComensales + ", " + reserva + ")";

        ejecutarSQL(conn, sql, "Mesa");
    }

    private static void insertarFactura(Connection conn, Scanner reader) {
        int idMesa = seleccionarMesa(conn, reader);

        System.out.print("Introduzca el tipo de pago: ");
        String tipoPago = reader.nextLine();

        System.out.print("Introduzca el importe: ");
        double importe = reader.nextDouble();
        reader.nextLine();

        String sql = "INSERT INTO factura (idMesa, tipoPago, importe) VALUES ("
                + idMesa + ", '" + tipoPago + "', " + importe + ")";

        ejecutarSQL(conn, sql, "Factura");
    }

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

    private static void insertarProducto(Connection conn, Scanner reader) {
        System.out.print("Introduzca la denominación del producto: ");
        String denominacion = reader.nextLine();

        System.out.print("Introduzca el precio: ");
        double precio = reader.nextDouble();
        reader.nextLine();

        String sql = "INSERT INTO productos (denominacion, precio) VALUES ('" + denominacion + "', " + precio + ")";

        ejecutarSQL(conn, sql, "Producto");
    }

    // ------------------- METODOS GENERICOS -------------------

    private static void ejecutarSQL(Connection conn, String sql, String nombreTabla) {
        try (Statement st = conn.createStatement()) {
            int filas = st.executeUpdate(sql);
            System.out.println(nombreTabla + " insertado correctamente. Filas afectadas: " + filas);
        } catch (SQLException e) {
            System.out.println("Error al insertar " + nombreTabla + ": " + e.getMessage());
        }
    }

    // ------------------- SELECCION DE FKS -------------------

    private static int seleccionarMesa(Connection conn, Scanner reader) {
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

        int idMesa;
        do {
            System.out.print("Seleccione el ID de la mesa: ");
            idMesa = reader.nextInt();
            reader.nextLine();
            if (!mesas.contains(idMesa)) {
                System.out.println("ID no válido, intente de nuevo.");
            }
        } while (!mesas.contains(idMesa));

        return idMesa;
    }

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
