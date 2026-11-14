//Compilation Command: javac -cp ".:gson-2.9.1.jar" KillCart.java

import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;// Just use absolute paths for everything. Working with relative paths is a headache.
import java.nio.file.PathMatcher;
import java.nio.file.FileSystems;
//import java.nio.file.Paths; //No need Path can do evrything this does.
//import com.google.gson.Gson; //Not yet

public class KillCart{
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
	public static final String CLEAR_SCREEN = "\033[H\033[2J";

static boolean done=true;
static Scanner sc = new Scanner(System.in);
static Path curPath;// ,
static String[] command;
static String startPathStr;
static int index;

public static void main(String[] love){

startPathStr=System.getProperty("user.dir");
curPath=Path.of(startPathStr);
System.out.println("Working Directory: " + startPathStr);

while(done){
System.out.print(PURPLE+"killcart> "+RESET);
command = sc.nextLine().split(";");//Creates new array.
index=1;
try{switch(command[0]){// Maybe i have to add a move command also. //U can imbune string parsing with regex wildcard(*s and stuff)
case "pwd": System.out.println(curPath.toAbsolutePath().toString()); break; //completed
case "clear": System.out.print(CLEAR_SCREEN); System.out.flush(); break;
case "ls": ls(); break;//compleated
case "cd": cd(); break;//completed
case "delete": delete(); break; //compleated
case "restore": restore(); break;
case "view": view(); break;//completed
case "move": move(); break;//have to write
case "PerminantlyDeleteAllSelectedFiles": DANGER(); break; //Will do at last....
case "exit": done=false; break;//Compleated!
default: System.out.print("Thats a wrong command. Try help\n"); break;//default
case "help": Help(); break;
case "": break;//Not very important.
}//switch
}catch(Exception e){System.out.println("An exception has occured. Make sure ur using the correct command format.");}//try
}//while
}//main
static void ls(){
Path prePath=curPath;
boolean a=true;
Stream<Path> flist=null;
ArrayList<Path> files=new ArrayList<>();
String s=MakeGoodString();
if(s.equals("-a")){ a=false; s=MakeGoodString();}
if(s=="") files.add(curPath);
else files=GetFiles(curPath,s);
for(int i=0,j=files.size();i<j;i++){
prePath=files.get(i);
if(Files.isRegularFile(prePath)){System.out.println(curPath.relativize(prePath)); continue;}
System.out.println(BLUE+"./"+curPath.relativize(prePath)+RESET+":");
try{flist = Files.list(prePath);
if(a) flist.filter(file -> file.getFileName().toString().charAt(0)!='.').forEach(file -> {if(Files.isDirectory(file)) System.out.println(BLUE+file.getFileName().toString()+RESET);else System.out.println(file.getFileName().toString());});
else flist.forEach(file -> {if(Files.isDirectory(file)) System.out.println(BLUE+file.getFileName().toString()+RESET);else System.out.println(file.getFileName().toString());});
}catch(Exception e){System.out.println("Exception in reading file list stream."+e.getMessage());}
finally{if(flist!=null)flist.close();}}//for
}//ls
static void view(){
Path prePath=curPath;
boolean w=true;
ArrayList<Path> files=null;
Stream<Path> flist=null;
String s=MakeGoodString();
if(s.equals("-w")) w=false;
else if(s.equals("")) s="*";
if(w) files=GetFiles(curPath,".killcart-"+s);
else{ try{ flist=Files.walk(Path.of(startPathStr));
flist.filter(file -> file.getFileName().toString().startsWith(".killcart-")).forEach(file -> {if(Files.isDirectory(file)) System.out.println(BLUE+file.toAbsolutePath().toString().replaceFirst("\\.killcart-","")+RESET);else System.out.println(file.toAbsolutePath().toString().replaceFirst("\\.killcart-",""));});}//else
catch(Exception e){System.out.println(" cant do view;-w:- id-2"+e.getMessage());}
finally{if(flist!=null) flist.close();
return;}//try-catch-finally
}//else
for(int i=0,j=files.size(); i<j; i++){
prePath=files.get(i);
System.out.println(prePath.getFileName().toString().replaceFirst("\\.killcart-",""));//replaceFirst() interprets arguments as regex.
}//for
}//view 
static void cd(){
String s=null;
ArrayList<Path> tempPath=new ArrayList<>();
s=MakeGoodString();
switch(s){
case "": tempPath.add(Path.of(startPathStr)); break;
default: tempPath=GetFiles(curPath,s);
}//switch
if(tempPath.size()==1) 
if(Files.isDirectory(tempPath.get(0))) curPath=tempPath.get(0).normalize();
else System.out.println("Thats not a valid directory!");
else if(tempPath.size()>1){ System.out.println("Too many arguments."); return;}
else{ tempPath.add(Path.of(curPath.toAbsolutePath().toString(),s));//tempPath.size()==0
if(Files.isDirectory(tempPath.get(0))) curPath=tempPath.get(0).normalize();
else System.out.println("Thats not a directory.");
}//else
}//cd


static void move(){
String s1,s2,sp;
ArrayList<Path> pp1,p;
Path p1,p2;
int npp1;
s1=MakeGoodString();
s2=MakeGoodString();
sp=curPath.toAbsolutePath().toString();
p1=Path.of(sp,s1);
p2=Path.of(sp,s2);
p1.normalize();
p2.normalize();
pp1=GetFiles(p1.getParent(),p1.getFileName().toString());
p=GetFiles(p2.getParent(), p2.getFileName().toString());
if(p.size()!=1){ System.out.println("Please give ONE destination."); return;}
p2=p.get(0);
p1=pp1.get(0);
npp1=pp1.size();
if(npp1==0){ System.out.println("Please give atleast one source file."); return;}
if(!Files.isDirectory(p2)) 
if(npp1>1){System.out.println("Can only move one file to one source."); return;}
else if(Files.exists(p2)){ System.out.println("The destination file already exists."); return;}
else try{Files.move(p1,p2);}catch(Exception e){System.out.println("Exception while moving files: "+e.getMessage());}
else
for(int i=0; i<npp1; i++){
p1=pp1.get(i);
p2=Path.of(p2.toAbsolutePath().toString(),p1.getFileName().toString());
try{Files.move(p1,p2);}catch(Exception e){System.out.println("Exception while moving files: "+e.getMessage());}

}//while

/*
p=GetFiles(curPath, s);
p1=p.get(0);
if(!Files.exists(p1)) p1=Path.of(curPath.toAbsolutePath().toString(), s);
s2=MakeGoodString();
Path p2=Path.of(s).getParent();
p=GetFiles(curPath, s);
if(p.size()!=1){ System.out.println("Please only give one destination to be moved."); return;}
p2=p.get(0);
if(Files.isDirectory(p2) || Files.isDirectory(p2.getParent()));
else p2=Path.of(curPath.toAbsolutePath().toString(), s);
*/


/*
if(Files.exists(p1)){ if(!Files.exists(p2))try{ Files.move(p1,p2);}catch(Exception e){System.out.println("Moving file failed. "+e.getMessage());}
else if(Files.isDirectory(p2)) try{ Files.move(p1,Path.of(p2.toAbsolutePath().toString(), p1.getFileName().toString()));}catch(Exception e){System.out.println("Fuckers Fail-4"+e.getMessage());}}//if
else System.out.println("Either "+curPath.relativize(p1)+" dosnt exist or "+curPath.relativize(p2)+" already exists.");
*/
}//move


static void delete(){
//short index=1;
while(index<command.length){
try{ String fname=MakeGoodString();
index++;
String curDirStr=curPath.toAbsolutePath().toString();
Path f1=Path.of(curDirStr,fname);
if(!Files.exists(f1)){System.out.println("That file does not exist!"); return;}
String kill = ".killcart-"+fname;
Path f2=Path.of(curDirStr,kill);//curDirStr+"/"+kill
if(!Files.exists(f2))Files.move(f1,f2);
else System.out.println("Another file of that name had been deleted, tf does this even occure?: "+f2.getFileName().toString().replaceFirst("\\.killcart-",""));
}catch(Exception e){System.out.println(e.getMessage());}//try
}//while
}//delete


static void restore(){//Later I have to add the feature of restoring multiple files at once.
//short index=1;
while(index<command.length){
try{ String restore=MakeGoodString(/*index*/);
index++;
	String fname=".killcart-"+restore;
String curDirStr=curPath.toAbsolutePath().toString();
Path f1=Path.of(curDirStr,fname);
if(!Files.exists(f1)){System.out.println("That file does not exist in the deleted!"); return;}
Path f2=Path.of(curDirStr,restore);
if(!Files.exists(f2))Files.move(f1,f2);
else System.out.println("A file with that name already exists, so we cant overwrite it, tf does this even occure?:"+f2.getFileName());
}catch(Exception e){System.out.println(e.getMessage());}//try
}//while
}//restore


static void DANGER(){
if(command.length>2){ System.out.println("Are you sure u want to delete these files?, it looks like u gave extra arguments?:"); view(); return;}
Stream<Path> files=null;
view();
index=1;//reset index, have to do this after every funciton call except MakeGoodString.
System.out.print("These files will be perminantly deleted(enter 'dsafds' to proceed):");
if(!sc.nextLine().equals("dsafds")) return;
if(MakeGoodString()!="-w"){
try{files = Files.list(curPath);
files.filter(file -> file.getFileName().toString().startsWith(".killcart-")).forEach(file ->{
try{Files.delete(file);}catch(Exception e){System.out.println("Fuckers Fail-2");}});
}catch(IOException e){ System.out.println("There has been an error deleting files: "+e.getMessage());
}finally{if(files!=null) files.close();}//try
}//if
else{ 
try{files = Files.walk(Path.of(startPathStr));
files.sorted((f1,f2) -> Integer.compare(f2.getNameCount(), f1.getNameCount())/*f2.getNameCount() - f1.getNameCount()*/).filter(file -> file.getFileName().toString().startsWith(".killcart-")).forEach(file ->{
try{Files.delete(file);}catch(Exception e){System.out.println("Fuckers Fail-2");}});
}catch(IOException e){ System.out.println("There has been an error deleting files: "+e.getMessage());
}finally{if(files!=null) files.close();}//try
}//else
}//DANGER


static void Help(){System.out.printf("pwd :- Print the current working directory\nls [;path] [-a] :- List directory contents (-a includes hidden files)\ncd [;path] :- Change directory (no argument returns to starting directory)\nview [-w] :- View staged files (-w searches recursively)\ndelete ;filename... :- Stage files or directories for deletion\nrestore ;filename... :- Restore staged files or directories\nmove ;source;destination :- Move or rename files/directories\nPerminantlyDeleteAllSelectedFiles [-w] :- Permanently delete staged files/directories (-w searches recursively)\nclear :- Clear the terminal\nhelp :- Display this help message\nexit :- Exit KillCart\nUse ';' to separate command arguments.\n");
}//Help


static String MakeGoodString(/*int n*/){//U can imbune string parsing with regex wildcard(*s and stuff), i imbuned it with glob.
if(index==command.length) return "";
String rstring=command[index++];
while(rstring.charAt(rstring.length()-1)=='\\'){
if(index==command.length) return "";
rstring+=command[index].substring(1,command[index].length()-1);
index++;}
return rstring;
}//MakeGoodString
static ArrayList<Path> GetFiles(Path dir, String pattern){//Only works for files beneth the given directory. and will walk.
Path dirr=dir;
ArrayList<Path> rpaths= new ArrayList<>();
while(true){
if(pattern.startsWith("../")){ dir=dir.getParent(); if(dir==null) dir=Path.of("/"); pattern=pattern.substring(3,pattern.length());}
else if(pattern.startsWith("./")) pattern=pattern.substring(2,pattern.length());
else break;
}//while
PathMatcher match=FileSystems.getDefault().getPathMatcher("glob:"+pattern);
try(Stream<Path> flist=Files.walk(dir)){// dont need to close whatever is in try(..) flist.filter(file->file.getFileName().toString().matches(s)).forEach(file -> rpaths.add(file.toAbsolutePath()));}
flist.filter(file -> match.matches(dirr.relativize(file))).forEach(file -> rpaths.add(file));}//match.matches() matches the ENTIRE PATH, so be careful.
catch(Exception e){System.out.println("Some error while making arraylist of paths to list:- id-1.\t"+e.getMessage()); return null;}//Id really preffer regex but glob just has better service here, cause my files has a lot of '.' and can do '\\.' everytime.
return rpaths;
}//GetFiles
static ArrayList<Path> GetAllFiles(Path dir, String pattern){//here dir is expected to be an absolute path and pattern is expected to be relative to dir.
Path pat=Path.of(dir.toAbsolutePath().toString(), pattern);
ArrayList<Path> rpaths=new ArrayList<>();
PathMatcher match=FileSystems.getDefault().getPathMatcher("glob:"+pat.toAbsolutePath().toString());
pat.normalize();
try(Stream<Path> flist=Files.walk(Path.of(startPathStr))){
flist.filter(file -> match.matches(file)).forEach(file -> rpaths.add(file));
}//try
catch(Exception e){System.out.println("Some error while making arraylist of paths to list:- id-1.\t"+e.getMessage()); return null;}
return rpaths;
}//GetAllFiles
}//KillCart

/*
{if(Files.isDirectory(file)) System.out.println(BLUE+file.getFileName().toString()+RESET);else System.out.println(file.getFileName().toString());}
System.out.print(file.getFileName().toString().replaceFirst(".killcart-","")+"\t")

if(s.charAt(0)=='~') s=startPathStr+s.substring(1,s.length());
*/