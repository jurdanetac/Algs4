/* Says hi and goodbye to the two names passed */

public class HelloGoodbye {
    public static void main(String[] args) {
        // greet
        System.out.println(String.format("Hello %s and %s.", args[0], args[1]));

        // goodbye
        System.out.println(String.format("Goodbye %s and %s.", args[1], args[0]));
    }
}
