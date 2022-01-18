import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
class DesignationTableModel extends AbstractTableModel
{
private Set<DesignationDTOInterface> dlDesignations;
private Map<Integer,DesignationDTOInterface> dlDesignationsMap;
private String title[];
DesignationTableModel()
{
populateDataStructure();
}
private void populateDataStructure()
{
title=new String[3];
title[0]="S.No.";
title[1]="Code";
title[2]="Title";
try
{
Set<DesignationDTOInterface> dlDesignations;
dlDesignations=new DesignationDAO().getAll();
dlDesignationsMap=new HashMap<>();
int i=0;
for(DesignationDTOInterface dlDesignation:dlDesignations)
{
dlDesignationsMap.put(i,dlDesignation);
i++;
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}

public String getColumnName(int columnIndex)
{
return title[columnIndex];
}

public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0 || columnIndex==1)
{
c=Class.forName("java.lang.Integer");
}
else c=Class.forName("java.lang.String");
}catch(ClassNotFoundException cnfe)
{
System.out.println(cnfe.getMessage());
}
return c;
}

public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}

public Object getValueAt(int rowIndex,int columnIndex)
{
Object obj=null;
if(columnIndex==0)
{
rowIndex+=1;
return rowIndex;
}
if(columnIndex==1)
{
obj=dlDesignationsMap.get(rowIndex).getCode();
}
if(columnIndex==2)
{
obj=dlDesignationsMap.get(rowIndex).getTitle();
}
return obj;
}

public int getColumnCount()
{
return 3;
}

public int getRowCount()
{
return 3;
}

}
class DesignationUI extends JFrame
{
private JTable table;
private JScrollPane jsp;
private DesignationTableModel designationTableModel;
private Container container;
DesignationUI()
{
designationTableModel=new DesignationTableModel();
table=new JTable(designationTableModel);
Font font=new Font("Times New Roman",Font.PLAIN,24);
table.setFont(font);
table.setRowHeight(30);
table.getTableHeader().setReorderingAllowed(false);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=1000;
int height=600;
setSize(1000,600);
int x=(d.width/2)-(width/2);
int y=(d.height/2)-(height/2);
setLocation(x,y);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setVisible(true);
}
}
class swing4psp
{
public static void main(String gg[])
{
DesignationUI designationUI=new DesignationUI();
}
}