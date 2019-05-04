package com.vasiliska.ARlibrary.rest;

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

    @GetMapping("/api/v1/books")
    public List<BookDto> getAll() {
        return bookService.showAllBooks().stream().map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v1/books/{bookId}")
    public BookDto getById(@PathVariable("bookId") Long bookId) {
        return BookDto.toDto(bookService.bookByName(bookService.getBookByBookId(bookId)));
    }

    @DeleteMapping("/api/v1/books/{bookId}")
    public void deleteById(@PathVariable("bookId") Long bookId) {
        bookService.delBook(bookService.getBookByBookId(bookId));
    }

    @PostMapping("/api/v1/books")
    public void save(@RequestBody BookDto bookDto) {
        bookService.addNewBook(bookDto.getBookName(), bookDto.getAuthorName(), bookDto.getGenreName());
    }

    @PutMapping("/api/v1/books/{bookId}")
    public void update(@PathVariable("bookId") Long bookId, @RequestBody BookDto bookDto) {
        bookService.updateBookNameById(bookId, bookDto.getBookName());
    }

}
