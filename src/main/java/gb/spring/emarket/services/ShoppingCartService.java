package gb.spring.emarket.services;

import gb.spring.emarket.dto.ProductDTO;
import gb.spring.emarket.dto.ShoppingCartDTO;
import gb.spring.emarket.entity.Product;
import gb.spring.emarket.errors.ProductNotFoundException;
import gb.spring.emarket.errors.ShoppingCardException;
import gb.spring.emarket.errors.ValidationException;
import gb.spring.emarket.mappers.ProductMapper;
import gb.spring.emarket.services.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@Data
@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final Map<Long, Integer> shoppingCart;
    private final ProductService productService;

    private float totalCost = 0;
    private int totalCount = 0;

    public void addProduct(ProductDTO dto) {

        Long productId = dto.getId();
        validateProductId(productId);

        Integer count = dto.getCount();
        totalCost += dto.getCost() * dto.getCount();
        totalCount += dto.getCount();

        shoppingCart.merge(productId, count, Integer::sum);
    }

    public void removeProduct(Long productId) {
        if (isPresent(productId)) {
            int count = shoppingCart.get(productId);
            totalCount -= count;

            Product product = productService.getByID(productId).get();  //позже переделать
            totalCost -= product.getCost() * count;

            shoppingCart.remove(productId);
        } else {
            throw new ShoppingCardException("Product with ID=" + productId + " is not present in Shopping Cart");
        }
    }

    public void changeCount(ProductDTO dto) {
        Long productId = dto.getId();
        int newCount = dto.getCount();

        List<String> errors = new ArrayList<>();
        if (!isPresent(productId)) {
            errors.add("Product with ID=" + productId + " is not present in Shopping Cart");
        }
        if (newCount < 1) {
            errors.add("New count couldn't be less that 1");
        }
        if (errors.size() > 0) throw new ValidationException(errors);

        totalCount = totalCount - shoppingCart.get(productId) + newCount;
        totalCost = totalCost - shoppingCart.get(productId) * dto.getCost() + newCount * dto.getCost(); //переделать

        shoppingCart.put(productId, newCount);
    }

    public boolean isPresent(Long id) {
        return shoppingCart.get(id) != null;
    }

    public void validateProductId(Long id) {
        if (id == null) throw new NullPointerException("Product's ID = NULL.");
        try {
            productService.getByID(id).get();
        } catch (NoSuchElementException ex) {
            throw new ProductNotFoundException("Wrong product ID. Can't add this position to shopping cart.");
        }
    }

    private List<ProductDTO> getProductDTOList() {
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Map.Entry<Long, Integer> entity :
                shoppingCart.entrySet()) {
            Product product = productService.getByID(entity.getKey()).get();
            ProductDTO dto = ProductMapper.MAPPER.fromProduct(product);
            dto.setCount(entity.getValue());

            productDTOList.add(dto);
        }
        return productDTOList;
    }

    public List<ProductDTO> getAll() {

        return getProductDTOList();
    }

    public ShoppingCartDTO getShoppingCart() {

        List<ProductDTO> productDTOList = getProductDTOList();
        int count = 0;
        float cost = 0;
        for (ProductDTO prod :
                productDTOList) {
            count += prod.getCount();
            cost += prod.getCost() * prod.getCount();
        }

        return new ShoppingCartDTO(count, cost, productDTOList);
    }

}
