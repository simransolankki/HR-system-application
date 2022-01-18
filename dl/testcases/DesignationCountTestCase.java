import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
public class DesignationCountTestCase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
int count=designationDAO.getCount();
System.out.println(count);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}