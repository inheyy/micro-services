package org.sid.billigservice.Repository;

import org.sid.billigservice.Entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {
   public Collection<ProductItem> findByBillId(Long id);
}
