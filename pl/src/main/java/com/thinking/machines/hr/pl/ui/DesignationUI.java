package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
private JLabel titleLabel;
private JLabel searchLabel;
private JLabel searchErrorLabel;
private JButton clearSearchTextField;
private JTextField searchTextField;
private Container container;
private JTable designationsTable;
private JScrollPane jsp;
private DesignationModel designationModel;
private DesignationPanel designationPanel;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
public DesignationUI()
{
initComponents();
setAppearance();
addListeners();
designationPanel.setViewMode();
}

private void initComponents()
{
titleLabel=new JLabel("Designations");
searchLabel=new JLabel("Search");
searchErrorLabel=new JLabel("");
searchTextField=new JTextField(50);
clearSearchTextField=new JButton();
designationModel=new DesignationModel();
designationsTable=new JTable(designationModel);
jsp=new JScrollPane(designationsTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
designationPanel=new DesignationPanel();
}

private void setAppearance()
{
int lm,tm;
lm=0;
tm=0;
titleLabel.setBounds(lm+5,tm+5,150,30);
Font titleFont=new Font("Verdana",Font.BOLD,20);
titleLabel.setFont(titleFont);
searchErrorLabel.setForeground(Color.red);
Font errorFont=new Font("Verdana",Font.PLAIN,12);
searchErrorLabel.setFont(errorFont);
searchErrorLabel.setBounds(lm+70+50+10+250-62,tm+5+30+3,80,10);
searchLabel.setBounds(lm+70,tm+50,70,20);
Font searchLabelFont=new Font("Verdana",Font.PLAIN,15);
searchLabel.setFont(searchLabelFont);
Font searchTextFieldFont=new Font("Verdana",Font.PLAIN,12);
searchTextField.setFont(searchTextFieldFont);
searchTextField.setBounds(lm+70+50+10,tm+50,250,24);
ImageIcon cancelIcon=new ImageIcon(this.getClass().getResource("/images/cancel.png"));
//System.out.println(icon.getImageLoadStatus());
//System.out.println(MediaTracker.ABORTED);
//System.out.println(MediaTracker.ERRORED);
//System.out.println(MediaTracker.COMPLETE);
clearSearchTextField.setIcon(cancelIcon);
clearSearchTextField.setBackground(Color.white);
clearSearchTextField.setBorder(null);
clearSearchTextField.setBounds(lm+70+50+10+250+5,tm+50,24,24);
Font dataFont=new Font("Verdana",Font.PLAIN,15);
designationsTable.setFont(dataFont);
designationsTable.setRowHeight(30);
designationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
JTableHeader header=designationsTable.getTableHeader();
header.setReorderingAllowed(false);
Font headerFont=new Font("Verdana",Font.BOLD,15);
header.setFont(headerFont);
TableColumnModel columnModel=designationsTable.getColumnModel();
columnModel.getColumn(0).setPreferredWidth(50);
columnModel.getColumn(1).setPreferredWidth(475-30);
jsp.setBounds(lm+5,tm+50+30,475,255);
designationPanel.setBounds(lm+5,tm+50+30+255+5,475,165);
setLayout(null);
container.add(searchLabel);
container.add(titleLabel);
container.add(searchErrorLabel);
container.add(searchTextField);
container.add(clearSearchTextField);
container.add(designationPanel);
container.add(jsp);
container.setBackground(Color.white);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int width=500;
int height=550;
int x=(d.width-width)/2;
int y=(d.height-height)/2;
setLocation(x,y);
setSize(width,height);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}

private void addListeners()
{

searchTextField.getDocument().addDocumentListener(this);
clearSearchTextField.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});

designationsTable.getSelectionModel().addListSelectionListener(this);
}

//setMode starts
private void setViewMode()
{
mode=MODE.VIEW;
if(designationsTable.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchTextField.setEnabled(false);
searchTextField.setEnabled(false);
designationsTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchTextField.setEnabled(true);
searchTextField.setEnabled(true);
designationsTable.setEnabled(true);
}
}

private void setAddMode()
{
searchTextField.setEnabled(false);
clearSearchTextField.setEnabled(false);
searchTextField.setEnabled(false);
designationsTable.setEnabled(false);
}

private void setEditMode()
{
searchTextField.setEnabled(false);
clearSearchTextField.setEnabled(false);
searchTextField.setEnabled(false);
designationsTable.setEnabled(false);
}

private void setDeleteMode()
{
searchTextField.setEnabled(false);
clearSearchTextField.setEnabled(false);
searchTextField.setEnabled(false);
designationsTable.setEnabled(false);
}

private void setExportToPDFMode()
{
searchTextField.setEnabled(false);
clearSearchTextField.setEnabled(false);
searchTextField.setEnabled(false);
designationsTable.setEnabled(false);
}
//setMode ends

private void searchDesignation()
{
searchErrorLabel.setText("");
String title=searchTextField.getText().trim();
if(title.length()==0) return;
int rowIndex;
try
{
rowIndex=designationModel.indexAgainstTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
return;
}
designationsTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationsTable.getCellRect(rowIndex,0,true);
designationsTable.scrollRectToVisible(rectangle);
}


