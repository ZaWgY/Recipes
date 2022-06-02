package recipes.mapper;

import org.springframework.stereotype.Component;
import recipes.dto.RecipeGetDTO;
import recipes.model.Recipe;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {

    public RecipeGetDTO mapEntityToGetDTO(Recipe recipe) {
        RecipeGetDTO recipeGetDTO = new RecipeGetDTO();
        recipeGetDTO.setName(recipe.getName());
        recipeGetDTO.setCategory(recipe.getCategory());
        recipeGetDTO.setDate(recipe.getDate().toString());
        recipeGetDTO.setDescription(recipe.getDescription());
        recipeGetDTO.setDirections(recipe.getDirections());
        recipeGetDTO.setIngredients(recipe.getIngredients());
        return recipeGetDTO;
    }
    public List<RecipeGetDTO> mapEntityListToGetDTOList(List<Recipe> recipes) {
        return recipes.stream().map(this::mapEntityToGetDTO).collect(Collectors.toList());
    }
}
