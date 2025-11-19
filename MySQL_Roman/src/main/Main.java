package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.util.Scanner;

import bd_crud.CrearTablas;
import bd_crud.InsertarRegistros;
import utils.Utils;

public class Main {

	public static void main(String[] args) {
		
		boolean exit = false;
		int opcion;
		

		
		// Creamos el scanner
		Scanner reader = new Scanner(System.in);
		
		// Conectamos con la base de datos
		try (Connection con = DriverManager.getConnection(Utils.URL, Utils.USER, Utils.PASSWORD)) {
			
			// Creamos un statement para trabajar con la BD
			Statement st = con.createStatement();
			
			
			// Bucle para el menu
			do {
				
				// Pintamos el menu
				menu();
				
				// Pedimos al usuario que introduzca una opcion
				System.out.print("\nSeleccione una opcion -> ");
				opcion = reader.nextInt();
				
				// Creamos un switch para las distintas opciones del menu
				switch (opcion) {
					case 1 -> {
						CrearTablas.crearTablas(con, reader);
					}
					case 2 -> {
						InsertarRegistros.insertarRegistros(con, reader);
					}
					case 3 -> {
						// Listar registros
					}
					case 4 -> {
						// Modificar registros
					}
					case 5 -> {
						// Borrar registros
					}
					case 6 -> {
						// Eliminar tablas
					}
					default -> {
						System.out.println("Opción no válida");
					}
				}
				
			} while (!exit);
	
		// Mostramos un mensaje de error si no se puede conectar con la base de datos
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error al conectar con la base de datos");
			e.printStackTrace();
		}
		
		// Cerramos el scanner
		reader.close();

	}
	
	
	private static void menu() {
		System.out.println("\nMySQL - Roman");
		System.out.println("-------------");
		System.out.println("1. Crear Tablas");
		System.out.println("2. Insertar");
		System.out.println("3. Listar");
		System.out.println("4. Modificar");
		System.out.println("5. Borrar");
		System.out.println("6. Eliminar Tablas");
	}
	

}