public void changedUpdate(DocumentEvent ev)
{
//searchErrorLabel.setText("changed");
searchDesignation();
}

public void valueChanged(ListSelectionEvent ev)
{
int selectedRow=designationsTable.getSelectedRow();
try
{
DesignationInterface designation;
designation=designationModel.getDesignationAt(selectedRow);
designationPanel.setDesignation(designation);
}catch(BLException blException)
{
designationPanel.clearDesignation();//clearPanel mein designation ko null assign kr diya hai
}
}

public void removeUpdate(DocumentEvent ev)
{
//searchErrorLabel.setText("remove");
searchDesignation();
}

public void insertUpdate(DocumentEvent ev)
{
//searchErrorLabel.setText("insert");
searchDesignation();
}


//inner class
public class DesignationPanel extends JPanel
{
private JLabel panelCaption;
private JLabel titleLabel;
private JPanel buttonsPanel;
private JButton addButton,editButton,deleteButton,cancelButton,exportToPDFButton;
private JButton clearTextFieldButton;
private JTextField titleTextField;
private DesignationInterface designation;
private ImageIcon addIcon,editIcon,cancelIcon,exportToPDFIcon,deleteIcon,saveIcon,updateIcon;
DesignationPanel()
{
initComponents();
setAppearance();
addListeners();
}


public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
titleLabel.setText(designation.getTitle());
}

public void clearDesignation()
{
this.designation=null;
titleLabel.setText("");
}

private void initComponents()
{
designation=null;
panelCaption=new JLabel("Designation");
titleLabel=new JLabel("");
titleTextField=new JTextField();
clearTextFieldButton=new JButton("x");
buttonsPanel=new JPanel();

addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
deleteButton=new JButton(deleteIcon);
cancelButton=new JButton(cancelIcon);
exportToPDFButton=new JButton(exportToPDFIcon);

//String imageLocation="C:"+File.separator+"javaProjects"+File.separator+"hr"+File.separator+"pl"+File.separator+"images";

addIcon=new ImageIcon(this.getClass().getResource("/images/add.png"));
editIcon=new ImageIcon(this.getClass().getResource("/images/edit.png"));
cancelIcon=new ImageIcon(this.getClass().getResource("/images/cancel.png"));
deleteIcon=new ImageIcon(this.getClass().getResource("/images/delete.png"));
saveIcon=new ImageIcon(this.getClass().getResource("/images/save.png"));
updateIcon=new ImageIcon(this.getClass().getResource("/images/update.png"));
exportToPDFIcon=new ImageIcon(this.getClass().getResource("/images/export-to-pdf.png"));
}

private void setAppearance()
{
int lm=0;
int tm=0;

Font panelCaptionFont=new Font("Verdana",Font.BOLD,15);
panelCaption.setFont(panelCaptionFont);
panelCaption.setBounds(lm+40,tm+20,100,18);

Font titleLabelFont=new Font("Verdana",Font.PLAIN,15);
titleLabel.setFont(titleLabelFont);
titleLabel.setBounds(lm+40+100+30,tm+20,200,18);

Font titleTextFieldFont=new Font("Verdana",Font.PLAIN,15);
titleTextField.setFont(titleTextFieldFont);
titleTextField.setBounds(lm+40+100+20,tm+20,200,20);

clearTextFieldButton.setBounds(lm+40+100+30+200+10,tm+20,25,25);

buttonsPanel.setBounds(lm+5,tm+20+18+50,475-10,40);
buttonsPanel.setBackground(Color.white);
//buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));

addButton.setBackground(Color.white);
addButton.setBorder(null);
addButton.setIcon(addIcon);
buttonsPanel.add(addButton);
editButton.setBackground(Color.white);
editButton.setBorder(null);
addButton.setIcon(editIcon);
buttonsPanel.add(editButton);
deleteButton.setBackground(Color.white);
deleteButton.setBorder(null);
deleteButton.setIcon(deleteIcon);
buttonsPanel.add(deleteButton);
cancelButton.setBackground(Color.white);
cancelButton.setBorder(null);
cancelButton.setIcon(cancelIcon);
buttonsPanel.add(cancelButton);
exportToPDFButton.setBackground(Color.white);
exportToPDFButton.setBorder(null);
exportToPDFButton.setIcon(exportToPDFIcon);
buttonsPanel.add(exportToPDFButton);

add(panelCaption);
add(titleLabel);
add(titleTextField);
add(clearTextFieldButton);
add(buttonsPanel);
setLayout(null);
setBackground(Color.white);
setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
}

private boolean addDesignation()
{
String title=this.titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,(new String("Designation required")),"Error",JOptionPane.ERROR_MESSAGE);
titleTextField.requestFocus();
return false;
}
else
{
DesignationInterface d=new Designation();
d.setTitle(title);
try
{
designationModel.add(d);
int rowIndex=designationModel.indexOf(d);
designationsTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationsTable.getCellRect(rowIndex,0,true);
designationsTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException(),"Error",JOptionPane.ERROR_MESSAGE);
}
else
{
if(blException.hasException("title")) JOptionPane.showMessageDialog(this,blException.getException("title"),"Error",JOptionPane.ERROR_MESSAGE);
}
titleTextField.requestFocus();
return false;
}
}
}

