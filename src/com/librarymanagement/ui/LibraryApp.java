package com.librarymanagement.ui;

import com.librarymanagement.business.Book;
import com.librarymanagement.business.LibraryService;
import com.librarymanagement.business.Member;
import com.librarymanagement.exceptions.BookNotAvailableException;
import com.librarymanagement.exceptions.BookNotBorrowedException;

import java.util.Scanner;

public class LibraryApp {
    private LibraryService libraryService;
    private Scanner scanner;

    public LibraryApp(){
        libraryService = new LibraryService();
        scanner = new Scanner(System.in);
    }

    public void mainMenu(){
        while(true){
            System.out.println("Enter the choice you want to make \n");
            System.out.println("1. Add Book\n");
            System.out.println("2. Add Member \n");
            System.out.println("3. Borrow Book\n");
            System.out.println("4. Return Book\n");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    addBook();
                    break;

                case 2:
                    addMember();
                    break;

                case 3:
                    borrowBook();
                    break;

                case 4:
                    returnBook();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid option! Please try again!");
            }
        }
    }

    private void addBook(){
        System.out.println("Enter the book ID");
        int bookId = scanner.nextInt();
        System.out.println("Enter the book name");
        String bookName = scanner.nextLine();
        System.out.println("Enter the author name:");
        String authorName = scanner.nextLine();

        Book book = new Book(bookId,bookName,authorName);
        libraryService.addBookToDb(book);
        System.out.println("Book added successfully!");
    }

    private void addMember(){
        System.out.println("Enter the member ID");
        int memberId = scanner.nextInt();
        System.out.println("Enter the member name");
        String memberName = scanner.nextLine();

        Member member = new Member(memberId,memberName);
        libraryService.addMemberToDb(member);
        System.out.println("Member added successfully!");
    }

    public void borrowBook(){
        System.out.println("Enter the member ID");
        int memberId = scanner.nextInt();
        System.out.println("Enter the book ID");
        int bookId = scanner.nextInt();
        try {
            libraryService.borrowBook(bookId,memberId);
            System.out.println("Book borrowed successfully!");
        } catch (BookNotAvailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnBook(){
        System.out.println("Enter the member ID");
        int memberId = scanner.nextInt();
        System.out.println("Enter the book ID");
        int bookId = scanner.nextInt();
        try{
            libraryService.returnBook(bookId,memberId);
            System.out.println("Book returned successfully!");
        } catch (BookNotBorrowedException e) {
            throw new RuntimeException(e);
        }
    }
}
