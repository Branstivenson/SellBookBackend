package com.analitrix.sellbook.dtos.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateDto {
    private Long isxn;
    private String title;
    private Long publicationDate;
    private Long units;
    private String editorial;
    private Long cost;
    private String author;
    private String image;
    private String categoryId;
}
