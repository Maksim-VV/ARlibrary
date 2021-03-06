package com.vasiliska.ARlibrary.service;


import com.vasiliska.ARlibrary.domain.Author;
import com.vasiliska.ARlibrary.domain.Book;
import com.vasiliska.ARlibrary.domain.Comment;
import com.vasiliska.ARlibrary.domain.Genre;
import com.vasiliska.ARlibrary.repository.AuthorRep;
import com.vasiliska.ARlibrary.repository.BookRep;
import com.vasiliska.ARlibrary.repository.CommentRep;
import com.vasiliska.ARlibrary.repository.GenreRep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private BookRep bookRep;
    private GenreRep genreRep;
    private AuthorRep authorRep;
    private CommentRep commentRep;

    private final String MSG_DONT_FIND = "Объект не найден!";
    private final String MSG_ADD_NEW_BOOK = "Книга \"%s\" добавлена";
    private final String MSG_ADD_NEW_COMMENT = "Комментарий на книгу \"%s\" добавлен";
    private final String MSG_DELETE_BOOK = "Книга \"%s\" удалена из библиотеки.";
    private final String MSG_DONT_ADD_BOOK = "Не удалось добавить книгу \"%s\"";


    @Autowired
    public BookServiceImpl(BookRep bookRep, GenreRep genreRep, AuthorRep authorRep, CommentRep commentRep) {
        this.authorRep = authorRep;
        this.genreRep = genreRep;
        this.bookRep = bookRep;
        this.commentRep = commentRep;
    }

    @Override
    @Transactional
    public String addNewBook(String bookName, String authorName, String genreName) {
        Genre genre = genreRep.getGenreByName(genreName);
        if (genre == null) {
            Genre genreNew = new Genre(genreName);
            genre = genreRep.save(genreNew);
        }

        Book book = new Book();
        book.setBookName(bookName);
        book.setGenre(genre);

        Author author = authorRep.getAuthorByName(authorName);
        if (author == null) {
            Author authorNew = new Author(authorName);
            author = authorRep.save(authorNew);
        }
        book.setAuthor(author);
        if (bookRep.save(book) == null) {
            return String.format(MSG_DONT_ADD_BOOK, bookName);
        }
        return String.format(MSG_ADD_NEW_BOOK, bookName);
    }

    @Override
    @Transactional
    public String delBook(String bookName) {
        Book book = bookRep.getBookByName(bookName);
        if (book == null) {
            return MSG_DONT_FIND;
        }
        String authorName = book.getAuthor().getAuthorName();
        List<Book> listBookByAuthor = bookRep.getBookByAuthor(authorName);
        if (listBookByAuthor.size() == 1) {
            authorRep.delete(listBookByAuthor.get(0).getAuthor());
        }

        String genreName = book.getGenre().getGenreName();
        List<Book> listBookByGenre = bookRep.getBookByGenre(genreName);
        if (listBookByGenre.size() == 1) {
            genreRep.delete(listBookByGenre.get(0).getGenre());
        }

        bookRep.delete(book);
        return String.format(MSG_DELETE_BOOK, bookName);
    }

    @Override
    public boolean updateBookNameById(Long bookId, String bookName) {
        if (bookRep.updateBookNameById(bookId, bookName) > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public String bookByGenre(String genreName) {
        return showBooks(bookRep.getBookByGenre(genreName));
    }

    @Override
    @Transactional(readOnly = true)
    public Book bookByName(String bookName) {
        return bookRep.getBookByName(bookName);
    }

    @Override
    @Transactional(readOnly = true)
    public String bookByAuthor(String authorName) {
        return showBooks(bookRep.getBookByAuthor(authorName));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> showAllBooks() {
        return bookRep.findAll();
    }

    @Override
    @Transactional
    public String addComment(String commentText, String bookName) {
        Book book = bookRep.getBookByName(bookName);
        if (book == null) {
            return MSG_DONT_FIND;
        }
        commentRep.save(new Comment(commentText, book));
        return String.format(MSG_ADD_NEW_COMMENT, bookName);
    }

    @Override
    @Transactional(readOnly = true)
    public String getCommentsByBook(String bookName) {
        Book book = bookRep.getBookByName(bookName);
        if (book == null) {
            return MSG_DONT_FIND;
        }
        return showComments(commentRep.getCommentByBook(book.getBookId()));
    }

    private String showBooks(List<Book> listBooks) {
        if (listBooks == null || listBooks.isEmpty()) {
            return MSG_DONT_FIND;
        }
        StringBuffer stringBuffer = new StringBuffer();
        listBooks.forEach(stringBuffer::append);
        return stringBuffer.toString();
    }

    private String showComments(List<Comment> listComments) {
        if (listComments == null || listComments.isEmpty()) {
            return MSG_DONT_FIND;
        }
        StringBuffer stringBuffer = new StringBuffer();
        listComments.forEach(stringBuffer::append);
        return stringBuffer.toString();
    }
}


