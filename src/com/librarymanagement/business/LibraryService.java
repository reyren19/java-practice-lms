package com.librarymanagement.business;

import com.librarymanagement.exceptions.BookNotAvailableException;
import com.librarymanagement.exceptions.BookNotBorrowedException;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private List<Book> books;
    private List<Member> members;

    public LibraryService(){
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addBookToDb(Book book){
        books.add(book);
    }

    public void removeBookFromDb(Book book){
        books.remove(book);
    }

    public void addMemberToDb(Member member){
        members.add(member);
    }

    public void borrowBook(int bookId, int memberId) throws BookNotAvailableException {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);

        if(book != null && member != null){
            if(!book.isAvailable()){
                throw new BookNotAvailableException("Book is currently not available");
            }
            book.setAvailable(false);
            member.borrowBook(book);
        }
    }

    public void returnBook(int bookId, int memberId) throws BookNotBorrowedException {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);

        if(book != null && member != null){
            if(!member.getBorrowedBooks().contains(book)){
                throw new BookNotBorrowedException("Book not borrowed by this member!");
            }
            book.setAvailable(true);
            member.returnBook(book);
        }
    }

    // Updated findBookById for integer IDs
    private Book findBookById(int bookId) {
        return books.stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);
    }

    // Updated findMemberById for integer IDs
    private Member findMemberById(int memberId) {
        return members.stream().filter(m -> m.getId() == memberId).findFirst().orElse(null);
    }
}
