package javaee.books_validation.utilities;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ISBNCheck.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ISBNValidator {
    String message() default "ISBN should be valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}