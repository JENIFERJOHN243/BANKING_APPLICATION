package validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	
    public boolean nameValidation(String userName) {
		
		Pattern pattern; 
		Matcher matcher;
		pattern = Pattern.compile("^[a-zA-Z]{4,15}$");
		matcher = pattern.matcher(userName);
		return matcher.matches();
	}
    
    public boolean mobileNumberValidation(String mobileNumber) {
		String mobileNumberValidation = "^[6-9][0-9]{9}$";
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(mobileNumberValidation);
		matcher = pattern.matcher(mobileNumber);
		return matcher.matches();
	}
}
