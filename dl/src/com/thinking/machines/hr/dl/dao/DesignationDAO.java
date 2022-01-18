package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.io.*;
public class DesignationDAO implements DesignationDAOInterface
{
private final static String FILE_NAME="designation.data";
public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation is null");
String title=designationDTO.getTitle();
if(title==null)throw new DAOException("Designation is null");
title=title;
if(title.length()==0) throw new DAOException("Length of designationn is zero");
try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(FILE_NAME,"rw");
int lastGeneratedCode=0;
int recordCount=0;
String lastGeneratedCodeString="";
String recordCountString="";
if(randomAccessFile.length()==0)
{
lastGeneratedCodeString="0";
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
recordCountString="0";
while(recordCountString.length()<10) recordCountString+=" ";
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
}
else
{
lastGeneratedCodeString=randomAccessFile.readLine().trim();
recordCountString=randomAccessFile.readLine().trim();
lastGeneratedCode=Integer.parseInt(lastGeneratedCodeString);
recordCount=Integer.parseInt(recordCountString);
}
String fileTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();//ye code padhne ke liye
fileTitle=randomAccessFile.readLine();
if(fileTitle.equalsIgnoreCase(title))
{
randomAccessFile.close();
throw new DAOException("Designation "+title+" exists");
}
}
int code=lastGeneratedCode+1;
randomAccessFile.writeBytes(String.valueOf(code));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(title);
randomAccessFile.writeBytes("\n");
designationDTO.setCode(code);
lastGeneratedCode++;
recordCount++;
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
recordCountString=String.valueOf(recordCount);
while(recordCountString.length()<10) recordCountString+=" ";
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public void update(DesignationDTOInterface designationDTO) throws DAOException
{
boolean found=false;
String title=designationDTO.getTitle();
int code=designationDTO.getCode();
try
{
if(code<=0) throw new DAOException("Invalid code");
if(title==null) throw new DAOException("Invalid title");
if(title.length()==0) throw new DAOException("Invalid title");
if(title.trim().length()==0) throw new DAOException("Invalid title");
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("Invalid code");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code");
}
randomAccessFile.readLine();
if(randomAccessFile.readLine().trim()=="0")
{
randomAccessFile.close();
throw new DAOException("Invalid code");
}
int fileCode=0;
String fileTitle="";
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fileCode=Integer.parseInt(randomAccessFile.readLine());
if(fileCode==code)
{
found=true;
break;
}
randomAccessFile.readLine();
}
if(found==false)
{
randomAccessFile.close();
throw new DAOException("Invalid code");
}
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fileCode=Integer.parseInt(randomAccessFile.readLine());
fileTitle=randomAccessFile.readLine();
if(fileTitle.equalsIgnoreCase(title) && fileCode!=code)
{
throw new DAOException("Title : "+title+" exists.");
}
}
randomAccessFile.seek(0);
File tmpfile=new File("tmp.tmp");
if(tmpfile.exists()) tmpfile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpfile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fileCode=Integer.parseInt(randomAccessFile.readLine());
fileTitle=randomAccessFile.readLine();
if(fileCode!=code)
{
tmpRandomAccessFile.writeBytes(String.valueOf(fileCode)+"\n");
tmpRandomAccessFile.writeBytes(fileTitle+"\n");
}
else
{
tmpRandomAccessFile.writeBytes(String.valueOf(fileCode)+"\n");
tmpRandomAccessFile.writeBytes(title+"\n");
}
}
tmpRandomAccessFile.seek(0);
randomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public void delete(int code) throws DAOException
{
try
{
if(code<=0) throw new DAOException("Invalid code");
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("Invalid code");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code");
}
randomAccessFile.readLine();
if(randomAccessFile.readLine().trim()=="0")
{
randomAccessFile.close();
throw new DAOException("Invalid code");
}
int fileCode=0;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fileCode=Integer.parseInt(randomAccessFile.readLine());
randomAccessFile.readLine();
if(fileCode==code)
{
found=true;
break;
}
}
if(found==false)
{
randomAccessFile.close();
throw new DAOException("Invalid code");
}
randomAccessFile.seek(0);
File tmpfile=new File("tmp.tmp");
if(tmpfile.exists()) tmpfile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpfile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount--;
tmpRandomAccessFile.writeBytes(String.valueOf(recordCount));
tmpRandomAccessFile.writeBytes("          \n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fileCode=Integer.parseInt(randomAccessFile.readLine());
if(fileCode!=code)
{
tmpRandomAccessFile.writeBytes(String.valueOf(fileCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
}
else
{
randomAccessFile.readLine();
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public Set<DesignationDTOInterface> getAll() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("No designation exists");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("No designation exists");
}
randomAccessFile.readLine();
if(randomAccessFile.readLine().trim()=="0")
{
randomAccessFile.close();
throw new DAOException("No designation exists");
}
Set<DesignationDTOInterface> designations;
designations=new TreeSet<>();
DesignationDTOInterface designationDTO;
int code=0;
String title="";
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
code=Integer.parseInt(randomAccessFile.readLine().trim());
title=randomAccessFile.readLine().trim();
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
designations.add(designationDTO);
}
return designations;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public DesignationDTOInterface getByCode(int code) throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("Invalid code : "+code);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
randomAccessFile.readLine();//lastGeneratedCode padha
if(randomAccessFile.readLine().trim()=="0")//recordCount pdha
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
int fileCode=0;
String fileTitle="";
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fileCode=Integer.parseInt(randomAccessFile.readLine());
fileTitle=randomAccessFile.readLine();
if(fileCode==code)
{
randomAccessFile.close();
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(fileTitle);
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean codeExists(int code) throws DAOException
{
try
{
if(code==0) throw new DAOException("Invalid code : "+code);
File file=new File(FILE_NAME);
if(!file.exists()) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
if(randomAccessFile.readLine().trim()=="0")
{
randomAccessFile.close();
return false;
}
int fileCode=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fileCode=Integer.parseInt(randomAccessFile.readLine());
randomAccessFile.readLine();
if(fileCode==code)
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException("Invalid code : "+code);
}
}
public boolean titleExists(String title) throws DAOException
{
if(title.trim()=="") return false;
try
{
File file=new File(FILE_NAME);
if(!file.exists()) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
if(randomAccessFile.readLine().trim()=="0")
{
randomAccessFile.close();
return false;
}
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
if(randomAccessFile.readLine().equalsIgnoreCase(title))
{
randomAccessFile.close();
return true;
}
}
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
String recordCountString=randomAccessFile.readLine().trim();
randomAccessFile.close();
int recordCount=Integer.parseInt(recordCountString);
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}