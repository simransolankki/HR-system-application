package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManager designationManager=null;
private DesignationManager() throws BLException
{
populateDataStructures();
}
private void populateDataStructures() throws BLException
{
this.codeWiseDesignationsMap=new HashMap<>();
this.titleWiseDesignationsMap=new HashMap<>();
this.designationsSet=new TreeSet<>();
try
{
Set<DesignationDTOInterface> dlDesignations;
dlDesignations=new DesignationDAO().getAll();
DesignationInterface designation;
for(DesignationDTOInterface dlDesignation:dlDesignations)
{
designation=new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
codeWiseDesignationsMap.put(designation.getCode(),designation);
titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
designationsSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static DesignationManager getDesignationManager() throws BLException
{
if(designationManager==null) designationManager=new DesignationManager();
return designationManager;
}



public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
}
int code=designation.getCode();
String title=designation.getTitle();
if(code!=0)
{
blException.addException("code","Code should be zero");
}
if(title==null)
{
blException.addException("title","Title required");
title="";
}
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title required");
}
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
{
blException.addException("title","Designation : "+title+" exists");
}
if(blException.hasExceptions()) throw blException;
try
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
code=designationDTO.getCode();
designationDTO.setCode(code);
DesignationInterface dsDesignation=new Designation();
dsDesignation.setTitle(title);
dsDesignation.setCode(code);
this.codeWiseDesignationsMap.put(code,dsDesignation);
this.titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
this.designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}

public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null) blException.setGenericException("Designation required");
int code=designation.getCode();
String title=designation.getTitle();
if(code<=0)
{
blException.addException("code","Code is invalid");
throw blException;
}
else
{
if(!codeWiseDesignationsMap.containsKey(code))
{
blException.addException("code","Code not found");
throw blException;
}
}
if(title==null) blException.addException("title","Title required");
title=title.trim();
if(title.length()==0) blException.addException("title","Title required");

if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
{
DesignationInterface d=this.titleWiseDesignationsMap.get(title.toUpperCase());
if(d.getCode()!=code) blException.addException("title","Designation : "+title+" exists.");
}
if(blException.hasExceptions()) throw blException;
try
{
DesignationDTOInterface dlDesignation=new DesignationDTO();
dlDesignation.setCode(code);
dlDesignation.setTitle(title);
new DesignationDAO().update(dlDesignation);
DesignationInterface dsDesignation=this.codeWiseDesignationsMap.get(code);
this.codeWiseDesignationsMap.remove(dsDesignation.getCode());
this.titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
this.designationsSet.remove(dsDesignation);
dsDesignation.setTitle(title);
this.codeWiseDesignationsMap.put(code,dsDesignation);
this.titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
this.designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}


public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0)
{
blException.addException("code","Code is invalid");
throw blException;
}
if(!codeWiseDesignationsMap.containsKey(code))
{
blException.addException("code","Code not found");
throw blException;
}
try
{
new DesignationDAO().delete(code);
DesignationInterface dsDesignation=this.codeWiseDesignationsMap.get(code);
this.codeWiseDesignationsMap.remove(code);
this.titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
this.designationsSet.remove(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}

DesignationInterface getDSDesignationByCode(int code) throws BLException
{
DesignationInterface dsDesignation=this.codeWiseDesignationsMap.get(code);
return dsDesignation;//actual address will be returned
}

public DesignationInterface getDesignationByCode(int code) throws BLException
{
DesignationInterface dsDesignation=this.codeWiseDesignationsMap.get(code);
if(dsDesignation==null)
{
BLException blException=new BLException();
blException.addException("code","Code is invalid");
throw blException;
}
DesignationInterface designation=new Designation();
designation.setTitle(dsDesignation.getTitle());
designation.setCode(dsDesignation.getCode());
return designation;
}

public DesignationInterface getDesignationByTitle(String title) throws BLException
{
DesignationInterface dsDesignation=this.titleWiseDesignationsMap.get(title.toUpperCase());
if(dsDesignation==null)
{
BLException blException=new BLException();
blException.addException("title","Title not found");
throw blException;
}
DesignationInterface designation=new Designation();
designation.setTitle(dsDesignation.getTitle());
designation.setCode(dsDesignation.getCode());
return designation;
}


public int getDesignationCount() throws BLException
{
return this.designationsSet.size();
}

public boolean designationCodeExists(int code) throws BLException
{
return this.codeWiseDesignationsMap.containsKey(code);
}

public boolean designationTitleExists(String title) throws BLException
{
return this.titleWiseDesignationsMap.containsKey(title.toUpperCase());
}

public Set<DesignationInterface> getDesignations()
{
Set<DesignationInterface> designations=new TreeSet<>();
designationsSet.forEach((designation)->
{
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
designations.add(d);
}
);
return designations;
}
}