package org.pahappa.pettycashapp.systems.petty_cash_app.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.pahappa.pettycashapp.systems.petty_cash_app.configurations.SessionConfiguration;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Permission;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Role;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDao {
    public void saveRole(Role role) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            session.saveOrUpdate(role);
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Role ");
            roles = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return roles;
    }

    public void deleteRoleOdId(int roleId) {
        try {
            System.out.println("role dell 1");
            System.out.println("ID ...\n" +roleId);
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("delete from Role where id = :roleId");
            qry.setParameter("roleId", roleId);
            qry.executeUpdate();
            System.out.println("role dell 2");
            trs.commit();
            SessionConfiguration.shutdown();
        }catch (Exception e){
            SessionConfiguration.shutdown();
            e.printStackTrace();
        }
    }

    public List<Permission> getPermissionsOfRole(int roleId) {
        List<Permission> permissions = null;
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Permission where role_id = :roleId");
            qry.setParameter("roleId", roleId);
            permissions = qry.list();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return permissions;
    }


    public Role getRoleOfname(String name) {
        Role role = null;
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Role where name = :name");
            qry.setParameter("name", name);
            role = (Role) qry.uniqueResult();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return role;
    }

    public void deletePermissionsOfroleId(int id) {
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("delete from Permission where role_id = :id");
            qry.setParameter("id", id);
            qry.executeUpdate();
            trs.commit();
            SessionConfiguration.shutdown();
        }catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public void updateRole(String name,List<String> permissions ,int roleId) {
        try {

            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Role role =(Role) session.get(Role.class,roleId);
            role.setName(name);
            for (String s : permissions ) {
                Permission permission = new Permission();
                role.getPermissions().add(permission);
                permission.setRole(role);
                permission.setName(s);
            }
            session.update(role);

            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
    }

    public Role getRoleOfId(int roleId) {
        Role role = null;
        try {
            SessionFactory sf = SessionConfiguration.getSessionFactory();
            Session session = sf.openSession();
            Transaction trs = session.beginTransaction();
            Query qry = session.createQuery("from Role where id = :roleId");
            qry.setParameter("roleId", roleId);
            role = (Role) qry.uniqueResult();
            trs.commit();
            SessionConfiguration.shutdown();
        }
        catch (Exception e){
            SessionConfiguration.shutdown();
        }
        return role;
    }
}
