package onlineShop.service;

import onlineShop.dao.CustomerDao;
import onlineShop.entity.Cart;
import onlineShop.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public void addCustomer(Customer customer){
        Cart cart = new Cart();
        customer.setCart(cart);

        customer.getUser().setEnabled(true);// soft destroy; enable之后表明用户是一个可以使用状态，以后删除时只要diabale就可以，不用删除

        customerDao.addCustomer(customer);
    }

    public Customer getCustomerByUserName(String userName){
        return customerDao.getCustomerByUserName(userName);
    }

}
