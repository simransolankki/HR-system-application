import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManagerUpdateTestcase
{
public static void main(String gg[])
{
String employeeId="A10000006";
String name="Rahul Trivedi";
DesignationInterface designation=new Designation();
designation.setTitle("Doctor");
designation.setCode(1);
char gender='M';
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
dateOfBirth=sdf.parse("12/07/1998");
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
}
boolean isIndian=true;
BigDecimal basicSalary=new BigDecimal("100000.00");
String panNumber="ABCD1200";
String aadharCardNumber="012345678900";
EmployeeInterface employee=new Employee();
employee.setEmployeeId(employeeId);
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(GENDER.MALE);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPanNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);

try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.updateEmployee(employee);
System.out.println("Employee added with employee Id. : "+employee.getEmployeeId());
}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}
}