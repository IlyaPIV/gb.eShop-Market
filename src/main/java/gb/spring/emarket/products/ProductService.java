package gb.spring.emarket.products;

import gb.spring.emarket.entity.Product;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Data
public class ProductService {

    @Autowired
    private ProductRepository repository;


    public List<Product> findAll(){
        return (List<Product>) repository.findAll();
    }

    public Product findById(Long id) throws ProductNotFoundException{
        try {
            return repository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ProductNotFoundException("Couldn't find product with id = " + id);
        }
    }

    public Product save(Product product) {

//        return repository.save(product);
        return null;
    }

    public void refreshList(){
//        if (repository instanceof ProductLocalRepository) {
//          //  ((ProductLocalRepository) repository).refresh();
//            System.out.println(">>> refresh local repository data");
//        }
    }

    public void delete(Long id) throws ProductNotFoundException{
        try {
            Product product = repository.findById(id).get();
            repository.delete(product);
        } catch (NoSuchElementException ex) {
            throw new ProductNotFoundException("Couldn't find product with id = " + id);
        }
    }

    public Product addNew(Product incomingProduct) {
       return repository.save(incomingProduct);
    }

    public List<Product> findAllWithFilter(Float min, Float max) {
        if (min == 0 && max == 0) return (List<Product>) repository.findAll();

        if (min < max) {
            return repository.findAllBetweenMinMaxPrice(min, max);
        } else {
            return repository.findAllWithPriceHigherThan(min);
        }
    }


}
