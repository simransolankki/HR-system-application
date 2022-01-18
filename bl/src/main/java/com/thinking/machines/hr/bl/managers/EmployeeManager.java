package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
private static EmployeeManagerInterface employeeManager=null;

public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
}

private EmployeeManager() throws BLException
{
populateDataStructures();
}

private void populateDataStructures() throws BLException
{
employeeIdWiseEmployeesMap=new HashMap<>();
panNumberWiseEmployeesMap=new HashMap<>();
aadharCardNumberWiseEmployeesMap=new HashMap<>();
designationCodeWiseEmployeesMap=new HashMap<>();
employeesSet=new TreeSet<>();


try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
Set<EmployeeDTOInterface> dlEmployees=employeeDAO.getAll();
EmployeeInterface employee;
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;

String employeeId;
String panNumber;
String aadharCardNumber;
int designationCode;
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee:dlEmployees)
{
employee=new Employee();
employeeId=dlEmployee.getEmployeeId();
employee.setEmployeeId(employeeId);
employee.setName(dlEmployee.getName());
designationCode=dlEmployee.getDesignationCode();
designation=designationManager.getDesignationByCode(designationCode);
employee.setDesignation(designation);
employee.setDateOfBirth(dlEmployee.getDateOfBirth());
if(dlEmployee.getGender()=='M') employee.setGender(GENDER.MALE);
else employee.setGender(GENDER.FEMALE);
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
panNumber=dlEmployee.getPanNumber();
employee.setPanNumber(panNumber);
aadharCardNumber=dlEmployee.getAadharCardNumber();
employee.setAadharCardNumber(aadharCardNumber);

employeeIdWiseEmployeesMap.put(employeeId.toUpperCase(),employee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),employee);
aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),employee);
ets=designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
ets=new TreeSet<>();
}
ets.add(employee);
designationCodeWiseEmployeesMap.put(designationCode,ets);
employeesSet.add(employee);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}



public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
int designationCode=0;
DesignationInterface designation=employee.getDesignation();

Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPanNumber();
String aadharCardNumber=employee.getAadharCardNumber();
if(employeeId!=null && employeeId!="")
{
blException.addException("employeeId","Employee Id. should be nil/empty.");
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()!=0) blException.addException("employeeId","Employee Id. should be nil/empty.");
}

if(name==null)
{
blException.addException("name","Name required.");
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required.");
}

DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","Designation required.");
}
else
{
designationCode=designation.getCode();
if(!designationManager.designationCodeExists(designationCode)) blException.addException("designation","Invalid designation.");
else
{
if(!designationManager.getDesignationByCode(designationCode).getTitle().equalsIgnoreCase(designation.getTitle())) blException.addException("designation","Invalid designation title.");
}
}

if(dateOfBirth==null) blException.addException("dateOfBirth","Date of birth required.");

if(gender==' ') blException.addException("gender","Gender required");

if(basicSalary==null) blException.addException("basicSalary","Basic Salary required");
if(basicSalary.signum()==-1) blException.addException("basicSalary","Basic salary can not be negative.");

if(panNumber==null)
{
blException.addException("panNumber","PAN Number required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","PAN Number required");
}
else
{
if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase())) blException.addException("panNumber","PAN Number "+panNumber+" exists.");
}
}


if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase())) blException.addException("aadharCardNumber","Aadhar card number "+aadharCardNumber+" exists.");
}
}

if(blException.hasExceptions()) throw blException;

try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
Date dlDateOfBirth=(Date)dateOfBirth.clone();
dlEmployee.setDateOfBirth(dlDateOfBirth);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPanNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.add(dlEmployee);
employee.setEmployeeId(dlEmployee.getEmployeeId());
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth(dlDateOfBirth);
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPanNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
employeeIdWiseEmployeesMap.put(employeeId.toUpperCase(),dsEmployee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
Set<EmployeeInterface> ets=designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
ets=new TreeSet<>();
}
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(designationCode,ets);
employeesSet.add(dsEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}

}



public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
int designationCode=0;
DesignationInterface designation=employee.getDesignation();
designationCode=designation.getCode();
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPanNumber();
String aadharCardNumber=employee.getAadharCardNumber();
if(employeeId==null)
{
blException.addException("employeeId","Employee Id. required");
throw blException;
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
blException.addException("employeeId","Employee Id. required.");
throw blException;
}
else
{
if(!employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase()))
{
blException.addException("employeeId","Invalid Employee Id."+employeeId);
throw blException;
}
}
}

if(name==null)
{
blException.addException("name","Name required.");
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required.");
}

DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","Designation required.");
}
else
{
if(!designationManager.designationCodeExists(designationCode)) blException.addException("designation","Invalid designation.");
else
{
if(!designationManager.getDesignationByCode(designationCode).getTitle().equalsIgnoreCase(designation.getTitle())) blException.addException("designation","Invalid designation title.");
}
}

if(dateOfBirth==null) blException.addException("dateOfBirth","Date of birth required.");

if(gender==' ') blException.addException("gender","Gender required");

if(basicSalary==null) blException.addException("basicSalary","Basic Salary required");
if(basicSalary.signum()==-1) blException.addException("basicSalary","Basic salary can not be negative.");

