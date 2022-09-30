package gb.spring.emarket.products;

import java.util.List;

public interface ProductRepository {

    public List<Product> findAll();

    public Product findById(Integer id) throws ProductNotFoundException;

    public Product save(Product product);
}
