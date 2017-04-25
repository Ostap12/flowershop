package ua.galicia.flowershop.model;
 
public class CartLineInfo {
 
    private FlowerInfo productInfo;
    private int quantity;
 
    public CartLineInfo() {
        this.quantity = 0;
    }
 
    public FlowerInfo getProductInfo() {
        return productInfo;
    }
 
    public void setProductInfo(FlowerInfo productInfo) {
        this.productInfo = productInfo;
    }
 
    public int getQuantity() {
        return quantity;
    }
 
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
 
    public double getAmount() {
        return this.productInfo.getPrice() * this.quantity;
    }
    
}