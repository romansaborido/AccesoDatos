package bd_crud;

import java.sql.*;
import java.util.Scanner;

public class BorrarRegistros {

	
    public static void eliminarRegistros(Scanner reader, Connection conn) {
    	
        int opcion;
        boolean exit = false;
        
        do {
        	menu();
            System.out.print("\nSeleccione una opción -> ");

            opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
                case 1 -> {
                    eliminarPorId("mesa", "idMesa", conn, reader);
                }    
                case 2 -> {
                	try {
						Statement st = conn.createStatement();
						st.execute("SET FOREIGN_KEY_CHECKS = 0");
						eliminarPorId("factura", "idFactura", conn, reader);
						st.execute("SET FOREIGN_KEY_CHECKS = 1");
					} catch (SQLException e) {
						System.out.println("Error al eliminar la factura");
					}
                }    
                case 3 -> {
                	try {
						Statement st = conn.createStatement();
						st.execute("SET FOREIGN_KEY_CHECKS = 0");
						eliminarPorId("pedido", "idPedido", conn, reader);
						st.execute("SET FOREIGN_KEY_CHECKS = 1");
					} catch (SQLException e) {
						System.out.println("Error al eliminar el pedido");
					}
                }    
                case 4 -> {
                	eliminarPorId("productos", "idProducto", conn, reader);
                }
                case 5 -> {
                	exit = true;
                }
                default -> {
                    System.out.println("Opción no válida. Intenta de nuevo");
                }    
            }
        } while (!exit);
    }


    private static void eliminarPorId(String tabla, String campo, Connection conn, Scanner reader) {
 
    	int id;
    	boolean idValido = false;
    	
    	do {
    		System.out.print("\nIntroduzca el ID que deseas eliminar de la tabla " + tabla + " -> ");
            id = reader.nextInt();
            
            // Validamos el ID
            if (!existeId(tabla, campo, id, conn)) {
                System.out.println("❌ El ID " + id + " no existe en la tabla " + tabla);
            } else {
            	idValido = true;
            }
    	} while (!idValido);
        

        try {
            // Eliminamos
            String sqlDelete = "DELETE FROM " + tabla + " WHERE " + campo + " = ?";
            PreparedStatement pst = conn.prepareStatement(sqlDelete);
            pst.setInt(1, id);

            int filas = pst.executeUpdate();

            if (filas > 0) {
                System.out.println("✔ Registro con ID " + id + " eliminado correctamente de la tabla " + tabla + ".");
            } else {
                System.out.println("❌ No se pudo eliminar el registro.");
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar: " + e.getMessage());
        }
    }


    private static boolean existeId(String tabla, String campo, int id, Connection conn) {
    	
    	boolean res = false;
    	
        String sqlCheck = "SELECT COUNT(*) FROM " + tabla + " WHERE " + campo + " = ?";
        PreparedStatement pst;
        
		try {
			pst = conn.prepareStatement(sqlCheck);

			pst.setInt(1, id);
			
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            res = rs.getInt(1) > 0;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return res;
    }
    
    
    private static void menu() {
        System.out.println("\n¿Sobre qué tabla quieres eliminar?");
        System.out.println("-------------------------------------");
        System.out.println("1. Mesa");
        System.out.println("2. Factura");
        System.out.println("3. Pedido");
        System.out.println("4. Producto");
        System.out.println("5. Volver");
    }
}
