package ord.sid.inventoryservice;

import ord.sid.inventoryservice.entities.Product;
import ord.sid.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration){
        restConfiguration.exposeIdsFor(Product.class);
        return args -> {
            productRepository.save(new Product(null,"ordinateur",788,12));
            productRepository.save(new Product(null,"imprimante",88,129));
            productRepository.save(new Product(null,"smart phone",1288,112));
            productRepository.findAll().forEach(p->{
                System.out.println(p.getName());
            });

        };
    }
}
