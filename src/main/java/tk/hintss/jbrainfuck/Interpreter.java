package tk.hintss.jbrainfuck;

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

    // The program's memory
    private byte[] mem = new byte[1];

    // current execution point in exec
    private int execPointer = 0;

    // current pointer location
    private int dataPointer = 0;

    public Interpreter(String code) {
        StringBuilder sb = new StringBuilder();

        for (char c : code.toCharArray()) {
            if (c == '>' || c == '<' || c == '+' || c == '-' || c == '.' || c == ',' || c == '[' || c == ']') {
                sb.append(c);
            }
        }

        exec = sb.toString().toCharArray();
    }

    public void exec() {
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

        System.out.println("");
    }

    public void sendInput(String newInput) {
        input += newInput;
    }

    public String getOutput() {
        return output.toString();
    }

    public void reset() {
        input = "";
        output = new StringBuilder();
        mem = new byte[1];
        execPointer = 0;
        dataPointer = 0;
    }
}
