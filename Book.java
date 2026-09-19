import java.util.*; 
import java.io.*; 
import java.time.*; 
class Book implements LibraryEntity{ 
    Scanner in=new Scanner(System.in); 
    String title,author,status,ISBN; 
    BufferedWriter f; 
    Book(int i){} 
    Book(){ 
        System.out.println("ISBN:"); 
        ISBN=in.nextLine(); 
        System.out.println("Book Title:"); 
        title=in.nextLine(); 
        System.out.println("Author:"); 
        author=in.nextLine(); 
        status="Available"; 
    } 
    @Override public void Write(){ 
        try{ 
            f=new BufferedWriter(new FileWriter("bookDetail.txt",true)); 
            f.write(ISBN); f.write("\n"); 
            f.write(title); 
            f.write("\n"); 
            f.write(author); 
            f.write("\n"); 
            f.write(status);  
            f.write("\n\n"); 
            f.close(); 
        } 
        catch(IOException e){ 
            System.out.println("IO Error"); 
        } 
    } 
    BufferedReader read; 
    @Override public void Read(){ 
        try{ 
            read=new BufferedReader(new FileReader("bookDetail.txt")); 
            String line=read.readLine(); 
            while(line!=null){ System.out.println(line); 
            line=read.readLine(); 
            } read.close(); 
        } catch(IOException e){ 
            System.out.println("IO Error"); 
        } 
    } 
}