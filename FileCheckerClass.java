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
//import org.
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import org.Kernel32;
//import sun.misc.IOUtils;
//import  
public class FileCheckerClass {
    
  static HashMap<String,FileProperties> hashMap1 = new HashMap<String,FileProperties>();
  static HashMap<String,FileProperties> hashMap2 = new HashMap<String,FileProperties>();
        static HashMap<String ,String> fiMap= new HashMap<String, String>();
        static String FileId="" ;
        static FileProperties fpObj = null;
        static FileProperties fpObje = null;
        static String Query =  "";
        static String fname = "";
        static int reCount=0;
        static int moCount=0;
        static int modCount=0;
        static int creCount =0;
        static int deCount= 0;
        static String md5 = "";
        
//       static void  recursiveFolder(File[] arr,int index,MessageDigest md) throws NoSuchAlgorithmException 
//    {        StringBuffer sb = new StringBuffer();
//                try{
//                  
//                    // terminate condition
//                if(index == arr.length)
//                  {     
////                      System.out.println("index : "+index+" file "+arr[index-1]);
//                        byte[] mdbytes = md.digest();
//                       
//                        for (int i = 0; i < mdbytes.length; i++) {
//                          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
//                        }
////                       return sb.toString(); 
////System.out.println("the string after last md5 "+sb.toString());
//                md5+=sb.toString();
//                return ;
//                  }
//                
//                 
//                // System.out.println(); 
//                
//                // for files
//                if(arr[index].isFile())
//                    {                     
//                           md.update(checkSum(arr[index].getAbsolutePath()).getBytes());
//                    }
//                // for sub-directories
//                else if( arr[index].isDirectory())
//                {       
//                    // recursion for sub-directories
//                    
//                      byte[] mdbytes = md.digest();
//                       
//                        for (int i = 0; i < mdbytes.length; i++) {
//                          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
//                        }
////                       return sb.toString(); 
////System.out.println("the string after last md5 for folder "+sb.toString()+" folder "+arr[index]);
//                    recursiveFolder(arr[index].listFiles(), 0,md);
//
//                }
//                
//                // recursion for main directory
//                recursiveFolder(arr,++index,md);
//                }
//                catch(Exception e){
//                    String s[]= e.toString().split(":");
//                                System.out.println("Error "+ e.toString());
//                }
////                   return sb.toString();   
//    } 
        
 
        
    ///begin of the creatingMd5 file method
    public static String creatingMd5(String path) throws NoSuchAlgorithmException{         
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(path.getBytes());
        
        byte byteData[] = md.digest(); 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
     
//        System.out.println("Digest(in hex format):: " + sb.toString());
        return sb.toString();
    }            
    ///End of the creatingMd5 file method
    
