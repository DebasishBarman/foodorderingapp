package Models;

public class delvModelAdmin {
    String order_id,admin_id,user_id,address,quantity,price,status,title;

    public delvModelAdmin() {
    }

    public delvModelAdmin(String order_id, String admin_id, String user_id, String address, String quantity, String price, String status, String title) {
        this.order_id = order_id;
        this.admin_id = admin_id;
        this.user_id = user_id;
        this.address = address;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
