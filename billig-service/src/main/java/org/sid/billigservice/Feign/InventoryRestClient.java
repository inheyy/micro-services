package org.sid.billigservice.Feign;

import org.sid.billigservice.Model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.QueryParam;

@FeignClient(name = "PRODUCT-SERVICE")
public interface InventoryRestClient {
    @GetMapping(path ="/products/{id}")
    public Product getProductById(@PathVariable Long id);
    @GetMapping(path ="/products")
    public PagedModel<Product> pageProducts();
}
