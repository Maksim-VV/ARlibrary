package com.vasiliska.ARlibrary.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasiliska.ARlibrary.domain.Author;
import com.vasiliska.ARlibrary.domain.Book;
import com.vasiliska.ARlibrary.domain.Genre;
import com.vasiliska.ARlibrary.service.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookServiceImpl bookService;

    private Author author;
    private Genre genre;
    private Book testBook;

    private final String TEST_BOOK_NAME = "Му-му";
    private final String TEST_AUTHOR = "Тургенев";
    private final String TEST_GENRE = "Драма";

    @Before
    public void setUp() throws Exception {
        createTestBook();
    }


    @Test
    public void addNewBook() throws Exception {
        BookDto bookDto = new BookDto(1L, TEST_BOOK_NAME, null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post("/api/v1/books")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isOk());
        verify(bookService, times(1))
                .addNewBook(bookDto.getBookName(), bookDto.getAuthorName(), bookDto.getGenreName());
    }

    @Test
    public void getBookById() throws Exception {
        when(bookService.getBookByBookId(1L)).thenReturn(TEST_BOOK_NAME);
        when(bookService.bookByName(TEST_BOOK_NAME)).thenReturn(testBook);
        mvc.perform(get("/api/v1/books/1")).andExpect(status().isOk());
        verify(bookService, times(1)).getBookByBookId(1L);
    }


    @Test
    public void deleteById() throws Exception {
        this.mvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isOk());
        verify(bookService, times(1))
                .delBook(bookService.getBookByBookId(1L));
    }

    private void createTestBook() {

        author = new Author(TEST_AUTHOR);
        author.setAuthorId(1L);

        genre = new Genre(TEST_GENRE);
        genre.setGenreId(1L);

        testBook = new Book(TEST_BOOK_NAME, author, genre);
        testBook.setBookId(1L);
    }


}