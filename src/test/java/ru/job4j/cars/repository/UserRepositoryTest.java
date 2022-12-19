package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final UserRepository userRepository = new UserRepository(crudRepository);

    @AfterEach
    void tearDown() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM auto_user").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenCreateUserThenSameUser() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        assertThat(userRepository.create(user)).isEqualTo(Optional.of(user));
    }

    @Test
    public void whenUpdateUserThenNewUser() {
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        userRepository.create(user);
        user.setLogin("new user");
        user.setPassword("new password");
        userRepository.update(user);
        User result = userRepository.findById(user.getId()).get();
        assertThat(result.getLogin()).isEqualTo("new user");
        assertThat(result.getPassword()).isEqualTo("new password");
    }
}