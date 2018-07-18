/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

/**
 *
 * @author Administrator
 */
import java.util.HashMap;
import java.util.Map;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinBase.FILETIME;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinBase.FILE_ID_INFO;
import com.sun.jna.platform.win32.WinDef.LPVOID;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;
import static java.lang.Math.E;
import static java.lang.StrictMath.E;
import java.util.List;
import java.util.Arrays;

public interface Kernel32 extends StdCallLibrary {
    final static Map<String, Object> WIN32API_OPTIONS = new HashMap<String, Object>() {
        private static final long serialVersionUID = 1L;
        {
            put(Library.OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
            put(Library.OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
        }
    };

    public Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("Kernel32", Kernel32.class, WIN32API_OPTIONS);

    public int GetLastError();

    /**
    typedef struct _BY_HANDLE_FILE_INFORMATION {
          DWORD    dwFileAttributes;
          FILETIME ftCreationTime;
          FILETIME ftLastAccessTime;
          FILETIME ftLastWriteTime;
          DWORD    dwVolumeSerialNumber;
          DWORD    nFileSizeHigh;
          DWORD    nFileSizeLow;
          DWORD    nNumberOfLinks;
          DWORD    nFileIndexHigh;
          DWORD    nFileIndexLow;
        } BY_HANDLE_FILE_INFORMATION, *PBY_HANDLE_FILE_INFORMATION;
     */

    public class BY_HANDLE_FILE_INFORMATION extends Structure {
        public DWORD    dwFileAttributes;
        public FILETIME ftCreationTime;
        public FILETIME ftLastAccessTime;
        public FILETIME ftLastWriteTime;
        public DWORD    dwVolumeSerialNumber;
        public DWORD    nFileSizeHigh;
        public DWORD    nFileSizeLow;
        public DWORD    nNumberOfLinks;
        public DWORD    nFileIndexHigh;
        public DWORD    nFileIndexLow;
      
        
        @Override
        protected List<String> getFieldOrder() {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     return Arrays.asList(new String[] { 
            "dwFileAttributes", 
            "ftCreationTime", 
            "ftLastAccessTime", 
            "ftLastWriteTime",
            "dwVolumeSerialNumber",
            "nFileSizeHigh",
            "nFileSizeLow",
            "nNumberOfLinks",
            "nFileIndexHigh",
            "nFileIndexLow",
           
        });
        }
        public static class ByReference extends BY_HANDLE_FILE_INFORMATION implements Structure.ByReference {

        };
        public static class ByValue extends BY_HANDLE_FILE_INFORMATION implements Structure.ByValue {

        };        
    }; 

    /**
    BOOL WINAPI GetFileInformationByHandle(
              __in   HANDLE hFile,
              __out  LPBY_HANDLE_FILE_INFORMATION lpFileInformation
            );
    */
    boolean GetFileInformationByHandle(
              HANDLE hFile,
              BY_HANDLE_FILE_INFORMATION lpFileInformation
            );
    
    
    /*
            typedef struct _FILE_ID_INFO {
          ULONGLONG   VolumeSerialNumber;
          FILE_ID_128 FileId;
        } FILE_ID_INFO, *PFILE_ID_INFO;
    */
    public class _FILE_ID_INFO extends Structure{

        /**
         *
         */
        public  FILE_ID_INFO.FILE_ID_128 FileId;
        public long VolumeSerialNumber;
        
        @Override
        protected List<String> getFieldOrder() {
           return Arrays.asList(new String[] {
           "FileId",
            "VolumeSerialNumber"});
        }
        
         public static class ByReference extends _FILE_ID_INFO implements Structure.ByReference {

        };
        public static class ByValue extends _FILE_ID_INFO implements Structure.ByValue {

        };       
        
    };
    
    
    /*BOOL GetFileInformationByHandleEx(
  HANDLE                    hFile,
  FILE_INFO_BY_HANDLE_CLASS FileInformationClass,
  LPVOID                    lpFileInformation,
  DWORD                     dwBufferSize
);*/
//    we have to create the class for this method 
    
    boolean GetFileInformationByHandleEx(
            HANDLE hFile,
            _FILE_INFO_BY_HANDLE_CLASS FileInformationClass,
            LPVOID                    lpFileInformation,
            DWORD                     dwBufferSize
    );
    
    
    
    /*
    typedef enum _FILE_INFO_BY_HANDLE_CLASS { 
  FileBasicInfo                   = 0,
  FileStandardInfo                = 1,
  FileNameInfo                    = 2,
  FileRenameInfo                  = 3,
  FileDispositionInfo             = 4,
  FileAllocationInfo              = 5,
  FileEndOfFileInfo               = 6,
  FileStreamInfo                  = 7,
  FileCompressionInfo             = 8,
  FileAttributeTagInfo            = 9,
  FileIdBothDirectoryInfo         = 10, // 0xA
  FileIdBothDirectoryRestartInfo  = 11, // 0xB
  FileIoPriorityHintInfo          = 12, // 0xC
  FileRemoteProtocolInfo          = 13, // 0xD
  FileFullDirectoryInfo           = 14, // 0xE
  FileFullDirectoryRestartInfo    = 15, // 0xF
  FileStorageInfo                 = 16, // 0x10
  FileAlignmentInfo               = 17, // 0x11
  FileIdInfo                      = 18, // 0x12
  FileIdExtdDirectoryInfo         = 19, // 0x13
  FileIdExtdDirectoryRestartInfo  = 20, // 0x14
  MaximumFileInfoByHandlesClass
} FILE_INFO_BY_HANDLE_CLASS, *PFILE_INFO_BY_HANDLE_CLASS;
*/
   public  enum _FILE_INFO_BY_HANDLE_CLASS {
   
  FileBasicInfo(0),     
  FileStandardInfo(1), 
  FileNameInfo(2),                     
  FileRenameInfo(3),  
  FileDispositionInfo(4),            
  FileAllocationInfo (5),             
  FileEndOfFileInfo(6),              
  FileStreamInfo(7),                  
  FileCompressionInfo(8),             
  FileAttributeTagInfo(9),            
  FileIdBothDirectoryInfo(10),
  FileIdBothDirectoryRestartInfo(11),   
  FileIoPriorityHintInfo (12),          
  FileRemoteProtocolInfo  (13),  
  FileFullDirectoryInfo    (14),  
  FileFullDirectoryRestartInfo  (15),  
  FileStorageInfo                  (16),
  FileAlignmentInfo                (17),
  FileIdInfo                      (18),
  FileIdExtdDirectoryInfo          (19),  
  FileIdExtdDirectoryRestartInfo   (20),
  MaximumFileInfoByHandlesClass(21);
  
  private int value ;
    _FILE_INFO_BY_HANDLE_CLASS(int value){
        this.value = value;
    }
    }
}
