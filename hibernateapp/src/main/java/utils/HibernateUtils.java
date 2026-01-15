package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import entities.*;

public class HibernateUtils {
    
    private static SessionFactory sessionFactory;
    
    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Mesa.class)
                    .addAnnotatedClass(Factura.class)
                    .addAnnotatedClass(Pedido.class)
                    .addAnnotatedClass(Producto.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Error al crear SessionFactory: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