EmployeeInterface dsEmployee=null;

if(panNumber==null)
{
blException.addException("panNumber","PAN Number required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","PAN Number required");
}
else
{
dsEmployee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(dsEmployee!=null)
{
if(!employeeId.equalsIgnoreCase(dsEmployee.getEmployeeId())) blException.addException("panNumber","PAN Number exists.");
}
}
}


if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
dsEmployee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(dsEmployee!=null)
{
if(!employeeId.equalsIgnoreCase(dsEmployee.getEmployeeId())) blException.addException("aadharCardNumber","Aadhar card number "+aadharCardNumber+" exists.");
}
}
}

if(blException.hasExceptions()) throw blException;

try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
dlEmployee.setEmployeeId(employeeId);
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designationCode);
Date dlDateOfBirth=(Date)dateOfBirth;
dlEmployee.setDateOfBirth(dlDateOfBirth);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPanNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.update(dlEmployee);

dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPanNumber=dsEmployee.getPanNumber();
String oldAadharCardNumber=dsEmployee.getAadharCardNumber();
int previousAllotedDesignationCode=dsEmployee.getDesignation().getCode();
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designationCode));
dsEmployee.setDateOfBirth(dlDateOfBirth);
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPanNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);

panNumberWiseEmployeesMap.remove(oldPanNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber.toUpperCase(),dsEmployee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);

if(designationCode!=previousAllotedDesignationCode)
{
Set<EmployeeInterface> ets=designationCodeWiseEmployeesMap.get(previousAllotedDesignationCode);
ets.remove(dsEmployee);
ets=designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null) ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(designationCode,ets);
}

}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}


}



public void deleteEmployee(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null)
{
blException.addException("employeeId","employee id required.");
throw blException;
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) 
{
blException.addException("employeeId","employee id required.");
throw blException;
}
}
employeeId=employeeId.toUpperCase();
EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeId);
if(dsEmployee==null)
{
blException.addException("employeeId","employee id not found.");
throw blException;
}
int designationCode=dsEmployee.getDesignation().getCode();
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.delete(employeeId);
employeeIdWiseEmployeesMap.remove(employeeId);
panNumberWiseEmployeesMap.remove(dsEmployee.getPanNumber().toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(dsEmployee.getAadharCardNumber().toUpperCase());
Set<EmployeeInterface> ets=designationCodeWiseEmployeesMap.get(designationCode);
ets.remove(dsEmployee);
employeesSet.remove(dsEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public Set<EmployeeInterface> getEmployees()
{
Set<EmployeeInterface> employees=new TreeSet<>();
employeesSet.forEach((dsEmployee)->
{
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
employee.setDesignation(dsEmployee.getDesignation());
employee.setDateOfBirth(dsEmployee.getDateOfBirth());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPanNumber(dsEmployee.getPanNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
});
return employees;
}

public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
BLException blException=new BLException();
EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
if(dsEmployee==null)
{
blException.addException("employeeId","employee id not found.");
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
employee.setDesignation(dsEmployee.getDesignation());
employee.setDateOfBirth(dsEmployee.getDateOfBirth());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPanNumber(dsEmployee.getPanNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}

public EmployeeInterface getEmployeeByPanNumber(String panNumber) throws BLException
{
BLException blException=new BLException();
EmployeeInterface dsEmployee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(dsEmployee==null)
{
blException.addException("panNumber","PAN Number not found.");
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
employee.setDesignation(dsEmployee.getDesignation());
employee.setDateOfBirth(dsEmployee.getDateOfBirth());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPanNumber(dsEmployee.getPanNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}


public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException=new BLException();
EmployeeInterface dsEmployee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(dsEmployee==null)
{
blException.addException("aadharCardNumber","Aadhar Card Number not found.");
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
employee.setDesignation(dsEmployee.getDesignation());
employee.setDateOfBirth(dsEmployee.getDateOfBirth());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPanNumber(dsEmployee.getPanNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}
public boolean employeeIdExists(String employeeId)
{
return employeeIdWiseEmployeesMap.containsKey(employeeId);
}
public boolean panNumberExists(String panNumber)
{
return panNumberWiseEmployeesMap.containsKey(panNumber);
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws BLException
{
return aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber);
}
public int getEmployeesCount()
{
return employeesSet.size();
}

public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
{
BLException blException=new BLException();
Set<EmployeeInterface> dsEmployees=designationCodeWiseEmployeesMap.get(designationCode);
if(dsEmployees==null)
{
blException.addException("designationCode","Invalid designation code.");
throw blException;
}
Set<EmployeeInterface> employees=new TreeSet<>();
dsEmployees.forEach((dsEmployee)->
{
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
employee.setDesignation(dsEmployee.getDesignation());
employee.setDateOfBirth(dsEmployee.getDateOfBirth());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPanNumber(dsEmployee.getPanNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
);
return employees;
}
public boolean isDesignationAlloted(int designationCode)
{
return designationCodeWiseEmployeesMap.containsKey(designationCode);
}
public int getEmployeesCountByDesignation(int designationCode) throws BLException
{
BLException blException=new BLException();
Set<EmployeeInterface> ets=designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
blException.addException("designationCode","Invalid designation code.");
throw blException;
}
return ets.size();
}

}