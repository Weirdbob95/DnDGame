package util;

import java.io.*;

public abstract class SerializationUtils {

    public static Object load(String name) {
        try {
            FileInputStream fileIn = new FileInputStream(name);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object r = in.readObject();
            in.close();
            fileIn.close();
            return r;
        } catch (Exception ex) {
            Log.error(ex);
        }
        return null;
    }

    public static void save(String name, Object o) {
        try {
            FileOutputStream fileOut = new FileOutputStream(name);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(o);
            out.close();
            fileOut.close();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
}
