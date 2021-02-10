package Models;

public class Book {
    private String name;
    private String surname;
    private String bookName;
    private int year;
    private String annotation;

    public Book(String name, String surname, String bookName, int year, String annotation) {
        this.name = name;
        this.surname = surname;
        this.bookName = bookName;
        this.year = year;
        this.annotation = annotation;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
