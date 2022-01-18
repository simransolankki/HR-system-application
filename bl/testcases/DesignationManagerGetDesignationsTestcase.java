import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
public class DesignationManagerGetDesignationsTestcase
{
public static void main(String gg[])
{
Set<DesignationInterface> designations;
try
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designations=designationManager.getDesignations();
designations.forEach((designation)->
{
System.out.println("Code : "+designation.getCode()+", Title :"+designation.getTitle());
});
}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> properties=blException.getProperties();
properties.forEach((property)->
{
System.out.println(blException.getException(property));
});
}
}
}