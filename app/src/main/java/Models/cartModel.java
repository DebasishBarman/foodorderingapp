package Models;

public class cartModel {

    String order_id,admin_id,price,title,user_id,imgUrl;

    public cartModel() {
    }

    public cartModel(String order_id, String admin_id, String price, String title, String user_id, String imgUrl) {
        this.order_id = order_id;
        this.admin_id = admin_id;
        this.price = price;
        this.title = title;
        this.user_id = user_id;
        this.imgUrl = imgUrl;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
