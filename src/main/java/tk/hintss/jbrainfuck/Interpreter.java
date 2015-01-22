package tk.hintss.jbrainfuck;

import tk.hintss.jbrainfuck.derivatives.BfDerivative;

/**
 * Created by Henry on 1/8/2015.
 */
public class Interpreter {
    // The code to be executed
    private char[] exec = new char[0];

    // The input to the program
    private String input = "";

    // The output of the program
    private StringBuilder output = new StringBuilder();

    // The output of the program since the last time this was checked
    private StringBuilder newOutput = new StringBuilder();

    // The program's memory
    private byte[] mem = new byte[1];

    // current execution point in exec
    private int execPointer = 0;

    // current pointer location
    private int dataPointer = 0;

    // The interpreter's current state
    private InterpreterState currentState = InterpreterState.WAITING_TO_START;

    /**
     * Creates a new Interpreter object that will run this brainfuck code
     * @param code The BrainFuck code for this interpreter to run
     */
    public Interpreter(String code) {
        StringBuilder sb = new StringBuilder();

        for (char c : code.toCharArray()) {
            // exclude non-brainfuck characters
            if (c == '>' || c == '<' || c == '+' || c == '-' || c == '.' || c == ',' || c == '[' || c == ']') {
                sb.append(c);
            }
        }

        exec = sb.toString().toCharArray();
    }

    /**
     * Creates a new Interpreter object that will run this $brainfuckDerivative code
     * @param code The $brainfuckDerivative code for this interpreter to run
     * @param derivative The $brainfuckDerivative
     */
    public Interpreter(String code, BfDerivative derivative) {
        this(derivative.process(code));
    }

    /**
     * Runs the BrainFuck program
     */
    public void exec() {
        currentState = InterpreterState.RUNNING;

        // while we're not at the end of the program
        while (execPointer < exec.length) {
            // run this step
            switch(exec[execPointer]) {
                // increment pointer
                case '>':
                    dataPointer++;
                    break;
                // decrement pointer
                case '<':
                    dataPointer--;
                    break;
                // increment byte at the pointer
                case '+':
                    mem[dataPointer]++;
                    break;
                // decrement byte at the pointer
                case '-':
                    mem[dataPointer]--;
                    break;
                // print the byte at the pointer
                case '.':
                    output.append((char) mem[dataPointer]);
                    newOutput.append((char) mem[dataPointer]);
                    break;
                // take a byte from input, store it's value at current pointer
                case ',':
                    mem[dataPointer] = ((byte) input.charAt(0));
                    input = input.substring(1);
                    break;
                // if byte at pointer is 0, jump to command after matching ']'
                case '[':
                    // set the pointer to the matching ']'
                    if (mem[dataPointer] == 0) {
                        // how many other '[' we see, so we can skip a matching number of ']'
                        int encountered = 1;

                        // the address of the matching ']'
                        int matching = 0;

                        search:
                        for (int i = execPointer + 1; i < exec.length; i++) {
                            switch(exec[i]) {
                                case '[':
                                    encountered++;
                                    break;
                                case ']':
                                    encountered--;

                                    if (encountered == 0) {
                                        matching = i;

                                        break search;
                                    }

                                    break;
                            }
                        }

                        execPointer = matching;
                    }

                    break;
                // if the byte at pointer is != 0, jump back to command after matching '['
                case ']':
                    // set the pointer to the matching '['
                    if (mem[dataPointer] != 0) {
                        // how many other ']' we see, so we can skip a matching number of '['
                        int encountered = -1;

                        // the address of the matching '['
                        int matching = 0;

                        search:
                        for (int i = execPointer - 1; i >= 0; i--) {
                            switch(exec[i]) {
                                case '[':
                                    encountered++;

                                    if (encountered == 0) {
                                        matching = i;

                                        break search;
                                    }

                                    break;
                                case ']':
                                    encountered--;
                                    break;
                            }
                        }
                        execPointer = matching;
                    }

                    break;
            }

            // increase the memory array size if we exceeded it
            if (dataPointer >= mem.length) {
                byte[] newMem = new byte[mem.length * 2];
                System.arraycopy(mem, 0, newMem, 0, mem.length);
                mem = newMem;
            }

            execPointer++;
        }

        currentState = InterpreterState.DONE;

        System.out.println("");
    }

    /**
     * Sends input to the brainfuck program
     * @param newInput The input to give it
     */
    public void sendInput(String newInput) {
        input += newInput;
    }

    /**
     * Gets any output that this program has generated so far
     * @return All the output so far
     */
    public String getOutput() {
        return output.toString();
    }

    /**
     * Gets any new output that was generated since the last time this function was called
     * @return Any new output that may have been created
     */
    public String getNewOutput() {
        String output = newOutput.toString();
        newOutput = new StringBuilder();
        return output;
    }

    /**
     * Resets this Interpreter to it's state before its first runs and inputs
     */
    public void reset() {
        input = "";
        output = new StringBuilder();
        newOutput = new StringBuilder();
        mem = new byte[1];
        execPointer = 0;
        dataPointer = 0;
    }

    /**
     * Gets the current state of this Interpreter
     * @return This interpreter's current state
     */
    public InterpreterState getState() {
        return currentState;
    }
}
