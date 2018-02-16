
package prototypes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
 
public class RegexDemo {
 
    public static void main(String[] args) {
 
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
 
        while (true) {
            try{
                System.out.println("Enter your regex: ");
                String regExStr = in.readLine();
                Pattern pattern = Pattern.compile(regExStr);

                //https://www.codeproject.com/articles/5412/writing-own-regular-expression-parser
               // https://regexr.com/
               // https://stackoverflow.com/questions/11009320/validate-mathematical-expressions-using-regular-expression

                //https://www.regexpal.com/93433
//                System.out.println("\nEnter input string to search: ");
//                String inputStr = in.readLine();
            	String f = "z*z + c";
                Matcher matcher = pattern.matcher(f);
 
                boolean found = false;
                while (matcher.find()) {
                    System.out.println("\nI found the text " + matcher.group() 
                                                        + " starting at index "
                            + matcher.start() + " and ending at " + matcher.end());
                    found = true;
                }
                if (!found) {
                    System.out.println("\nNo match found.");
                }
            }
            catch (Exception e) {
                System.out.println("\nCaught an exception. Description: " + e.getMessage());
            }
            System.out.println("---------");
        }
    }
}
