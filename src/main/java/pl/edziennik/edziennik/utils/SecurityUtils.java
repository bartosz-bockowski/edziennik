package pl.edziennik.edziennik.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityUtils {
    public static Boolean checkPassword(String password){
        if(!checkAtLeastOneCapitalLetterInPassword(password)){
            return false;
        }
        if(!checkAtLeastOneSpecialCharacterInPassword(password)){
            return false;
        }
        if(!checkAtLeastOneDigitInPassword(password)){
            return false;
        }
        return checkPasswordLength(password);
    }
    public static Boolean checkAtLeastOneCapitalLetterInPassword(String password){
        return checkRegexText(".*[!@#$%^&*].*",password);
    }
    public static Boolean checkAtLeastOneSpecialCharacterInPassword(String password){
        return checkRegexText(".*[A-Z].*",password);
    }

    public static Boolean checkAtLeastOneDigitInPassword(String password){
        return checkRegexText(".*[1-9].*",password);
    }

    public static Boolean checkPasswordLength(String password){
        return password.length() >= 10;
    }

    public static Boolean checkRegexText(String regex, String text){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
