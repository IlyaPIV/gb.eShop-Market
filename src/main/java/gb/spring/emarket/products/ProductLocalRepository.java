package gb.spring.emarket.products;


import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ProductLocalRepository implements ProductRepository {

    private final ProductsGenerator prodGen;
    private final ConcurrentHashMap<Integer, Product> priceList;
    private static AtomicInteger CURRENT_NEW_ID;

    public ProductLocalRepository() {
        this.prodGen   = new ProductsGenerator();
        this.priceList = new ConcurrentHashMap<>();
        CURRENT_NEW_ID = new AtomicInteger(0);
    }

    public void initProductList(int size) {
        List<Product> productList = prodGen.generateList(size);
        for (Product prod:
             productList) {
            CURRENT_NEW_ID.set(prod.getId() + 1);
            priceList.put(prod.getId(), prod);
        }
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(priceList.values());
    }

    public void refresh(){
        priceList.clear();
        CURRENT_NEW_ID.set(0);
        initProductList(10);
    }

    @Override
    public Product findById(Integer id) throws ProductNotFoundException{
        Product prod = priceList.get(id);
        if (prod == null) throw new ProductNotFoundException("No such product with ID = " + id + " in list");
        return priceList.get(id);
    }

    @Override
    public Product save(Product product) {
        product.setId(CURRENT_NEW_ID.get());
        priceList.put(CURRENT_NEW_ID.get(), product);
        CURRENT_NEW_ID.getAndIncrement();

        return product;
    }

    @Override
    public void delete(Product product) {
        priceList.remove(product.getId());
    }

    public Product addNew(String name, Integer cost){
        Product newProd = new Product(CURRENT_NEW_ID.get(), name, cost);
        priceList.put(CURRENT_NEW_ID.get(), newProd);
        CURRENT_NEW_ID.getAndIncrement();

        return newProd;
    }
}
