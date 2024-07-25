package org.pahappa.pettycashapp.systems.petty_cash_app.configurations;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Service
public class SessionConfiguration {
    private final  SessionFactory sessionFactory = buildSessionFactory();

    private  SessionFactory buildSessionFactory() {
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
            configuration.addAnnotatedClass(Role.class);
            configuration.addAnnotatedClass(Permission.class);
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public  SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public  void shutdown() {
        getSessionFactory().close();
    }
}
