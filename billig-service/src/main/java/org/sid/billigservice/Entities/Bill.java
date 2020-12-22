package org.sid.billigservice.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.billigservice.Model.Customer;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Bill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date billingDate;
    @OneToMany(mappedBy = "bill")
    private Collection<ProductItem> productItems;
    private Long customerID;
    @Transient
    private Customer customer;
}
