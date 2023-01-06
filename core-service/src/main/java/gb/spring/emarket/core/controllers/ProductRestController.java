package gb.spring.emarket.core.controllers;


import gb.spring.emarket.api.dto.ProductDTO;
import gb.spring.emarket.api.errors.WebServiceErrorMessage;
import gb.spring.emarket.api.errors.ProductsWebServiceError;
import gb.spring.emarket.core.services.ProductService;
import gb.spring.emarket.core.validators.ProductValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v2/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Methods working with products list")
public class ProductRestController {

    private final ProductService service;
    private final ProductValidator validator;

    @GetMapping()
    @Operation(
            summary = "Request for getting products page",
            responses = {
                    @ApiResponse(
                            description = "Success response", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    public Page<ProductDTO> getAllProducts(@RequestParam(name = "page", defaultValue = "1") int pageNum,
                                           @RequestParam(name = "minPrice", required = false) Integer minPrice,
                                           @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
                                           @RequestParam(name = "name", required = false) String partName) {
        return service.getPage(pageNum, minPrice, maxPrice, partName);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Request for getting product by Id",
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDTO.class))
                    ),
                    @ApiResponse(
                            description = "Error", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = WebServiceErrorMessage.class))
                    )
            }
    )
    public ProductDTO findById(@PathVariable @Parameter(description = "Product's id", required = true) Long id) {
        return service.findById(id);
    }

    @PostMapping()
    @Operation(
            summary = "Request for creating new product from DTO object",
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDTO.class))
                    ),
                    @ApiResponse(
                            description = "validation error", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ProductsWebServiceError.class))
                    )
            }
    )
    public ProductDTO addNewProduct(@RequestBody @Parameter(description = "Product's DTO with title and price", required = true) ProductDTO productDTO) {
        validator.validate(productDTO);
        return service.addNew(productDTO);
    }

    @PutMapping()
    @Operation(
            summary = "Request for updating product",
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDTO.class))
                    ),
                    @ApiResponse(
                            description = "validation error", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ProductsWebServiceError.class))
                    )
            }
    )
    public void updateProduct(@RequestBody @Parameter(description = "Product's DTO with title and price", required = true) ProductDTO productDTO) {
        validator.validate(productDTO);
        service.update(productDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Request for deleting product by Id",
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDTO.class))
                    ),
                    @ApiResponse(
                            description = "Not found error", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ProductsWebServiceError.class))
                    )
            }
    )
    public void deleteProductById(@PathVariable @Parameter(description = "Product's id", required = true) Long id) {
        service.delete(id);
    }


}
