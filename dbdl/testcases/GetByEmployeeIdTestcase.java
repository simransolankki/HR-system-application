import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class GetByEmployeeIdTestcase
{
public static void main(String gg[])
{
String employeeId=gg[0];
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO=employeeDAO.getByEmployeeId(employeeId);
System.out.println(employeeDTO.getEmployeeId());
System.out.println(employeeDTO.getName());
System.out.println(employeeDTO.getDesignationCode());
System.out.println(employeeDTO.getDateOfBirth());
System.out.println(employeeDTO.getGender());
System.out.println(employeeDTO.getIsIndian());
System.out.println(employeeDTO.getBasicSalary());
System.out.println(employeeDTO.getPanNumber());
System.out.println(employeeDTO.getAadharCardNumber());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}