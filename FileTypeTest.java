
package com;

/**
 *
 * @author Administrator
 */
import java.io.*;
import java.util.*;

public class FileTypeTest {
    private static final int BUFFER_SIZE = 4096;
    private static final int MAX_SIGNATURE_SIZE = 8;

    // PDF files starts with: %PDF
    // MS office files starts with: (D0 CF 11 E0 A1 B1 1A E1)
    // Java does not support byte literals. Use int literals instead.
    private static final int[] pdfSig = { 0x25, 0x50, 0x44, 0x46 };
    private static final int[] msOfficeSig = { 0xd0, 0xcf, 0x11, 0xe0, 0xa1, 0xb1, 0x1a, 0xe1 };

    private Map<String,int[]> signatureMap;
    public FileTypeTest() {
	signatureMap = new HashMap<String,int[]>();
	signatureMap.put("PDF", pdfSig);
	signatureMap.put("MS Office", msOfficeSig);
    }

    public String getFileType(File f) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        InputStream in = new FileInputStream(f);
	try {
	    int n = in.read(buffer, 0, BUFFER_SIZE);
            System.out.println("n value : "+n);
	    int m = n;
	    while ((m < MAX_SIGNATURE_SIZE) && (n > 0)) { 
		n = in.read(buffer, m, BUFFER_SIZE - m);
		              System.out.println(" n value inside "+n);
                m += n;
                System.out.println("m value "+m);
	    }

	    String fileType = "UNKNOWN";
	    for (Iterator<String> i = signatureMap.keySet().iterator(); i.hasNext();) {
		String key = i.next();
		if (matchesSignature(signatureMap.get(key), buffer, m)) {
		    fileType = key;
		    break;
		}
	    }
	    return fileType;
	}
	finally {
	    in.close();
	}
    }

    private static boolean matchesSignature(int[] signature, byte[] buffer, int size) {
        if (size < signature.length) {
            return false;
        }

        boolean b = true;
        for (int i = 0; i < signature.length; i++) {
            if (signature[i] != (0x00ff & buffer[i])) {
                b = false;
                break;
            }
        }
        
        return b;
    }

    public static void main(String[] args) throws Exception {
//        if (args.length < 1) {
//            System.out.println("Usage: java TestExcelPDF <filename>");
//            System.exit(1);
//        }
             Scanner sin = new Scanner(System.in);   
	FileTypeTest t = new FileTypeTest();
	System.out.println("File type: " + t.getFileType(new File(sin.nextLine())));
    }
}