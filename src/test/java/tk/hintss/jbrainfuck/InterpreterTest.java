package tk.hintss.jbrainfuck;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Henry on 1/18/2015.
 */
public class InterpreterTest {
    @Test
    public void interpretert() {
        Interpreter bfInter = new Interpreter(".");
        bfInter.exec();
        assertEquals(". outputs the current byte", "\0", bfInter.getOutput());
        assertEquals("getNewOutput() returns new output", "\0", bfInter.getNewOutput());
        assertEquals("getNewOutput() ONLY returns new output", "", bfInter.getNewOutput());
    }
}
