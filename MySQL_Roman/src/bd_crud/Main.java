package bd_crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import utils.Credenciales;

public class Main {

	public static void main(String[] args) {
		
		boolean exit = false;
		
		// Conectamos con la base de datos
		try (Connection con = DriverManager.getConnection(Credenciales.URL, Credenciales.USER, Credenciales.PASSWORD)) {
			
			// Creamos un statement para trabajar con la BD
			Statement st = con.createStatement();
			
			do {
				
				
				
			} while (!exit);
	
		// Mostramos un mensaje de error si no se puede conectar con la base de datos
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error al conectar con la base de datos");
		}

	}
	
	
	private void menu() {
		System.out.println("MySQL - Roman");
		System.out.println("-------------");
		System.out.println("1. Crear Tablas");
		System.out.println("2. Insertar");
		System.out.println("3. Listar");
		System.out.println("4. Modificar");
		System.out.println("5. Borrar");
		System.out.println("6. Eliminar Tablas");
	}
	

}
