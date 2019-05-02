package com.vasiliska.ARlibrary.rest;


import com.vasiliska.ARlibrary.domain.Author;
import com.vasiliska.ARlibrary.domain.Book;
import com.vasiliska.ARlibrary.domain.Genre;
import lombok.Data;

@Data
public class BookDto {

    private Long bookId;
    private String bookName;
    private Author author;
    private Genre genre;

    private String authorName;
    private String genreName;

    public BookDto() {
    }

    public BookDto(long id, String bookName, Author author, Genre genre) {
        this.bookId = id;
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
    }

    public BookDto(long id, String bookName) {
        this.bookId = id;
        this.bookName = bookName;
    }

    public BookDto(Long bookId, String bookName, String authorName, String genreName) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.genreName = genreName;
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book.getBookId(), book.getBookName(), book.getAuthor(), book.getGenre());
    }
}
