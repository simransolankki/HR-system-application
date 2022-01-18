package com.thinking.machines.hr.bl.interfaces.managers;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import java.util.*;
public interface EmployeeManagerInterface
{
public void addEmployee(EmployeeInterface employee) throws BLException;
public void updateEmployee(EmployeeInterface employee) throws BLException;
public void deleteEmployee(String employeeId) throws BLException;
public Set<EmployeeInterface> getEmployees() throws BLException;
public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException;
public boolean isDesignationAlloted(int designationCode) throws BLException;
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException;
public EmployeeInterface getEmployeeByPanNumber(String panNumber) throws BLException;
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException;
public boolean employeeIdExists(String employeeId) throws BLException;
public boolean panNumberExists(String panNumber) throws BLException;
public boolean aadharCardNumberExists(String aadharCardNumber) throws BLException;
public int getEmployeesCount() throws BLException;
public int getEmployeesCountByDesignation(int designationCode) throws BLException;
}