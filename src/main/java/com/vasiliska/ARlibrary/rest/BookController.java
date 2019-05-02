package com.vasiliska.ARlibrary.rest;

import com.vasiliska.ARlibrary.domain.Book;
import com.vasiliska.ARlibrary.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {


    private final BookServiceImpl bookService;

    @Autowired
    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = bookService.showAllBooks();
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/edit/{bookName}")
    public String editPage(@PathVariable("bookName") String bookName, Model model) {
        Book book = bookService.bookByName(bookName);
        model.addAttribute("book", book);
        return "edit";
    }

    //
    @PostMapping("/update")
    public String updatePage(@RequestParam(name = "bookName") String bookName,
                             @RequestParam(name = "bookId") Long bookId,
                             Model model) {
        bookService.updateBookNameById(bookId, bookName);
        return "redirect:/";
    }

    @PostMapping("/add")
    public String add(@RequestParam(name = "bookName") String bookName,
                      @RequestParam(name = "authors") String authors,
                      @RequestParam(name = "genres") String genres,
                      Model model) {
        bookService.addNewBook(bookName, authors, genres);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String add(Book book) {
        return "newbook";
    }

    @PostMapping("/delete/{bookName}")
    public String delete(@PathVariable("bookName") String bookName, Model model) {
        bookService.delBook(bookName);
        return "redirect:/";
    }
}
