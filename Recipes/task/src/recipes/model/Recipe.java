package recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    private String category;

    private LocalDateTime date;
    @ElementCollection
    private List<String> ingredients;
    @ElementCollection
    private List<String> directions;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Recipe(String name, String description,String category, LocalDateTime date, List<String> ingredients, List<String> directions) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.category = category;
        this.date = date;
    }
}
