package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class PostRepository {
    private static final String FIND_CAR_FOR_CURRENT_TIME =
            """
                    FROM Post WHERE created BETWEEN :fFrom AND :fTo
            """;
    private final CrudRepository crudRepository;

    /**
     * показать объявления за последний день.
     *
     * @return list of posts.
     */
    public List<Post> findCreatedLastDay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        return crudRepository.query(
                FIND_CAR_FOR_CURRENT_TIME,
                Post.class,
                Map.of("fTo", now, "fFROM", yesterday));
    }


    /**
     * показать объявления с фото.
     *
     * @return list of posts.
     */
    public List<Post> findAllWithPhoto() {
        return crudRepository.query(
                "from Post where photo is not null",
                Post.class);
    }

    /**
     * показать объявления определенной марки.
     *
     * @param name Car brand
     * @return list of posts.
     */
    public List<Post> findByName(int name) {
        return crudRepository.query(
                "from Post p join fetch p.car c where c.name = :fName",
                Post.class,
                Map.of("fName", name)
        );
    }
}
