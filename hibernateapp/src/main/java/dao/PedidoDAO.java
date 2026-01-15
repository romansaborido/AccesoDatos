package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Pedido;
import entities.Factura;
import entities.Producto;
import utils.HibernateUtils;
import java.util.List;
import java.util.Scanner;

public class PedidoDAO {
    
	static Scanner reader = new Scanner(System.in);
	
    // INSERTAR
    public void insertar(Pedido pedido) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(pedido);
            transaction.commit();
            System.out.println("✓ Pedido insertado correctamente con ID: " + pedido.getIdPedido());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("✗ Error al insertar pedido: " + e.getMessage());
        }
    }
    
    // INSERTAR INTERACTIVO (con selección de factura y producto)
    public void insertarInteractivo() {
        Transaction transaction = null;
        
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Mostrar facturas disponibles
            List<Factura> facturas = session.createQuery("FROM Factura", Factura.class).list();
            
            if (facturas.isEmpty()) {
                System.err.println("✗ No hay facturas disponibles. Debe crear facturas primero.");
                return;
            }
            
            System.out.println("\n=== FACTURAS DISPONIBLES ===");
            facturas.forEach(f -> System.out.println("ID: " + f.getIdFactura() + 
                " | Mesa: " + f.getMesa().getIdMesa() + 
                " | Tipo Pago: " + f.getTipoPago()));
            
            System.out.print("\nID de la factura: ");
            int idFactura = reader.nextInt();
            
            Factura factura = session.get(Factura.class, idFactura);
            if (factura == null) {
                System.err.println("✗ No existe una factura con ID " + idFactura);
                return;
            }
            
            // Mostrar productos disponibles
            List<Producto> productos = session.createQuery("FROM Producto", Producto.class).list();
            
            if (productos.isEmpty()) {
                System.err.println("✗ No hay productos disponibles. Debe crear productos primero.");
                return;
            }
            
            System.out.println("\n=== PRODUCTOS DISPONIBLES ===");
            productos.forEach(p -> System.out.println("ID: " + p.getIdProducto() + 
                " | " + p.getDenominacion() + 
                " | Precio: " + p.getPrecio() + "€"));
            
            System.out.print("\nID del producto: ");
            int idProducto = reader.nextInt();
            
            Producto producto = session.get(Producto.class, idProducto);
            if (producto == null) {
                System.err.println("✗ No existe un producto con ID " + idProducto);
                return;
            }
            
            System.out.print("Cantidad: ");
            int cantidad = reader.nextInt();
            
            transaction = session.beginTransaction();
            Pedido pedido = new Pedido(factura, producto, cantidad);
            session.save(pedido);
            transaction.commit();
            
            System.out.println("✓ Pedido insertado correctamente con ID: " + pedido.getIdPedido());
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("✗ Error al insertar pedido: " + e.getMessage());
        }
    }
    
    // LISTAR TODOS
    public List<Pedido> listarTodos() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pedido", Pedido.class).list();
        } catch (Exception e) {
            System.err.println("✗ Error al listar pedidos: " + e.getMessage());
            return null;
        }
    }
    
    // BUSCAR POR ID
    public Pedido buscarPorId(int id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(Pedido.class, id);
        } catch (Exception e) {
            System.err.println("✗ Error al buscar pedido: " + e.getMessage());
            return null;
        }
    }
    
    // MODIFICAR
    public void modificar(int id) {
        Transaction transaction = null;
        
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Pedido pedido = session.get(Pedido.class, id);
            if (pedido == null) {
                System.err.println("✗ No existe un pedido con ID " + id);
                return;
            }
            
            System.out.println("Pedido actual: " + pedido);
            System.out.print("Nueva cantidad (actual=" + pedido.getCantidad() + "): ");
            int cantidad = reader.nextInt();
            
            pedido.setCantidad(cantidad);
            session.update(pedido);
            
            System.out.println("\nPedido modificado: " + pedido);
            System.out.print("¿Confirmar cambios? (S/N): ");
            String confirmar = reader.next();
            
            if (confirmar.equalsIgnoreCase("S")) {
                transaction.commit();
                System.out.println("✓ Cambios confirmados");
            } else {
                transaction.rollback();
                System.out.println("✗ Cambios cancelados");
            }
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("✗ Error al modificar: " + e.getMessage());
        }
    }
    
    // ELIMINAR
    public void eliminar(int id) {
        Transaction transaction = null;
        
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Pedido pedido = session.get(Pedido.class, id);
            if (pedido == null) {
                System.err.println("✗ No existe un pedido con ID " + id);
                return;
            }
            
            System.out.println("Pedido a eliminar: " + pedido);
            System.out.print("¿Confirmar eliminación? (S/N): ");
            String confirmar = reader.next();
            
            if (confirmar.equalsIgnoreCase("S")) {
                session.delete(pedido);
                transaction.commit();
                System.out.println("✓ Pedido eliminado correctamente");
            } else {
                transaction.rollback();
                System.out.println("✗ Eliminación cancelada");
            }
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }
    }
}   

