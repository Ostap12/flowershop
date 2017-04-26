package ua.galicia.flowershop.validator;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.galicia.flowershop.dao.ProductDAO;
import ua.galicia.flowershop.entity.Flower;
import ua.galicia.flowershop.model.FlowerInfo;

// @Component: As a Bean.
@Component
public class FlowerInfoValidator implements Validator {
 
    @Autowired
    private ProductDAO flowerDAO;
 
    // This Validator support ProductInfo class.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == FlowerInfo.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        FlowerInfo productInfo = (FlowerInfo) target;
 
        // Check the fields of ProductInfo class.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty.productForm.code");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");
 
        String code = productInfo.getCode();
        if (code != null && code.length() > 0) {
            if (code.matches("\\s+")) {
                errors.rejectValue("code", "Pattern.productForm.code");
            } else if(productInfo.isNewProduct()) {
                Flower product = flowerDAO.findFlower(code);
                if (product != null) {
                    errors.rejectValue("code", "Duplicate.productForm.code");
                }
            }
        }
    }
 
}