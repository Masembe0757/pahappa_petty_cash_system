package org.pahappa.pettycashapp.systems.petty_cash_app.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;
@ApplicationScope
@Repository
public class CategoryDao {
    @Autowired
    SessionConfiguration sessionConfiguration;
    public void saveCategory(Category category) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.getCurrentSession();
            Transaction trs = session.beginTransaction();
            session.save(category);
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
    }
    public Category getCategoryOfId(int categoryId) {
        Category category = new Category();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Category where id = :categoryId");
            qry.setParameter("categoryId", categoryId);
            category = (Category) qry.uniqueResult();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return category;
    }


    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Category");
            categories = qry.list();
            trs.commit();
            sessionConfiguration.shutdown();
        } catch (Exception e) {
            sessionConfiguration.shutdown();
        }
        return categories;
    }

    public Category returnCategory(String name) {
        Category category = new Category();
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Category where name = :name");
            qry.setParameter("name", name);
            category = (Category) qry.uniqueResult();
            trs.commit();
            sessionConfiguration.shutdown();
        }
        catch (Exception e){
            sessionConfiguration.shutdown();
        }
        return category;
    }

    public boolean updateCategory(int categoryId, String newName, String newDescription) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();

            Category categoryToUpdate = (Category) session.get(Category.class, categoryId);

            if (categoryToUpdate != null) {

                categoryToUpdate.setName(newName);
                categoryToUpdate.setDescription(newDescription);

                session.update(categoryToUpdate);

                trs.commit();
                sessionConfiguration.shutdown();
                return true;
            } else {
                sessionConfiguration.shutdown();
                return false;
            }
        } catch (Exception e) {
            sessionConfiguration.shutdown();
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCategory(int categoryId) {
        try {
            SessionFactory sf = sessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();

            Category categoryToDelete = (Category) session.get(Category.class, categoryId);

            if (categoryToDelete != null) {
                session.delete(categoryToDelete);

                trs.commit();
                sessionConfiguration.shutdown();
                return true;
            } else {

                sessionConfiguration.shutdown();
                return false;
            }
        } catch (Exception e) {
            sessionConfiguration.shutdown();
            e.printStackTrace();
            return false;
        }
    }
}