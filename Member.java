import java.util.*; 
import java.io.*; 
import java.time.*; 
class Member implements LibraryEntity{ 
    Scanner in=new Scanner(System.in); 
    String name,lib_no,ph_no,mail,filename; 
    ArrayList dues=new ArrayList<>(); 
    BufferedWriter f; 
    Member(int i){} 
    Member(){ 
        System.out.println("Library number:"); 
        lib_no=in.nextLine(); 
        System.out.println("Name:"); 
        name=in.nextLine(); 
        System.out.println("Phone number:"); 
        ph_no=in.nextLine(); 
        System.out.println("Email address:"); 
        mail=in.nextLine(); 
        dues.add("No more Dues"); 
        filename=lib_no+".txt"; 
    } 
    @Override public void Write(){ 
        try{ 
            f=new BufferedWriter(new FileWriter(filename,true)); 
            f.write(lib_no); 
            f.write("\n"); 
            f.write(name); 
            f.write("\n"); 
            f.write(ph_no); 
            f.write("\n"); 
            f.write(mail); 
            f.write("\n"); 
            f.write(dues+System.lineSeparator()); 
            f.write("\n"); 
            f.close(); 
        } catch(IOException e){ 
            System.out.println("IO Error"); 
        } 
    } 
    BufferedReader read; 
    @Override public void Read(){} 
    void Read(String filename){ 
        try{ 
            read=new BufferedReader(new FileReader(filename)); 
            String line=read.readLine(); 
            while(line!=null){ 
                System.out.println(line); 
                line=read.readLine(); 
            } read.close(); 
        } catch(IOException e){ 
            System.out.println("IO Error"); 
        } 
    } 
}