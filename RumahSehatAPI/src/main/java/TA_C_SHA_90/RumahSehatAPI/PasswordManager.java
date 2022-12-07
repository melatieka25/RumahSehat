package TA_C_SHA_90.RumahSehatAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordManager {
    //Referensi: https://codescracker.com/java/program/java-check-password-strength.htm
    public static boolean validationChecker(String password){
        int passwordLength = password.length();
        int upChars = 0, lowChars = 0, digits = 0, special = 0;
        if(passwordLength < 8) {
            return false;
        } else {
            for(int i=0; i<passwordLength; i++) {
                Character ch = password.charAt(i);
                if(Character.isUpperCase(ch))
                    upChars = 1;
                else if(Character.isLowerCase(ch))
                    lowChars = 1;
                else if(Character.isDigit(ch))
                    digits = 1;
                else
                    special = 1;
            }
        }
        if(upChars==1 && lowChars==1 && digits==1 && special==1)
            return true;
        else
            return false;
    }
}
