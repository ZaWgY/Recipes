package recipes.service;

import recipes.dto.IdDTO;
import recipes.dto.RecipeGetDTO;
import recipes.dto.RecipePostDTO;

import java.util.List;


public interface RecipeService {

    IdDTO addNewRecipe(RecipePostDTO recipeDTO, String email);

    RecipeGetDTO getRecipeById(Long id);

    void deleteRecipeById(Long id, String email);

    void updateRecipe(Long id, RecipePostDTO recipe, String email);

    List<RecipeGetDTO> getSpecificRecipe(String category, String name);
}
