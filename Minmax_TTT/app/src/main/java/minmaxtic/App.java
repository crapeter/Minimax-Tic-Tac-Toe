package minmaxtic;

public class App {
    public String getGreeting() {
        return "Tic Tac Toe with the Minimax Algorithm";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        new Game();
    }
}
