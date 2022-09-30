package gb.spring.emarket.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("")
    public String showProductList(Model model){
        model.addAttribute("productList", service.findAll());

        return "products/products";
    }

    @GetMapping("/new")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Add new product");

        return "products/product_form";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes){
        try {
            Product product = service.findById(id);
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", "Edit product (ID: " + id + ")");

            return "products/product_form";
        } catch (ProductNotFoundException ex) {


            return "redirect:/products";
        }
    }

    @PostMapping("/save")
    public String saveProduct(Product product){

        service.save(product);

        return "redirect:/products";
    }

    @GetMapping("/refresh")
    public String refreshList(){
        service.refreshList();
        return "redirect:/products";
    }
}
