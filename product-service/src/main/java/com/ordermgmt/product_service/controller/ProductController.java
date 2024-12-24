package com.ordermgmt.product_service.controller;

import com.ordermgmt.product_service.model.Product;
import com.ordermgmt.product_service.model.ProductStocks;
import com.ordermgmt.product_service.model.ProductType;
import com.ordermgmt.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React app
public class ProductController {
    @Autowired
    private ProductService productService;

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    // Get all product categories
    @GetMapping("/types")
    public List<ProductType> getAllProductTypes() {
        return productService.getAllProductTypes();
    }

    // Get products by category
    @GetMapping("/type/{typeId}")
    public List<Product> getProductsByType(@PathVariable Long typeId) {
        return productService.getProductsByType(typeId);
    }

    // Get stock details for a product
    @GetMapping("/{productId}/stock")
    public ProductStocks getStockDetails(@PathVariable Long productId) {
        return productService.getStockDetails(productId);
    }

    // Update stock details for a product
    @PutMapping("/{productId}/updateStock")
    public ProductStocks updateStock(@PathVariable Long productId,
                                    @RequestParam Integer newStock,
                                    @RequestParam Integer reservedStock) {
        return productService.addOrUpdateStock(productId, newStock, reservedStock);
    }

    @PutMapping("/{productId}/removeStock")
    public ProductStocks removeStock(@PathVariable Long productId,
                                     @RequestParam Integer orderedStock,
                                     @RequestParam Integer orderedReservedStock) {
        return productService.removeStock(productId, orderedStock, orderedReservedStock);
    }

    @GetMapping("/{productId}/available")
    public boolean isStockAvailable(@PathVariable Long productId) {
        return productService.isStocksAvailable(productId);
    }
}
