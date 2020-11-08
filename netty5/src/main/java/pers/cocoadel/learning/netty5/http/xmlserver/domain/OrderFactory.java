package pers.cocoadel.learning.netty5.http.xmlserver.domain;

public class OrderFactory {

    public static Order createOrder(long orderId) {
        Order order = new Order();
        order.setOrderNumber(orderId);
        order.setTotal(999f);
        Customer customer = new Customer();
        customer.setCustomerNumber(orderId);
        customer.setFirstName("Rose");
        customer.setLastName("ruby");
        order.setCustomer(customer);
        Address address = new Address();
        address.setCity("汕头市");
        address.setCountry("中国");
        address.setPostCode("505011");
        address.setState("广东省");
        address.setStreet1("汕头大学D座110");
        address.setStreet2("汕头大学D座109");
        order.setBillTo(address);
        order.setShipping(Shipping.DOMESTIC_EXPRESS);
        order.setShipTo(address);
        return order;
    }
}
