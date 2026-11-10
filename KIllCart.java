//Compilation Command: javac -cp ".:gson-2.9.1.jar" KillCart.java

import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;// Just use absolute paths for everything. Working with relative paths is a headache.
//import java.nio.file.Paths; //No need Path can do evrything this does.
//import com.google.gson.Gson; //Not yet

public class KillCart{

static boolean done=true;
static Scanner sc = new Scanner(System.in);
static Path curPath;// ,
static String[] command;
static String startPathStr;

public static void main(String[] Love) throws Exception{

startPathStr=System.getProperty("user.dir");
curPath=Path.of(startPathStr);
System.out.println("Working Directory: " + startPathStr);

while(done){
System.out.print("killcart> ");
command = sc.nextLine().split(" ");//Creates new array.

try{switch(command[0]){// Maybe i have to add a move command also. //U can imbune string parsing with regex wildcard(*s and stuff)
case "ls": ls(); break;//compleated
case "cd": cd(); break;//completed
case "pwd": System.out.println(curPath.toAbsolutePath().toString()); break; //completed
case "delete": delete(); break; //compleated
case "restore": restore(); break;
case "view": view(); break;//completed
case "PerminantlyDeleteAllSelectedFiles": DANGER(); break; //Will do at last....
case "exit": done=false; break;//Compleated!
default: System.out.print("Thats a wrong command.\n");//default
case "help":
case "": Help();//Not very important.
}//switch
}catch(Exception e){System.out.println("And exception has occured. Make sure ur using the correct command format.");}//try
}//while
}//main
static void ls(){
Path prePath=curPath;
short index=1;
boolean a=true;
try{String s=MakeGoodString(index);
if(s.equals("-a")){ a=false; index++; s=MakeGoodString(index);}
prePath=Path.of(curPath.toAbsolutePath().toString(), s);
if(!Files.isDirectory(prePath)){
if(Files.exists(prePath)) System.out.println(prePath.getFileName());
else System.out.println("File dosnt exist or invalid file."); return;
}//if
}catch(Exception e){}//feature
Stream<Path> flist=null;
try{flist = Files.list(prePath);
if(a) flist.filter(file -> file.getFileName().toString().charAt(0)!='.').forEach(file -> System.out.print(file.getFileName().toString()+"\t"));
else flist.forEach(file -> System.out.print(file.getFileName().toString()+"\t"));
flist.close();
}catch(Exception e){System.out.println("Exception in reading file list stream."+e.getMessage());}
System.out.println();
}//ls
static void cd(){
String s=null;
Path tempPath;
if(command.length>1){ try{s=MakeGoodString(1);}catch(Exception e){System.out.println("Fuckers Fail-1.");}
tempPath=Path.of(curPath.toAbsolutePath().toString(),s);
tempPath=tempPath.normalize();
}else tempPath=Path.of(startPathStr);
if(Files.isDirectory(tempPath)) curPath=tempPath;
else System.out.println("Thats not a valid directory!");
}//cd
static void delete(){//Actually just rename! // Later I have to add feature of deleting multiple files at once.
short index=1;
while(index<command.length){
try{ String fname=MakeGoodString(index);
index++;
String curDirStr=curPath.toAbsolutePath().toString();
Path f1=Path.of(curDirStr,fname);
if(!Files.exists(f1)){System.out.println("That file does not exist!"); return;}
String kill = ".killcart-"+fname;
Path f2=Path.of(curDirStr,kill);//curDirStr+"/"+kill
if(!Files.exists(f2))Files.move(f1,f2);
else System.out.println("Another file of that name had been deleted, tf does this even occure?:"+f2.getFileName());
}catch(Exception e){System.out.println(e.getMessage());}//try
}//while
}//delete
static void DANGER(){
if(command.length>1){ System.out.println("Are you sure u want to delete these files?, it looks like u gave extra arguments?:"); view(); return;}
Stream<Path> files=null;
view();
System.out.print("These files will be deleted(enter 'dsafds' to proceed):");
if(!sc.nextLine().equals("dsafds")) return;
try{files = Files.list(curPath);
files.filter(file -> file.getFileName().toString().startsWith(".killcart-"))//(But this not where regex works)The FUll Stop(.) is the regex wildcard for any single charecter aka astrik(*) for but only 1 character.
.forEach(file ->{
try{Files.delete(file);}catch(Exception e){System.out.println("Fuckers Fail-2");}});
}catch(IOException e){ System.out.println("There has been an error deleting files: "+e.getMessage());
}finally{if(files!=null) files.close();}//try
}//DANGER
static void restore(){//Later I have to add the feature of restoring multiple files at once.
short index=1;
while(index<command.length){
try{ String restore=MakeGoodString(index);
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
static void view(){
Path prePath=curPath;
try{String s=MakeGoodString(1);
prePath=Path.of(curPath.toAbsolutePath().toString(), s);
if(!Files.isDirectory(prePath)){
if(Files.exists(prePath)) System.out.println(prePath.getFileName());
else System.out.println("File dosnt exist or invalid file."); return;
}//if
}catch(Exception e){}//feature
Stream<Path> flist=null;
try{flist = Files.list(prePath);
flist.filter(file -> file.getFileName().toString().startsWith(".killcart-")).forEach(file -> System.out.print(file.getFileName().toString().replaceFirst(".killcart-","")+"\t"));
flist.close();
}catch(Exception e){System.out.println("Exception in reading file list stream."+e.getMessage());}
System.out.println();
}//view 
static String MakeGoodString(int n) throws Exception{//U can imbune string parsing with regex wildcard(*s and stuff)
//System.out.println("Behold! The string parsing is yet to be compleate, pls dont expect it to be done properly especially when u have special and space charecters!");
return command[n];//This is for now!
}//MakeGoodString
static void Help(){System.out.printf("exit :- Exit the program, the Data will be saved.\nls :- to print the contents of current directory\ncd :- to change directory\ndelete :- delete the file\nPerminantlyDeleteAllSelectedFiles :- to perminantly delete all the deleted files\npwd :- print current directory\nrestore :- restore a file\nview :- view the deleted files\n");
}//Help
}//KillCart

/*class FileData{
	String originalName;//Along side directory and stuff.
	String fileName;//The custom name we have given it aka the id and stuff.
}*///This is for Part 2 of the project.

/*
Bugs:
What happens if a file named .killcart-song.mp3 already exists?
What happens if you stage two files with the same name from different directories?
What happens if the original filename itself starts with .killcart-?
What happens if you try to stage a directory?

*/