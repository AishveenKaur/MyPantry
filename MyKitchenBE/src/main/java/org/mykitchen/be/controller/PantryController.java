package org.mykitchen.be.controller;

import org.mykitchen.be.model.Ingredient;
import org.mykitchen.be.service.PantryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pantry")
public class PantryController {

    private final PantryService service;

    public PantryController(PantryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ingredient> list() {
        return service.list();
    }

    @PostMapping
    public Ingredient add(@RequestBody Ingredient ing) {
        return service.add(ing);
    }

    @DeleteMapping("/{name}")
    public void remove(@PathVariable String name) {
        service.delete(name);
    }
}
