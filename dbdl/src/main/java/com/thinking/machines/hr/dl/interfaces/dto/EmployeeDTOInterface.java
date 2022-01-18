package com.thinking.machines.hr.dl.interfaces.dto;
import com.thinking.machines.enums.*;
import java.math.*;// for BigDecimal
import java.util.*;// for Date class
public interface EmployeeDTOInterface extends Comparable<EmployeeDTOInterface>,java.io.Serializable
{
public void setEmployeeId(String employeeId);
public String getEmployeeId();
public void setName(String name);
public String getName();
public void setDesignationCode(int code);
public int getDesignationCode();
public void setDateOfBirth(Date dateOfBirth);
public Date getDateOfBirth();
public void setGender(GENDER gender);
public char getGender();
public void setIsIndian(boolean isIndian);
public boolean getIsIndian();
public void setBasicSalary(BigDecimal basicSalary);
public BigDecimal getBasicSalary();
public void setPanNumber(String panNumber);
public String getPanNumber();
public void setAadharCardNumber(String aadharCardNumber);
public String getAadharCardNumber();
}