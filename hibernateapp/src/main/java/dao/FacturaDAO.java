package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import entities.Factura;
import entities.Mesa;
import utils.HibernateUtils;
import java.util.List;
import java.util.Scanner;

public class FacturaDAO {
    
	static Scanner reader = new Scanner(System.in);
	
    // INSERTAR
    public void insertar(Factura factura) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(factura);
            transaction.commit();
            System.out.println("✓ Factura insertada correctamente con ID: " + factura.getIdFactura());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("✗ Error al insertar factura: " + e.getMessage());
        }
    }
    
    // INSERTAR INTERACTIVO (con selección de mesa)
    public void insertarInteractivo() {
        Transaction transaction = null;
        
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Mostrar mesas disponibles
            List<Mesa> mesas = session.createQuery("FROM Mesa", Mesa.class).list();
            
            if (mesas.isEmpty()) {
                System.err.println("✗ No hay mesas disponibles. Debe crear mesas primero.");
                return;
            }
            
            System.out.println("\n=== MESAS DISPONIBLES ===");
            mesas.forEach(m -> System.out.println("ID: " + m.getIdMesa() + 
                " | Comensales: " + m.getNumComensales() + 
                " | Reserva: " + m.getReserva()));
            
            System.out.print("\nID de la mesa: ");
            int idMesa = reader.nextInt();
            reader.nextLine();
            
            Mesa mesa = session.get(Mesa.class, idMesa);
            if (mesa == null) {
                System.err.println("✗ No existe una mesa con ID " + idMesa);
                return;
            }
            
            System.out.print("Tipo de pago: ");
            String tipoPago = reader.nextLine();
            
            System.out.print("Importe: ");
            double importe = reader.nextDouble();
            
            transaction = session.beginTransaction();
            Factura factura = new Factura(mesa, tipoPago, importe);
            session.save(factura);
            transaction.commit();
            
            System.out.println("✓ Factura insertada correctamente con ID: " + factura.getIdFactura());
            
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("✗ Error al insertar factura: " + e.getMessage());
        }
    }
    
    // LISTAR TODAS
    public List<Factura> listarTodas() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Factura", Factura.class).list();
        } catch (Exception e) {
            System.err.println("✗ Error al listar facturas: " + e.getMessage());
            return null;
        }
    }
    
    // BUSCAR POR ID
    public Factura buscarPorId(int id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(Factura.class, id);
        } catch (Exception e) {
            System.err.println("✗ Error al buscar factura: " + e.getMessage());
            return null;
        }
    }
    
    // BUSCAR POR TIPO DE PAGO (LIKE)
    public List<Factura> buscarPorTipoPago(String tipoPago) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Factura> query = session.createQuery("FROM Factura WHERE tipoPago LIKE :tipo", Factura.class);
            query.setParameter("tipo", "%" + tipoPago + "%");
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
            
            Factura factura = session.get(Factura.class, id);
            if (factura == null) {
                System.err.println("✗ No existe una factura con ID " + id);
                return;
            }
            
            System.out.println("Factura actual: " + factura);
            System.out.print("Nuevo tipo de pago (actual=" + factura.getTipoPago() + "): ");
            String tipoPago = reader.nextLine();
            
            System.out.print("Nuevo importe (actual=" + factura.getImporte() + "): ");
            double importe = reader.nextDouble();
            
            factura.setTipoPago(tipoPago);
            factura.setImporte(importe);
            
            session.update(factura);
            
            System.out.println("\nFactura modificada: " + factura);
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
            
            Factura factura = session.get(Factura.class, id);
            if (factura == null) {
                System.err.println("✗ No existe una factura con ID " + id);
                return;
            }
            
            if (!factura.getPedidos().isEmpty()) {
                System.err.println("✗ No se puede eliminar: la factura tiene pedidos asociados");
                return;
            }
            
            System.out.println("Factura a eliminar: " + factura);
            System.out.print("¿Confirmar eliminación? (S/N): ");
            String confirmar = reader.next();
            
            if (confirmar.equalsIgnoreCase("S")) {
                session.delete(factura);
                transaction.commit();
                System.out.println("✓ Factura eliminada correctamente");
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
