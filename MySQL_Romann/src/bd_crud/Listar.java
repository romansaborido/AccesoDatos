package bd_crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class Listar {

    
    public static void listarTablas(Connection connection, Scanner reader) {

        // Controla la salida del menu principal
        boolean exit = false;

        // Variable para almacenar la seleccion del usuario en los menus
        int opcion;
        
        // Campos de la BD
        int idMesa, numComensales, reserva;
        int idFactura, idPedido, idProducto, cantidad;
        String denominacion, tipoPago;
        double precio, importe;

        
        do {

            // Muestra el menu de tablas
            menuListarTablas();
            System.out.print("\nSeleccione una opcion -> ");
            opcion = reader.nextInt();
            reader.nextLine();
            
            
            switch (opcion) {

                // Filtrado tabla Mesa
                case 1 -> {
                	
                	// Controla la salida del menu
                    boolean exitMesa = false;
                    
                    do {
                    	
                    	// Imprimimos el menu
                        menuMesa();
                        System.out.print("\nSeleccione una opcion -> ");
                        opcion = reader.nextInt();
                        reader.nextLine();

                        switch (opcion) {
                            
                            // Listar todos los registros sin filtro
                            case 1 -> listarTodo(connection, "mesa");
                            
                            // Filtrado por el campo idMesa (valor entero)
                            case 2 -> { 
                                System.out.print("\nIntroduzca el ID de la Mesa -> ");
                                idMesa = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    // Muestra el submenú de operadores de comparación (=, <, >)
                                    filtrarInt(idMesa);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "mesa", "idMesa", idMesa, "=");
                                        case 2 -> busquedaInt(connection, "mesa", "idMesa", idMesa, "<");
                                        case 3 -> busquedaInt(connection, "mesa", "idMesa", idMesa, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Filtrado por el campo numComensales (valor entero)
                            case 3 -> { 

                                System.out.print("\n Introduzca el número de comensales -> ");
                                numComensales = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(numComensales);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "mesa", "numComensales", numComensales, "=");
                                        case 2 -> busquedaInt(connection, "mesa", "numComensales", numComensales, "<");
                                        case 3 -> busquedaInt(connection, "mesa", "numComensales", numComensales, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Filtrado por el campo reserva (valor entero)
                            case 4 -> { 

                                System.out.print("\nIntroduzca el número de reserva -> ");
                                reserva = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(reserva);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "mesa", "reserva", reserva, "=");
                                        case 2 -> busquedaInt(connection, "mesa", "reserva", reserva, "<");
                                        case 3 -> busquedaInt(connection, "mesa", "reserva", reserva, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Volver al menú de tablas
                            case 5 -> exitMesa = true;
                            default -> System.out.println("\nOpción no válida");
                        }

                    } while (!exitMesa);
                }

                // Lógica de filtrado para la Tabla Factura
                case 2 -> {
                    boolean exitFactura = false;
                    do {

                        menuFactura();
                        System.out.print("\nSeleccione una opcion -> ");
                        opcion = reader.nextInt();
                        reader.nextLine();

                        switch (opcion) {
                            
                            // Listar todos los registros
                            case 1 -> listarTodo(connection, "factura"); 
                            
                            // Filtrado por idFactura
                            case 2 -> { 

                                System.out.print("\nIntroduzca el ID de la Factura -> ");
                                idFactura = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idFactura);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "factura", "idFactura", idFactura, "=");
                                        case 2 -> busquedaInt(connection, "factura", "idFactura", idFactura, "<");
                                        case 3 -> busquedaInt(connection, "factura", "idFactura", idFactura, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Filtrado por idMesa
                            case 3 -> { 

                                System.out.print("\nIntroduzca el ID de la Mesa asociada -> ");
                                idMesa = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idMesa);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "factura", "idMesa", idMesa, "=");
                                        case 2 -> busquedaInt(connection, "factura", "idMesa", idMesa, "<");
                                        case 3 -> busquedaInt(connection, "factura", "idMesa", idMesa, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Filtrado por tipoPago (valor de texto/VARCHAR)
                            case 4 -> { 

                                System.out.print("\nIntroduzca el tipo de pago -> ");
                                tipoPago = reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    // Muestra el submenú de operadores de comparación (=, LIKE)
                                    filtrarVarChar(tipoPago);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaString(connection, "factura", "tipoPago", tipoPago, "=");
                                        case 2 -> busquedaString(connection, "factura", "tipoPago", tipoPago, "LIKE");
                                        case 3 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Filtrado por importe (valor numérico double, convertido a int para la función de búsqueda)
                            case 5 -> { 

                                System.out.print("\nIntroduzca el importe -> ");
                                importe = reader.nextDouble();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    // Se usa la función de filtro Int al convertir temporalmente el importe a int
                                    filtrarInt((int) importe);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "factura", "importe", (int) importe, "=");
                                        case 2 -> busquedaInt(connection, "factura", "importe", (int) importe, "<");
                                        case 3 -> busquedaInt(connection, "factura", "importe", (int) importe, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nLa opción seleccionada no es válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Volver al menú de tablas
                            case 6 -> exitFactura = true;
                            default -> System.out.println("\nOpción no válida");
                        }

                    } while (!exitFactura);
                }

                // Lógica de filtrado para la Tabla Pedido
                case 3 -> {
                    boolean exitPedido = false;
                    do {

                        menuPedido();
                        System.out.print("\nSeleccione una opcion -> ");
                        opcion = reader.nextInt();
                        reader.nextLine();

                        switch (opcion) {
                            
                            // Listar todos los registros
                            case 1 -> listarTodo(connection, "pedido");
                            
                            // Filtrado por idPedido
                            case 2 -> { 

                                System.out.print("Introduzca el ID del Pedido -> ");
                                idPedido = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idPedido);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "pedido", "idPedido", idPedido, "=");
                                        case 2 -> busquedaInt(connection, "pedido", "idPedido", idPedido, "<");
                                        case 3 -> busquedaInt(connection, "pedido", "idPedido", idPedido, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Filtrado por idFactura
                            case 3 -> { 

                                System.out.print("\nIntroduzca el ID de la Factura asociada -> ");
                                idFactura = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idFactura);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "pedido", "idFactura", idFactura, "=");
                                        case 2 -> busquedaInt(connection, "pedido", "idFactura", idFactura, "<");
                                        case 3 -> busquedaInt(connection, "pedido", "idFactura", idFactura, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Filtrado por idProducto
                            case 4 -> { 

                                System.out.print("\nIntroduzca el ID del Producto -> ");
                                idProducto = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idProducto);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "pedido", "idProducto", idProducto, "=");
                                        case 2 -> busquedaInt(connection, "pedido", "idProducto", idProducto, "<");
                                        case 3 -> busquedaInt(connection, "pedido", "idProducto", idProducto, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Filtrado por cantidad
                            case 5 -> { 

                                System.out.print("\nIntroduzca la cantidad -> ");
                                cantidad = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(cantidad);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "pedido", "cantidad", cantidad, "=");
                                        case 2 -> busquedaInt(connection, "pedido", "cantidad", cantidad, "<");
                                        case 3 -> busquedaInt(connection, "pedido", "cantidad", cantidad, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Volver al menú de tablas
                            case 6 -> exitPedido = true;
                            default -> System.out.println("\nOpción no válida");
                        }

                    } while (!exitPedido);
                }

                // Lógica de filtrado para la Tabla Productos
                case 4 -> {
                    boolean exitProductos = false;
                    do {

                        menuProductos();
                        System.out.print("\nSeleccione una opcion -> ");
                        opcion = reader.nextInt();
                        reader.nextLine();

                        switch (opcion) {
                            
                            // Listar todos los registros
                            case 1 -> listarTodo(connection, "productos");
                            
                            // Filtrado por idProducto
                            case 2 -> { 

                                System.out.print("\nIntroduzca el ID del Producto -> ");
                                idProducto = reader.nextInt();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarInt(idProducto);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "productos", "idProducto", idProducto, "=");
                                        case 2 -> busquedaInt(connection, "productos", "idProducto", idProducto, "<");
                                        case 3 -> busquedaInt(connection, "productos", "idProducto", idProducto, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Filtrado por denominacion (valor de texto/VARCHAR)
                            case 3 -> { 

                                System.out.print("\nIntroduzca la denominación -> ");
                                denominacion = reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    filtrarVarChar(denominacion);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaString(connection, "productos", "denominacion", denominacion, "=");
                                        case 2 -> busquedaString(connection, "productos", "denominacion", denominacion, "LIKE");
                                        case 3 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Filtrado por precio (valor numérico double, convertido a int para la función de búsqueda)
                            case 4 -> { 

                                System.out.print("\nIntroduzca el precio -> ");
                                precio = reader.nextDouble();
                                reader.nextLine();

                                boolean exitFiltro = false;
                                do {

                                    // Se usa la función de filtro Int al convertir temporalmente el precio a int
                                    filtrarInt((int) precio);
                                    System.out.print("\nSeleccione una opcion -> ");
                                    opcion = reader.nextInt();
                                    reader.nextLine();

                                    // Lógica para ejecutar la consulta con el operador seleccionado
                                    switch (opcion) {
                                        case 1 -> busquedaInt(connection, "productos", "precio", (int) precio, "=");
                                        case 2 -> busquedaInt(connection, "productos", "precio", (int) precio, "<");
                                        case 3 -> busquedaInt(connection, "productos", "precio", (int) precio, ">");
                                        case 4 -> exitFiltro = true;
                                        default -> System.out.println("\nOpción no válida");
                                    }

                                } while (!exitFiltro);
                            }

                            // Volver al menú de tablas
                            case 5 -> exitProductos = true;
                            default -> System.out.println("\nOpción no válida");
                        }

                    } while (!exitProductos);
                }

                // Opción para salir del menú de listado y volver al menú anterior (presumiblemente)
                case 5 -> exit = true;
                default -> System.out.println("\nOpción no válida");
            }

        } while (!exit);
    }


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
        System.out.println("5. Volver");
    }

    private static void menuMesa() {
        System.out.println("\n¿Por qué campo quieres buscar?");
        System.out.println("------------------------------");
        System.out.println("1. Listar Todo");
        System.out.println("2. idMesa");
        System.out.println("3. numComensales");
        System.out.println("4. reserva");
        System.out.println("5. Volver");
    }

    private static void menuFactura() {
        System.out.println("\n¿Por qué campo quieres buscar?");
        System.out.println("------------------------------");
        System.out.println("1. Listar Todo");
        System.out.println("2. idFactura");
        System.out.println("3. idMesa");
        System.out.println("4. tipoPago");
        System.out.println("5. importe");
        System.out.println("6. Volver");
    }

    private static void menuPedido() {
        System.out.println("\n¿Por qué campo quieres buscar?");
        System.out.println("------------------------------");
        System.out.println("1. Listar Todo");
        System.out.println("2. idPedido");
        System.out.println("3. idFactura");
        System.out.println("4. idProducto");
        System.out.println("5. cantidad");
        System.out.println("6. Volver");
    }

    private static void menuProductos() {
        System.out.println("\n¿Por qué campo quieres buscar?");
        System.out.println("------------------------------");
        System.out.println("1. Listar Todo");
        System.out.println("2. idProducto");
        System.out.println("3. denominacion");
        System.out.println("4. precio");
        System.out.println("5. Volver");
    }

    
    public static void listarTodo(Connection connection, String tabla) {
    	
        try {
            String sql = "SELECT * FROM " + tabla;
            
            // Prepara y ejecuta la consulta SQL
            PreparedStatement ps = connection.prepareStatement(sql); 
            ResultSet rs = ps.executeQuery();
            
            // Obtiene metadatos para conocer el número y nombre de columnas
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            System.out.println("\nResultados de la tabla " + tabla.toUpperCase() + ": \n");
            
            boolean hayResultados = false;
            
            // Itera sobre los resultados y los imprime dinámicamente
            while (rs.next()) {
                hayResultados = true;
                for (int i = 1; i <= columnas; i++) {
                    String nombreColumna = meta.getColumnName(i);
                    String valorColumna = rs.getString(i);
                    System.out.println(nombreColumna + ": " + valorColumna);
                }
                System.out.println("--------------------------");
            }
            
            // Mensaje en caso de que la consulta no devuelva registros
            if (!hayResultados) {
                System.out.println("La tabla está vacía o no se encontraron registros");
            }

        } catch (Exception e) {
        	
            // Manejo de errores de la base de datos
            System.err.println("Error al listar la tabla " + tabla);
        }
    }


    private static void busquedaInt(Connection connection, String tabla, String campo, int valor, String operador) {
    	
        try {
            // Construye la consulta SQL con el operador (ej. =, <, >)
            String sql = "SELECT * FROM " + tabla + " WHERE " + campo + " " + operador + " ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            // Asigna el valor entero al parámetro de la consulta
            ps.setInt(1, valor);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            System.out.println("\nResultados:");
            boolean hayResultados = false;
            
            // Itera e imprime los resultados
            while (rs.next()) {
                hayResultados = true;
                for (int i = 1; i <= columnas; i++) {
                    String nombreColumna = meta.getColumnName(i);
                    String valorColumna = rs.getString(i);
                    System.out.println(nombreColumna + ": " + valorColumna);
                }
                System.out.println("--------------------------");
            }
            
            // Mensaje si no hay registros que cumplan el filtro
            if (!hayResultados) {
                System.out.println("No se encontraron registros que cumplan el filtro");
            }

        } catch (Exception e) {
            System.err.println("Error al listar la tabla " + tabla);
        }
    }


    private static void busquedaString(Connection connection, String tabla, String campo, String valor, String operador) {
    	
    	// Almacena la consulta sql a ejecutar
    	String sql;

        try {
        	
        	// Adapta la consulta y el valor si se usa el operador LIKE
            if (operador.equalsIgnoreCase("LIKE")) {
                sql = "SELECT * FROM " + tabla + " WHERE " + campo + " LIKE ?";
                
                // Añade los comodines % para buscar la subcadena
                valor = "%" + valor + "%";
            } else {
                sql = "SELECT * FROM " + tabla + " WHERE " + campo + " = ?";
            }

            // Creamos el preparedStatement
            PreparedStatement ps = connection.prepareStatement(sql);
            
            // Asignamos el valor de texto a la consulta
            ps.setString(1, valor);

            // Ejecutamos la consulta y obtenemos su resultado
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            System.out.println("\nResultados:");
            boolean hayResultados = false;
            
            // Itera e imprime los resultados
            while (rs.next()) {
                hayResultados = true;
                for (int i = 1; i <= columnas; i++) {
                    String nombreColumna = meta.getColumnName(i);
                    String valorColumna = rs.getString(i);
                    System.out.println(nombreColumna + ": " + valorColumna);
                }
                System.out.println("--------------------------");
            }
            
            // Mensaje si no hay registros que cumplan el filtro
            if (!hayResultados) {
                System.out.println("No se encontraron registros que cumplan el filtro");
            }

        } catch (Exception e) {
        	System.err.println("Error al listar la tabla " + tabla);
        }
    }
}