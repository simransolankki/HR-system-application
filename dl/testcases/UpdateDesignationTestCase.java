import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class UpdateDesignationTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
String title=gg[1];
try
{
DesignationDTO designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAO designationDAO=new DesignationDAO();
designationDAO.update(designationDTO);
System.out.println("Designation updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}