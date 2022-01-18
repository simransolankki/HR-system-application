package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.text.*;
import java.math.*;
import java.util.*;
import java.io.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private final static String FILE_NAME="employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String name=employeeDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Name length is zero");

int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code");
DesignationDAOInterface designationDAO=new DesignationDAO();
boolean designationCodeExists=designationDAO.codeExists(designationCode);
if(!designationCodeExists) throw new DAOException("Invalid designation code");

Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of birth is null");

char gender=employeeDTO.getGender();

boolean isIndian=employeeDTO.getIsIndian();

BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary.signum()==-1) throw new DAOException("Basic salary cannot be negative");

String panNumber=employeeDTO.getPanNumber();
if(panNumber==null) throw new DAOException("PAN Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Invalid PAN Number");
boolean panNumberExists=false;

String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Invalid Aadhar card number");
boolean aadharCardNumberExists=false;

try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");

int recordCount=0;
int lastGeneratedEmployeeId=10000000;
String recordCountString=null;
String lastGeneratedEmployeeIdString=null;
String filePANNumber;
String fileAadharCardNumber;
if(randomAccessFile.length()==0)
{
recordCountString=String.format("%-10d",recordCount);
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedEmployeeId=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
}
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int x=1;x<=7;x++) randomAccessFile.readLine();
filePANNumber=randomAccessFile.readLine().trim();
if(panNumberExists==false && panNumber.equalsIgnoreCase(filePANNumber)) panNumberExists=true;
fileAadharCardNumber=randomAccessFile.readLine().trim();
if(aadharCardNumberExists==false && aadharCardNumber.equalsIgnoreCase(fileAadharCardNumber)) aadharCardNumberExists=true;
if(panNumberExists && aadharCardNumberExists)
{
break;
}
}
if(panNumberExists && aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN Number : "+panNumber+" and Aadhar card number : "+aadharCardNumber+" exist.");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN Number : "+panNumber+" exists.");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Aadhar card number : "+aadharCardNumber+" exists.");
}

recordCount++;
lastGeneratedEmployeeId++;
String employeeIdString=String.valueOf(lastGeneratedEmployeeId);
recordCountString=String.format("%-10d",recordCount);
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);

employeeDTO.setEmployeeId(employeeIdString);

randomAccessFile.writeBytes("A"+employeeIdString+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
String dateOfBirthString=simpleDateFormat.format(dateOfBirth);
randomAccessFile.writeBytes(dateOfBirthString+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");

randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();

}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("Employee id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Invalid employee id");

String name=employeeDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Name length is zero");

int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code");
DesignationDAOInterface designationDAO=new DesignationDAO();
boolean designationCodeExists=designationDAO.codeExists(designationCode);
if(!designationCodeExists) throw new DAOException("Invalid designation code");

Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of birth is null");

char gender=employeeDTO.getGender();

boolean isIndian=employeeDTO.getIsIndian();

BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary.signum()==-1) throw new DAOException("Basic salary cannot be negative");

String panNumber=employeeDTO.getPanNumber();
if(panNumber==null) throw new DAOException("PAN Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Invalid PAN Number");

String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Invalid Aadhar card number");


try
{
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("Employee id not found");

RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Employee id not found");
}

String fEmployeeId;
String fPANNumber;
String fAadharCardNumber;
boolean employeeIdFound=false;
boolean aadharCardNumberFound=false;
boolean panNumberFound=false;
String panNumberFoundAgainstEmployeeId=null;
String aadharCardNumberFoundAgainstEmployeeId=null;
long foundAt=0;

randomAccessFile.readLine();
randomAccessFile.readLine();

if(randomAccessFile.getFilePointer()==randomAccessFile.length())
{
randomAccessFile.close();
throw new DAOException("Employee id not found");
}

long location;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
location=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine().trim();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
foundAt=location;
}

for(int x=1;x<=6;x++) randomAccessFile.readLine();

fPANNumber=randomAccessFile.readLine().trim();
if(panNumberFound==false && panNumber.equalsIgnoreCase(fPANNumber))
{
panNumberFound=true;
panNumberFoundAgainstEmployeeId=fEmployeeId;
}

fAadharCardNumber=randomAccessFile.readLine().trim();
if(aadharCardNumberFound==false && aadharCardNumber.equalsIgnoreCase(fAadharCardNumber))
{
aadharCardNumberFound=true;
aadharCardNumberFoundAgainstEmployeeId=fEmployeeId;
}

