package gb.spring.emarket.products;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Data
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @PostConstruct
    private void initIfNeed(){
        if (repository instanceof ProductLocalRepository) {
            ((ProductLocalRepository) repository).initProductList(10);
            System.out.println(">>> init local repository data");
        }
    }

    public List<Product> findAll(){
        return repository.findAll();
    }

    public Product findById(Integer id) throws ProductNotFoundException{
        return repository.findById(id);
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public void refreshList(){
        if (repository instanceof ProductLocalRepository) {
            ((ProductLocalRepository) repository).refresh();
            System.out.println(">>> refresh local repository data");
        }

    }

}
