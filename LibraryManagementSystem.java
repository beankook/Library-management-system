import java.util.*; 
import java.io.*; 
import java.time.*; 
public class LibraryManagementSystem{ 
    public static void main(String args []){ 
        int ch1,ch2,ch3; Scanner in=new Scanner(System.in); System.out.println("User:\n1. Librarian\n2. Member"); 
        ch1=in.nextInt(); in.nextLine(); Book b=new Book(1); Member m=new Member(1); 
        Loan l=new Loan(); String lib_no; 
        switch(ch1){ 
            case 1: { 
                System.out.println("1.Add books\n2.View all books\n3.Search for a book\n4.Add new member\n5. View member details"); 
                ch2=in.nextInt(); in.nextLine(); 
                switch(ch2){ 
                    case 1: { b=new Book(); b.Write(); break; } 
                    case 2: { if(b!=null){ b.Read(); } else{ System.out.println("No books available"); } break; } 
                    case 3: { l.Search(b); break; } 
                    case 4: { m=new Member(); m.Write(); break; } 
                    case 5: { l.ViewMemberDetails(m); break; }
                } 
                break; 
            } 
            case 2: { 
                System.out.println("1.View all books\n2.Search for a book\n3.View member details\n4.Borrow\n5.Return"); 
                ch3=in.nextInt(); in.nextLine(); 
                switch(ch3){ 
                    case 1: { b.Read(); break; } 
                    case 2: { l.Search(b); break; } 
                    case 3: { l.ViewMemberDetails(m); break; } 
                    case 4: { b=new Book(1); l.Borrow(m,b); break; } 
                    case 5: { LocalDate returndate= l.Return(m,b); break; } 
                } 
                break; 
            } 
        } 
    } 
}