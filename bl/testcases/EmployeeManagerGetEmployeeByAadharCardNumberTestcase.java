import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManagerGetEmployeeByAadharCardNumberTestcase
{
public static void main(String gg[])
{
String aadharCardNumber=gg[0];
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
EmployeeInterface employee=employeeManager.getEmployeeByAadharCardNumber(aadharCardNumber);
System.out.println("Employee Id. : "+employee.getEmployeeId());
System.out.println("Name : "+employee.getName());
System.out.println("Designation code : "+employee.getDesignation().getCode()+", title : "+employee.getDesignation().getTitle());
try
{
String dob;
dob=simpleDateFormat.format(employee.getDateOfBirth());
System.out.println("Date of birth : "+dob);
}catch(Exception e)
{
}
System.out.println("Gender : "+employee.getGender());
System.out.println("Is Indian ? : "+employee.getIsIndian());
System.out.println("Basic salary : "+employee.getBasicSalary());
System.out.println("PAN number : "+employee.getPanNumber());
System.out.println("Aadhar card number : "+employee.getAadharCardNumber());

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