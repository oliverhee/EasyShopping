package onlineShop.dao;

import onlineShop.entity.Authorities;
import onlineShop.entity.Customer;
import onlineShop.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addCustomer(Customer customer){
        Authorities authorities = new Authorities();
        authorities.setAuthorities("ROLE_USER");
        authorities.setEmailId(customer.getUser().getEmailId());
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(authorities);
            session.save(customer);
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback(); //有异常后，刚写的数据不保存到数据库
        } finally {
            if (session != null){
                session.close();
            }
        }
    }

    // userName等价于emailId
    public Customer getCustomerByUserName(String userName) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {

            Criteria criteria = session.createCriteria(User.class);
            user = (User) criteria.add(Restrictions.eq("emailId", userName)).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null)
            return user.getCustomer();
        return null;
    }

}



