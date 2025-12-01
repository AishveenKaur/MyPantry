package org.mykitchen.be.service;

import org.springframework.stereotype.Service;
import org.mykitchen.be.model.Ingredient;
import org.mykitchen.be.repository.IngredientRepository;

import java.util.List;

@Service
public class PantryService {

    private final IngredientRepository repo;

    public PantryService(IngredientRepository repo) {
        this.repo = repo;
    }

    public List<Ingredient> list() {
        return repo.findAll();
    }

    public Ingredient add(Ingredient ing) {
        return repo.save(ing);
    }

    public void delete(String name) {
        repo.deleteById(name);
    }
}