package com.crtv.creativetechnocollege;

public class BookDetails {
    private String issueDate;
    private String studentId;
    private String bookName;
    private String bookId;
    private String bookEdition;
    private String bookCategory;
    private String bookPublisher;

    public BookDetails(String issueDate, String studentId, String bookName,
                       String bookId, String bookEdition, String bookCategory, String bookPublisher) {
        this.issueDate = issueDate;
        this.studentId = studentId;
        this.bookName = bookName;
        this.bookId = bookId;
        this.bookEdition = bookEdition;
        this.bookCategory = bookCategory;
        this.bookPublisher = bookPublisher;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }
}
