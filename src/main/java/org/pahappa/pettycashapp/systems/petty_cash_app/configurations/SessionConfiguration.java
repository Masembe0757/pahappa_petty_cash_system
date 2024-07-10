package org.pahappa.pettycashapp.systems.petty_cash_app.configurations;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.*;

public class SessionConfiguration {
    private final static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            AnnotationConfiguration configuration = new AnnotationConfiguration();
            configuration.configure(); // Loads hibernate.cfg.xml configuration

            // Add annotated classes here
              configuration.addAnnotatedClass(User.class);
              configuration.addAnnotatedClass(BudgetLine.class);
              configuration.addAnnotatedClass(Accountability.class);
            configuration.addAnnotatedClass(Category.class);
            configuration.addAnnotatedClass(Requisition.class);
            configuration.addAnnotatedClass(Review.class);
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
