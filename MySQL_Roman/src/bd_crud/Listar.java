package bd_crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class Listar {

    // Método principal para listar y filtrar tablas
    public static void listarTablas(Connection connection, Scanner reader) {

        boolean exit = false;

        int opcion;
        int idMesa, numComensales, reserva;
        int idFactura, idPedido, idProducto, cantidad;
        String denominacion, tipoPago;
        double precio, importe;

        do {

            menuListarTablas();
            System.out.print("\nSeleccione una opcion -> ");
            opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {

                // Tabla Mesa
                case 1 -> {
                    boolean exitMesa = false;
                    do {

                        menuMesa();
                        System.out.print("\nSeleccione una opcion -> ");
                        opcion = reader.nextInt();
                        reader.nextLine();

                        switch (opcion) {

                            case 1 -> {

                                System.out.print("\nIntroduzca el ID de la Mesa -> ");
                                idMesa = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idMesa);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "mesa", "idMesa", idMesa, "=");
                                        case 2 -> busquedaInt(connection, "mesa", "idMesa", idMesa, "<");
                                        case 3 -> busquedaInt(connection, "mesa", "idMesa", idMesa, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 2 -> {

                                System.out.print("\n Introduzca el número de comensales -> ");
                                numComensales = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(numComensales);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "mesa", "numComensales", numComensales, "=");
                                        case 2 -> busquedaInt(connection, "mesa", "numComensales", numComensales, "<");
                                        case 3 -> busquedaInt(connection, "mesa", "numComensales", numComensales, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 3 -> {

                                System.out.print("\nIntroduzca el número de reserva -> ");
                                reserva = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(reserva);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "mesa", "reserva", reserva, "=");
                                        case 2 -> busquedaInt(connection, "mesa", "reserva", reserva, "<");
                                        case 3 -> busquedaInt(connection, "mesa", "reserva", reserva, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 4 -> exitMesa = true;
                            default -> System.out.println("\nOpción no válida");
                        }

                    } while (!exitMesa);
                }

                // Tabla Factura
                case 2 -> {
                    boolean exitFactura = false;
                    do {

                        menuFactura();
                        System.out.print("\nSeleccione una opcion -> ");
                        opcion = reader.nextInt();
                        reader.nextLine();

                        switch (opcion) {

                            case 1 -> {

                                System.out.print("\nIntroduzca el ID de la Factura -> ");
                                idFactura = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idFactura);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "factura", "idFactura", idFactura, "=");
                                        case 2 -> busquedaInt(connection, "factura", "idFactura", idFactura, "<");
                                        case 3 -> busquedaInt(connection, "factura", "idFactura", idFactura, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 2 -> {

                                System.out.print("\nIntroduzca el ID de la Mesa asociada -> ");
                                idMesa = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idMesa);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "factura", "idMesa", idMesa, "=");
                                        case 2 -> busquedaInt(connection, "factura", "idMesa", idMesa, "<");
                                        case 3 -> busquedaInt(connection, "factura", "idMesa", idMesa, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 3 -> {

                                System.out.print("\nIntroduzca el tipo de pago -> ");
                                tipoPago = reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarVarChar(tipoPago);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaString(connection, "factura", "tipoPago", tipoPago, "=");
                                        case 2 -> busquedaString(connection, "factura", "tipoPago", tipoPago, "LIKE");
                                        case 3 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 4 -> {

                                System.out.print("\nIntroduzca el importe -> ");
                                importe = reader.nextDouble();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt((int) importe);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "factura", "importe", (int) importe, "=");
                                        case 2 -> busquedaInt(connection, "factura", "importe", (int) importe, "<");
                                        case 3 -> busquedaInt(connection, "factura", "importe", (int) importe, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 5 -> exitFactura = true;
                            default -> System.out.println("\nOpción no válida");
                        }

                    } while (!exitFactura);
                }

                // Tabla Pedido
                case 3 -> {
                    boolean exitPedido = false;
                    do {

                        menuPedido();
                        System.out.print("\nSeleccione una opcion -> ");
                        opcion = reader.nextInt();
                        reader.nextLine();

                        switch (opcion) {

                            case 1 -> {

                                System.out.print("Introduzca el ID del Pedido -> ");
                                idPedido = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idPedido);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "pedido", "idPedido", idPedido, "=");
                                        case 2 -> busquedaInt(connection, "pedido", "idPedido", idPedido, "<");
                                        case 3 -> busquedaInt(connection, "pedido", "idPedido", idPedido, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 2 -> {

                                System.out.print("\nIntroduzca el ID de la Factura asociada -> ");
                                idFactura = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idFactura);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "pedido", "idFactura", idFactura, "=");
                                        case 2 -> busquedaInt(connection, "pedido", "idFactura", idFactura, "<");
                                        case 3 -> busquedaInt(connection, "pedido", "idFactura", idFactura, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 3 -> {

                                System.out.print("\nIntroduzca el ID del Producto -> ");
                                idProducto = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idProducto);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "pedido", "idProducto", idProducto, "=");
                                        case 2 -> busquedaInt(connection, "pedido", "idProducto", idProducto, "<");
                                        case 3 -> busquedaInt(connection, "pedido", "idProducto", idProducto, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 4 -> {

                                System.out.print("\nIntroduzca la cantidad -> ");
                                cantidad = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(cantidad);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "pedido", "cantidad", cantidad, "=");
                                        case 2 -> busquedaInt(connection, "pedido", "cantidad", cantidad, "<");
                                        case 3 -> busquedaInt(connection, "pedido", "cantidad", cantidad, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 5 -> exitPedido = true;
                            default -> System.out.println("\nOpción no válida");
                        }

                    } while (!exitPedido);
                }

                // Tabla Productos
                case 4 -> {
                    boolean exitProductos = false;
                    do {

                        menuProductos();
                        System.out.print("\nSeleccione una opcion -> ");
                        opcion = reader.nextInt();
                        reader.nextLine();

                        switch (opcion) {

                            case 1 -> {

                                System.out.print("\nIntroduzca el ID del Producto -> ");
                                idProducto = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idProducto);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "productos", "idProducto", idProducto, "=");
                                        case 2 -> busquedaInt(connection, "productos", "idProducto", idProducto, "<");
                                        case 3 -> busquedaInt(connection, "productos", "idProducto", idProducto, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 2 -> {

                                System.out.print("\nIntroduzca la denominación -> ");
                                denominacion = reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarVarChar(denominacion);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaString(connection, "productos", "denominacion", denominacion, "=");
                                        case 2 -> busquedaString(connection, "productos", "denominacion", denominacion, "LIKE");
                                        case 3 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 3 -> {

                                System.out.print("\nIntroduzca el precio -> ");
                                precio = reader.nextDouble();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt((int) precio);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "productos", "precio", (int) precio, "=");
                                        case 2 -> busquedaInt(connection, "productos", "precio", (int) precio, "<");
                                        case 3 -> busquedaInt(connection, "productos", "precio", (int) precio, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            case 4 -> exitProductos = true;
                            default -> System.out.println("\nOpción no válida");
                        }

                    } while (!exitProductos);
                }

                case 5 -> exit = true;
                default -> System.out.println("\nOpción no válida");
            }

        } while (!exit);
    }

    // ---------------- Menús ----------------

    private static void filtrarVarChar(String dato) {
        System.out.println("\n¿Cómo quieres filtrar?");
        System.out.println("----------------------");
        System.out.println("1. = " + dato);
        System.out.println("2. Contiene " + dato);
        System.out.println("3. Volver");
    }

    private static void filtrarInt(int dato) {
        System.out.println("\n¿Cómo quieres filtrar?");
        System.out.println("----------------------");
        System.out.println("1. = " + dato);
        System.out.println("2. < " + dato);
        System.out.println("3. > " + dato);
        System.out.println("4. Volver");
    }

    private static void menuListarTablas() {
        System.out.println("\n¿Qué tablas quieres listar?");
        System.out.println("--------------------------");
        System.out.println("1. Mesa");
        System.out.println("2. Factura");
        System.out.println("3. Pedido");
        System.out.println("4. Productos");
        System.out.println("5. Salir");
    }

    private static void menuMesa() {
        System.out.println("\n¿Por qué campo quieres buscar?");
        System.out.println("------------------------------");
        System.out.println("1. idMesa");
        System.out.println("2. numComensales");
        System.out.println("3. reserva");
        System.out.println("4. Volver");
    }

    private static void menuFactura() {
        System.out.println("\n¿Por qué campo quieres buscar?");
        System.out.println("------------------------------");
        System.out.println("1. idFactura");
        System.out.println("2. idMesa");
        System.out.println("3. tipoPago");
        System.out.println("4. importe");
        System.out.println("5. Volver");
    }

    private static void menuPedido() {
        System.out.println("\n¿Por qué campo quieres buscar?");
        System.out.println("------------------------------");
        System.out.println("1. idPedido");
        System.out.println("2. idFactura");
        System.out.println("3. idProducto");
        System.out.println("4. cantidad");
        System.out.println("5. Volver");
    }

    private static void menuProductos() {
        System.out.println("\n¿Por qué campo quieres buscar?");
        System.out.println("------------------------------");
        System.out.println("1. idProducto");
        System.out.println("2. denominacion");
        System.out.println("3. precio");
        System.out.println("4. Volver");
    }

    // ---------------- Búsquedas ----------------

    private static void busquedaInt(Connection connection, String tabla, String campo, int valor, String operador) {
        try {

            String sql = "SELECT * FROM " + tabla + " WHERE " + campo + " " + operador + " ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, valor);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            System.out.println("\nResultados:");
            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    String nombreColumna = meta.getColumnName(i);
                    String valorColumna = rs.getString(i);
                    System.out.println(nombreColumna + ": " + valorColumna);
                }
                System.out.println("--------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void busquedaString(Connection connection, String tabla, String campo, String valor, String operador) {
        try {

            String sql;
            if (operador.equalsIgnoreCase("LIKE")) {
                sql = "SELECT * FROM " + tabla + " WHERE " + campo + " LIKE ?";
                valor = "%" + valor + "%";
            } else {
                sql = "SELECT * FROM " + tabla + " WHERE " + campo + " = ?";
            }

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, valor);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            System.out.println("\nResultados:");
            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    String nombreColumna = meta.getColumnName(i);
                    String valorColumna = rs.getString(i);
                    System.out.println(nombreColumna + ": " + valorColumna);
                }
                System.out.println("--------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
