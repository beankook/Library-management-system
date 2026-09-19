import java.util.*;
import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

class Loan {
    Scanner in = new Scanner(System.in);
    String title, author, ISBN;
    BufferedReader read;
    String lib_no;
    LocalDate borrow;
    LocalDate due;
    LocalDate returndate;
    static final double DAILY_FINE = 1.0;
    SmsSender smsSender;
    EmailSender emailSender;

    Loan() {
        this.smsSender = new SmsSender();
        this.emailSender = new EmailSender();
    }

    String SearchByISBN(Book b) {
        System.out.println("ISBN:");
        ISBN = in.nextLine();
        try {
            read = new BufferedReader(new FileReader("bookDetail.txt"));
            String line;
            while ((line = read.readLine()) != null) {
                if (line.equals(ISBN)) {
                    System.out.println("Book found with ISBN: " + line);
                    b.ISBN = line;
                    b.title = read.readLine();
                    b.author = read.readLine();
                    b.status = read.readLine();
                    System.out.println("Title: " + b.title);
                    System.out.println("Author: " + b.author);
                    System.out.println("Status: " + b.status);
                }
            }
            read.close();
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
        return b.ISBN;
    }

    void Search(Book b) {
        int ch;
        System.out.println("Search by:\n1.ISBN\n2.Title\n3.Author");
        ch = in.nextInt();
        in.nextLine();
        switch (ch) {
            case 1: {
                System.out.println("ISBN:");
                ISBN = in.nextLine();
                try {
                    read = new BufferedReader(new FileReader("bookDetail.txt"));
                    String line;
                    while ((line = read.readLine()) != null) {
                        if (line.equals(ISBN)) {
                            for (int i = 0; i < 3; i++) {
                                line = read.readLine();
                                if (line != null) {
                                    System.out.println(line);
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    read.close();
                } catch (IOException e) {
                    System.out.println("IO Error: " + e.getMessage());
                }
                break;
            }
            case 2: {
                System.out.println("Title:");
                title = in.nextLine();
                try {
                    read = new BufferedReader(new FileReader("bookDetail.txt"));
                    String line, previousLine = null;
                    while ((line = read.readLine()) != null) {
                        if (line.equals(title)) {
                            System.out.println(previousLine);
                            System.out.println(line);
                            for (int i = 0; i < 3; i++) {
                                line = read.readLine();
                                if (line != null) {
                                    System.out.println(line);
                                } else {
                                    break;
                                }
                            }
                        }
                        previousLine = line;
                    }
                    read.close();
                } catch (IOException e) {
                    System.out.println("IO Error: " + e.getMessage());
                }
                break;
            }
            case 3: {
                System.out.println("Author:");
                author = in.nextLine();
                try {
                    read = new BufferedReader(new FileReader("bookDetail.txt"));
                    String line, previousLine = null;
                    String previousline2 = null;
                    while ((line = read.readLine()) != null) {
                        if (line.equals(author)) {
                            System.out.println(previousline2);
                            System.out.println(previousLine);
                            System.out.println(line);
                            for (int i = 0; i < 3; i++) {
                                line = read.readLine();
                                if (line != null) {
                                    System.out.println(line);
                                } else {
                                    break;
                                }
                            }
                        }
                        previousline2 = previousLine;
                        previousLine = line;
                    }
                    read.close();
                } catch (IOException e) {
                    System.out.println("IO Error: " + e.getMessage());
                }
                break;
            }
        }
    }

    void ModifyFile(String ogfilename, String ogword, String newword, String ISBNtomodify) {
        File temp = new File("temp.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ogfilename));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
            String line;
            boolean foundISBN = false;
            while ((line = reader.readLine()) != null) {
                if (foundISBN && line.equals(ogword)) {
                    line = newword;
                    foundISBN = false;
                }
                writer.write(line);
                writer.newLine();
                if (line.equals(ISBNtomodify)) {
                    foundISBN = true;
                }
            }
            writer.close();
            reader.close();
            File ogfile = new File(ogfilename);
            if (ogfile.delete()) {
                if (!temp.renameTo(ogfile)) {
                    System.out.println("File renaming failed.");
                }
            }
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }

    void DeleteLine(String ogfilename, String ISBNtodelete) {
        File temp = new File("temp.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ogfilename));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(ISBNtodelete)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            writer.close();
            reader.close();
            File ogfile = new File(ogfilename);
            if (ogfile.delete()) {
                if (!temp.renameTo(ogfile)) {
                    System.out.println("File renaming failed.");
                }
            }
        } catch (IOException e) {
            System.out.println("IO Error");
        }
    }

    void Borrow(Member m, Book b) {
        System.out.println("Enter library number:");
        lib_no = in.nextLine();
        String filename = lib_no + ".txt";
        m.Read(filename);
        System.out.println("Enter book ISBN to be borrowed:");
        String searchedISBN = SearchByISBN(b);
        if (b.status.equals("Available")) {
            try {
                BufferedWriter f = new BufferedWriter(new FileWriter(filename, true));
                borrow = LocalDate.now();
                due = borrow.plusDays(1);
                String bookDetail = b.ISBN + " " + b.title + " " + b.author + " Borrowed on: " + borrow + ", Due on: " + due;
                m.dues.add(bookDetail);
                f.write(bookDetail);
                f.newLine();
                f.close();
                ModifyFile("bookDetail.txt", "Available", "Borrowed", b.ISBN);
                b.status = "Borrowed";
                System.out.println("Book borrowed successfully.");
            } catch (IOException e) {
                System.out.println("IO Error: " + e.getMessage());
            }
        } else {
            System.out.println("Book is not available for borrowing.");
        }
    }

    void ViewMemberDetails(Member m) {
        System.out.println("Enter library number:");
        lib_no = in.nextLine();
        String filename = lib_no + ".txt";
        File memberFile = new File(filename);
        if (memberFile.exists()) {
            UpdateMemberFileWithFineAndOverdue(m, filename);
            m.Read(filename);
        } else {
            System.out.println("No member found with this library number.");
        }
    }

    LocalDate Return(Member m, Book b) {
        System.out.println("Enter library number:");
        lib_no = in.nextLine();
        String filename = lib_no + ".txt";
        m.Read(filename);
        System.out.println("Enter book ISBN to be returned:");
        String searchedISBN = SearchByISBN(b);
        if (b.status.equals("Borrowed")) {
            returndate = LocalDate.now();
            UpdateMemberFileWithFineAndOverdue(m, filename);
            DeleteSpecificBook(filename, b.ISBN);
            ModifyFile("bookDetail.txt", "Borrowed", "Available", b.ISBN);
            System.out.println("Book returned successfully.");
            b.status = "Available";

            String returnMessage = "Thank you for returning the book with ISBN " + b.ISBN + ".";
            String subject = "Book Returned";

            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line;
                String phoneNumber = null;
                String email = null;
                int lineCount = 0;

                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    if (lineCount == 3) {
                        phoneNumber = line;   // line 2 has phone
                    }
                    if (lineCount == 4) {
                        email = line;         // line 3 has email
                    }

                }
                reader.close();
                
                smsSender.getDetails(phoneNumber, returnMessage);
                emailSender.sendEmail(email, subject, returnMessage);

            } catch (IOException e) {
                System.out.println("IO Error: " + e.getMessage());
            }
        } else {
            System.out.println("This book is not currently marked as borrowed.");
        }
        return returndate;
    }

    void DeleteSpecificBook(String ogfilename, String ISBNtodelete) {
        File temp = new File("temp.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ogfilename));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!(line.startsWith(ISBNtodelete))) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();
            File ogfile = new File(ogfilename);
            if (ogfile.delete()) {
                if (!temp.renameTo(ogfile)) {
                    System.out.println("File renaming failed.");
                }
            }
        } catch (IOException e) {
            System.out.println("IO Error");
        }
    }

    String CheckOverdue(LocalDate due, String phoneNumber, String email) {
        if (LocalDate.now().isAfter(due)) {
            String message = "Your book with ISBN " + ISBN + " is overdue. Please return it and pay a fine of " + CalculateFine(due);
            String subject = "Overdue Book Notice";
            smsSender.getDetails(phoneNumber, message);
            try {
                emailSender.sendEmail(email, subject, message);
            } catch (IOException e) {
                System.out.println("Error sending overdue email: " + e.getMessage());
            }
            return "Overdue";
        } else {
            return "Not overdue";
        }
    }

    void UpdateMemberFileWithFineAndOverdue(Member m, String filename) {
        File temp = new File("temp.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
            String line;
            String phoneNumber = null;
            String email = null;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount == 3) {
                    phoneNumber = line;
                }
                if (lineCount == 4) {
                    email = line;
                }
                if (line.contains("Borrowed on:")) {
                    String[] details = line.split(", Due on: ");
                    LocalDate dueDate = LocalDate.parse(details[1].split(",")[0].trim());
                    String overdueStatus = CheckOverdue(dueDate, phoneNumber, email);
                    double fine = CalculateFine(dueDate);
                    line = details[0] + ", Due on: " + dueDate + ", " + overdueStatus + ", Fine: ₹" + fine;
                }
                writer.write(line);
                writer.newLine();
            }
            reader.close();
            writer.close();
            File ogfile = new File(filename);
            if (ogfile.delete()) {
                if (!temp.renameTo(ogfile)) {
                    System.out.println("File renaming failed.");
                }
            }
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        }
    }

    double CalculateFine(LocalDate dueDate) {
        long daysOverdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        return daysOverdue * DAILY_FINE;
    }
}
