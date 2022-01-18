class xyz implements Cloneable
{
public int x;
public int y;
String g;
public Object clone()
{
try
{
return super.clone();
}catch(CloneNotSupportedException cnse)
{
return null;
}
}
}
class psp
{
public static void main(String gg[])
{
xyz x1=new xyz();
x1.x=100;
x1.y=200;
x1.g="Ujjain";
xyz x2=(xyz)x1.clone();
System.out.println("x: "+x1.x+", y:"+x1.y+", g:"+x1.g);
System.out.println("x: "+x2.x+", y:"+x2.y+", g:"+x2.g);
}
}