package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.io.*;
import java.sql.*;
public class DesignationDAO implements DesignationDAOInterface
{
public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation is null");
String title=designationDTO.getTitle();
if(title==null) throw new DAOException("Designation is null");
else
{
title=title.trim();
if(title.length()==0) throw new DAOException("Designation's length zero");
}
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation : "+title+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into designation (title) values (?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int code=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
designationDTO.setCode(code);
}catch(SQLException sqlException)
{
System.out.println("Yaha atka");
throw new DAOException(sqlException.getMessage());
}
}//add ends

public void update(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation is null");
int code=designationDTO.getCode();
if(code<=0) throw new DAOException("Invalid code");
String title=designationDTO.getTitle();
if(title==null) throw new DAOException("Designation is null");
title=title.trim();
if(title.length()==0) throw new DAOException("Designation's length cannot be zero");
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("Select title from designation where code=?",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)//code doesnt exist
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("Select code from designation where title=? and code!=?",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)//title exists against other code
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Title : "+title+" exists against code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update designation set title=? where code=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}
public void delete(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code :"+code);
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("Select title from designation where code=?",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)//code doesnt exist
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from designation where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code :"+code);
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("Select title from designation where code=?",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)//code doesnt exist
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code : "+code);
}
String title=resultSet.getString("title").trim();
resultSet.close();
preparedStatement.close();
connection.close();
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
return designationDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public boolean codeExists(int code) throws DAOException
{
if(code<=0) return false;
boolean codeExists=false;
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
String sql="select title from designation where code="+code;
ResultSet resultSet=statement.executeQuery(sql);
if(resultSet.next()) codeExists=true;
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return codeExists;
}

public boolean titleExists(String title) throws DAOException
{
if(title==null) return false;
title=title.trim();
if(title.length()==0) return false;
boolean titleExists=false;
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
String sql="select code from designation where title="+title;
ResultSet resultSet=statement.executeQuery(sql);
if(resultSet.next()) titleExists=true;
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return titleExists;
}

public int getCount() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select count(*) as cnt from designation");
resultSet.next();
int count=resultSet.getInt(1);
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public Set<DesignationDTOInterface> getAll() throws DAOException
{
Set<DesignationDTOInterface> designations=new TreeSet<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from designation");
DesignationDTOInterface designationDTO;
String title;
int code;
while(resultSet.next())
{
designationDTO=new DesignationDTO();
title=resultSet.getString("title").trim();
code=resultSet.getInt("code");
designationDTO.setTitle(title);
designationDTO.setCode(code);
designations.add(designationDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return designations;
}//getAll ends
}//class definition ends