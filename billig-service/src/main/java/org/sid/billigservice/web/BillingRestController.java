package org.sid.billigservice.web;

import org.sid.billigservice.Entities.Bill;
import org.sid.billigservice.Feign.CustomerRestClient;
import org.sid.billigservice.Feign.InventoryRestClient;
import org.sid.billigservice.Model.Customer;
import org.sid.billigservice.Model.Product;
import org.sid.billigservice.Repository.BillRepository;
import org.sid.billigservice.Repository.ProductItemRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private InventoryRestClient inventoryRestClient;
    public BillingRestController(BillRepository billRepository,
                                 ProductItemRepository productItemRepository,
                                 CustomerRestClient customerRestClient, InventoryRestClient inventoryRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.inventoryRestClient = inventoryRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill=billRepository.findById(id).get();
        Customer customer=customerRestClient.getCustomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(p->{
            Product product=inventoryRestClient.getProductById(p.getProductID());
            //p.setProduct(product);
            p.setProductName(product.getName());
        });
        return bill;
    }
}
