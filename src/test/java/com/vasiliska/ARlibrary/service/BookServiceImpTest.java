package com.vasiliska.ARlibrary.service;

import com.vasiliska.ARlibrary.repository.BookRep;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceImpTest {

    @MockBean
      private BookRep bookRep;

      @Autowired
      BookServiceImpl bookServiceImpl;

      private final String TEST_BOOK_NAME = "Айвенго";
      private final String TEST_AUTHOR = "В.Скотт";
      private final String TEST_GENRE = "Роман";
      private final String TEST_COMMENT = "Книга супер!";

      @Test
      public void addNewBook() {
          assertTrue(bookServiceImpl.addNewBook(TEST_BOOK_NAME, TEST_AUTHOR, TEST_GENRE).contains(TEST_BOOK_NAME));
      }

      @Test
      public void bookByGenre() {
          bookServiceImpl.bookByGenre(TEST_GENRE);
          verify(bookRep).getBookByGenre(TEST_GENRE);
      }

      @Test
      public void bookByName() {
          bookServiceImpl.bookByName(TEST_BOOK_NAME);
          verify(bookRep).getBookByName(TEST_BOOK_NAME);
      }

      @Test
      public void bookByAuthor() {
          bookServiceImpl.bookByAuthor(TEST_AUTHOR);
          verify(bookRep).getBookByAuthor(TEST_AUTHOR);
      }

      @Test
      public void showAllBooks() {
          bookServiceImpl.showAllBooks();
          verify(bookRep).findAll();
      }

      @Test
      public void addComment() {
          bookServiceImpl.addComment(TEST_COMMENT, TEST_BOOK_NAME);
          verify(bookRep).getBookByName(TEST_BOOK_NAME);
      }

      @Test
      public void getCommentByBook() {
          bookServiceImpl.getCommentsByBook(TEST_BOOK_NAME);
          verify(bookRep).getBookByName(TEST_BOOK_NAME);
      }

}
