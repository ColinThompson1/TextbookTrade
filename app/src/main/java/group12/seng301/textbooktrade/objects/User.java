package group12.seng301.textbooktrade.objects;

import java.util.ArrayList;

import group12.seng301.textbooktrade.RegisterActivity;


public class User {

    private String email, fullname, password;
    private RegisterActivity.Major major;
    private ArrayList<Book> books;

    public User(String email, String fullname, RegisterActivity.Major major, String password) {
        this(email, fullname, major);
        this.password = password;
    }

    public User(String email, String fullname, RegisterActivity.Major major) {
        this.email = email;
        this.fullname = fullname;
        this.major = major;
        this.password = password;
    }

    public User(User user) {
        this.email = user.getEmail();
        this.fullname = user.getFullname();
        this.major = user.getMajor();
        this.books = new ArrayList<Book>(books);
    }

    public RegisterActivity.Major getMajor() {
        return major;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setMajor(RegisterActivity.Major major) {
        this.major = major;
    }

    public ArrayList<Book> getBooks() {
        return new ArrayList<Book>(books);
    }

    public void addBook(Book book) {
        books.add(new Book(book));
    }

    public void setBooks(ArrayList<Book> books) {
        books = new ArrayList<Book>(books);
    }

    public boolean hasBooks() {
        return !books.isEmpty();
    }



}
