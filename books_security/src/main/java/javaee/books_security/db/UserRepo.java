package javaee.books_security.db;

import javaee.books_security.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("SELECT user FROM User user "
            + "LEFT JOIN FETCH user.permissions "
            + "WHERE user.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

}
