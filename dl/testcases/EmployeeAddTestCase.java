import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeAddTestCase
{
public static void main(String gg[])
{
String employeeId="";
String name=gg[0];
int designationCode=Integer.parseInt(gg[1]);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yy");
Date dateOfBirth=null;
try
{
dateOfBirth=simpleDateFormat.parse(gg[2]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
return;
}
char gender=gg[3].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[4]);
BigDecimal basicSalary=new BigDecimal(gg[5]);
String panNumber=gg[6];
String aadharCardNumber=gg[7];

EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPanNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAO employeeDAO;
employeeDAO=new EmployeeDAO();
try
{
employeeDAO.add(employeeDTO);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}