package Models;

public class OrderModel {

    String user_id,admin_id,name,price,quantity,order_id,title,total_price;

    public OrderModel() {
    }

    public OrderModel(String user_id, String admin_id, String name, String price, String quantity,String order_id) {
        this.user_id = user_id;
        this.admin_id = admin_id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.order_id=order_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
