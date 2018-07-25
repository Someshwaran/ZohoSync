/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 *
 * @author Administrator
 */
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
 

import org.apache.tika.Tika;

public class TypedetectionMod {
private  HashMap<String,String> mimeType = new  HashMap<String,String>();
////////////// This is for insert the Type of mime Following
  public  TypedetectionMod(){
        mimeType.put("text/plain", "text");
        mimeType.put("application/java-archive", "jar");
        mimeType.put("application/vnd.android.package-archive","jar");
        mimeType.put("application/x-tika-java-enterprise-archive","jar");
        mimeType.put("application/x-tika-java-web-archive", "jar");
        mimeType.put("application/java-serialized-object", "objectFile");
        mimeType.put("application/javascript","javascript");
        mimeType.put("application/json","json");
        mimeType.put("application/java-vm","class");
        mimeType.put("application/x-java-vm","class");
        mimeType.put("application/x-java","class");
        mimeType.put("application/x-java-jnilib","jniLib");
        mimeType.put("application/quicktime","QuicktimeVideo");
        mimeType.put("application/mp4","mp4");
        mimeType.put("application/msword","MSword");
        mimeType.put("application/msword2","MSword");
        mimeType.put("application/msword5","MSword");
        mimeType.put("application/vnd.msword","MSword");
        mimeType.put("application/x-tika-msoffice","MSword");
        mimeType.put("application/octet-stream","stream");
        mimeType.put("application/oda","oda");
        mimeType.put("application/ogg","ogx");
        mimeType.put("application/oebps-package+xml","opf");
        mimeType.put("application/onenote","OneNote");
        mimeType.put("application/msonenote","OneNote");
        mimeType.put("application/onenote;format=one","oneNote");
        mimeType.put("application/onenote;format=onetoc2", "OneNote Table of Contents");
        mimeType.put("application/onenote; format=package","OneNote Package");
        mimeType.put("application/pdf","PDF");        
        mimeType.put("application/x-pdf","PDF");    
        mimeType.put("application/pgp","pgp");
        mimeType.put("application/pgp-encrypted","pgp");
        mimeType.put("application/pgp-signature","Signature"); 
        mimeType.put("application/pics-rules","pic-rules");
        mimeType.put("application/timestamped-data","Time-data");
        mimeType.put("application/vnd.ms-spreadsheetml","Excel-xls");
        mimeType.put("application/vnd.ms-wordml","Word-doc");
        mimeType.put("application/vnd.ms-word2006ml","Word-xml");
        mimeType.put("application/sereal","Serializable");
        mimeType.put("application/sereal;version=1","Serializable");
        mimeType.put("application/sereal;version=2","Serializable");        
        mimeType.put("application/sereal;version=3","Serializable"); 
        mimeType.put("application/zip","Zip");
        mimeType.put("application/vnd.etsi.asic-e+zip","zip(signatured container)");
        mimeType.put("application/vnd.ms-excel","MS-Excel");
        mimeType.put("application/msexcel","MS-Excel");
        mimeType.put("application/x-tika-msoffice","MS-Excel");
        mimeType.put("application/vnd.ms-excel.addin.macroenabled.12","open office XML addin");
        mimeType.put("application/vnd.ms-excel.sheet.macroenabled.12","open office xml ");
        mimeType.put("application/vnd.ms-excel.sheet.binary.macroenabled.12","MS-Excel binary");
        mimeType.put("application/vnd.ms-excel.sheet.2","MS-Excel 32");
        mimeType.put("application/vnd.ms-excel.sheet.3","MS-Excel 3");
        mimeType.put("application/vnd.ms-excel.sheet.4","MS-Excel 4");
        mimeType.put("application/vnd.ms-excel.workspace.3","MS-Excel3 workspace");
        mimeType.put("application/vnd.ms-excel.workspace.4","MS-Excel4 workspace");
        mimeType.put("application/vnd.ms-powerpoint","ms-powerpoint"); 
        mimeType.put("application/mspowerpoint","ms-powerpoint"); 
        mimeType.put("application/vnd.ms-powerpoint.addin.macroenabled.12","office open xml addin presentation");
        mimeType.put("application/vnd.ms-powerpoint.macroenabled.12","office open xml presentation");
        mimeType.put("application/vnd.ms-powerpoint.slide.macroenabled.12","powerpoint slide ");
        mimeType.put("application/vnd.ms-powerpoint.slideshow.macroenabled.12","powerpoint slide show");
        mimeType.put("application/vnd.ms-powerpoint.template.macroenabled.12","powerpoint template");
        mimeType.put("application/rtf","rich text format");
        mimeType.put("application/x-rar-compressed","winrar compressed");
        
        /////////////// this for open document files 
        mimeType.put("application/vnd.oasis.opendocument.chart", "OpenDocument v1.0: Chart document");
        mimeType.put("application/x-vnd.oasis.opendocument.chart","OpenDocument v1.0: Chart document");
        mimeType.put("application/vnd.oasis.opendocument.chart-template","OpenDocument v1.0: Chart document used as template");
        mimeType.put("application/x-vnd.oasis.opendocument.chart-template","OpenDocument v1.0: Chart document used as template");
        mimeType.put("application/vnd.oasis.opendocument.base","for open document database");
        mimeType.put("application/vnd.oasis.opendocument.database","for open document database");
        mimeType.put("application/vnd.oasis.opendocument.formula","OpenDocument v1.0: Formula document");
        mimeType.put("application/x-vnd.oasis.opendocument.formula","OpenDocument v1.0: Formula document");
        mimeType.put("application/vnd.oasis.opendocument.formula-template","OpenDocument v1.0: Formula document template");
        mimeType.put("application/x-vnd.oasis.opendocument.formula-template","OpenDocument v1.0: Formula document template");
        mimeType.put("application/vnd.oasis.opendocument.graphics","OpenDocument v1.0: Graphics document (Drawing)");
        mimeType.put("application/x-vnd.oasis.opendocument.graphics","OpenDocument v1.0: Graphics document (Drawing)");
        
        mimeType.put("application/vnd.oasis.opendocument.graphics-template","OpenDocument v1.0: Graphics document (Drawing) template");
        mimeType.put("application/x-vnd.oasis.opendocument.graphics-template","OpenDocument v1.0: Graphics document (Drawing) template");
        mimeType.put("application/vnd.oasis.opendocument.image","OpenDocument v1.0: Image document");
        mimeType.put("application/x-vnd.oasis.opendocument.image","OpenDocument v1.0: Image document");
        mimeType.put("application/vnd.oasis.opendocument.image-template","OpenDocument v1.0: Image document template");
        mimeType.put("application/x-vnd.oasis.opendocument.image-template","OpenDocument v1.0: Image document template");
        mimeType.put("application/vnd.oasis.opendocument.presentation","OpenDocument v1.0: Presentation document");
        mimeType.put("application/x-vnd.oasis.opendocument.presentation","OpenDocument v1.0: Presentation document");
        
        mimeType.put("application/vnd.oasis.opendocument.presentation-template","OpenDocument v1.0: Presentation document template");
        mimeType.put("application/x-vnd.oasis.opendocument.presentation-template","OpenDocument v1.0: Presentation document template");
        mimeType.put("application/vnd.oasis.opendocument.spreadsheet","OpenDocument v1.0: Spreadsheet document");
        mimeType.put("application/x-vnd.oasis.opendocument.spreadsheet","OpenDocument v1.0: Spreadsheet document");
        
        mimeType.put("application/vnd.oasis.opendocument.spreadsheet-template","OpenDocument v1.0: Spreadsheet document template");
        mimeType.put("application/x-vnd.oasis.opendocument.spreadsheet-template","OpenDocument v1.0: Spreadsheet document template");
        mimeType.put("application/vnd.oasis.opendocument.text","OpenDocument v1.0: Text document");
        mimeType.put("application/x-vnd.oasis.opendocument.text","OpenDocument v1.0: Text document");
        
        mimeType.put("application/vnd.oasis.opendocument.text-template","OpenDocument v1.0: Text document template");
        mimeType.put("application/x-vnd.oasis.opendocument.text-template","OpenDocument v1.0: Text document template");
        
        mimeType.put("application/vnd.oasis.opendocument.text-master","OpenDocument v1.0: Global Text document");
        mimeType.put("application/x-vnd.oasis.opendocument.text-master","OpenDocument v1.0: Global Text document");
        
        mimeType.put("application/vnd.oasis.opendocument.text-web","OpenDocument v1.0: Text document used as template for HTML documents");
        mimeType.put("application/x-vnd.oasis.opendocument.text-web","OpenDocument v1.0: Text document used as template for HTML documents");
        
        /////////////////////////images 
        mimeType.put("image/x-tga","Traga image data");
        mimeType.put("image/x-targa","Traga image data");
        mimeType.put("application/x-apple-diskimage","apple disk image");
        mimeType.put("application/x-cdlink","Virtual CD-ROM CD Image File");
        mimeType.put("application/x-iso9660-image","iso-image file");
        mimeType.put("image/aces","ACES Image Container File");
        mimeType.put("image/bmp","windows bitmap");
        mimeType.put("image/x-bmp","windows bitmap");
        mimeType.put("image/x-ms-bmp","windows bitmap");
        mimeType.put("image/x-bpg","Better Portable Graphics");
        mimeType.put("image/cgm", "Computer Graphics Metafile");
        mimeType.put("image/x-dpx","Digital Picture Exchange from SMPTE");
        mimeType.put("image/emf","Enhanced Metafile");
        mimeType.put("image/x-emf","Enhanced Metafile");
        mimeType.put("application/x-emf","Enhanced Metafile");
        mimeType.put("image/x-emf-compressed","Compressed Enhanced Metafile");
          
          mimeType.put("application/x-ms-emz","Compressed Enhanced Metafile");
        mimeType.put("image/fits", "Fits");
        mimeType.put("image/g3fax","fax image");
        mimeType.put("image/gif","Graphics Interchange Format");
        mimeType.put("image/icns","Apple Icon Image Format");
        mimeType.put("image/jp2","JPEG 2000 Part 1 (JP2)");
        mimeType.put("image/jpeg","Joint Photographic Experts Group(JPEG)");
        mimeType.put("image/jpm","JPEG 2000 Part 6 (JPM)");
        mimeType.put("image/jpx","JPEG 2000 Part 2 (JPX)");
        mimeType.put("image/nitf","nitf");
        mimeType.put("image/png","Portable Network Graphics(PNG)");
        mimeType.put("image/tiff","Tagged Image File Format");
        mimeType.put("image/webp","webpack image ");
        mimeType.put("image/wmf","Windows Metafile");
        mimeType.put("image/x-freehand","free handed ");
        mimeType.put("image/x-niff","Navy Interchange File Format");
        mimeType.put("image/x-pict","Apple Macintosh QuickDraw/PICT Format");
        mimeType.put("image/x-portable-anymap","Portable Any Map");
        mimeType.put("image/x-portable-bitmap","Portable Bit Map");
        mimeType.put("image/x-portable-graymap","Portable Graymap Graphic");
        mimeType.put("text/x-awk","AWK script");
        mimeType.put("text/x-c++hdr"," Header files");
        mimeType.put("text/x-c++src","source code (*.cpp,*.c,*c++)");
        mimeType.put("text/x-chdr","c source header");
        mimeType.put("text/x-java-source","java source");
        mimeType.put("text/x-java-properties","java properties ");
        mimeType.put("text/x-jsp","java server page (JSP)");
        mimeType.put("text/x-python","Python");
        mimeType.put("text/x-ruby","Ruby");
        mimeType.put("text/x-sql","SQl"); 
        mimeType.put("video/3gpp","3gp files");
        mimeType.put("video/jpeg","jpeg video");
        mimeType.put("video/mj2","jpeg video files 2");
        mimeType.put("video/mp4","mp4  video format");
        mimeType.put("video/mpeg","MPEG Movie Clip");
        mimeType.put("video/x-ms-wmv","windows media video");
        mimeType.put("video/x-msvideo","AVI type ");
        mimeType.put("application/x-matroska","Matroska Media Container");
        mimeType.put("video/x-matroska","Matroska video");
        mimeType.put("audio/x-matroska","Matroska audio");
        mimeType.put("text/x-asciidoc","ASCII docs ");
        mimeType.put("text/x-d","D source code");
        mimeType.put("text/html", "html document or text");
        mimeType.put("audio/ac3","Dolby Digital Audio Compression File(AC3)");
        mimeType.put("audio/amr"," amr audio");
        mimeType.put("audio/amr-wb","amr web audio files");
        mimeType.put("audio/midi","Musical Instrument Digital Interface(MIDI)");
        mimeType.put("audio/mp4","mp4 audio  file");
        mimeType.put("audio/mpeg","mp3 audio file ");
        mimeType.put("audio/x-mod","MOD audio  file");
        mimeType.put("audio/vnd.wave","wave audio file");
        mimeType.put("audio/wave","wave audio file");
        mimeType.put("audio/x-wav","wave audio file");
        mimeType.put("audio/wav","wave audio file");
        
        
        
        
        
        
        
        
        
        
                
        
}
  public String fileType(File file) throws IOException{
        Tika tika = new Tika();
      
      //detecting the file type using detect method
      String filetype = tika.detect(file);
//      System.out.println("file type "+filetype);
      String result ="";
      if(mimeType.containsKey(filetype)){
          result= mimeType.get(filetype);
      }
      else{
          result ="UNKNOWN";
      }
      return result;
  }
  
//   public static void main(String[] args) throws Exception {
//
//      Scanner sin = new Scanner(System.in);
//      File file = new File(sin.nextLine());//
//      
//      Tika tika = new Tika();
//      
//      //detecting the file type using detect method
//      String filetype = tika.detect(file);
//       
//      System.out.println(filetype);
//   }
     
}