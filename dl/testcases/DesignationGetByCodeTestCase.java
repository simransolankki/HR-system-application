import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
public class DesignationGetByCodeTestCase
{
public static void main(String gg[])
{
try
{
int code=Integer.parseInt(gg[0]);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
DesignationDTOInterface designationDTO;
designationDTO=designationDAO.getByCode(code);
System.out.println("Code :"+designationDTO.getCode()+" Designation :"+designationDTO.getTitle());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}