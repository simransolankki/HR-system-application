import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class EmployeeUpdateTestCase
{
public static void main(String gg[])
{
String employeeId=gg[0];
String name=gg[1];
int designationCode=Integer.parseInt(gg[2]);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
dateOfBirth=simpleDateFormat.parse(gg[3]);
}catch(ParseException parseException)
{
System.out.println(parseException.getMessage());
}
char gender=gg[4].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[5]);
BigDecimal basicSalary=new BigDecimal(gg[6]);
String panNumber=gg[7];
String adhaarCardNumber=gg[8];
try
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAdhaarCardNumber(adhaarCardNumber);
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.update(employeeDTO);
System.out.println("Employee with id : "+employeeId+" updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}