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
                        	
                        	// Desactivamos el autoCommit
                            con.setAutoCommit(false);
                            
                            // Modificamos la mesa
                            modificarMesa(con, reader, id);
                            
                            // Confirmamos los cambios
                            confirmarCambios(con, reader);
                            
                        } catch (SQLException e) {
                            System.err.println("Error al modificar la mesa");
                        } finally {
                            try { con.setAutoCommit(true); } catch (SQLException ex) {
                            	System.err.println("Error al modificar la mesa");
                            	}
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
                            try { con.setAutoCommit(true); } catch (SQLException ex) {
                            	System.err.println("Error al modificar la factura");
                            	}
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
                            try { con.setAutoCommit(true); } catch (SQLException ex) {
                            	System.err.println("Error al modificar el pedido");
                            	}
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
                            try { con.setAutoCommit(true); } catch (SQLException ex) {
                            	System.err.println("Error al modificar el producto");
                            	}
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
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
        	
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    res = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("No existe un campo en la tabla " + tabla + " con el ID " + id);
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
            System.err.println("Error al confirmar los cambios");
        }
    }

    private static void modificarMesa(Connection conn, Scanner reader, int id) {
        
    	int numComensales;
    	int reserva;
    	
    	System.out.print("\nNúmero de comensales -> ");
        numComensales = reader.nextInt();
        
        System.out.print("Número de reserva -> ");
        reserva = reader.nextInt();

        String sql = "UPDATE mesa SET numComensales=?, reserva=? WHERE idMesa=?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	
            stmt.setInt(1, numComensales);
            stmt.setInt(2, reserva);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
        	System.err.println("Error al updatear la mesa con ID " + id);
        }
    }

    private static void modificarProducto(Connection conn, Scanner reader, int id) {
        
    	String denominacion;
    	double precio;
    	
    	reader.nextLine();
        System.out.print("Denominación -> ");
        denominacion = reader.nextLine();
        
        System.out.print("Precio -> ");
        precio = reader.nextDouble();

        String sql = "UPDATE productos SET denominacion=?, precio=? WHERE idProducto=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
        	
            ps.setString(1, denominacion);
            ps.setDouble(2, precio);
            ps.setInt(3, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
        	System.err.println("Error al updatear el producto con ID " + id);
        }
    }

    private static void modificarFactura(Connection conn, Scanner reader, int id) {
        
    	int idMesa;
    	String tipoPago;
    	double importe;
    	
    	System.out.print("idMesa -> ");
        idMesa = reader.nextInt();
        reader.nextLine();
        
        System.out.print("Tipo de pago -> ");
        tipoPago = reader.nextLine();
        
        System.out.print("Importe -> ");
        importe = reader.nextDouble();

        String sql = "UPDATE factura SET idMesa=?, tipoPago=?, importe=? WHERE idFactura=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
        	
            ps.setInt(1, idMesa);
            ps.setString(2, tipoPago);
            ps.setDouble(3, importe);
            ps.setInt(4, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
        	System.err.println("Error al updatear la factura con ID " + id);
        }
    }

    private static void modificarPedido(Connection conn, Scanner reader, int id) {
    	
    	int idFactura;
    	int idProducto;
    	int cantidad;
    	
        System.out.print("idFactura -> ");
        idFactura = reader.nextInt();
        
        System.out.print("idProducto -> ");
        idProducto = reader.nextInt();
        
        System.out.print("Cantidad -> ");
        cantidad = reader.nextInt();

        String sql = "UPDATE pedido SET idFactura=?, idProducto=?, cantidad=? WHERE idPedido=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
        	
            ps.setInt(1, idFactura);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.setInt(4, id);
            ps.executeUpdate();
           
        } catch (SQLException e) {
        	System.err.println("Error al updatear el pedido con ID " + id);
        }
    }
}
