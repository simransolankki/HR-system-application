//Example of deep cloning
class Bulb
{
public int wattage;
}
class xyz implements Cloneable
{
public int x;
public int y;
String g;
Bulb b=new Bulb();
public Object clone()
{
try
{
xyz xx=(xyz)super.clone();
//for deep cloning
xx.b=new Bulb();
xx.b.wattage=this.b.wattage;
return xx;
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
x1.b.wattage=5;
xyz x2=(xyz)x1.clone();
System.out.println("x: "+x1.x+", y:"+x1.y+", g:"+x1.g+", wattage: "+x1.b.wattage);
System.out.println("x: "+x2.x+", y:"+x2.y+", g:"+x2.g+", wattage: "+x2.b.wattage);
x1.x=1000;
x1.y=2000;
x1.g="Mumbai";
x1.b.wattage=55555;
System.out.println("x: "+x1.x+", y:"+x1.y+", g:"+x1.g+", wattage: "+x1.b.wattage);
System.out.println("x: "+x2.x+", y:"+x2.y+", g:"+x2.g+", wattage: "+x2.b.wattage);
//x2.b.wattage should not have changed but it changed
//Because b ka original address share hua.
//to jab humne x1 ki wattage mein change kiya tb x2 ki bulb k wattage ki value bhi change ho gyi
//kyuki original address tha dono k paas or bulb ka clone nhi bna tha. That is why to clone it properly
//humme manually clone method mein bulb ka clone bnana pdega
}
}