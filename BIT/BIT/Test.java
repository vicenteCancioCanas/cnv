package BIT;

import BIT.highBIT.*;
import java.io.*;
import java.util.*;

public class Test {
    private static PrintStream out = null;
    private static int i_count = 0, b_count = 0, m_count = 0;

    public static void main(String argv[]) {
        File file_in = new File(argv[0]);
        String infilenames[] = file_in.list();

        for (int i = 0; i < infilenames.length; i++) {
            String infilename = infilenames[i];
            if (infilename.endsWith(".class")) {
                // create class info object
                ClassInfo ci = new ClassInfo(argv[0] + System.getProperty("file.separator") + infilename);

                // loop through all the routines
                // see java.util.Enumeration for more information on Enumeration class
                for (Enumeration e = ci.getRoutines().elements(); e.hasMoreElements(); ) {
                    Routine routine = (Routine) e.nextElement();
                    routine.addBefore("BIT/Test", "mcount", new Integer(1));

                    for (Enumeration b = routine.getBasicBlocks().elements(); b.hasMoreElements(); ) {
                        BasicBlock bb = (BasicBlock) b.nextElement();
                        bb.addBefore("BIT/Test", "count", new Integer(bb.size()));
                    }
                }
                //ci.addAfter("ICount", "printICount", ci.getClassName());
                ci.write(argv[1] + System.getProperty("file.separator") + infilename);
            }
        }
    }

    public static synchronized void printICount(String foo) {
        System.out.println(i_count + " instructions in " + b_count + " basic blocks were executed in " + m_count + " methods.");
    }

    public static synchronized void count(int incr) {
        i_count += incr;
        b_count++;
    }

    public static synchronized String getICount() {
        return i_count;
    }

    public static synchronized void mcount(int incr) {
        m_count++;
    }

}
