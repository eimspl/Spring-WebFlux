package com.kodilla.webflux.controller;

import com.kodilla.webflux.dto.BookDto;
import com.kodilla.webflux.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxController {

    @Autowired
    BookService bookService;

    @GetMapping(value = "/books", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<BookDto> getBooks() {
        return Flux
                .just(bookService.getBooks().toArray(BookDto[]::new))
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

}