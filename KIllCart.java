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

public static void main(String[] Love) throws Exception{

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
//short index=1;
boolean a=true;
try{String s=MakeGoodString(/*index*/);
if(s.equals("-a")){ a=false; index++; s=MakeGoodString(/*index*/);}
prePath=Path.of(curPath.toAbsolutePath().toString(), s);
if(!Files.isDirectory(prePath)){
if(Files.exists(prePath)) System.out.println(prePath.getFileName());
else System.out.println("File dosnt exist or invalid file."); return;
}//if
}catch(Exception e){}//feature
Stream<Path> flist=null;
try{flist = Files.list(prePath);
if(a) flist.filter(file -> file.getFileName().toString().charAt(0)!='.').forEach(file -> {if(Files.isDirectory(file)) System.out.println(BLUE+file.getFileName().toString()+RESET);else System.out.println(file.getFileName().toString());});
/*
{if(Files.isDirectory(file)) System.out.println(BLUE+file.getFileName().toString()+RESET);else System.out.println(file.getFileName().toString());}
System.out.print(file.getFileName().toString().replaceFirst(".killcart-","")+"\t")
*/
else flist.forEach(file -> {if(Files.isDirectory(file)) System.out.println(BLUE+file.getFileName().toString()+RESET);else System.out.println(file.getFileName().toString());});
flist.close();
}catch(Exception e){System.out.println("Exception in reading file list stream."+e.getMessage());}
}//ls
static void view(){
Path prePath=curPath;
boolean w=true;
try{String s=MakeGoodString(/*1*/);
if(s=="-w"){w=false; s=MakeGoodString();}
prePath=Path.of(curPath.toAbsolutePath().toString(), s);
if(!Files.isDirectory(prePath)){
if(Files.exists(prePath) && prePath.getFileName().toString().startsWith(".killcart-")) System.out.println(prePath.getFileName().toString().replaceFirst(".killcart-",""));
else System.out.println("File dosnt exist or invalid file in present directory's deleted files."); return;
}//if
}catch(Exception e){}//feature
Stream<Path> flist=null;
try{if(w){ flist = Files.list(prePath);
flist.filter(file -> file.getFileName().toString().startsWith(".killcart-")).forEach(file -> {if(Files.isDirectory(file)) System.out.println(BLUE+file.getFileName().toString().replaceFirst(".killcart-","")+RESET);else System.out.println(file.getFileName().toString().replaceFirst(".killcart-",""));});}//if
else{ flist=Files.walk(Path.of(startPathStr));
flist.filter(file -> file.getFileName().toString().startsWith(".killcart-")).forEach(file -> {if(Files.isDirectory(file)) System.out.println(BLUE+file.toAbsolutePath().toString().replaceFirst(".killcart-","")+RESET);else System.out.println(file.toAbsolutePath().toString().replaceFirst(".killcart-",""));});}//else
flist.close();
}catch(Exception e){System.out.println("Exception in reading file list stream."+e.getMessage());}
}//view 
static void cd(){
String s=null;
Path tempPath;
if(command.length>1){ try{s=MakeGoodString(/*1*/);}catch(Exception e){System.out.println("Fuckers Fail-1."+e.getMessage());}
tempPath=Path.of(curPath.toAbsolutePath().toString(),s);
tempPath=tempPath.normalize();
}else tempPath=Path.of(startPathStr);
if(Files.isDirectory(tempPath)) curPath=tempPath;
else System.out.println("Thats not a valid directory!");
}//cd
static void move(){
String s=MakeGoodString();
Path p1,p2;
if(s.charAt(0)=='~') p1=Path.of(startPathStr,s.substring(1,s.length()));
else p1=Path.of(curPath.toAbsolutePath().toString(), s);
s=MakeGoodString();
if(s.charAt(0)=='~') p2=Path.of(startPathStr,s.substring(1,s.length()));
else p2=Path.of(curPath.toAbsolutePath().toString(), s);
if(Files.exists(p1)){if(!Files.exists(p2))try{ Files.move(p1,p2);}catch(Exception e){System.out.println("Fuckers Fail-3"+e.getMessage());}
else if(Files.isDirectory(p2)) try{ Files.move(p1,Path.of(p2.toAbsolutePath().toString(), p1.getFileName().toString()));}catch(Exception e){System.out.println("Fuckers Fail-4"+e.getMessage());}}//if
else System.out.println("Either "+curPath.relativize(p1)+" dosnt exist or "+curPath.relativize(p2)+" already exists.");
}//move
static void delete(){//Actually just rename! // Later I have to add feature of deleting multiple files at once.
//short index=1;
while(index<command.length){
try{ String fname=MakeGoodString(/*index*/);
index++;
String curDirStr=curPath.toAbsolutePath().toString();
Path f1=Path.of(curDirStr,fname);
if(!Files.exists(f1)){System.out.println("That file does not exist!"); return;}
String kill = ".killcart-"+fname;
Path f2=Path.of(curDirStr,kill);//curDirStr+"/"+kill
if(!Files.exists(f2))Files.move(f1,f2);
else System.out.println("Another file of that name had been deleted, tf does this even occure?: "+f2.getFileName().toString().replaceFirst(".killcart-",""));
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
if(index==command.length){ System.out.println("Your command was wrong, please redo."); return "";}
String rstring=command[index++];
while(rstring.charAt(rstring.length()-1)=='\\'){
if(index==command.length){ System.out.println("Your command was wrong, please redo."); return "";}
rstring+=command[index].substring(1,command[index].length()-1);
index++;}
return rstring;
}//MakeGoodString
static ArrayList<Path> GetFiles(Path dir, String pattern){//Only works for files inside given directory.
ArrayList<Path> rpaths= new ArrayList<>();
/*String s=p.getFileName().toString();*/
PathMatcher match=FileSystems.getDefault().getPathMatcher("glob:"+pattern);
try(Stream<Path> flist=Files.list(dir)){// dont need to close whatever is in try(..) flist.filter(file->file.getFileName().toString().matches(s)).forEach(file -> rpaths.add(file.toAbsolutePath()));}
flist.filter(file -> match.matches(file.getFileName())).forEach(file -> rpaths.add(file));}//match.matches() matches the ENTIRE PATH, so be careful.
catch(Exception e){System.out.println("Some error while making arraylist of paths to list:- id-1.\t"+e.getMessage());}//Id really preffer regex but glob just has better service here, cause my files has a lot of '.' and can do '\\.' everytime.
return rpaths;
}//GetFilePaths
}//KillCart