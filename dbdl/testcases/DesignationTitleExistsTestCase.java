import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationTitleExistsTestCase
{
public static void main(String gg[])
{
String title=gg[0].trim();
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
System.out.println(designationDAO.titleExists(title));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}