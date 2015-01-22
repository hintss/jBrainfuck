package tk.hintss.jbrainfuck.derivatives;

/**
 * Created by Henry on 1/22/2015.
 */
public interface BfDerivative {
    /**
     * Takes code written in a brainfuck derivative, and outputs the code translated into brainfuck
     * @param code input code in this derivative
     * @return the code, in brainfuck
     */
    public abstract String process(String code);
}
