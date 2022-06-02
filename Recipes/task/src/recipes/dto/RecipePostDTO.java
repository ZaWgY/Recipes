package recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipePostDTO {
    @NotNull @NotBlank
    String name;
    @NotNull @NotBlank
    String description;
    @NotNull @NotBlank
    String category;
    @NotNull @Size(min = 1)
    List<String> ingredients;
    @NotNull @Size(min = 1)
    List<String> directions;
}