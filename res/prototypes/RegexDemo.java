
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
//                String regExStr = in.readLine();
                String re1=".*?";	// Non-greedy match on filler
                String re2="(?:[a-z][a-z0-9_]*)";	// Uninteresting: var
                String re3=".*?";	// Non-greedy match on filler
                String re4="((?:[a-z][a-z0-9_]*))";	// Variable Name 1
                String regExStr = re1+re2+re3+re4;
                Pattern pattern = Pattern.compile(regExStr);

                //https://www.codeproject.com/articles/5412/writing-own-regular-expression-parser
               // https://regexr.com/
               // https://stackoverflow.com/questions/11009320/validate-mathematical-expressions-using-regular-expression

                //https://www.regexpal.com/93433
//                System.out.println("\nEnter input string to search: ");
//                String inputStr = in.readLine();
//            	String f = "vec2(z.x * z.x - z.y * z.y, 2.0 * z.x * z.y) + c";
            	String f = "vec2(z) + c";
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
