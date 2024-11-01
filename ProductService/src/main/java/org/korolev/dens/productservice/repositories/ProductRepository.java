package org.korolev.dens.productservice.repositories;

import jakarta.validation.constraints.NotNull;
import org.korolev.dens.productservice.entities.Product;
import org.korolev.dens.productservice.entities.UnitOfMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
  int countAllByPartNumber(@NotNull String partNumber);
  int countAllByOwner_PassportID(@NotNull String passportID);
  int countAllByUnitOfMeasureGreaterThan(UnitOfMeasure unitOfMeasure);
}