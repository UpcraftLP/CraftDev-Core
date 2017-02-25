package core.upcraftlp.craftdev.API.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.common.annotations.Beta;

/**
 * Utility class handling Files / File Systems.
 * 
 * @author UpcraftLP
 */
@Deprecated
@Beta
public class FSUtils {

    public static void extractZip(String inputFile, String outputFolder) throws IOException {
        byte[] buffer = new byte[1024];
        File folder = new File(outputFolder);
        if ( !folder.exists() )
            folder.mkdir();
        ZipInputStream input = new ZipInputStream(new FileInputStream(new File(inputFile)));
        try {
            ZipEntry entry;
            while ((entry = input.getNextEntry()) != null) {
                String outPath = outputFolder + File.separator + entry.getName();
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(outPath);
                    int length = 0;
                    while ((length = input.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                }finally {
                    if ( out != null )
                        out.close();
                }

            }
        }finally {
            input.close();
        }
    }

}
