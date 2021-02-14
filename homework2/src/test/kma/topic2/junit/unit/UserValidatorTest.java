package kma.topic2.junit.unit;

import kma.topic2.junit.exceptions.ConstraintViolationException;
import kma.topic2.junit.exceptions.LoginExistsException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.repository.UserRepository;
import kma.topic2.junit.validation.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest{
    /**
     * We mock userRepository to test only userValidator Unit, without depending on userRepository behavior
     */
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserValidator userValidator;

    /**
     * test for validation User with login that already exists in our userRepository
     */
    @Test
    void throwExceptionIfLoginExists(){
        var login = "test_login";
        //mock isLoginExists method to simulate login existence
        when(userRepository.isLoginExists(login)).thenReturn(true);
        //assert exception was thrown
        assertThatThrownBy(() -> userValidator.validateNewUser(
                NewUser.builder()
                        .login(login)
                        .fullName("any")
                        .password("pass")
                        .build()
        )).isInstanceOf(LoginExistsException.class)
                .hasMessage("Login " +login+" already taken");
    }

    /**
     * test right passwords to pass our validation
     * @param password to test
     */
    @ParameterizedTest(name="password \"{0}\" should pass validation")
    @MethodSource("passwordsPass")
    void passwordShouldPass(String password){
        var login = "login";
        //mock isLoginExists method to simulate login absent
        when(userRepository.isLoginExists(login)).thenReturn(false);
        userValidator.validateNewUser(
                NewUser.builder()
                        .login(login)
                        .fullName("any")
                        .password(password)
                        .build()
        );
    }

    /**
     * test wrong passwords to fail our validation
     * @param password to test
     */
    @ParameterizedTest(name="password \"{0}\" should fail with ConstraintViolationException")
    @MethodSource("passwordsFailed")
    void throwExceptionIfWrongPass(String password){
        var login = "login";
        //mock isLoginExists method to simulate login absent
        when(userRepository.isLoginExists(login)).thenReturn(false);
        assertThatThrownBy(() -> userValidator.validateNewUser(
                NewUser.builder()
                        .login(login)
                        .fullName("any")
                        .password(password)
                        .build()
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessage("You have errors in you object");
    }

    /**
     * @return params for throwExceptionIfWrongPass test
     */
    private static Stream<Arguments> passwordsFailed(){

        return Stream.of(
                Arguments.of(""),
                Arguments.of("Фффф"),
                Arguments.of("-123"),
                Arguments.of("my_pas"),
                Arguments.of("12"),
                Arguments.of("12345678"),
                Arguments.of("1234567891"));
    }

    /**
     * @return params for passwordShouldPass test
     */
    private static Stream<Arguments> passwordsPass(){
        return Stream.of(
                Arguments.of("pas"),
                Arguments.of("pasword"),
                Arguments.of("PAS"),
                Arguments.of("PASword"),
                Arguments.of("1234567"),
                Arguments.of("pas123w"));
    }
}