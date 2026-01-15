package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import entities.Mesa;
import utils.HibernateUtils;
import java.util.List;
import java.util.Scanner;

public class MesaDAO {
    
	static Scanner reader = new Scanner(System.in);
	
    // INSERTAR
    public void insertar(Mesa mesa) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(mesa);
            transaction.commit();
            System.out.println("✓ Mesa insertada correctamente con ID: " + mesa.getIdMesa());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("✗ Error al insertar mesa: " + e.getMessage());
        }
    }
    
    // INSERTAR INTERACTIVO (pide datos al usuario)
    public void insertarInteractivo() {
        try {
            System.out.print("Número de comensales: ");
            int numComensales = reader.nextInt();
            
            System.out.print("Reserva (0=No, 1=Sí): ");
            int reserva = reader.nextInt();
            
            Mesa mesa = new Mesa(numComensales, reserva);
            insertar(mesa);
            
        } catch (Exception e) {
            System.err.println("✗ Error en los datos introducidos");
        }
    }
    
    // LISTAR TODAS
    public List<Mesa> listarTodas() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Mesa", Mesa.class).list();
        } catch (Exception e) {
            System.err.println("✗ Error al listar mesas: " + e.getMessage());
            return null;
        }
    }
    
    // BUSCAR POR ID
    public Mesa buscarPorId(int id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(Mesa.class, id);
        } catch (Exception e) {
            System.err.println("✗ Error al buscar mesa: " + e.getMessage());
            return null;
        }
    }
    
    // BUSCAR POR NÚMERO DE COMENSALES (=)
    public List<Mesa> buscarPorComensales(int numComensales) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Mesa> query = session.createQuery("FROM Mesa WHERE numComensales = :num", Mesa.class);
            query.setParameter("num", numComensales);
            return query.list();
        } catch (Exception e) {
            System.err.println("✗ Error al buscar: " + e.getMessage());
            return null;
        }
    }
    
    // BUSCAR POR RESERVA
    public List<Mesa> buscarPorReserva(int reserva) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Mesa> query = session.createQuery("FROM Mesa WHERE reserva = :res", Mesa.class);
            query.setParameter("res", reserva);
            return query.list();
        } catch (Exception e) {
            System.err.println("✗ Error al buscar: " + e.getMessage());
            return null;
        }
    }
    
    // MODIFICAR (con confirmación)
    public void modificar(int id) {
        Transaction transaction = null;
        
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Mesa mesa = session.get(Mesa.class, id);
            if (mesa == null) {
                System.err.println("✗ No existe una mesa con ID " + id);
                return;
            }
            
            System.out.println("Mesa actual: " + mesa);
            System.out.print("Nuevo número de comensales (actual=" + mesa.getNumComensales() + "): ");
            int numComensales = reader.nextInt();
            
            System.out.print("Nueva reserva (actual=" + mesa.getReserva() + "): ");
            int reserva = reader.nextInt();
            
            mesa.setNumComensales(numComensales);
            mesa.setReserva(reserva);
            
            session.update(mesa);
            
            System.out.println("\nMesa modificada: " + mesa);
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
    
    // ELIMINAR POR ID (con confirmación)
    public void eliminar(int id) {
        Transaction transaction = null;
        
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Mesa mesa = session.get(Mesa.class, id);
            if (mesa == null) {
                System.err.println("✗ No existe una mesa con ID " + id);
                return;
            }
            
            // Verificar si tiene facturas asociadas
            if (!mesa.getFacturas().isEmpty()) {
                System.err.println("✗ No se puede eliminar: la mesa tiene facturas asociadas");
                System.out.println("Debe eliminar primero las facturas relacionadas");
                return;
            }
            
            System.out.println("Mesa a eliminar: " + mesa);
            System.out.print("¿Confirmar eliminación? (S/N): ");
            String confirmar = reader.next();
            
            if (confirmar.equalsIgnoreCase("S")) {
                session.delete(mesa);
                transaction.commit();
                System.out.println("✓ Mesa eliminada correctamente");
            } else {
                transaction.rollback();
                System.out.println("✗ Eliminación cancelada");
            }
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("✗ Error al eliminar: " + e.getMessage());
        }
    }
    
    // ELIMINAR TODAS (con confirmación)
    public void eliminarTodas() {
        Transaction transaction = null;
        
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            List<Mesa> mesas = session.createQuery("FROM Mesa", Mesa.class).list();
            
            if (mesas.isEmpty()) {
                System.out.println("No hay mesas para eliminar");
                return;
            }
            
            System.out.println("Mesas a eliminar: " + mesas.size());
            mesas.forEach(System.out::println);
            
            System.out.print("¿Confirmar eliminación de TODAS las mesas? (S/N): ");
            String confirmar = reader.next();
            
            if (confirmar.equalsIgnoreCase("S")) {
                session.createQuery("DELETE FROM Mesa").executeUpdate();
                transaction.commit();
                System.out.println("✓ Todas las mesas eliminadas");
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
