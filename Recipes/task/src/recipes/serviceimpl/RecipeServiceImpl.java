package recipes.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.dto.IdDTO;
import recipes.dto.RecipeGetDTO;
import recipes.dto.RecipePostDTO;
import recipes.mapper.RecipeMapper;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.repository.RecipeRepository;
import recipes.repository.UserRepository;
import recipes.service.RecipeService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;
    private UserRepository userRepository;
    private RecipeMapper recipeMapper;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper, UserRepository userRepository) {
        this.recipeMapper = recipeMapper;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public IdDTO addNewRecipe(RecipePostDTO recipeDTO, String email) {
        User user =  userRepository.findUserByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST)
        );
        if (recipeDTO != null) {
            Recipe recipe = new Recipe();
            recipe.setName(recipeDTO.getName());
            recipe.setDate(LocalDateTime.now());
            recipe.setCategory(recipeDTO.getCategory());
            recipe.setIngredients(recipeDTO.getIngredients());
            recipe.setDescription(recipeDTO.getDescription());
            recipe.setDirections(recipeDTO.getDirections());
            recipe.setUser(user);

            this.recipeRepository.save(recipe);
            return new IdDTO(recipe.getId());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public RecipeGetDTO getRecipeById(Long id) {
        if(id != null){
           Recipe recipe =  recipeRepository.findById(id)
                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
           return new RecipeGetDTO(
                   recipe.getName(),
                   recipe.getDescription(),
                   recipe.getCategory(),
                   recipe.getDate().toString(),
                   recipe.getIngredients(),
                   recipe.getDirections()
           );
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public void deleteRecipeById(Long id, String email) {
        if(id != null) {
            Recipe recipe =  recipeRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            if (!recipe.getUser().getEmail().equals(email))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            recipeRepository.delete(recipe);

        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public void updateRecipe(Long id, RecipePostDTO recipePostDTO, String email) {
        if(id != null) {
            Recipe recipe =  recipeRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            if (!recipe.getUser().getEmail().equals(email))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            recipe.setName(recipePostDTO.getName());
            recipe.setDescription(recipePostDTO.getDescription());
            recipe.setCategory(recipePostDTO.getCategory());
            recipe.setDate(LocalDateTime.now());
            recipe.setDirections(recipePostDTO.getDirections());
            recipe.setIngredients(recipePostDTO.getIngredients());
            recipeRepository.save(recipe);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<RecipeGetDTO> getSpecificRecipe(String category, String name) {
        System.out.println("Category is " + category + "   Name is " + name);
        if(category != null && name == null) {
            return recipeMapper.mapEntityListToGetDTOList(
                    recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category)
            );

        }
        if(name != null && category == null) {
            return recipeMapper.mapEntityListToGetDTOList(
                    recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name)
            );

        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
