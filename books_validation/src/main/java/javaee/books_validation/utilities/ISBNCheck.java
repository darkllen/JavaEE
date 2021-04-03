package javaee.books_validation.utilities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ISBNCheck implements
        ConstraintValidator<ISBNValidator, String> {

    @Override
    public void initialize(ISBNValidator isbn) {
    }

    private boolean checkSum(int[] digits){
        int sum = 0;
        for (int i = 0; i < digits.length-1; i++) {
            int multip = i%2==0? 1:3;
            sum +=digits[i]*multip;
        }
        int modul = (sum%10);
        modul = modul==0?0:10-modul;
        return modul==digits[digits.length-1];
    }

    @Override
    public boolean isValid(String isbn,
            ConstraintValidatorContext cxt) {
        if (isbn.matches("ISBN (978|979)-[0-9]-[0-9]{1,7}-[0-9]{1,7}-[0-9]")
                && (isbn.length() == 22)){
            int[] digits = isbn.chars().filter(Character::isDigit).map(Character::getNumericValue).toArray();
            return checkSum(digits);
        }
        return false;
    }

}
