package org.mykitchen.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.mykitchen.be.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {
}