private boolean updateDesignation()
{
String title=this.titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,new String("Designation required"),"Error",JOptionPane.ERROR_MESSAGE);
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
try
{
designationModel.update(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOf(d);
designationsTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationsTable.getCellRect(rowIndex,0,true);
designationsTable.scrollRectToVisible(rectangle);
}catch(BLException blException)
{
//do nothing
}
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException(),"Error",JOptionPane.ERROR_MESSAGE);
}
else
{
if(blException.hasException("title")) JOptionPane.showMessageDialog(this,blException.getException("title"),"Error",JOptionPane.ERROR_MESSAGE);
}
return false;
}
return true;
}

private void deleteDesignation()
{
try
{
String title=this.designation.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this,"Delete "+title+" ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+" deleted.");
this.clearDesignation();
}catch(BLException blException)
{
if(blException.hasGenericException()) JOptionPane.showMessageDialog(this,blException.getGenericException(),"Error",JOptionPane.ERROR_MESSAGE);
else
{
if(blException.hasException("title")) JOptionPane.showMessageDialog(this,blException.getException("title"),"Error",JOptionPane.ERROR_MESSAGE);
}
}
}


private void addListeners()
{

addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW) setAddMode();
else
{
if(addDesignation())
{
setViewMode();
}
}
}

});

editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW) setEditMode();
else
{
if(updateDesignation()) setViewMode();
}
}
});

deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setDeleteMode();
}
});


cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
DesignationPanel.this.setViewMode();
DesignationUI.this.setViewMode();
}
});

exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
JFileChooser jFileChooser=new JFileChooser();
jFileChooser.setCurrentDirectory(new File("."));
jFileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){
public boolean accept(File file)
{
if(file.getName().endsWith(".pdf")) return true;
return false;
}
public String getDescription()
{
return "PDF files";
}
});
try
{
int selectedOption=jFileChooser.showSaveDialog(DesignationUI.this);
File selectedFile=jFileChooser.getSelectedFile();
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith(".")) pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false) pdfFile+=".pdf";
File file=new File(pdfFile);
File parent=new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect path");
return;
}
System.out.println(pdfFile);
designationModel.exportToPDF(file);
}
catch(BLException blException)
{
JOptionPane.showMessageDialog(DesignationUI.this,blException.getGenericException());
}
catch(Exception e)
{
System.out.println(e);
}
}
});

clearTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
DesignationPanel.this.titleTextField.setText("");
}
});



}




//inner class setModes starts
void setViewMode()
{
DesignationUI.this.setViewMode();
this.titleTextField.setEnabled(false);
this.titleTextField.setVisible(false);
this.clearTextFieldButton.setEnabled(false);
this.clearTextFieldButton.setVisible(false);
this.titleLabel.setVisible(true);
addButton.setIcon(addIcon);
addButton.setEnabled(true);
editButton.setIcon(editIcon);
cancelButton.setEnabled(false);
if(designationsTable.getRowCount()==0)
{
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
else
{
editButton.setEnabled(true);
deleteButton.setEnabled(true);
exportToPDFButton.setEnabled(true);
}
}

void setAddMode()
{
mode=MODE.ADD;
DesignationUI.this.setAddMode();
this.titleTextField.setText("");
this.titleLabel.setVisible(false);
this.titleTextField.setEnabled(true);
this.titleTextField.setVisible(true);
this.clearTextFieldButton.setEnabled(true);
this.clearTextFieldButton.setVisible(true);
addButton.setIcon(saveIcon);
addButton.setEnabled(true);
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}

void setEditMode()
{
mode=MODE.EDIT;
if(designationsTable.getSelectedRow()<0 || designationsTable.getSelectedRow()>=designationsTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit");
this.setViewMode();
}
else
{
editButton.setIcon(updateIcon);
DesignationUI.this.setEditMode();
this.titleTextField.setEnabled(true);
this.titleTextField.setText(this.designation.getTitle());
this.titleLabel.setEnabled(false);
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
addButton.setEnabled(false);
editButton.setEnabled(true);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
}

void setDeleteMode()
{
mode=MODE.DELETE;
if(designationsTable.getSelectedRow()<0 || designationsTable.getSelectedRow()>=designationsTable.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit");
return;
}
DesignationUI.this.setDeleteMode();
this.titleLabel.setVisible(true);
this.titleTextField.setEnabled(false);
this.titleTextField.setVisible(false);
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
deleteDesignation();
this.setViewMode();
}

void setExportToPDFMode()
{
mode=MODE.EXPORT_TO_PDF;
DesignationUI.this.setExportToPDFMode();
this.titleTextField.setText(this.designation.getTitle());
this.titleLabel.setVisible(true);
this.titleTextField.setVisible(true);
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false); 
}
//inner class setModes ends


}
}