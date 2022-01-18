package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.io.*;
import javax.swing.table.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.io.image.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.layout.property.*;
import java.lang.reflect.*;
import java.lang.*;
import java.net.URL;
public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> designations;
private String columnTitles[];
public DesignationModel()
{
populateDataStructures();
}

DesignationManager designationManager;

private void populateDataStructures()
{
columnTitles=new String[2];
columnTitles[0]="S.No.";
columnTitles[1]="Designation";
try
{
designationManager=DesignationManager.getDesignationManager();
Set<DesignationInterface> blDesignations;
blDesignations=designationManager.getDesignations();
designations=new LinkedList<>();
for(DesignationInterface designation:blDesignations)
{
designations.add(designation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}catch(BLException blException)
{
System.out.println(blException.getMessage());
}
}

public int getRowCount()
{
return designations.size();//agr list empty hui to dikkat aaege, designation.data file honi chahiye jaha run krwa rhe hain
}

public int getColumnCount()
{
return columnTitles.length;
}

public String getColumnName(int columnIndex)
{
return columnTitles[columnIndex];
}

public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0) c=Class.forName("java.lang.Integer");
else c=Class.forName("java.lang.String");
}catch(ClassNotFoundException cnfe)
{
//do nothing
}
return c;
}

public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
return designations.get(rowIndex).getTitle();
}

//APPLICATION SPECIFIC METHODS
public void add(DesignationInterface designation) throws BLException
{
designationManager.addDesignation(designation);
designations.add(designation);
Collections.sort(designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}

public int indexOf(DesignationInterface designation) throws BLException
{
Iterator<DesignationInterface> iterator=designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(d.equals(designation))
{
return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid designation : "+designation.getTitle());//esa kabhi hoga nahi.
throw blException;
}

public int indexAgainstTitle(String title,boolean partialLeftSearch) throws BLException
{
Iterator<DesignationInterface> iterator=designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(partialLeftSearch)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase()))
{
return index;
}
}
else
{
if(d.getTitle().equalsIgnoreCase(title))
{
return index;
}
}
index++;
}//while loop ends here
BLException blException=new BLException();
blException.setGenericException("Invalid title : "+title);//esa kabhi hoga nahi.
throw blException;
}

public void update(DesignationInterface designation) throws BLException
{
designationManager.updateDesignation(designation);
designations.remove(indexOf(designation));
this.designations.add(designation);
Collections.sort(designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}

public void remove(int code) throws BLException
{
designationManager.removeDesignation(code);//neeche check krne ki jrurt hi nahi hai ki code valid hai ya nhi kyuki
//jb is upr wali method ko call krenge to agr code invalid hai to ye khud hi exception throw kr degi

Iterator<DesignationInterface> iterator=designations.iterator();
DesignationInterface designation;
int index=0;
while(iterator.hasNext())
{
designation=iterator.next();
if(designation.getCode()==code) break;
index++;
}
if(index==designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid code : "+code);
throw blException;
}
this.designations.remove(index);
fireTableDataChanged();
}

public void exportToPDF(File file) throws BLException
{
try
{
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document document=new Document(pdfDocument);
URL url=ClassLoader.getSystemClassLoader().getResource("cancel.png");
System.out.println("*******************");
System.out.println(url);
document.close();
}catch(Exception e)
{
BLException blException;
blException=new BLException();
throw blException;
}
}

public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index>=this.designations.size())
{
BLException blException;
blException=new BLException();
blException.setGenericException("Invalid index : "+index);
throw blException;
}
return this.designations.get(index);
}

}