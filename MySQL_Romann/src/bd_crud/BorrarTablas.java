package bd_crud;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class BorrarTablas {

	public static void borrarTablas(Connection con, Scanner reader) {
		
		boolean exit = false;
		int opcion;
		String tabla;
		
		do {
			menu();
			
			System.out.print("\nSeleccione una opcion -> ");
			opcion = reader.nextInt();
			reader.nextLine();
			
			switch (opcion) {
				case 1 -> {
					tabla = "mesa";
					eliminarTabla(con, tabla, reader);
					System.out.println();
				}
				case 2 -> { 
					tabla = "factura";
					eliminarTabla(con, tabla, reader);
					System.out.println();
				}
				case 3 -> {
					tabla = "pedido";
					eliminarTabla(con, tabla, reader);
					System.out.println();
				}
				case 4 -> {
					tabla = "productos";
					eliminarTabla(con, tabla, reader);
					System.out.println();
				}
				case 5 -> {
					eliminarTablas(con, reader);
					System.out.println();
				}
				case 6 -> {
					exit = true;
				}
				default -> {
					System.out.println("Introduzca una opción válida");
				}
			}
			
		} while (!exit);
	}  
	
	private static void menu() {
		System.out.println("\n¿Qué tabla quieres borrar?");
		System.out.println("--------------------------");
		System.out.println("1. Mesas");
		System.out.println("2. Facturas");
		System.out.println("3. Pedidos");
		System.out.println("4. Productos");
		System.out.println("5. Todas");
		System.out.println("6. Volver");
	}
	
	
	private static void eliminarTabla(Connection con, String tabla, Scanner reader) {

		// Controla que la respuesta de confirmacion del usuario sea valida
		boolean confirmacion = false;
		
		// Sentencia sql para eliminar la tabla
	    String sql = "DROP TABLE IF EXISTS " + tabla;
	    
	    // Respuesta de confirmacion del usuario
	    String respuesta = "";

	    // Creamos el statement
	    try (Statement st = con.createStatement()) {
	    	
	    	// Desactivamos el autoCommit
	    	con.setAutoCommit(false);
	    	
	    	// Listamos la tabla
	    	Listar.listarTodo(con, tabla);
	    	
	    	// Preguntamos confirmacion
	    	do {
		        System.out.print("\n¿Seguro que quieres borrar la tabla '" + tabla + "'? (s/n): ");
		        respuesta = reader.nextLine().trim().toLowerCase();
		        
		        // Validamos la respuesta del usuario
		        if (respuesta.equals("s") || respuesta.equals("n")) { 
		        	confirmacion = true; 
		        } else {
		        	System.out.println("Debe introducir 's' o 'n'");
		        }
	    	} while (!confirmacion);
	    	
	        // Si la respuesta es s (si)
	        if (respuesta.equals("s")) {
	        	
		        // Desactivar temporalmente las FK
		        st.execute("SET FOREIGN_KEY_CHECKS = 0");

		        // Borrar la tabla
		        st.executeUpdate(sql);

		        // Reactivar las FK
		        st.execute("SET FOREIGN_KEY_CHECKS = 1");
		        
		        // Hacemos commit
		        con.commit();

		        // Mostramos el resultado
		        System.out.println("\nTabla '" + tabla + "' borrada con éxito");
		        
		    // Si la respuesta es n (no)    
	        } else {
	            con.rollback();
	            System.out.println("Operación cancelada");
	        }
	      
	    // Capturamos si hay errores
	    } catch (SQLException e) {
	        System.err.println("Error al borrar tabla '" + tabla);
	    }
	}
	
	
	private static void eliminarTablas(Connection con, Scanner reader) {
		
		// Controla que la respuesta de confirmacion del usuario sea valida
		boolean confirmacion = false;
		
	    // Respuesta de confirmacion del usuario
	    String respuesta = "";
		
		// Sentencia sql para eliminar la tabla
	    String sqlMesa = "DROP TABLE IF EXISTS mesa";
	    String sqlProductos = "DROP TABLE IF EXISTS productos";
	    String sqlFactura = "DROP TABLE IF EXISTS factura";
	    String sqlPedido = "DROP TABLE IF EXISTS pedido";

		// Creamos el statement
		try (Statement st = con.createStatement()) {
			
			// Mostramos todas las tablas
			Listar.listarTodo(con, "pedido");
			Listar.listarTodo(con, "factura");
			Listar.listarTodo(con, "productos");
			Listar.listarTodo(con, "mesa");
			
	    	// Preguntamos confirmacion
	    	do {
		        System.out.print("\n¿Seguro que quieres borrar todas las tablas de la BD? (s/n): ");
		        respuesta = reader.nextLine().trim().toLowerCase();
		        
		        // Validamos la respuesta del usuario
		        if (respuesta.equals("s") || respuesta.equals("n")) { 
		        	confirmacion = true; 
		        } else {
		        	System.out.println("Debe introducir 's' o 'n'");
		        }
	    	} while (!confirmacion);
	    	
	    	// Si la respuesta es s (si)
	    	if (respuesta.equals("s")) {
	    		
				// Desactivar temporalmente las FK
			    st.execute("SET FOREIGN_KEY_CHECKS = 0");

			    // Borramos todas las tablas
			    st.executeUpdate(sqlPedido);
			    st.executeUpdate(sqlFactura);
			    st.executeUpdate(sqlProductos);
			    st.executeUpdate(sqlMesa);

			    // Reactivar las FK
			    st.execute("SET FOREIGN_KEY_CHECKS = 1");

			    // Mostramos el resultado
			    System.out.println("\nTodas las tablas han sido borradas con éxito");
			    
			// Si la respuesta es n (no)    
	    	} else {
	    		System.out.println("Operacion cancelada");
	    	}

		// Capturamos si hay errores	
		} catch (SQLException e) {
			System.err.println("Ha ocurrido un error al eliminar todas las tablas");
			e.printStackTrace();
		}
	}


}
