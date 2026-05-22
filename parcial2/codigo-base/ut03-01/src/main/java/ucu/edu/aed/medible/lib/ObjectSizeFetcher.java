package ucu.edu.aed.medible.lib;    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.instrument.Instrumentation;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author docampo
 */
public class ObjectSizeFetcher {
    private static final String[] SI = {"B", "kB", "MB", "GB", "TB", "PB", "EB"};
    private static final String[] IEC = {"B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB"};

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();
            return baos.size();
        } catch (IOException ex) {
            Logger.getLogger(ObjectSizeFetcher.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert oos != null;
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(ObjectSizeFetcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }



}
