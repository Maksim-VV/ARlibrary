package com.vasiliska.ARlibrary.rest;

import com.vasiliska.ARlibrary.domain.Book;
import com.vasiliska.ARlibrary.service.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookServiceImpl bookService;

    @Test
    public void listPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("books"));
        verify(bookService).showAllBooks();
    }


    @Test
    public void delete() throws Exception {
        given(this.bookService.showAllBooks()).willReturn(singletonList(new Book()));
        this.mvc.perform(post("/delete/newBook"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }

}