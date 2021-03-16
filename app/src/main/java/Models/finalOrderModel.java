package Models;

public class finalOrderModel {
    String user_id,admin_id,quantity,order_id,title,total_price,ids,address;

    public finalOrderModel() {
    }

    public finalOrderModel(String user_id, String admin_id, String quantity, String order_id, String title, String total_price,String address) {
        this.user_id = user_id;
        this.admin_id = admin_id;
        this.quantity = quantity;
        this.order_id = order_id;
        this.address=address;
        this.title = title;
        this.total_price = total_price;
        this.ids = ids;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
