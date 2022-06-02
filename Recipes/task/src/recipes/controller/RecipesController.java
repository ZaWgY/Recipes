package recipes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import recipes.dto.IdDTO;
import recipes.dto.RecipeGetDTO;
import recipes.dto.RecipePostDTO;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipesController {

    private RecipeService recipeService;

    @Autowired
    public RecipesController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public ResponseEntity<IdDTO> addRecipe(@Valid @RequestBody RecipePostDTO recipe, Authentication authentication) {
        IdDTO id = this.recipeService.addNewRecipe(recipe, authentication.getName());
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeGetDTO> getRecipe(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id, Authentication authentication) {
        recipeService.deleteRecipeById(id, authentication.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id,@Valid @RequestBody RecipePostDTO recipe, Authentication authentication) {
        recipeService.updateRecipe(id,recipe, authentication.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeGetDTO>> getSpecificRecipe(@RequestParam(name = "category", required = false) String category,
                                                                @RequestParam(name = "name", required = false) String name) {

        return ResponseEntity.ok(recipeService.getSpecificRecipe(category,name));
    }


}
