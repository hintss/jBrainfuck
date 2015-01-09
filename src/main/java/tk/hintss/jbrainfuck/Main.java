package tk.hintss.jbrainfuck;

/**
 * Created by Henry on 1/8/2015.
 */
public class Main {
    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter("++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.");
        interpreter.exec();
        System.out.println(interpreter.getOutput());
    }
}
