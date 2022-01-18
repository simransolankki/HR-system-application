import com.thinking.machines.hr.pl.model.*;
import java.awt.*;
import javax.swing.*;
class DesignationFrame extends JFrame
{
private Container container;
private JTable table;
private JScrollPane jsp;
private DesignationModel designationModel;
public DesignationFrame()
{
designationModel=new DesignationModel();
table=new JTable(designationModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
setLayout(new BorderLayout());
container=getContentPane();
container.add(jsp);
setLocation(500,300);
setSize(500,500);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
}
class DesignationModelTestcase
{
public static void main(String gg[])
{
DesignationFrame designationFrame=new DesignationFrame();
}
}