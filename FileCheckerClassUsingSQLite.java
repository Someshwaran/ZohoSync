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
import static com.FileChecker2.creCount;
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
public class FileCheckerClassUsingSQLite {
    
	static HashMap<String,FileProperties> hashMap1 = new HashMap<String,FileProperties>();
	static HashMap<String,FileProperties> hashMap2 = new HashMap<String,FileProperties>();
        static ArrayList<String> hgRenamed = new ArrayList<String>();
        static ArrayList<String> hgMoved = new ArrayList<String>();
        static ArrayList<String> hgModified = new ArrayList<String>();
        static ArrayList<String> hgRestored = new ArrayList<String>();
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
               static   String rootFile="";
               static String rootFolder = "";
                static String rootLog= "";
                static String rootLogF= "";
        
       
 
        
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
 
//						fpObj = fpObjectCreater(arr[index]);
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
//								System.out.println("Error "+ s[1]+" string refun "+e.toString());
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
////////////////////////////////////////////////////////////// this part had the displaying methods for logdata ////////////////////////////////////////////////////////////////////////////
                static void lCreatedDisplay(ArrayList<CrModDeOperations> cList, Statement st){
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
                       if(hgRestored.isEmpty()){
                           try{
                           String Query="";
                       int count = 0;
                        int max = -999999999;
                        String name ="";
                        String temp ="";
                        String path ="";
                         for(String str : fileId){
                             count =0;
//                             for(CrModDeOperations obj : cList){
//                                 if(obj.getOperation().equalsIgnoreCase("created")){
//                                        if(str.equalsIgnoreCase(obj.getId())){
//                                            temp = obj.getName();
//                                            path = obj.getPlace();
//                                            count++;
//                                        }
//                                 }
//                             }
//                             System.out.println("count in restored "+count+" name "+temp);
                                Query = "select count(id) , name ,parentid from "+rootLog +" where id='"+str+"' and operation='Created';";
                                ResultSet rs = st.executeQuery(Query);
                                count = Integer.parseInt(rs.getString(1));
                                temp = rs.getString(2);
                                String pid = rs.getString(3);
                                rs.close();
                               
                             if(count!=1&& max<count ){
                                 name = temp; 
                                 max = count;
                                  Query = " select fname , path from "+rootFolder+" where fid='"+pid+"'"; 
                                rs = st.executeQuery(Query);
                                path = rs.getString(2)+"\\"+rs.getString(1);
                                rs.close();
                                
                                 hgRestored.clear();
                                 hgRestored.add("Highest Restored is "+name+" in "+path+" Restored Time "+(max-1));
                             }
                             else if(max==count){
                                Query = " select fname , path from "+rootFolder+" where fid='"+pid+"'"; 
                                rs = st.executeQuery(Query);
                                path = rs.getString(2)+"\\"+rs.getString(1);
                                rs.close();
                                 hgRestored.add("Highest Restored is "+temp+" in "+path+" Restored Time "+(max-1));
                             }
                         }
                         
                         }
                           catch(Exception e){
                               System.out.println("error "+e.toString());
                           }
                       }
//                         if(max>1){
//                         System.out.println("Highest Restored is "+name+" in "+path+" Restored Time "+(max-1));
//                         }
                             System.out.println();
                              for(String str : hgRestored){
                                System.out.println(str);
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
                static void lModifiedDisplay(ArrayList<CrModDeOperations> mList, Statement st){
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
                        if(hgModified.isEmpty()){
                        try{
                        
                        int count = 0;
                        int max = -999999999;
                        String name ="";
                        String temp ="";
                        String path ="";
                         for(String str : fileId){
                             count =0;
//                             for(CrModDeOperations obj : mList){
//                                 if(obj.getOperation().equalsIgnoreCase("modified")){
//                                        if(str.equalsIgnoreCase(obj.getId())){
//                                            temp = obj.getName();
//                                            path = obj.getPlace();
//                                            count++;
//                                        }
//                                 }
//                             }
                                 Query = "select count(id) , name ,parentid from "+rootLog +" where id='"+str+"' and operation='Modified';";
                                ResultSet rs = st.executeQuery(Query);
                                count = Integer.parseInt(rs.getString(1));
                                temp = rs.getString(2);
                                String pid = rs.getString(3);
                                rs.close();
                             if(max<count){
                                 name = temp; 
                                 max = count;
                                  Query = " select fname , path from "+rootFolder+" where fid='"+pid+"'"; 
                                rs = st.executeQuery(Query);
                                path = rs.getString(2)+"\\"+rs.getString(1);
                                rs.close();
                                 hgModified.clear();
                                 hgModified.add("Highest Modified is "+name+" in "+path+" modified Time "+max);
                             }
                             else if(max==count){
                                  Query = " select fname , path from "+rootFolder+" where fid='"+pid+"'"; 
                                rs = st.executeQuery(Query);
                                path = rs.getString(2)+"\\"+rs.getString(1);
                                rs.close();
                             hgModified.add("Highest Modified is "+temp+" in "+path+" modified Time "+max);
                             }
                         }
                         
                         }
                        catch(Exception e){
                        }
                         }
//                         if(fileId.size()>1){
//                         System.out.println("Highest Modified is "+name+" in "+path+" modified Time "+max);
//                         }
                            System.out.println();
                              for(String str : hgModified){
                                System.out.println(str);
                            }   
                }
                static void lMovedDisplay(ArrayList<MoveOperation> movList,Statement st){
                    try{
                        TreeSet<String> fileId = new TreeSet<String>();
                    System.out.println("Total Moved : "+moCount);
                     System.out.println("Name\t\t\t\t\tFrom\t\t\t\t\t\tTo\t\t\tType");
                       for(MoveOperation obj : movList){
                                    if(obj.getFrom().equalsIgnoreCase("From")){
                                    Query = " select max(sno) from "+rootLog+" where id ='"+obj.getId()+"';";
                                    int max = Integer.parseInt(st.executeQuery(Query).getString(1));
                                    Query = "select max(sno), parentid from "+rootLog+" where sno <"+max+" and id ='"+obj.getId()+"';";
                                    String parentid = st.executeQuery(Query).getString(2);
                                    Query = " select fname , path from "+rootFolder+" where fid ='"+parentid+"';";
                                    ResultSet rp = st.executeQuery(Query);
                                    obj.setFrom(rp.getString(2)+"\\"+rp.getString(1));
                                    }
                                    System.out.println(obj.getName()+" "+obj.getFrom()+" "+obj.getTo()+" \t\t"+obj.getType());
                                      fileId.add(obj.getId());                                      
                       }
                       if(hgMoved.isEmpty()){
                           try{
                           
                       int count = 0;
                        int max = -999999999;
                        String name ="";
                        String temp ="";
                        String path ="";
                         for(String str : fileId){
                             count =0;
//                             for(MoveOperation obj : movList){
////                                 if(obj.getOperation().equalsIgnoreCase("modified")){
//                                        if(str.equalsIgnoreCase(obj.getId())){
//                                            temp = obj.getName();
////                                            path = obj.getPlace();
//                                            count++;
//                                        }
////                                 }
//                             }
                                Query = "select count(id) ,name  from "+rootLog +" where id='"+str+"' and operation='Moved';";
                                ResultSet rs = st.executeQuery(Query);
                                count = Integer.parseInt(rs.getString(1));
                                temp = rs.getString(2);
//                              String pid = rs.getString(3);
                                rs.close();               
                             if(count>=2 && max<count ){
                                 name = temp; 
                                 max = count;
                                 hgMoved.clear();
                                 hgMoved.add("Highest Moved is "+name+" Moved Time "+max);
                             }
                             else if(max==count){
                                 hgMoved.add("Highest Moved is "+temp+" Moved Time "+max);
                             }
                         }
                         }
                           catch(Exception e){
                               System.out.println("error "+e.toString());
                           }
                       }
//                         if(fileId.size()>1){
//                         System.out.println("Highest Moved is "+name+" Moved Time "+max);
//                         }
                            System.out.println();
                            for(String str : hgMoved){
                                System.out.println(str);
                            }
                    }
                    catch(Exception e){
                    }
                       
                }
                static void lRenamedDisplay(ArrayList<RenameOperation> reList,Statement st){
                      try{
                          TreeSet<String> fileId = new TreeSet<String>();                      
                    System.out.println("Total Renamed : "+reCount);
                    System.out.println("Old Name\t\t\tNew Name\t\t\t\tPlace\t\t\tType");
                     for(RenameOperation obj : reList){
                            if(obj.getOldName().equalsIgnoreCase("oldname")){
                                Query = " select max(sno), name from "+rootLog+" where id ='"+obj.getId()+"' and name !='"+obj.getNewName()+"';";
                                ResultSet rs = st.executeQuery(Query);
                                System.out.println(rs.getString(2)+" "+obj.getNewName()+" "+obj.getPlace()+" \t\t"+obj.getType());
//                                obj.setOldName(st.executeQuery(Query).getString(2));
                            } 
                            
                             fileId.add(obj.getId());
                     }
                    
                        if(hgRenamed.isEmpty()){
                        int count = 0;
                        int max = -999999999;
                        String name ="";
                        String temp ="";
                        String path ="";
                         for(String str : fileId){
                             count =0;
//                             for(RenameOperation obj : reList){
////                                 if(obj.getOperation().equalsIgnoreCase("modified")){
//                                        if(str.equalsIgnoreCase(obj.getId())){
//                                            temp = obj.getNewName();
//                                            path = obj.getPlace();
//                                            count++;
//                                        }
////                                 }
//                             }
                                Query = "select count(id) , name ,parentid from "+rootLog +" where id='"+str+"' and operation='Renamed';";
                                ResultSet rs = st.executeQuery(Query);
                                count = Integer.parseInt(rs.getString(1));
                                temp = rs.getString(2);
                                String pid = rs.getString(3);
                                rs.close();
                             if(max<count){
                                 name = temp; 
                                 max = count;
                                
                                 hgRenamed.clear();
                                 hgRenamed.add("Highest Renamed is "+name+" in "+path+" Renamed Time "+max);
                             }
                             else if(max==count){ 
//                                Query = " select fname , path from "+rootFolder+" where fid='"+pid+"'"; 
//                                rs = st.executeQuery(Query);
//                                path = rs.getString(2)+"\\"+rs.getString(1);
//                                rs.close();
                                 hgRenamed.add("Highest Renamed is "+temp+" in "+path+" Renamed Time "+max);
                             }
                         }
                        }
                         System.out.println();
                         for(String str : hgRenamed){
                             System.out.println(str);
                         }
                      }
                      catch(Exception e){
                          e.printStackTrace();
                      }
//                         if(max>0){
//                         System.out.println("Highest Renamed is "+name+" in "+path+" Renamed Time "+max);
//                         } 
                       
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
        ////////////////////////////////// this are the table name strings //////////////////////////////////////////////////////
              
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
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
                      String rs1 [] = FileIdGetter(maindir).split("\\|");
                                            hashMap2.put(rs1[0], fpObjectCreater(maindir));
//                    for(String key : hashMap2.keySet()){
//                        fpObj = hashMap2.get(key);
//                        System.out.println("key "+key+" name : "+fpObj.getName()+" parent id  : "+fpObj.getParentObj()+" parent path :"+fpObj.getParentName()+" check sum "+fpObj.getLastModified());
//                    }
                    dataPath = "C:\\Users\\Administrator\\Desktop\\DataFolder\\";
                    logPath ="C:\\Users\\Administrator\\Desktop\\LogDataFolder\\"; 
                                        String fpath = creatingMd5(folderPath);
//                ///////////////////////////////// This here starts db creation ///////////////////////////////////
                                        rootFile="rootf"+fpath;
                                        rootFolder="rootfo"+fpath;
                                        rootLog= "rootl"+fpath;
                                        rootLogF = "rootlf"+fpath;
                                        System.out.println(" file "+rootFile+"\n folder "+rootFolder+"\n log "+rootLog+"\n log folder "+rootLogF);
                                   ////////////////////// table name set ends 
                                  String Query ="";
                                   Connection con = null;
                                  Statement st = null;
                                  ResultSet rs = null;
                                   try{
                                       Class.forName("org.sqlite.JDBC");                                   }
                                   catch(Exception e){
                                       System.out.println(" Error "+e.toString());
                                   }
                                   try{
                                       con = DriverManager.getConnection("jdbc:sqlite:C:\\SQLite\\foldertables.db");
                                       st  = con.createStatement();
                                       Query =" create table if not exists "+rootFile+" ( id varchar(100) PRIMARY KEY , name varchar(1000) not null , parentid varhcar(100) not null , checksum TEXT(20000) not null);";                                       
                                       if(st.execute(Query)){
                                           System.out.println(rootFile+" is created  ");
                                       }
                                       Query="create table if not exists "+rootFolder+" (fid varchar(100) PRIMARY KEY ,fname varchar(1000) not null ,fparentid varchar(100) not null ,  path TEXT(20000) not null);";
                                      if(st.execute(Query)){
                                           System.out.println(rootFolder+" is created  ");
                                       }
                                       Query = "create table if not exists "+rootLog+" (sno  INTEGER PRIMARY KEY AUTOINCREMENT , operation varchar(20) not null, id varchar(100) not null, name varchar(1000) not null, parentid varchar(100) not null ,type varchar(20) not null );";
                                    if(st.execute(Query)){
                                           System.out.println(rootLog+" is created  ");
                                       }
//                                       Query =" create table if not exists "+rootLogF+" (fid varchar(100), path TEXT(20000));";
//                                       if(st.execute(Query)){
//                                           System.out.println(rootLogF+" is created  ");
//                                       }
                                       Query = "select * from "+rootFile+";";
                                       rs = st.executeQuery(Query);
                                       if(rs.next()){
//                                           This Place for stack 
                                             Query ="select operation,id,name,fname,path,type from "+rootLog+" inner join "+rootFolder+" on "+rootLog+".parentid = "+rootFolder+".fid;"; 
//                                             Query = "select id ,name ,parentid,fname,path,checksum from "+rootFile+" inner join "+rootFolder+" on "+rootFile+".parentid = "+rootFolder+".fid"+";";

                                             ResultSet rsp = st.executeQuery(Query);
//                                         bufferedReader = new BufferedReader(new FileReader(ldataFile));
							    	String readLine="";
							    	while (rsp.next()) {
//						                 System.out.println(readLine);
//						                String rest[] = readLine.split("\\|");
//                                                                for(String sp : rest){
//                                                                    System.out.println(" r split "+sp);
//                                                                }
//                                                                    System.out.println(" operation "+rsp.getString(1)+" id "+rsp.getString(2)+" name "+rsp.getString(3)+" path "+rsp.getString(5)+"\\"+rsp.getString(4)+" type "+rsp.getString(6));
                                                                  if(rsp.getString(1).equalsIgnoreCase("created") || rsp.getString(1).equalsIgnoreCase("modified") || rsp.getString(1).equalsIgnoreCase("deleted")){
                                                                      CrModDeOperations obj = new CrModDeOperations();
                                                                      obj.setOperation(rsp.getString(1));
                                                                      obj.setId(rsp.getString(2));
                                                                      obj.setName(rsp.getString(3));
                                                                      
                                                                      obj.setPlace(rsp.getString(5)+"\\"+rsp.getString(4));
                                                                      obj.setType(rsp.getString(6));
                                                                      cmdList.add(obj);
                                                                      
                                                                      if(rsp.getString(1).equalsIgnoreCase("created")){
                                                                          creCount++;
                                                                      }
                                                                      else if(rsp.getString(1).equalsIgnoreCase("modified")){
                                                                          modCount++;
                                                                      }
                                                                      else if(rsp.getString(1).equalsIgnoreCase("deleted")){
                                                                          deCount++;
                                                                      } 
                                                                  }  
                                                                  else if(rsp.getString(1).equalsIgnoreCase("moved")){
                                                                      MoveOperation obj = new MoveOperation();
                                                                      obj.setId(rsp.getString(2));
                                                                      obj.setName(rsp.getString(3));
                                                                      obj.setFrom("From");
                                                                      obj.setTo(rsp.getString(5)+"\\"+rsp.getString(4));
                                                                      obj.setType(rsp.getString(6));
                                                                      movList.add(obj);
                                                                      moCount++;
                                                                  }
                                                                  else if(rsp.getString(1).equalsIgnoreCase("renamed")){
                                                                      RenameOperation obj = new RenameOperation();
                                                                      obj.setId(rsp.getString(2));
                                                                      obj.setOldName("oldName");
                                                                      obj.setNewName(rsp.getString(3));
                                                                      obj.setPlace(rsp.getString(5)+"\\"+rsp.getString(4));
                                                                      obj.setType(rsp.getString(6));
                                                                      renList.add(obj);
                                                                      reCount++;
                                                                  }

						                 
						            }
                                                                ////////// Initial display /////////////
  
                                                                 System.out.println("\t\tStack of This File ");   
 
                                                                  lCreatedDisplay(cmdList,st);
                                                                  
 
                                                                 lModifiedDisplay(cmdList,st);
                                                                 lDeletedDisplay(cmdList);
                                                                 lMovedDisplay(movList,st);
                                                                 lRenamedDisplay(renList,st);
 
                                                                 while(true){
                                                                     System.out.println("\t\t\t\tThis Menu for Stack of the Folder Given\nEnter the choice for Displaying \n1.Created \t2.Modified\t 3.Moved\t4.Deleted\t5.Rename\t6.for File Evalvation Process ");
                                                                     int choice = sin.nextInt();
                                                                     switch(choice){
                                                                         case 1:
//                                                                              created 
                                                                               lCreatedDisplay(cmdList,st);
                                                                             break;
                                                                         case 2:
//                                                                             Modified
                                                                                lModifiedDisplay(cmdList,st);
                                                                             break;
                                                                         case 3:
//                                                                             Moved    
                                                                                lMovedDisplay(movList,st);
                                                                             break;
                                                                         case 4:
//                                                                             Deleted      
                                                                               lDeletedDisplay(cmdList);
                                                                                    
                                                                             break;
                                                                         case 5:
//                                                                             Renamed          
                                                                               lRenamedDisplay(renList,st);
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
                                        
                                        
                                            
//                                    }
//                                    else{
//                                        System.out.println("There is no Logs for this File");
//                                    }
                                                            
//                                            here we data verification for the file and folder's
                                            Query = "select id ,name ,parentid,fname,path,checksum from "+rootFile+" inner join "+rootFolder+" on "+rootFile+".parentid = "+rootFolder+".fid"+";";
                                            ResultSet rsf = st.executeQuery(Query);
                                            
                                            System.out.println("file properites \n");
                                            while(rsf.next()){                                                
                                                System.out.println(" file "+rsf.getString(1)+" "+rsf.getString(2)+" "+rsf.getString(3)+" "+rsf.getString(4)+" "+rsf.getString(5)+" "+rsf.getString(6));
 
//                                                /////////// put the data in the hashmap 1
                                                fpObj = new FileProperties();
						                fpObj.setName(rsf.getString(2));
						                fpObj.setParentName(rsf.getString(5)+"\\"+rsf.getString(4));
						                fpObj.setParentObj(rsf.getString(3));
						                fpObj.setLastModified(rsf.getString(6));

						                hashMap1.put(rsf.getString(1),fpObj);
 
 
                                            }                                            
                                            rsf.close();
//                                            this is for getting the root folder files 
 
                                           ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                              Query = "select * from "+rootFolder+";";
                                            ResultSet rsfol = st.executeQuery(Query);
                                            System.out.println("\n folder properties ");
                                            while(rsfol.next())
                                            {
                                                System.out.println(" folder "+rsfol.getString(1)+" "+rsfol.getString(2)+" "+rsfol.getString(3)+" "+rsfol.getString(4));
                                                 /////////// put the data in the hashmap 1
                                                
                                                 fpObj = new FileProperties();
						                fpObj.setName(rsf.getString(2));
						                fpObj.setParentName(rsf.getString(4));
						                fpObj.setParentObj(rsf.getString(3));
						                fpObj.setLastModified("Folder");

						                hashMap1.put(rsf.getString(1),fpObj);
                                            }
                                            rsfol.close();
//                                          below for matching the two hashMaps
                                                                reCount = 0;
                                                                moCount = 0;
                                                                modCount = 0;
                                                                creCount = 0;
                                                                deCount = 0;
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
//                                                                                    dataString ="Moved|"+key+"|"+fpObje.getName()+"|"+fpObj.getParentName()+"|"+fpObje.getParentName()+"|Folder\n";
                                                                                        Query = "update "+rootFolder+" set fparentid = '"+fpObje.getParentObj()+"' , path = '"+fpObje.getParentName()+"' where fid = '"+key+"';";
                                                                                        st.executeUpdate(Query);
//                                                                                         for the logtable 
                                                                                                
                                                                                            Query = "insert into "+rootLog+" (operation, id, name, parentid,type) values ('Moved','"+key+"','"+fpObje.getName()+"','"+fpObje.getParentObj()+"','Folder');";
                                                                                            st.execute(Query);
                                                                                    }
                                                                                    else{
                                                                                    result=fpObje.getName()+" (File) \t"+fpObj.getParentName()+" "+fpObje.getParentName();
                                                                                    Query = "update "+rootFile+" set parentid = '"+fpObje.getParentObj()+"' where id ='"+key+"';";
//                                                                                    dataString ="Moved|"+key+"|"+fpObje.getName()+"|"+fpObj.getParentName()+"|"+fpObje.getParentName()+"|File\n";
                                                                                     st.executeUpdate(Query);
                                                                                     
                                                                                     Query = "insert into "+rootLog+" (operation, id, name, parentid,type) values ('Moved','"+key+"','"+fpObje.getName()+"','"+fpObje.getParentObj()+"','File');";
                                                                                            st.execute(Query);
                                                                                    }
                                                                                    moList.add(result);
//                                                                                    Here for updating the data into the database 
                                                                                    
//                                                                                    for logdata file
//                                                                                     dlist.add(dataString);
//						            			System.out.println(" "+fpObje.getName()+" is  Moved from "+fpObj.getParentName()+" to "+fpObje.getParentName());
						            		}

						            		if (!(fpObj.getName()).equals(fpObje.getName())) {
						            			 
                                                                                reCount++;
                                                                                if(reCount==1){
                                                                                    result="From\t\tTo\t\tPlace";
                                                                                    reList.add(result);
                                                                                }
                                                                                    if(fpObje.getLastModified().equals("Folder")){
                                                                                    result=fpObj.getName()+" (Folder) \t"+fpObje.getName()+"\t"+fpObje.getParentName();
                                                                                    Query = "update "+rootFolder+" set fname ='"+fpObje.getName()+"' where fid='"+key+"';";
                                                                                    st.executeUpdate(Query);
//                                                                                    dataString="renamed|"+key+"|"+fpObj.getName()+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|Folder\n";
                                                                                    Query = "insert into "+rootLog+" (operation, id, name, parentid,type) values ('Renamed','"+key+"','"+fpObje.getName()+"','"+fpObje.getParentObj()+"','Folder');";
                                                                                            st.execute(Query);
                                                                                    }
                                                                                    else{
                                                                                        result=fpObj.getName()+" (File) \t"+fpObje.getName()+"\t"+fpObje.getParentName();
                                                                                        Query = "update "+rootFile+" set name = '"+fpObje.getName()+"' where id = '"+key+"';";
                                                                                        st.executeUpdate(Query);
                                                                                        Query = "insert into "+rootLog+" (operation, id, name, parentid,type) values ('Renamed','"+key+"','"+fpObje.getName()+"','"+fpObje.getParentObj()+"','File');";
                                                                                            st.execute(Query);
//                                                                                    dataString="renamed|"+key+"|"+fpObj.getName()+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|File\n";
                                                                                    }
                                                                                    
                                                                                    reList.add(result);
//                                                                                    dlist.add(dataString); // for the log data 
                                                                                    
//						            			System.out.println(" Renamed from "+fpObj.getName()+" to "+fpObje.getName()+" in "+fpObje.getParentName());
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
//                                                                                        dataString = "Modified|"+key+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|File|"+fpObje.getModifiedTime()+"\n";
//                                                                                        dlist.add(dataString);
                                                                                           Query = "update "+rootFile+" set checksum ='"+fpObje.getLastModified()+"' where id ='"+key+"';";
                                                                                           st.executeUpdate(Query);
                                                                                           Query = "insert into "+rootLog+" (operation, id, name, parentid,type) values ('Modified','"+key+"','"+fpObje.getName()+"','"+fpObje.getParentObj()+"','File');";
                                                                                            st.execute(Query);
                                                                                        System.out.println("last data "+fpObj.getLastModified()+"\nnew data "+fpObje.getLastModified());
//						            				System.out.println(" "+fpObje.getName()+" is Modified in "+fpObj.getParentName()+" this directory ");
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
                                                                             Query = "insert into "+rootFolder+" values('"+key+"','"+fpObje.getName()+"','"+fpObje.getParentObj()+"','"+fpObje.getParentName()+"');";
                                                                              st.execute(Query);
                                                                              Query = "insert into "+rootLog+" (operation, id, name, parentid,type) values ('Created','"+key+"','"+fpObje.getName()+"','"+fpObje.getParentObj()+"','Folder');";
                                                                                            st.execute(Query);
//                                                                                dataString = "created|"+key+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|Folder|"+fpObje.getCreatedTime()+"\n";
                                                                            }
                                                                            else{
                                                                                result=fpObje.getName()+" (File) \t"+fpObje.getParentName();
                                                                                Query = "insert into "+rootFile+" values('"+key+"','"+fpObje.getName()+"','"+fpObje.getParentObj()+"','"+fpObje.getLastModified()+"');";
                                                                                st.execute(Query);
                                                                                Query = "insert into "+rootLog+" (operation, id, name, parentid,type) values ('Created','"+key+"','"+fpObje.getName()+"','"+fpObje.getParentObj()+"','File');";
                                                                                            st.execute(Query);
//                                                                                dataString = "created|"+key+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|File|"+fpObje.getCreatedTime()+"\n";
                                                                            }
//                                                                            dlist.add(dataString); // for the log data file 
                                                                            creList.add(result);
                                                                       
//						            		System.out.println(" Created "+fpObje.getName());
						            	}
						            }
 
                                       }
                                       else{
                                           ////////////////////inserting data into the corresponding tables 
                                         
                                           for(String key : hashMap2.keySet()){
                                               FileProperties obj = hashMap2.get(key);
                                                 System.out.println("key "+key+" name : "+obj.getName()+" parent id  : "+obj.getParentObj()+" parent path :"+obj.getParentName()+" check sum "+obj.getLastModified());
                                               if(obj.getLastModified().equalsIgnoreCase("folder")){
//                                                for Folder table insertion 
                                                   System.out.println("inside the Folder if ");
                                                Query = "insert into "+rootFolder+" values('"+key+"','"+obj.getName()+"','"+obj.getParentObj()+"','"+obj.getParentName()+"');";
                                                st.execute(Query);
                                                 Query = "insert into "+rootLog+" (operation, id, name, parentid,type) values ('exist','"+key+"','"+obj.getName()+"','"+obj.getParentObj()+"','Folder');";
                                                     st.execute(Query);
                                                
                                               }
                                               else{
                                                   System.out.println("insert in the else file ");
//                                                   for File table insertion
                                                  Query = "insert into "+rootFile+" values('"+key+"','"+obj.getName()+"','"+obj.getParentObj()+"','"+obj.getLastModified()+"');";
                                                  st.execute(Query); 
                                                  Query = "insert into "+rootLog+" (operation, id, name, parentid,type) values ('exist','"+key+"','"+obj.getName()+"','"+obj.getParentObj()+"','Folder');";
                                                     st.execute(Query);
                                               }
                                           }
                                       
                                       }
                                   }
                                   catch(Exception e){
                                       System.out.println(" Error "+e.toString());
                                   }
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
                                   
 
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                 This for stack data Printing and Menu part 
                                     
                                            

         ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////               
 
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
                                                                    Query = "delete from "+rootFolder+" where fid='"+key+"';";
                                                                    st.execute(Query);
                                                                    Query = "insert into "+rootLog+" (operation, id, name, parentid,type) values ('Deleted','"+key+"','"+fpObje.getName()+"','"+fpObje.getParentObj()+"','Folder');";
                                                                                            st.execute(Query);
//                                                                    dataString ="deleted|"+key+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|Folder\n";
                                                                }
                                                                else{
                                                                    System.out.println(fpObje.getName()+" (File)\t"+fpObje.getParentName());	 
                                                                    Query= " delete from "+rootFile+" where id='"+key+"';";
                                                                    st.execute(Query);
                                                                    Query = "insert into "+rootLog+" (operation, id, name, parentid,type) values ('Deleted','"+key+"','"+fpObje.getName()+"','"+fpObje.getParentObj()+"','File');";
                                                                                            st.execute(Query);
//                                                                dataString ="deleted|"+key+"|"+fpObje.getName()+"|"+fpObje.getParentName()+"|File\n";
                                                                }
//						            	dlist.add(dataString);
						            }
                                                            ///////// Place for Log file Creating for the folder given by somesh on july 
               
                                                            
                                                            
                                                            //////////////////////////////////////////////////////////////////////////////////////
//							    }
//							    else
//							    {           System.out.println("Folder Monitor fisrt time ");
//							    		createFile(dataFile);							    	 
//							    }
                                        
                               
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
    
    
 
} // End of the class

 