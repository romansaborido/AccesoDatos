package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import entities.Producto;
import utils.HibernateUtils;
import java.util.List;
import java.util.Scanner;

public class ProductoDAO {
    
	static Scanner reader = new Scanner(System.in);
	
    // INSERTAR
    public void insertar(Producto producto) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(producto);
            transaction.commit();
            System.out.println("✓ Producto insertado correctamente con ID: " + producto.getIdProducto());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("✗ Error al insertar producto: " + e.getMessage());
        }
    }
    
    // INSERTAR INTERACTIVO
    public void insertarInteractivo() {
        try {
            System.out.print("Denominación: ");
            String denominacion = reader.nextLine();
            
            System.out.print("Precio: ");
            double precio = reader.nextDouble();
            
            Producto producto = new Producto(denominacion, precio);
            insertar(producto);
            
        } catch (Exception e) {
            System.err.println("✗ Error en los datos introducidos");
        }
    }
    
    // LISTAR TODOS
    public List<Producto> listarTodos() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Producto", Producto.class).list();
        } catch (Exception e) {
            System.err.println("✗ Error al listar productos: " + e.getMessage());
            return null;
        }
    }
    
    // BUSCAR POR ID
    public Producto buscarPorId(int id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(Producto.class, id);
        } catch (Exception e) {
            System.err.println("✗ Error al buscar producto: " + e.getMessage());
            return null;
        }
    }
    
    // BUSCAR POR DENOMINACIÓN (LIKE)
    public List<Producto> buscarPorDenominacion(String denominacion) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Producto> query = session.createQuery("FROM Producto WHERE denominacion LIKE :denom", Producto.class);
            query.setParameter("denom", "%" + denominacion + "%");
            return query.list();
        } catch (Exception e) {
            System.err.println("✗ Error al buscar: " + e.getMessage());
            return null;
        }
    }
    
    // BUSCAR POR PRECIO (< o >)
    public List<Producto> buscarPorPrecio(double precio, String operador) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String hql = "FROM Producto WHERE precio " + operador + " :precio";
            Query<Producto> query = session.createQuery(hql, Producto.class);
            query.setParameter("precio", precio);
            return query.list();
        } catch (Exception e) {
            System.err.println("✗ Error al buscar: " + e.getMessage());
            return null;
        }
    }
    
    // MODIFICAR
    public void modificar(int id) {
        Transaction transaction = null;
        
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Producto producto = session.get(Producto.class, id);
            if (producto == null) {
                System.err.println("✗ No existe un producto con ID " + id);
                return;
            }
            
            System.out.println("Producto actual: " + producto);
            System.out.print("Nueva denominación (actual=" + producto.getDenominacion() + "): ");
            String denominacion = reader.nextLine();
            
            System.out.print("Nuevo precio (actual=" + producto.getPrecio() + "): ");
            double precio = reader.nextDouble();
            
            producto.setDenominacion(denominacion);
            producto.setPrecio(precio);
            
            session.update(producto);
            
            System.out.println("\nProducto modificado: " + producto);
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
            
            Producto producto = session.get(Producto.class, id);
            if (producto == null) {
                System.err.println("✗ No existe un producto con ID " + id);
                return;
            }
            
            if (!producto.getPedidos().isEmpty()) {
                System.err.println("✗ No se puede eliminar: el producto tiene pedidos asociados");
                return;
            }
            
            System.out.println("Producto a eliminar: " + producto);
            System.out.print("¿Confirmar eliminación? (S/N): ");
            String confirmar = reader.next();
            
            if (confirmar.equalsIgnoreCase("S")) {
                session.delete(producto);
                transaction.commit();
                System.out.println("✓ Producto eliminado correctamente");
            } else {
                transaction.rollback();
                System.out.println("✗ Eliminación cancelada");
            }
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("✗ Error al eliminar: " + e.getMessage());
        }
    }
}
