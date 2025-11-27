package bd_crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Modificar {

    public static void modificarRegistro(Connection con, Scanner reader) {

    	// Almacena la opcion del menu seleccionada por el usuario
        int opcion;
        
        // Controla la salida del menu
        boolean exit = false;
        
        // Nombre de la tabla sobre la que se va a buscar
        String tabla = null;
        
        // Nombre del campo ID que se va a buscar
        String campoId = null;
        
        // ID del registro a buscar
        int id;

        
        // Imprimimos el menu
        do {
            menuModificarTablas();
            System.out.print("\nSeleccione una opcion -> ");
            opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
            
            	// Mesa
                case 1 -> {
                    tabla = "mesa";
                    campoId = "idMesa";
                    
                    // Pedimos el ID
                    id = pedirId(reader);

                    // Si el registro existe
                    if (existeRegistro(con, tabla, campoId, id)) {
                    	
                    	// Pedimos al usuario que introduzca la nueva mesa y updateamos
                        try {
                            con.setAutoCommit(false);
                            modificarMesa(con, reader, id);
                            confirmarCambios(con, reader);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try { con.setAutoCommit(true); } catch (SQLException ignored) {}
                        }
                    
                    // Si el registro no existe    
                    } else {
                        System.out.println("\n ❌ No existe una mesa con ese ID");
                    }
                }
                
                // Factura
                case 2 -> {
                    tabla = "factura";
                    campoId = "idFactura";
                    
                    // Pedimos el ID
                    id = pedirId(reader);

                    // Si el registro existe
                    if (existeRegistro(con, tabla, campoId, id)) {
                    	
                    	// Pedimos al usuario que introduzca la nueva factura y updateamos
                        try {
                            con.setAutoCommit(false);
                            modificarFactura(con, reader, id);
                            confirmarCambios(con, reader);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try { con.setAutoCommit(true); } catch (SQLException ignored) {}
                        }
                    
                    // Si el registro no existe    
                    } else {
                        System.out.println("\n ❌ No existe una factura con ese ID");
                    }
                }
                
                // Pedido
                case 3 -> {
                    tabla = "pedido";
                    campoId = "idPedido";
                    
                    // Pedimos el ID
                    id = pedirId(reader);

                    // Si el registro existe
                    if (existeRegistro(con, tabla, campoId, id)) {
                    	
                    	// Pedimos al usuario que introduzca el nuevo pedido y updateamos
                        try {
                            con.setAutoCommit(false);
                            modificarPedido(con, reader, id);
                            confirmarCambios(con, reader);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try { con.setAutoCommit(true); } catch (SQLException ignored) {}
                        }
                    
                    // Si el pedido no existe    
                    } else {
                        System.out.println("\n ❌ No existe un pedido con ese ID");
                    }
                }
                
                // Productos
                case 4 -> {
                    tabla = "productos";
                    campoId = "idProducto";
                    
                    // Pedimos el ID
                    id = pedirId(reader);

                    // Si el registro existe
                    if (existeRegistro(con, tabla, campoId, id)) {
                        try {
                            con.setAutoCommit(false);
                            modificarProducto(con, reader, id);
                            confirmarCambios(con, reader);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try { con.setAutoCommit(true); } catch (SQLException ignored) {}
                        }
                    
                    // Si el registro no existe    
                    } else {
                        System.out.println("\n ❌ No existe un producto con ese ID");
                    }
                }
                
                // Salir
                case 5 -> exit = true;
                
                // Opcion no valida
                default -> System.out.println("Opción no válida");
            }
        } while (!exit);
    }

    private static void menuModificarTablas() {
        System.out.println("\n¿Qué tablas quieres modificar?");
        System.out.println("--------------------------");
        System.out.println("1. Mesa");
        System.out.println("2. Factura");
        System.out.println("3. Pedido");
        System.out.println("4. Productos");
        System.out.println("5. Volver");
    }

    private static int pedirId(Scanner reader) {
        int id = 0;
        boolean idValido = false;
        do {
            System.out.print("\nIntroduzca el ID del registro a modificar -> ");
            try {
                id = reader.nextInt();
                idValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe introducir un número entero");
                reader.nextLine();
            }
        } while (!idValido);
        return id;
    }

    private static boolean existeRegistro(Connection conn, String tabla, String campoId, int id) {
        boolean res = false;
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE " + campoId + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    res = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    private static void confirmarCambios(Connection conn, Scanner reader) {
        reader.nextLine();
        System.out.print("\n¿Desea confirmar los cambios? (S/N) -> ");
        String respuesta = reader.nextLine().trim().toUpperCase();
        try {
            if (respuesta.equals("S")) {
                conn.commit();
                System.out.println("Cambios confirmados.");
            } else {
                conn.rollback();
                System.out.println("Cambios cancelados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void modificarMesa(Connection conn, Scanner reader, int id) {
        System.out.print("\nNúmero de comensales -> ");
        int numComensales = reader.nextInt();
        System.out.print("¿Está reservada? (1=Sí, 0=No) -> ");
        int reserva = reader.nextInt();

        String sql = "UPDATE mesa SET numComensales=?, reserva=? WHERE idMesa=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numComensales);
            stmt.setInt(2, reserva);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void modificarProducto(Connection conn, Scanner reader, int id) {
        reader.nextLine();
        System.out.print("Denominación -> ");
        String denominacion = reader.nextLine();
        System.out.print("Precio -> ");
        double precio = reader.nextDouble();

        String sql = "UPDATE productos SET denominacion=?, precio=? WHERE idProducto=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, denominacion);
            stmt.setDouble(2, precio);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void modificarFactura(Connection conn, Scanner reader, int id) {
        System.out.print("idMesa -> ");
        int idMesa = reader.nextInt();
        reader.nextLine();
        System.out.print("Tipo de pago -> ");
        String tipoPago = reader.nextLine();
        System.out.print("Importe -> ");
        double importe = reader.nextDouble();

        String sql = "UPDATE factura SET idMesa=?, tipoPago=?, importe=? WHERE idFactura=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMesa);
            stmt.setString(2, tipoPago);
            stmt.setDouble(3, importe);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void modificarPedido(Connection conn, Scanner reader, int id) {
        System.out.print("idFactura -> ");
        int idFactura = reader.nextInt();
        System.out.print("idProducto -> ");
        int idProducto = reader.nextInt();
        System.out.print("Cantidad -> ");
        int cantidad = reader.nextInt();

        String sql = "UPDATE pedido SET idFactura=?, idProducto=?, cantidad=? WHERE idPedido=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFactura);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidad);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
