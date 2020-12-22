package org.sid.billigservice;

import org.sid.billigservice.Entities.Bill;
import org.sid.billigservice.Entities.ProductItem;
import org.sid.billigservice.Feign.CustomerRestClient;
import org.sid.billigservice.Feign.InventoryRestClient;
import org.sid.billigservice.Model.Customer;
import org.sid.billigservice.Model.Product;
import org.sid.billigservice.Repository.BillRepository;
import org.sid.billigservice.Repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BilligServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BilligServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(
            BillRepository billRepository,
            ProductItemRepository productItemRepository,
            CustomerRestClient customerRestClient,
            InventoryRestClient inventoryRestClient
    ){
        return args -> {
            Customer customer= customerRestClient.getCustomerById(1L);
            Bill bill=billRepository.save(new Bill(null,new Date(),null,customer.getId(),null));
            PagedModel<Product> productPagedModel=inventoryRestClient.pageProducts();
            productPagedModel.forEach(p->{
                ProductItem productItem=new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity(1+new Random().nextInt(100));
                productItem.setBill(bill);
                productItem.setProductID(p.getId());
                productItemRepository.save(productItem);
            });
        };
    }
}
