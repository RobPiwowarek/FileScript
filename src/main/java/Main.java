public class Main {
    public static void main(String[] args) {
        System.setProperty("resources", "src/main/resources/");
        Interpreter interpreter = new Interpreter();
        interpreter.execute();
    }
}
