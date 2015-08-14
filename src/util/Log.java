package util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Log {

    public static boolean PRINT_TEXT = true;
    public static boolean PRINT_ERRORS = true;

    private static PrintWriter writer;

    public static void close() {
        writer.close();
    }

    public static void init() {
        try {
            writer = new PrintWriter("logs/log-" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + ".txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void print(Object o) {
        if (PRINT_TEXT) {
            System.out.println(o);
        }
        writer.println(o);
    }

    public static void error(Object o) {
        if (PRINT_ERRORS) {
            System.out.println(o);
        }
        writer.println(o);
    }
}
