package gb.spring.emarket.core.validators;

import gb.spring.emarket.api.dto.ProductDTO;
import gb.spring.emarket.api.errors.ProductValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {

    public void validate(ProductDTO productDTO) {
        List<String> errors = new ArrayList<>();

        if (productDTO.getCost() < 0) {
            errors.add("Product price can't be less than 0");
        }

        if (productDTO.getTitle().isBlank()) {
            errors.add("Product can't have blank title");
        }

        if (!errors.isEmpty()) {
            throw new ProductValidationException(errors);
        }
    }
}
