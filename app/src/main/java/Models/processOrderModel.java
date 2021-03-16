package Models;

public class processOrderModel {
    String user_id,doc_id,admin_id,food_name,order_id,total_price,quantity,price,address;

    public processOrderModel() {
    }

    public processOrderModel(String user_id, String doc_id, String admin_id, String food_name, String order_id, String total_price, String quantity, String price, String address) {
        this.user_id = user_id;
        this.doc_id = doc_id;
        this.admin_id = admin_id;
        this.food_name = food_name;
        this.order_id = order_id;
        this.total_price = total_price;
        this.quantity = quantity;
        this.price = price;
        this.address = address;
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

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }
}