if(employeeIdFound && panNumberFound && aadharCardNumberFound)
{
break;
}
}

if(!employeeIdFound)
{
randomAccessFile.close();
throw new DAOException("Employee id : "+employeeId+" does not exist.");
}

if((panNumberFound && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false) && aadharCardNumberFound && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
randomAccessFile.close();
throw new DAOException("PAN Number : "+panNumber+" and Aadhar card number "+aadharCardNumber+" exist against another employee id.");
}

if(panNumberFound && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
randomAccessFile.close();
throw new DAOException("PAN Number : "+panNumber+" exists against another employee id.");
}

if(aadharCardNumberFound && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
randomAccessFile.close();
throw new DAOException("Aadhar Card Number : "+aadharCardNumber+" exists against another employee id.");
}

File tmpFile=new File("tmp.tmp");
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");

tmpRandomAccessFile.writeBytes(employeeId+"\n");
tmpRandomAccessFile.writeBytes(name+"\n");
tmpRandomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
String dateOfBirthString=simpleDateFormat.format(dateOfBirth);
tmpRandomAccessFile.writeBytes(dateOfBirthString+"\n");
tmpRandomAccessFile.writeBytes(gender+"\n");
tmpRandomAccessFile.writeBytes(isIndian+"\n");
tmpRandomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
tmpRandomAccessFile.writeBytes(panNumber+"\n");
tmpRandomAccessFile.writeBytes(aadharCardNumber+"\n");

randomAccessFile.seek(foundAt);
for(int x=1;x<=9;x++) randomAccessFile.readLine();

while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}


randomAccessFile.setLength(foundAt);
randomAccessFile.setLength(foundAt+tmpRandomAccessFile.length());
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public void delete(String id) throws DAOException
{
if(id==null) throw new DAOException("Employee id is null");
id=id.trim();
if(id.length()==0) throw new DAOException("Invalid employee id");

try
{
File file=new File(FILE_NAME);
if(!file.exists()) throw new DAOException("Employee id not found");

RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Employee id not found");
}

String fEmployeeId;
boolean employeeIdFound=false;
long foundAt=0;

randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());

if(randomAccessFile.getFilePointer()==randomAccessFile.length())
{
randomAccessFile.close();
throw new DAOException("Employee id not found");
}

long location;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
location=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine().trim();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(id))
{
employeeIdFound=true;
foundAt=location;
}

for(int x=1;x<=8;x++) randomAccessFile.readLine();
if(employeeIdFound)
{
break;
}
}

if(!employeeIdFound)
{
randomAccessFile.close();
throw new DAOException("Employee id : "+id+" does not exist.");
}

File tmpFile=new File("tmp.tmp");
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");

randomAccessFile.seek(foundAt);
for(int x=1;x<=9;x++) randomAccessFile.readLine();

while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}

randomAccessFile.setLength(foundAt);
randomAccessFile.setLength(foundAt+tmpRandomAccessFile.length());
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.seek(0);
randomAccessFile.readLine();
recordCount--;
String recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
randomAccessFile.readLine();
randomAccessFile.readLine();
String employeeId;
String name;
int designationCode;
Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
EmployeeDTOInterface employeeDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeId=randomAccessFile.readLine().trim();
name=randomAccessFile.readLine().trim();
designationCode=Integer.parseInt(randomAccessFile.readLine().trim());
dateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine().trim());
gender=randomAccessFile.readLine().trim();
isIndian=Boolean.parseBoolean(randomAccessFile.readLine().trim());
basicSalary=new BigDecimal(randomAccessFile.readLine().trim());
panNumber=randomAccessFile.readLine().trim();
aadharCardNumber=randomAccessFile.readLine().trim();
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender((gender=="MALE")?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPanNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employees.add(employeeDTO);
}
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employees;
}

public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
throw new DAOException("Not yet implemented");
}

public EmployeeDTOInterface getByEmployeeId(String id) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public EmployeeDTOInterface getByPanNumber(String panNumber) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean employeeIdExists(String id) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean panNumberExists(String panNumber) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public int getCount() throws DAOException
{
throw new DAOException("Not yet implemented");
}
public int getCountByDesignation(int designationCode) throws DAOException
{
throw new DAOException("Not yet implemented");
}
public boolean isDesignationAlloted(int designationCode) throws DAOException
{
throw new DAOException("Not yet implemented");
}

}