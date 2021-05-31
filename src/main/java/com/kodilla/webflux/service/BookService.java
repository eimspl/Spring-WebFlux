package com.kodilla.webflux.service;

import com.kodilla.webflux.dto.BookDto;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class BookService {
    private final Logger logger = getLogger(BookService.class);

    public List<BookDto> getBooks() {
        return readBooksFromCsv();
    }

    private List<BookDto> readBooksFromCsv() {
        List<BookDto> books = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("books.csv");
        try (
                InputStream inputStream = resource.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(streamReader)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                BookDto book = new BookDto(values[0], values[1], Integer.parseInt(values[2]));
                books.add(book);
            }
        } catch (IOException ex) {
            logger.error("Cannot read books.csv. ", ex);
        }
        return books;
    }
}
