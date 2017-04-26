package ua.galicia.flowershop.dao;
 
import ua.galicia.flowershop.entity.Product;
import ua.galicia.flowershop.model.PaginationResult;
import ua.galicia.flowershop.model.ProductInfo;
 
public interface ProductDAO {
 
    
    
    public Product findFlower(String code);
    
    public ProductInfo findFlowerInfo(String code) ;
  
    
    public PaginationResult<ProductInfo> queryProducts(int page,
                                                       int maxResult, int maxNavigationPage  );
    
    public PaginationResult<ProductInfo> queryProducts(int page, int maxResult,
                                                       int maxNavigationPage, String likeName);
 
    public void save(ProductInfo productInfo);
    
}