    ///File checkSum method begins
        static String checkSum(String path ) throws FileNotFoundException, NoSuchAlgorithmException, IOException{
            
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(path);
        
        byte[] dataBytes = new byte[1024];
     
        int nread = 0; 
        while ((nread = fis.read(dataBytes)) != -1) {
          md.update(dataBytes, 0, nread);
        };
        byte[] mdbytes = md.digest();
     
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        fis.close();
//        System.out.println("file Digest(in hex format):: " + sb.toString());
//        fiMap.put(creatingMd5(path), sb.toString());
        return sb.toString();
        }
    ///File checkSum method ends
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////
//    recursive method for the Depth fisrt Search  begins    
      static void recursiveFunction(File[] arr,int index) 
  {
        try{
          // terminate condition
        if(index == arr.length)
          return;          
        // System.out.println(); 
        
        // for files
                                String rs [] = FileIdGetter(arr[index]).split("\\|");
        if(arr[index].isFile())
          {
             
            hashMap2.put(rs[0], fpObjectCreater(arr[index]));
          }
        // for sub-directories
        else if(arr[index].isDirectory())
        {
 
//            fpObj = fpObjectCreater(arr[index]);
            // System.out.println(" objectId "+objectId+" folder ");
            hashMap2.put(rs[0], fpObjectCreater(arr[index]));
 
          // recursion for sub-directories
          recursiveFunction(arr[index].listFiles(), 0);
        }
        
        // recursion for main directory
        recursiveFunction(arr,++index);
        }
        catch(Exception e){
          String s[]= e.toString().split(":");
//                System.out.println("Error "+ s[1]+" string refun "+e.toString());
        }
  }

///////////// end method     
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
          static FileProperties fpObjectCreater(File fp) throws IOException{

      fpObj = new FileProperties();
       
      try{
        // System.out.println(" name "+fp.getName()+" parent "+fp.getParentFile().getName()+" size "+fp.length());
        // fp.getParentFile().getName()
                                String res[] = FileIdGetter(fp.getParentFile()).split("\\|");
      fpObj.setParentName(fp.getParent());
      fpObj.setParentObj(res[0]);
      fpObj.setName(fp.getName());
                                res = FileIdGetter(fp).split("\\|");
                        fpObj.setCreatedTime(res[1]);
                        fpObj.setModifiedTime(res[2]);
        // System.out.println(" file last modified "+fp.lastModified()+" name "+fp.getName());
      // fpObj.setSize(fp.length());
                        if(fp.isFile())
                        {fpObj.setLastModified( checkSum(fp.getAbsolutePath()));
//                            System.out.println(" in the if for file "+fiMap.get(creatingMd5(fp.getAbsolutePath())));
                        }
                        else
                        { 
//                            MessageDigest md = MessageDigest.getInstance("MD5"); 
//                            File file [] = fp.listFiles();
//                           recursiveFolder(file,0,md);
                        fpObj.setLastModified("Folder");
                        }
                        
      }
      catch(Exception e){

          String s[]= e.toString().split(":");
          System.out.println("Error "+ s[1]);
      }
    return fpObj; 
  }
///////////////////////////////////////////////////////////// this part had the displaying methods for logdata ////////////////////////////////////////////////////////////////////////////
                static void lCreatedDisplay(ArrayList<CrModDeOperations> cList){
                    TreeSet<String> fileId = new TreeSet<String>();
                     System.out.println("Total Created : "+creCount); 
                     System.out.println("Name\t\t\tPlace\t\t\tType");
                       for(CrModDeOperations obj : cList){
                                         if(obj.getOperation().equalsIgnoreCase("created")){
                                                   System.out.println(obj.getName()+" "+obj.getPlace()+" \t\t"+obj.getType());
//                                         This part for the showing the restored file  
                                              fileId.add(obj.getId());
                                         }
                        }
                       int count = 0;
                        int max = -999999999;
                        String name ="";
                        String temp ="";
                        String path ="";
                         for(String str : fileId){
                             count =0;
                             for(CrModDeOperations obj : cList){
                                 if(obj.getOperation().equalsIgnoreCase("created")){
                                        if(str.equalsIgnoreCase(obj.getId())){
                                            temp = obj.getName();
                                            path = obj.getPlace();
                                            count++;
                                        }
                                 }
                             }
                             if(count!=1&& max<count ){
                                 name = temp; 
                                 max = count;
                             }
                         }
                         if(max>1){
                         System.out.println("Highest Restored is "+name+" in "+path+" Restored Time "+(max-1));
                         }
                }
                static void lDeletedDisplay(ArrayList<CrModDeOperations> dList){
                                 System.out.println("Total Deleted : "+deCount);
                                 System.out.println("Name\t\t\tPlace\t\t\tType");
                                   for(CrModDeOperations obj : dList){
                                           if(obj.getOperation().equalsIgnoreCase("deleted")){
                                                     System.out.println(obj.getName()+" "+obj.getPlace()+" \t\t"+obj.getType());
                                            }
                                    }       
                }
                static void lModifiedDisplay(ArrayList<CrModDeOperations> mList){
                    TreeSet<String> fileId = new TreeSet<String>();
                    System.out.println("Total Modified : "+modCount);
                    System.out.println("Name\t\t\tPlace\t\t\tType");
                        for(CrModDeOperations obj : mList){
                               if(obj.getOperation().equalsIgnoreCase("modified")){
                                      System.out.println(obj.getName()+" "+obj.getPlace()+"\t\t "+obj.getType());
//                               This part for finding the Highest modified file  
                                    fileId.add(obj.getId());
                               }
                        }
                        int count = 0;
                        int max = -999999999;
                        String name ="";
                        String temp ="";
                        String path ="";
                         for(String str : fileId){
                             count =0;
                             for(CrModDeOperations obj : mList){
                                 if(obj.getOperation().equalsIgnoreCase("modified")){
                                        if(str.equalsIgnoreCase(obj.getId())){
                                            temp = obj.getName();
                                            path = obj.getPlace();
                                            count++;
                                        }
                                 }
                             }
                             if(max<count){
                                 name = temp; 
                                 max = count;
                             }
                         }
                         if(fileId.size()>1){
                         System.out.println("Highest Modified is "+name+" in "+path+" modified Time "+max);
                         }
                         
                                 
                }
                static void lMovedDisplay(ArrayList<MoveOperation> movList){
                    TreeSet<String> fileId = new TreeSet<String>();
                    System.out.println("Total Moved : "+moCount);
                     System.out.println("Name\t\t\t\t\tFrom\t\t\t\t\t\tTo\t\t\tType");
                       for(MoveOperation obj : movList){
                                      System.out.println(obj.getName()+" "+obj.getFrom()+" "+obj.getTo()+" \t\t"+obj.getType());
                                      fileId.add(obj.getId());                                      
                       }
                       int count = 0;
                        int max = -999999999;
                        String name ="";
                        String temp ="";
                        String path ="";
                         for(String str : fileId){
                             count =0;
                             for(MoveOperation obj : movList){
//                                 if(obj.getOperation().equalsIgnoreCase("modified")){
                                        if(str.equalsIgnoreCase(obj.getId())){
                                            temp = obj.getName();
//                                            path = obj.getPlace();
                                            count++;
                                        }
//                                 }
                             }
                             if(max<count){
                                 name = temp; 
                                 max = count;
                             }
                         }
                         if(fileId.size()>1){
                         System.out.println("Highest Moved is "+name+" Moved Time "+max);
                         }
                       
                }
                static void lRenamedDisplay(ArrayList<RenameOperation> reList){
                      TreeSet<String> fileId = new TreeSet<String>();
                      
                    System.out.println("Total Renamed : "+reCount);
                    System.out.println("Old Name\t\t\tNew Name\t\t\t\tPlace\t\t\tType");
                     for(RenameOperation obj : reList){
                             System.out.println(obj.getOldName()+" "+obj.getNewName()+" "+obj.getPlace()+" \t\t"+obj.getType());
                             fileId.add(obj.getId());
                     }
                     
                       int count = 0;
                        int max = -999999999;
                        String name ="";
                        String temp ="";
                        String path ="";
                         for(String str : fileId){
                             count =0;
                             for(RenameOperation obj : reList){
//                                 if(obj.getOperation().equalsIgnoreCase("modified")){
                                        if(str.equalsIgnoreCase(obj.getId())){
                                            temp = obj.getNewName();
                                            path = obj.getPlace();
                                            count++;
                                        }
//                                 }
                             }
                             if(max<count){
                                 name = temp; 
                                 max = count;
                             }
                         }
                         if(max>0){
                         System.out.println("Highest Renamed is "+name+" in "+path+" Renamed Time "+max);
                         } 
                       
                }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void  main(String []args){
       String folderPath=null;
       BufferedReader bufferedReader = null;
       String dataPath = "";
       String logPath = "";
//       C:\\Users\\Administrator\\Desktop\\DataFolder\\";    
       File maindir;
       int co =0;
        while(true){
        try{
            Scanner sin = new Scanner(System.in);
            ArrayList <String> moList = new ArrayList<>();
            ArrayList <String> modList = new ArrayList<>();
            ArrayList <String> creList = new ArrayList<>();
            ArrayList <String> reList = new ArrayList<>();
            hashMap1 = new HashMap<String,FileProperties>();
            hashMap2 = new HashMap<String,FileProperties>();
//            new added for the stack showing on 18 july 
        ArrayList<CrModDeOperations> cmdList = new ArrayList<>();
        ArrayList<MoveOperation> movList = new ArrayList<>();
        ArrayList<RenameOperation> renList = new ArrayList<>();
        
            reCount =0;
            moCount =0;
            modCount=0;
            creCount =0;
            deCount =0;
            while(true){
                System.out.println("Enter the Folder's Full Path to Verify ");
              folderPath = sin.nextLine();
            // file creation and checking  for the root folder 
            maindir = new File(folderPath);
                if(maindir.exists()&& maindir.isDirectory()){
                    break;
                    }
                else{
                    System.out.println("Please enter the Valid Folder path\n");
                }
            } // end of the inner looop
            
            /*
                //////////////////////// 
                This is for the Folder table name information storing in databse
                This table consist of the path MD5 and foldertable for unique 
            */
               try {    
                    File arr[] = maindir.listFiles();
                     System.out.println("Data Loding ...");
                    recursiveFunction(arr,0);
                    dataPath = "C:\\Users\\Administrator\\Desktop\\DataFolder\\";
                    logPath ="C:\\Users\\Administrator\\Desktop\\LogDataFolder\\"; 
                                        String fpath = creatingMd5(folderPath);
                                        dataPath+=fpath+".txt";
                                        logPath+=fpath+".txt";
                                        System.out.println("data path "+dataPath+" count loop "+(++co));
                                        File dataFile = new File(dataPath);
                                        File ldataFile = new File(logPath); 
                                        if (dataFile.exists()) {
                    // System.out.println(" exists ");
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                    This for stack data Printing and Menu part 
                                    if(ldataFile.exists()){
                                         bufferedReader = new BufferedReader(new FileReader(ldataFile));
                    String readLine="";
                    while ((readLine = bufferedReader.readLine()) != null) {
//                             System.out.println(readLine);
                            String rest[] = readLine.split("\\|");
//                                                                for(String sp : rest){
//                                                                    System.out.println(" r split "+sp);
//                                                                }
                                                                  if(rest[0].equalsIgnoreCase("created") || rest[0].equalsIgnoreCase("modified") || rest[0].equalsIgnoreCase("deleted")){
                                                                      CrModDeOperations obj = new CrModDeOperations();
                                                                      obj.setOperation(rest[0]);
                                                                      obj.setId(rest[1]);
                                                                      obj.setName(rest[2]);
                                                                      obj.setPlace(rest[3]);
                                                                      obj.setType(rest[4]);
                                                                      cmdList.add(obj);
                                                                      
                                                                      if(rest[0].equalsIgnoreCase("created")){
                                                                          creCount++;
                                                                      }
                                                                      else if(rest[0].equalsIgnoreCase("modified")){
                                                                          modCount++;
                                                                      }
                                                                      else if(rest[0].equalsIgnoreCase("deleted")){
                                                                          deCount++;
                                                                      } 
                                                                  }  
                                                                  else if(rest[0].equalsIgnoreCase("moved")){
                                                                      MoveOperation obj = new MoveOperation();
                                                                      obj.setId(rest[1]);
                                                                      obj.setName(rest[2]);
                                                                      obj.setFrom(rest[3]);
                                                                      obj.setTo(rest[4]);
                                                                      obj.setType(rest[5]);
                                                                      movList.add(obj);
                                                                      moCount++;
                                                                  }
                                                                  else if(rest[0].equalsIgnoreCase("renamed")){
                                                                      RenameOperation obj = new RenameOperation();
                                                                      obj.setId(rest[1]);
                                                                      obj.setOldName(rest[2]);
                                                                      obj.setNewName(rest[3]);
                                                                      obj.setPlace(rest[4]);
                                                                      obj.setType(rest[5]);
                                                                      renList.add(obj);
                                                                      reCount++;
                                                                  }

                             
                        }
                                                                ////////// Initial display /////////////
  
                                                                 System.out.println("\t\tStack of This File ");   
//                                                                 System.out.println("Total Created : "+creCount); 
//                                                                 System.out.println("Name\t\t\tPlace\t\t\tType");
//                                                                 for(CrModDeOperations obj : cmdList){
//                                                                     if(obj.getOperation().equalsIgnoreCase("created")){
//                                                                         System.out.println(obj.getName()+" "+obj.getPlace()+" \t"+obj.getType());
//                                                                     }
//                                                                 }
                                                                  lCreatedDisplay(cmdList);
                                                                  
//                                                                 System.out.println("Total Modified : "+modCount);
//                                                                 System.out.println("Name\t\t\tPlace\t\t\tType");
//                                                                 for(CrModDeOperations obj : cmdList){
//                                                                     if(obj.getOperation().equalsIgnoreCase("modified")){
//                                                                         System.out.println(obj.getName()+" "+obj.getPlace()+" \t"+obj.getType());
//                                                                     }
//                                                                 }
                                                                 lModifiedDisplay(cmdList);
                                                                 lDeletedDisplay(cmdList);
                                                                 lMovedDisplay(movList);
                                                                  lRenamedDisplay(renList);
//                                                                 System.out.println("Total Deleted : "+deCount);
//                                                                 System.out.println("Name\t\t\tPlace\t\t\tType");
//                                                                 for(CrModDeOperations obj : cmdList){
//                                                                     if(obj.getOperation().equalsIgnoreCase("deleted")){
//                                                                         System.out.println(obj.getName()+" "+obj.getPlace()+" \t"+obj.getType());
//                                                                     }
//                                                                 }
                                                                 
//                                                                 System.out.println("Total Moved : "+moCount);
//                                                                 System.out.println("Name\t\t\t\tFrom\t\t\t\tTo\t\t\tType");
//                                                                 for(MoveOperation obj : movList){
//                                                                     System.out.println(obj.getName()+" "+obj.getFrom()+" "+obj.getTo()+" \t\t"+obj.getType());
//                                                                 }
//                                                                 System.out.println("Total Renamed : "+reCount);
//                                                                 System.out.println("Name\t\t\tRename\t\t\t\tPlace\t\tType");
//                                                                 for(RenameOperation obj : renList){
//                                                                     System.out.println(obj.getOldName()+" "+obj.getNewName()+" "+obj.getPlace()+"\t\t "+obj.getType());
//                                                                 }
                                                                 while(true){
                                                                     System.out.println("\t\t\t\tThis Menu for Stack of the Folder Given\nEnter the choice for Displaying \n1.Created \t2.Modified\t 3.Moved\t4.Deleted\t5.Rename\t6.for File Evalvation Process ");
                                                                     int choice = sin.nextInt();
                                                                     switch(choice){
                                                                         case 1:
//                                                                              created 
                                                                               lCreatedDisplay(cmdList);
                                                                             break;
                                                                         case 2:
//                                                                             Modified
                                                                                lModifiedDisplay(cmdList);
                                                                             break;
                                                                         case 3:
//                                                                             Moved    
                                                                                lMovedDisplay(movList);
                                                                             break;
                                                                         case 4:
//                                                                             Deleted      
                                                                               lDeletedDisplay(cmdList);
                                                                                    
                                                                             break;
                                                                         case 5:
//                                                                             Renamed          
                                                                               lRenamedDisplay(renList);
                                                                             break;
                                                                         case 6:    
                                                                             System.out.println("File Evaluation  Processing.......");
                                                                             break;
                                                                     }
                                                                     if(choice==6){
                                                                     break;
                                                                     }
                                                                 }
                                                                ////////// Menu For stack display /////////////////
                                        
                                        
                                            
                                    }
                                    else{
                                        System.out.println("There is no Logs for this File");
                                    }
                                            

         ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////               
                                                                reCount =0;
                                                                moCount =0;
                                                                modCount=0;
                                                                creCount =0;
                                                                deCount =0;
                                                                bufferedReader = new BufferedReader(new FileReader(dataFile));
                    String readLine="";
                    while ((readLine = bufferedReader.readLine()) != null) {
                            // System.out.println(readLine);
                            String rest[] = readLine.split("\\|");
//                                                                for(String sp : rest){
//                                                                    System.out.println(" r split "+sp);
//                                                                }
                            fpObj = new FileProperties();
                            fpObj.setName(rest[1]);
                            fpObj.setParentName(rest[2]);
                            fpObj.setParentObj(rest[3]);
                            fpObj.setLastModified(rest[4]);

                            hashMap1.put(rest[0],fpObj);
                        }
                                                               ////checking the fimap 
//                                                               for(String key:hashMap1.keySet()){
//                                                                   System.out.println("key "+key+" value "+hashMap1.get(key));
//                                                               }
//                                                              for(String key : hashMap2.keySet()){
//                    // System.out.println(" value "+hashMap2.get(key)+" key "+key);
//                    fpObj = hashMap2.get(key);
//                    // System.out.println(" key is "+key);
//                    String datFo = "key "+key+" Name "+fpObj.getName()+" parent path "+fpObj.getParentName()+" parent id "+fpObj.getParentObj()+" checksum "+fpObj.getLastModified()+"\n";
//                      System.out.println(" data string "+datFo);
//                   
//                    }  
                        //////////////////////////////////////////////Comparing Two HashMap ///////////////////////////////////////
                        String result="";
                                                            String dataString = "";
                                                            ArrayList<String> dlist = new ArrayList<>();
                        for (String key : hashMap2.keySet() ) {
                            
                          if (hashMap1.containsKey(key)) {
                            /////getting  size of the files for both hashmap's///
                             

                            fpObj = hashMap1.get(key);
                            fpObje = hashMap2.get(key);
                                                                        
                            if (!(fpObj.getParentObj().equals(fpObje.getParentObj()))) {
                                
                                                                                moCount++;
                                                                                if(moCount==1){
                                                                                   result="Name\t\tFrom\t\t\t\t\tTo"; 
                                                                                   moList.add(result);
                                                                                  }
                                                                                    if(fpObje.getLastModified().equals("Folder")){
                                                                                    result=fpObje.getName()+" (Folder) \t"+fpObj.getParentName()+" "+fpObje.getParentName();
                                                                                    dataString ="Moved|"+key+"|"+fpObje.getName()+"|"+fpObj.getParentName()+"|"+fpObje.getParentName()+"|Folder\n";
                                                                                    }
                                                                                    else{
                                                                                    result=fpObje.getName()+" (File) \t"+fpObj.getParentName()+" "+fpObje.getParentName();
                                                                                    dataString ="Moved|"+key+"|"+fpObje.getName()+"|"+fpObj.getParentName()+"|"+fpObje.getParentName()+"|File\n";
                                                                                    }
                                                                                    moList.add(result);
//                                                                                    for logdata file
                                                                                     dlist.add(dataString);
//                              System.out.println(" "+fpObje.getName()+" is  Moved from "+fpObj.getParentName()+" to "+fpObje.getParentName());
                            }

                            if (!(fpObj.getName()).equals(fpObje.getName())) {
                               
                                                                                reCount++;
                                                                                if(reCount==1){
                                                                                    result="From\t\tTo\t\tPlace";
                                                                                    reList.add(result);
                                                                                }
                                                                                    if(fpObje.getLastModified().equals("Folder")){
                                                                                    result=fpObj.getName()+" (Folder) \t"+fpObje.getName()+"\t"+fpObje.getParentName();
                                                                                    dataString="renamed|"+key+"|"+fpObj.getName()+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|Folder\n";
                                                                                    }
                                                                                    else{
                                                                                        result=fpObj.getName()+" (File) \t"+fpObje.getName()+"\t"+fpObje.getParentName();
                                                                                    dataString="renamed|"+key+"|"+fpObj.getName()+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|File\n";
                                                                                    }
                                                                                    
                                                                                    reList.add(result);
                                                                                    dlist.add(dataString); // for the log data 
                                                                                    
//                              System.out.println(" Renamed from "+fpObj.getName()+" to "+fpObje.getName()+" in "+fpObje.getParentName());
                            }
                                                                        if(!fpObje.getLastModified().equals("Folder")){    
                            if (!fpObj.getLastModified().equals(fpObje.getLastModified())) {
                               
                                                                                    modCount++;
                                                                                    if(modCount==1){
                                                                                        result="Name\t\tPlace";                                                                   
                                                                                        modList.add(result);
                                                                                    }                                                                                    
                                                                                        result=fpObje.getName()+"\t"+fpObje.getParentName();
                                                                                        modList.add(result);
                                                                                        dataString = "Modified|"+key+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|File|"+fpObje.getModifiedTime()+"\n";
                                                                                        dlist.add(dataString);
                                                                                        System.out.println("last data "+fpObj.getLastModified()+"\nnew data "+fpObje.getLastModified());
//                                System.out.println(" "+fpObje.getName()+" is Modified in "+fpObj.getParentName()+" this directory ");
                            }
                                                                        }
                            
  
                            // System.out.println(result);


                             hashMap1.remove(key);  
                          }
                          else{
                            fpObje = hashMap2.get(key);
                                                                        creCount++;
                                                                        if(creCount==1){
                                                                        result="Name\t\t\tPlace";
                                                                        creList.add(result);
                                                                        }
                                                                            if(fpObje.getLastModified().equals("Folder")){
                                                                                result=fpObje.getName()+" (Folder) \t"+fpObje.getParentName();
                                                                                dataString = "created|"+key+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|Folder|"+fpObje.getCreatedTime()+"\n";
                                                                            }
                                                                            else{
                                                                                result=fpObje.getName()+" (File) \t"+fpObje.getParentName();
                                                                                dataString = "created|"+key+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|File|"+fpObje.getCreatedTime()+"\n";
                                                                            }
                                                                            dlist.add(dataString); // for the log data file 
                                                                            creList.add(result);
                                                                       
//                            System.out.println(" Created "+fpObje.getName());
                          }
                        }
                        dataFile.delete();  //blocked for restections 
                        createFile(dataFile);
                        // System.out.println("\n size of the hashMap1 "+hashMap1.size()+"\n\n");
                                                            deCount = hashMap1.size();
                      
                                                            
                                                            ///////////////////////////////////////////////////////////////////////////////////////
//                                                            File data displaying  and creating data for the file storing 
                                                        
                                                               System.out.println("No of Created :"+creCount);
                                                               if(creCount>0) {
                                                               System.out.println("List of Created");
                                                               }
//                                                               System.out.println(" size of the file creates "+creList.size());
                                                              for(String s:creList){
                                                                  System.out.println(s);
                                                              } 
                                                              System.out.println("No of  Renamed :"+reCount);
                                                              if(reCount>0){
                                                               System.out.println("List of Renamed");
                                                              }
//                                                            System.out.println("size of the file renamed "+reList.size());
                                                              for(String s:reList){
                                                                  System.out.println(s);
                                                              }
                                                               System.out.println("No of  Moved :"+moCount);
                                                               if(moCount>0) {
                                                               System.out.println("List of  Moved");
                                                               }
//                                                               System.out.println("size of the Molist "+moList.size());
                                                               for(String s: moList){
                                                                   System.out.println(s);
                                                               }
                                                               System.out.println("No of File Modified :"+modCount);
                                                               if(modCount>0) {
                                                                   System.out.println("List of File Modified");
                                                               }
//                                                               System.out.println("size of the modify list "+modList.size());
                                                               for(String s: modList){
                                                                   System.out.println(s);
                                                               }
//                                                                
                                                               System.out.println("No of  Deleted :"+deCount);
                                                                if(deCount>0) {
                                                                System.out.println("List of  Deleted");
                                                                }
                                                              for (String key  : hashMap1.keySet() ) {  
                          fpObje = hashMap1.get(key);
                                                                if(fpObje.getLastModified().equals("Folder")){
                                                                    System.out.println(fpObje.getName()+" (Folder) \t"+fpObje.getParentName());
                                                                    dataString ="deleted|"+key+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|Folder\n";
                                                                }
                                                                else{
                                                                    System.out.println(fpObje.getName()+" (File)\t"+fpObje.getParentName());   
                                                                dataString ="deleted|"+key+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|File\n";
                                                                }
                          dlist.add(dataString);
                        }
                                                            ///////// Place for Log file Creating for the folder given by somesh on july 
                BufferedWriter bw = null;
    FileWriter fw = null;

    try {
 
      if(ldataFile.exists()){
                            ldataFile.createNewFile();
                        }

      // true = append file
      fw = new FileWriter(ldataFile.getAbsoluteFile(), true);
      bw = new BufferedWriter(fw);
                        for(String ldata : dlist){
                            bw.write(ldata);
                        }
//      System.out.println("Done");

    } catch (IOException e) {

      e.printStackTrace();

    }
                finally {

      try {

        if (bw != null)
          bw.close();

        if (fw != null)
          fw.close();

      } catch (IOException ex) {

        ex.printStackTrace();

      }
    }
                                                            
                                                            
                                                            //////////////////////////////////////////////////////////////////////////////////////
                  }
                  else
                  {           System.out.println("Folder Monitor fisrt time ");
                      createFile(dataFile);                    
                  }
                                        
                               
               }
               catch(Exception e){
                   
               }
//            maindir
//            maindir.c
            //to break the main loop 
            
            System.out.println(" Enter the Choice\n 1. For Exit \t  otherwise it will continue Check the Other Folder Again ");
            try{
                
            int choice = sin.nextInt();
            if(choice==1){
                System.out.println("The Program Teriminates "); 
                break;
            } 
            }
            catch(Exception e){
                 
            }
//            break;
//    maindir.
        }
        catch(Exception e){
        }
        
//    return 
       }
    }

       
    // Method for the file id Getter
    static String FileIdGetter(File file){
         
        final int FILE_SHARE_READ = (0x00000001);
         
        final int OPEN_EXISTING = (3);
        final int GENERIC_READ = (0x80000000);
        final int FILE_FLAG_BACKUP_SEMANTICS = 0x02000000;
 

        WinBase.SECURITY_ATTRIBUTES attr = null;
        Kernel32.BY_HANDLE_FILE_INFORMATION lpFileInformation = new Kernel32.BY_HANDLE_FILE_INFORMATION();
//        _FILE_ID_INFO fileId = new _FILE_ID_INFO(); // for the file id reference e
        
        WinNT.HANDLE hFile = null;
        WinDef.LPVOID lpInformation = null;
        Kernel32._FILE_INFO_BY_HANDLE_CLASS info = null;
        Scanner sin = new Scanner(System.in);
 
//        File file = new File(path);
        hFile = com.sun.jna.platform.win32.Kernel32.INSTANCE.CreateFile(file.toString(), GENERIC_READ, FILE_SHARE_READ, attr, OPEN_EXISTING, FILE_FLAG_BACKUP_SEMANTICS, null);

//        HANDLE handle = com.sun.jna.platform.win32.Kernel32.INSTANCE.CreateFile(file.toString(), GENERIC_READ, FILE_SHARE_READ, SECURITY_ATTRIBUTES, OPEN_EXISTING, FILE_FLAG_BACKUP_SEMANTICS, null);
//        System.out.println("CreateFile last error:" + com.sun.jna.platform.win32.Kernel32.INSTANCE.GetLastError()); 
        {

            org.Kernel32.INSTANCE.GetFileInformationByHandle(hFile, lpFileInformation);
            
            org.Kernel32.INSTANCE.GetFileInformationByHandleEx(hFile, info , lpInformation, null);
//            System.out.println("CREATION TIME: " + WinBase.FILETIME.filetimeToDate(lpFileInformation.ftCreationTime.dwHighDateTime, lpFileInformation.ftCreationTime.dwLowDateTime));
//
//            System.out.println("VOLUME SERIAL NO.: " + Integer.toHexString(lpFileInformation.dwVolumeSerialNumber.intValue()));
//            System.out.println("Last access time : " +WinBase.FILETIME.filetimeToDate(lpFileInformation.ftLastAccessTime.dwHighDateTime , lpFileInformation.ftLastAccessTime.dwLowDateTime) );
//            System.out.println("FILE INDEX HIGH: " + lpFileInformation.nFileIndexHigh);
//            System.out.println("FILE INDEX LOW: " + lpFileInformation.nFileIndexLow);
//            System.out.println(" unique id is "+lpFileInformation.nFileIndexHigh+lpFileInformation.nFileIndexLow+ Integer.toHexString(lpFileInformation.dwVolumeSerialNumber.intValue()));
//            System.out.println("FILE ID : "+lpFileInformation.FileId.Identifier.toString());
//            System.out.println("VOULME ID : "+lpFileInformation.VolumeSerialNumber);

//            System.out.println("GetFileInformationByHandle last error:" + com.sun.jna.platform.win32.Kernel32.INSTANCE.GetLastError());
        }

        com.sun.jna.platform.win32.Kernel32.INSTANCE.CloseHandle(hFile);

//        System.out.println("CloseHandle last error:" + com.sun.jna.platform.win32.Kernel32.INSTANCE.GetLastError());

    return ""+lpFileInformation.nFileIndexHigh+lpFileInformation.nFileIndexLow+ Integer.toHexString(lpFileInformation.dwVolumeSerialNumber.intValue())+"|"+WinBase.FILETIME.filetimeToDate(lpFileInformation.ftCreationTime.dwHighDateTime, lpFileInformation.ftCreationTime.dwLowDateTime)+"|"+WinBase.FILETIME.filetimeToDate(lpFileInformation.ftLastAccessTime.dwHighDateTime , lpFileInformation.ftLastAccessTime.dwLowDateTime);
    }
    
