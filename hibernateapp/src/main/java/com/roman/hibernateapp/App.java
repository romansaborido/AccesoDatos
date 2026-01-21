package com.roman.hibernateapp;

import dao.*;
import entities.*;
import utils.HibernateUtils;
import java.util.List;
import java.util.Scanner;

public class App {
    
    private static Scanner scanner = new Scanner(System.in);
    private static MesaDAO mesaDAO = new MesaDAO();
    private static FacturaDAO facturaDAO = new FacturaDAO();
    private static PedidoDAO pedidoDAO = new PedidoDAO();
    private static ProductoDAO productoDAO = new ProductoDAO();
    
    public static void main(String[] args) {
        System.out.println("SISTEMA DE GESTIÓN DE RESTAURANTE");
        System.out.println("==================================\n");
        
        menuPrincipal();
        
        // Cerrar recursos
        HibernateUtils.shutdown();
        scanner.close();
        System.out.println("\n✓ Sistema cerrado correctamente. ¡Hasta pronto!");
    }
    
    // MENU PRINCIPAL
    private static void menuPrincipal() {
        boolean salir = false;
        
        do {
            try {
                System.out.println("\nMENÚ PRINCIPAL");
                System.out.println("==============");
                System.out.println("1. Gestionar Mesas");
                System.out.println("2. Gestionar Facturas");
                System.out.println("3. Gestionar Pedidos");
                System.out.println("4. Gestionar Productos");
                System.out.println("5. Salir");
                System.out.print("\nSeleccione una opción: ");
                
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcion) {
                    case 1:
                        menuMesas();
                        break;
                    case 2:
                        menuFacturas();
                        break;
                    case 3:
                        menuPedidos();
                        break;
                    case 4:
                        menuProductos();
                        break;
                    case 5:
                        salir = true;
                        break;
                    default:
                        System.out.println("✗ Opción no válida. Intente de nuevo.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("✗ Error: Entrada no válida. Intente de nuevo.");
                scanner.nextLine(); // Limpiar buffer
            }
        } while (!salir);
    }
    
    // MENÚ MESAS
    private static void menuMesas() {
        boolean volver = false;
        
        do {
            try {
                System.out.println("\nGESTIÓN DE MESAS");
                System.out.println("================");
                System.out.println("1. Insertar nueva mesa");
                System.out.println("2. Listar todas las mesas");
                System.out.println("3. Buscar mesa por ID");
                System.out.println("4. Buscar mesas por número de comensales");
                System.out.println("5. Buscar mesas por reserva");
                System.out.println("6. Modificar mesa");
                System.out.println("7. Eliminar mesa");
                System.out.println("8. Eliminar todas las mesas");
                System.out.println("9. Volver al menú principal");
                System.out.print("\nSeleccione una opción: ");
                
                int opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1:
                        mesaDAO.insertarInteractivo();
                        break;
                    case 2:
                        List<Mesa> mesas = mesaDAO.listarTodas();
                        if (mesas != null && !mesas.isEmpty()) {
                            System.out.println("\n=== LISTADO DE MESAS ===");
                            mesas.forEach(System.out::println);
                        } else {
                            System.out.println("No hay mesas registradas.");
                        }
                        break;
                    case 3:
                        System.out.print("ID de la mesa: ");
                        int idBuscar = scanner.nextInt();
                        Mesa mesa = mesaDAO.buscarPorId(idBuscar);
                        if (mesa != null) {
                            System.out.println("Mesa encontrada: " + mesa);
                        } else {
                            System.out.println("✗ No se encontró la mesa con ID " + idBuscar);
                        }
                        break;
                    case 4:
                        System.out.print("Número de comensales: ");
                        int numComensales = scanner.nextInt();
                        List<Mesa> mesasPorComensales = mesaDAO.buscarPorComensales(numComensales);
                        if (mesasPorComensales != null && !mesasPorComensales.isEmpty()) {
                            System.out.println("\n=== MESAS CON " + numComensales + " COMENSALES ===");
                            mesasPorComensales.forEach(System.out::println);
                        } else {
                            System.out.println("No se encontraron mesas con ese número de comensales.");
                        }
                        break;
                    case 5:
                        System.out.print("Reserva (0=No, 1=Sí): ");
                        int reserva = scanner.nextInt();
                        List<Mesa> mesasPorReserva = mesaDAO.buscarPorReserva(reserva);
                        if (mesasPorReserva != null && !mesasPorReserva.isEmpty()) {
                            System.out.println("\n=== MESAS CON RESERVA=" + reserva + " ===");
                            mesasPorReserva.forEach(System.out::println);
                        } else {
                            System.out.println("No se encontraron mesas con esa reserva.");
                        }
                        break;
                    case 6:
                        System.out.print("ID de la mesa a modificar: ");
                        int idModificar = scanner.nextInt();
                        scanner.nextLine();
                        mesaDAO.modificar(idModificar);
                        break;
                    case 7:
                        System.out.print("ID de la mesa a eliminar: ");
                        int idEliminar = scanner.nextInt();
                        scanner.nextLine();
                        mesaDAO.eliminar(idEliminar);
                        break;
                    case 8:
                        mesaDAO.eliminarTodas();
                        break;
                    case 9:
                        volver = true;
                        break;
                    default:
                        System.out.println("✗ Opción no válida. Intente de nuevo.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("✗ Error: Entrada no válida. Intente de nuevo.");
                scanner.nextLine();
            }
        } while (!volver);
    }
    
    // MENÚ FACTURAS
    private static void menuFacturas() {
        boolean volver = false;
        
        do {
            try {
                System.out.println("\nGESTIÓN DE FACTURAS");
                System.out.println("===================");
                System.out.println("1. Insertar nueva factura");
                System.out.println("2. Listar todas las facturas");
                System.out.println("3. Buscar factura por ID");
                System.out.println("4. Buscar facturas por tipo de pago");
                System.out.println("5. Modificar factura");
                System.out.println("6. Eliminar factura");
                System.out.println("7. Volver al menú principal");
                System.out.print("\nSeleccione una opción: ");
                
                int opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1:
                        facturaDAO.insertarInteractivo();
                        break;
                    case 2:
                        List<Factura> facturas = facturaDAO.listarTodas();
                        if (facturas != null && !facturas.isEmpty()) {
                            System.out.println("\n=== LISTADO DE FACTURAS ===");
                            facturas.forEach(f -> {
                                System.out.println(f + " | Mesa: " + f.getMesa().getIdMesa());
                            });
                        } else {
                            System.out.println("No hay facturas registradas.");
                        }
                        break;
                    case 3:
                        System.out.print("ID de la factura: ");
                        int idBuscar = scanner.nextInt();
                        Factura factura = facturaDAO.buscarPorId(idBuscar);
                        if (factura != null) {
                            System.out.println("Factura encontrada: " + factura);
                            System.out.println("Mesa asociada: " + factura.getMesa());
                        } else {
                            System.out.println("✗ No se encontró la factura con ID " + idBuscar);
                        }
                        break;
                    case 4:
                        System.out.print("Tipo de pago a buscar: ");
                        String tipoPago = scanner.nextLine();
                        List<Factura> facturasPorTipo = facturaDAO.buscarPorTipoPago(tipoPago);
                        if (facturasPorTipo != null && !facturasPorTipo.isEmpty()) {
                            System.out.println("\n=== FACTURAS CON TIPO DE PAGO: " + tipoPago + " ===");
                            facturasPorTipo.forEach(System.out::println);
                        } else {
                            System.out.println("No se encontraron facturas con ese tipo de pago.");
                        }
                        break;
                    case 5:
                        System.out.print("ID de la factura a modificar: ");
                        int idModificar = scanner.nextInt();
                        scanner.nextLine();
                        facturaDAO.modificar(idModificar);
                        break;
                    case 6:
                        System.out.print("ID de la factura a eliminar: ");
                        int idEliminar = scanner.nextInt();
                        scanner.nextLine();
                        facturaDAO.eliminar(idEliminar);
                        break;
                    case 7:
                        volver = true;
                        break;
                    default:
                        System.out.println("✗ Opción no válida. Intente de nuevo.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("✗ Error: Entrada no válida. Intente de nuevo.");
                scanner.nextLine();
            }
        } while (!volver);
    }
    
    // MENÚ PEDIDOS
    private static void menuPedidos() {
        boolean volver = false;
        
        do {
            try {
                System.out.println("\nGESTIÓN DE PEDIDOS");
                System.out.println("==================");
                System.out.println("1. Insertar nuevo pedido");
                System.out.println("2. Listar todos los pedidos");
                System.out.println("3. Buscar pedido por ID");
                System.out.println("4. Modificar pedido");
                System.out.println("5. Eliminar pedido");
                System.out.println("6. Volver al menú principal");
                System.out.print("\nSeleccione una opción: ");
                
                int opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1:
                        pedidoDAO.insertarInteractivo();
                        break;
                    case 2:
                        List<Pedido> pedidos = pedidoDAO.listarTodos();
                        if (pedidos != null && !pedidos.isEmpty()) {
                            System.out.println("\n=== LISTADO DE PEDIDOS ===");
                            pedidos.forEach(p -> {
                                System.out.println(p + 
                                    " | Factura: " + p.getFactura().getIdFactura() + 
                                    " | Producto: " + p.getProducto().getDenominacion());
                            });
                        } else {
                            System.out.println("No hay pedidos registrados.");
                        }
                        break;
                    case 3:
                        System.out.print("ID del pedido: ");
                        int idBuscar = scanner.nextInt();
                        Pedido pedido = pedidoDAO.buscarPorId(idBuscar);
                        if (pedido != null) {
                            System.out.println("Pedido encontrado: " + pedido);
                            System.out.println("Factura: " + pedido.getFactura());
                            System.out.println("Producto: " + pedido.getProducto());
                        } else {
                            System.out.println("✗ No se encontró el pedido con ID " + idBuscar);
                        }
                        break;
                    case 4:
                        System.out.print("ID del pedido a modificar: ");
                        int idModificar = scanner.nextInt();
                        scanner.nextLine();
                        pedidoDAO.modificar(idModificar);
                        break;
                    case 5:
                        System.out.print("ID del pedido a eliminar: ");
                        int idEliminar = scanner.nextInt();
                        scanner.nextLine();
                        pedidoDAO.eliminar(idEliminar);
                        break;
                    case 6:
                        volver = true;
                        break;
                    default:
                        System.out.println("✗ Opción no válida. Intente de nuevo.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("✗ Error: Entrada no válida. Intente de nuevo.");
                scanner.nextLine();
            }
        } while (!volver);
    }
    
    // MENÚ PRODUCTOS
    private static void menuProductos() {
        boolean volver = false;
        
        do {
            try {
                System.out.println("\nGESTIÓN DE PRODUCTOS");
                System.out.println("====================");
                System.out.println("1. Insertar nuevo producto");
                System.out.println("2. Listar todos los productos");
                System.out.println("3. Buscar producto por ID");
                System.out.println("4. Buscar productos por denominación");
                System.out.println("5. Buscar productos por precio");
                System.out.println("6. Modificar producto");
                System.out.println("7. Eliminar producto");
                System.out.println("8. Volver al menú principal");
                System.out.print("\nSeleccione una opción: ");
                
                int opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1:
                        productoDAO.insertarInteractivo();
                        break;
                    case 2:
                        List<Producto> productos = productoDAO.listarTodos();
                        if (productos != null && !productos.isEmpty()) {
                            System.out.println("\n=== LISTADO DE PRODUCTOS ===");
                            productos.forEach(System.out::println);
                        } else {
                            System.out.println("No hay productos registrados.");
                        }
                        break;
                    case 3:
                        System.out.print("ID del producto: ");
                        int idBuscar = scanner.nextInt();
                        Producto producto = productoDAO.buscarPorId(idBuscar);
                        if (producto != null) {
                            System.out.println("Producto encontrado: " + producto);
                        } else {
                            System.out.println("✗ No se encontró el producto con ID " + idBuscar);
                        }
                        break;
                    case 4:
                        System.out.print("Denominación a buscar: ");
                        String denominacion = scanner.nextLine();
                        List<Producto> productosPorDenom = productoDAO.buscarPorDenominacion(denominacion);
                        if (productosPorDenom != null && !productosPorDenom.isEmpty()) {
                            System.out.println("\n=== PRODUCTOS CON DENOMINACIÓN: " + denominacion + " ===");
                            productosPorDenom.forEach(System.out::println);
                        } else {
                            System.out.println("No se encontraron productos con esa denominación.");
                        }
                        break;
                    case 5:
                        System.out.print("Precio: ");
                        double precio = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Operador (< o >): ");
                        String operador = scanner.nextLine();
                        List<Producto> productosPorPrecio = productoDAO.buscarPorPrecio(precio, operador);
                        if (productosPorPrecio != null && !productosPorPrecio.isEmpty()) {
                            System.out.println("\n=== PRODUCTOS CON PRECIO " + operador + " " + precio + " ===");
                            productosPorPrecio.forEach(System.out::println);
                        } else {
                            System.out.println("No se encontraron productos con ese criterio.");
                        }
                        break;
                    case 6:
                        System.out.print("ID del producto a modificar: ");
                        int idModificar = scanner.nextInt();
                        scanner.nextLine();
                        productoDAO.modificar(idModificar);
                        break;
                    case 7:
                        System.out.print("ID del producto a eliminar: ");
                        int idEliminar = scanner.nextInt();
                        scanner.nextLine();
                        productoDAO.eliminar(idEliminar);
                        break;
                    case 8:
                        volver = true;
                        break;
                    default:
                        System.out.println("✗ Opción no válida. Intente de nuevo.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("✗ Error: Entrada no válida. Intente de nuevo.");
                scanner.nextLine();
            }
        } while (!volver);
    }
}
