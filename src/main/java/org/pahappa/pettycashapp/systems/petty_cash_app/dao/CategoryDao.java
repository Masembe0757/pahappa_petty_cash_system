package org.pahappa.pettycashapp.systems.petty_cash_app.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDao {
    public void saveCategory(Category category) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.save(category);
            trs.commit();
            SessionConfiguration.shutdown();
        } catch (Exception e) {
            SessionConfiguration.shutdown();
        }
    }


    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Category");
            categories = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        } catch (Exception e) {
            SessionConfiguration.shutdown();
        }
        return categories;
    }
}