    static void createFile(File dataFile) throws IOException{
                  BufferedWriter bufferedWriter = null;
                                                                        Writer writer = null;
                  try{

                    dataFile.createNewFile();
                      writer = new FileWriter(dataFile);
                       
                           bufferedWriter = new BufferedWriter(writer);
                        
                  for(String key : hashMap2.keySet()){
                    // System.out.println(" value "+hashMap2.get(key)+" key "+key);
                    fpObj = hashMap2.get(key);
                    // System.out.println(" key is "+key);
                    String datFo = key+"|"+fpObj.getName()+"|"+fpObj.getParentName()+"|"+fpObj.getParentObj()+"|"+fpObj.getLastModified()+"\n";
                     // System.out.println(" data string "+datFo);
                    bufferedWriter.write(datFo);
  
                    }
//                                                                        writer.close();
                                                                         
                                                                                
                  }   
                  catch(Exception e){
                    String s[]= e.toString().split(":");
                    System.out.println( "\n There is a Problem in the File Creation \n Error : "+s[1]+"\n Please Give Valid Path");
                    
                 
                  }

                                               finally{
              try{     
                if(bufferedWriter != null) bufferedWriter.close();
              } catch(Exception e){
                String s[]= e.toString().split(":");
                System.out.println("Error "+ s[1]);
              }
          }   


  }
 
} // End of the class

