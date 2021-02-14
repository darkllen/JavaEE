package kma.topic2.junit.integration;

import kma.topic2.junit.UserService;
import kma.topic2.junit.exceptions.ConstraintViolationException;
import kma.topic2.junit.exceptions.LoginExistsException;
import kma.topic2.junit.exceptions.UserNotFoundException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.model.User;
import kma.topic2.junit.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//annotation to guarantee that throwLoginExistsExceptionIfUserExists test will be called after creationNewUserPass test
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@SpringBootTest()
class UserServiceTest {

    //create userService and userRepository from beans
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    /**
     * create new valid user and check if it was recorded in userRepository
     * @param login user login
     * @param name user name
     * @param pass user password
     */
    @ParameterizedTest(name="create user with login \"{0}\" first time")
    @MethodSource("userCreateParams")
    void creationNewUserPass(String login, String name, String pass){
        userService.createNewUser(
                NewUser.builder()
                        .login(login)
                        .fullName(name)
                        .password(pass).
                        build()
        );
        assertThat(User
                .builder()
                .login(login)
                .fullName(name)
                .password(pass)
                .build()).isEqualTo(userRepository.getUserByLogin(login));

    }

    /**
     * create user that already exists and get LoginExistsException
     * @param login user login
     * @param name user name
     * @param pass user password
     */
    @ParameterizedTest(name="trying to create user with login \"{0}\" one more time")
    @MethodSource("userCreateParams")
    void throwLoginExistsExceptionIfUserExists(String login, String name, String pass){
        assertThatThrownBy(()->userService.createNewUser(
                NewUser.builder()
                        .login(login)
                        .fullName(name)
                        .password(pass).
                        build()
        )).isInstanceOf(LoginExistsException.class)
        .hasMessage("Login "+login+" already taken");

    }

    /**
     * trying to create wrong user, check that it wasn't recorded in userRepository
     */
    @Test
    void creationWrongUser(){
        var login = "otherLogn";
        var name = "name";
        var pass = "";
        assertThatThrownBy(()->userService.createNewUser(
                NewUser.builder()
                        .login(login)
                        .fullName(name)
                        .password(pass).
                        build()
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessage("You have errors in you object");

        assertThatThrownBy(()->userRepository.getUserByLogin(login))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Can't find user by login: "+login);

    }

    /**
     * @return params for creationNewUserPass and throwLoginExistsExceptionIfUserExists tests
     */
    private static Stream<Arguments> userCreateParams(){
        return Stream.of(
                Arguments.of("login", "name", "pass"));
    }

}