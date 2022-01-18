import java.io.*;
public class test
{
public static void main(String gg[])
{
try
{
File tmpfile=new File("tmp.tmp");
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpfile, "rw");
tmpRandomAccessFile.writeBytes("abcdefghij");
tmpRandomAccessFile.close();
tmpfile.delete();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}
}