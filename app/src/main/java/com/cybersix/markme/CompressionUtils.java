package com.cybersix.markme;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionUtils {

    public byte[] compressImage(byte[] image) {

        try {

            Deflater deflater = new Deflater();
            deflater.setInput(image);
            deflater.setLevel(Deflater.BEST_COMPRESSION);

            ByteArrayOutputStream os = new ByteArrayOutputStream(image.length);
            GZIPOutputStream gos = new GZIPOutputStream(os);
            gos.write(image);
            gos.close();
            byte[] compressed = os.toByteArray();
            os.close();

            Log.d("Vishal_Original: ",image.length / 1024 + " Kb");
            Log.d("Vishal_Compressed: ", compressed.length / 1024 + " Kb");
            return compressed;

        } catch(UnsupportedEncodingException e) {
            // handle
        } catch (IOException e) {

        }

        return new byte[0];
    }

    public byte[] decompressImage(byte[] image) {

//        try {
//
//            // Decompress the bytes
//            Inflater decompresser = new Inflater();
//            decompresser.setInput(output, 0, compressedDataLength);
//            byte[] result = new byte[100];
//            int resultLength = decompresser.inflate(result);
//            decompresser.end();
//
//            // Decode the bytes into a String
//            String outputString = new String(result, 0, resultLength, "UTF-8");
//        } catch(java.io.UnsupportedEncodingException ex) {
//            // handle
//        } catch (java.util.zip.DataFormatException ex) {
//            // handle
//        }

        return new byte[0];
    }

}
