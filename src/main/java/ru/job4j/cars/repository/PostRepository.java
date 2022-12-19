package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostRepository {
    private static final String FIND_CAR_FOR_CURRENT_TIME =
            """
                            FROM Post WHERE created BETWEEN :fFrom AND :fTo
                    """;
    private final CrudRepository crudRepository;

    public Optional<Post> create(Post post) {
        try {
            crudRepository.run(session -> session.persist(post));
            return Optional.of(post);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Update Post in database.
     *
     * @param post Post.
     */
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    /**
     * Delete Post by id.
     *
     * @param postId ID
     */
    public void delete(int postId) {
        crudRepository.run(
                "delete from Post where id = :fId",
                Map.of("fId", postId)
        );
    }

    /**
     * Find all posts ordered by id.
     *
     * @return list of posts.
     */
    public List<Post> findAllOrderById() {
        return crudRepository.query("from Post p join fetch p.car c join fetch c.engine order by p.id",
                Post.class
        );
    }

    /**
     * Find Post by id
     *
     * @param postId ID
     * @return Post.
     */
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "from Post where id = :fId", Post.class,
                Map.of("fId", postId)
        );
    }

    /**
     * показать объявления за последний день.
     *
     * @return list of posts.
     */
    public List<Post> findCreatedLastDay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
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
