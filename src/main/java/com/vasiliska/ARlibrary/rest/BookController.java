package com.vasiliska.ARlibrary.rest;

import com.vasiliska.ARlibrary.domain.Book;
import com.vasiliska.ARlibrary.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {


    private final BookServiceImpl bookService;

    @Autowired
    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<BookDto> getAll() {
        return bookService.showAllBooks().stream().map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/books/{bookName}")
    public BookDto getById(@PathVariable("bookName") String bookName) {
        return BookDto.toDto(bookService.bookByName(bookName));
    }

    @DeleteMapping("/books/{bookName}")
    public void deleteById(
            @PathVariable("bookName") String bookName) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        bookService.delBook(bookName);
    }

    @PostMapping("/books")
    public void save(@RequestBody BookDto bookDto) {

        System.out.println(bookDto.getBookName());
        System.out.println(bookDto.getAuthorName());
        System.out.println(bookDto.getAuthorName());

        bookService.addNewBook(bookDto.getBookName(), bookDto.getAuthorName(), bookDto.getAuthorName());
    }

    @PutMapping("/books/{id}")
    public void update(@PathVariable("bookId") Long bookId, @RequestBody Book book) {
        bookService.updateBookNameById(bookId, book.getBookName());
    }


//    @PostMapping("/update")
//    public String updatePage(@RequestParam(name = "bookName") String bookName,
//                             @RequestParam(name = "bookId") Long bookId,
//                             Model model) {
//        bookService.updateBookNameById(bookId, bookName);
//        return "redirect:/";
//    }
//
//    @PostMapping("/add")
//    public String add(@RequestParam(name = "bookName") String bookName,
//                      @RequestParam(name = "authors") String authors,
//                      @RequestParam(name = "genres") String genres,
//                      Model model) {
//        bookService.addNewBook(bookName, authors, genres);
//        return "redirect:/";
//    }
//
//    @GetMapping("/add")
//    public String add(Book book) {
//        return "newbook";
//    }
//
//    @PostMapping("/delete/{bookName}")
//    public String delete(@PathVariable("bookName") String bookName, Model model) {
//        bookService.delBook(bookName);
//        return "redirect:/";
//    }
}
