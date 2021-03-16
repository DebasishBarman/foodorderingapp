package Models;

public class UserOrders {
    String user_id,order_id,title,total_price,quantity,ids,admin_id,address,imgurl;

    public UserOrders() {
    }

    public UserOrders(String user_id, String order_id, String title, String total_price, String quantity, String ids, String admin_id,String address,String imgurl) {
        this.user_id = user_id;
        this.order_id = order_id;
        this.title = title;
        this.address=address;
        this.total_price = total_price;
        this.quantity = quantity;
        this.ids = ids;
        this.admin_id = admin_id;
        this.imgurl=imgurl;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }
}
