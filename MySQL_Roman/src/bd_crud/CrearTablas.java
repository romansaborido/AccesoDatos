package bd_crud;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import utils.Utils;

public class CrearTablas {

	
	public static void crearTablas(Connection con, Scanner reader) throws SQLException {
		
		// Variables necesarias
		boolean exit = false;
		int opcion;
		int res;
		

		// Creamos Statement
		try (Statement st = con.createStatement()) {
			
			// Bucle para el menu
			do {
				
				// Pintamos el menu
				menuCrearTablas();
				
				// Pedimos al usuario que introduzca una opcion
				System.out.print("\nSeleccione una opcion -> ");
				opcion = reader.nextInt();
				reader.nextLine();
				
				// Dependiendo de la opcion que seleccione el usuario...
				switch (opcion) {
				
					// Crear tabla Mesa
					case 1 -> {
						
						// Comprobamos si la tabla mesa ya existe
						DatabaseMetaData meta = con.getMetaData();
						try (ResultSet rs = meta.getTables(null, null, "mesa", null)) {
							
							// Si no existe
							if (!rs.next()) {
								
								// Creamos la tabla
								res = st.executeUpdate(Utils.MESA);
								
								// Si la tabla se ha creado correctamente
								if (res == 0) {
									System.out.println("\n✅ Tabla MESA creada correctamente");
									
								// Si la tabla no se ha creado o no se ha creado correctamente
								} else {
									System.out.println("\n❌ La tabla MESA no se ha creado o no se ha podido crear correctamente");
								}
							
							// Si existe
							} else {
								System.out.println("\n⚠️ La tabla MESA ya existe");
							}
						}
					}
					
					// Crear tabla Factura
					case 2 -> {
						
						// Comprobamos si la tabla mesa existe (necesaria para las FK)
						DatabaseMetaData meta = con.getMetaData();
						try (ResultSet rsMesa = meta.getTables(null, null, "mesa", null)) {
							
							// Si mesa existe
							if (rsMesa.next()) {
								
								// Comprobamos si factura ya existe
								try (ResultSet rsFactura = meta.getTables(null, null, "factura", null)) {
									
									// Si no existe
									if (!rsFactura.next()) {
										
										// Creamos la tabla Factura
										res = st.executeUpdate(Utils.FACTURA);
										
										// Si la tabla se ha creado correctamente
										if (res == 0) {
											System.out.println("\n✅ Tabla FACTURA creada correctamente");
											
										// Si la tabla no se ha creado o no se ha creado correctamente
										} else {
											System.out.println("\n❌ La tabla FACTURA no se ha creado o no se ha podido crear correctamente");
										}
									
									// Si la tabla factura ya existe
									} else {
										System.out.println("\n⚠️ La tabla FACTURA ya existe");
									}
								}
							
							// Si la tabla mesa no existe
							} else {
								System.out.println("\n❌ La tabla FACTURA no se ha creado o no se ha podido crear correctamente");
								System.out.println("\n❓ Comprueba si la tabla MESA ya está creada (necesario para las FK)");
							}
						}
					}
					
					// Crear tabla Pedido
					case 3 -> {
						
						DatabaseMetaData meta = con.getMetaData();
						
						try (ResultSet rsProductos = meta.getTables(null, null, "productos", null);
							 ResultSet rsFactura = meta.getTables(null, null, "factura", null)) {
							
							if (rsProductos.next() && rsFactura.next()) {
								try (ResultSet rsPedido = meta.getTables(null, null, "pedido", null)) {
									if (!rsPedido.next()) {
										// Creamos la tabla Pedido
										res = st.executeUpdate(Utils.PEDIDO);
										
										// Si la tabla se ha creado correctamente
										if (res == 0) {
											System.out.println("\n✅ Tabla PEDIDO creada correctamente");
											
										// Si la tabla no se ha creado o no se ha creado correctamente
										} else {
											System.out.println("\n❌ La tabla PEDIDO no se ha creado o no se ha podido crear correctamente");
										}
									} else {
										System.out.println("\n⚠️ La tabla PEDIDO ya existe");
									}
								}
							} else {
								System.out.println("\n❌ La tabla PEDIDO no se ha creado o no se ha podido crear correctamente");
								System.out.println("\n❓ Comprueba si la tabla FACTURA y la tabla PRODUCTO ya están creadas (necesario para las FK)");
							}
						}
					}
					
					// Crear tabla Productos
					case 4 -> {
						
						DatabaseMetaData meta = con.getMetaData();
						try (ResultSet rs = meta.getTables(null, null, "productos", null)) {
							if (!rs.next()) {
								res = st.executeUpdate(Utils.PRODUCTOS);
								if (res == 0) {
									System.out.println("\n✅ Tabla PRODUCTOS creada correctamente");
								} else {
									System.out.println("\n❌ La tabla no se ha creado o no se ha podido crear correctamente");
								}
							} else {
								System.out.println("\n⚠️ La tabla PRODUCTOS ya existe");
							}
						}
						
					}
					
					// Crear todas las tablas
					case 5 -> {
					    
					    try {
					        // Creamos las tablas (puedes añadir comprobaciones si quieres)
					        st.executeUpdate(Utils.MESA);
					        st.executeUpdate(Utils.PRODUCTOS);
					        st.executeUpdate(Utils.FACTURA);
					        st.executeUpdate(Utils.PEDIDO);
					        
					        System.out.println("✅ Todas las tablas se han creado correctamente");
					    } catch (SQLException e) {
					        System.out.println("❌ Ha ocurrido un error al crear las tablas");
					        e.printStackTrace();
					    }
					}

					case 6 -> {
						exit = true;
					}
					default -> {
						System.out.println("Opción no válida");
					}
				}
				
			} while (!exit);
		} // Statement cerrado automáticamente
	}
	
	private static void menuCrearTablas() {
		System.out.println("\n¿Qué tablas quieres crear?");
		System.out.println("--------------------------");
		System.out.println("1. Mesa");
		System.out.println("2. Factura");
		System.out.println("3. Pedido");
		System.out.println("4. Productos");
		System.out.println("5. Todas");
		System.out.println("6. Volver");
	}
}
