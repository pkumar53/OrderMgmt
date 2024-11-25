package com.ordermgmt.product_service.service;

import com.ordermgmt.product_service.model.Product;
import com.ordermgmt.product_service.model.ProductStocks;
import com.ordermgmt.product_service.model.ProductType;
import com.ordermgmt.product_service.repository.ProductRepository;
import com.ordermgmt.product_service.repository.ProductStocksRepository;
import com.ordermgmt.product_service.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductStocksRepository productStockRepository;

    // Fetch all product types
    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    // Fetch all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Fetch all products by product type
    public List<Product> getProductsByType(Long productTypeId) {
        return productRepository.findByProductType_ProductTypeId(productTypeId);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Fetch stock details for a product
    public ProductStocks getStockDetails(Long productId) {
        return productStockRepository.findByProduct_ProductId(productId);
    }

    // Add or update stock for a product
    public ProductStocks addOrUpdateStock(Long productId, Integer newStock, Integer newReservedStock) {
        ProductStocks stock = productStockRepository.findByProduct_ProductId(productId);
        if (stock == null) {
            Product product = getProductById(productId);
            stock = new ProductStocks();
            stock.setProduct(product);
        }
        stock.setStockQuantity(stock.getStockQuantity() + newStock);
        stock.setReservedStock(stock.getReservedStock() + newReservedStock);
        return productStockRepository.save(stock);
    }

    public ProductStocks removeStock(Long productId, Integer orderedStock, Integer orderedReservedStock) {
        ProductStocks stock = productStockRepository.findByProduct_ProductId(productId);
        if (stock == null) {
            throw new RuntimeException("Stocks not available to remove");
        }
        stock.setProduct(getProductById(productId));
        stock.setStockQuantity(stock.getStockQuantity() - orderedStock);
        stock.setReservedStock(stock.getReservedStock() - orderedReservedStock);
        return productStockRepository.save(stock);
    }

    public boolean isStocksAvailable(Long productId) {
        ProductStocks stock = productStockRepository.findByProduct_ProductId(productId);
        if (stock == null) {
            return false;
        }
        return stock.getStockQuantity() > 0;
    }
}
