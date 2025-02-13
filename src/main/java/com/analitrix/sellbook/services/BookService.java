package com.analitrix.sellbook.services;

import java.util.*;

import com.analitrix.sellbook.enums.SortEnum;
import com.analitrix.sellbook.dtos.book.*;
import com.analitrix.sellbook.dtos.common.ResponseHttp;
import com.analitrix.sellbook.models.Book;
import com.analitrix.sellbook.models.Category;
import com.analitrix.sellbook.repositories.BookRepository;
import com.analitrix.sellbook.repositories.CategoryRepository;
import com.analitrix.sellbook.specifications.BookSpecifications;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	ModelMapper modelMapper = new ModelMapper();

	public ResponseEntity<ResponseHttp> create(BookCreateDto bookCreateDto) {
		Optional<Category> categoryOptional = categoryRepository.findById(bookCreateDto.getCategoryId());
		if (categoryOptional.isEmpty())
			return new ResponseEntity<>(new ResponseHttp(204, "Categoria no encontrada"), HttpStatus.NO_CONTENT);
		if (bookRepository.findByIsxn(bookCreateDto.getIsxn()).isPresent())
			return new ResponseEntity<>(new ResponseHttp(406, "El libro con el isxn: " + bookCreateDto.getIsxn() + ", ya existe."), HttpStatus.CONFLICT);
		Book book = modelMapper.map(bookCreateDto, Book.class);
		book.setId(UUID.randomUUID().toString());
		book.setCategory(categoryOptional.get());
		bookRepository.save(book);
		return new ResponseEntity<>(new ResponseHttp(201, "Libro: " + bookCreateDto.getTitle() + ", Creado correctamente."), HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseHttp> createMany(List<BookCreateDto> booksPostDto) {
		List<Book> books = new ArrayList<>();
		for (BookCreateDto bookCreateDto :booksPostDto){
			Optional<Category> categoryOptional = categoryRepository.findById(bookCreateDto.getCategoryId());
			if (categoryOptional.isEmpty())
				return new ResponseEntity<>(new ResponseHttp(204, "Categoria no encontrada"), HttpStatus.NO_CONTENT);
			if (bookRepository.findByIsxn(bookCreateDto.getIsxn()).isPresent())
				return new ResponseEntity<>(new ResponseHttp(406, "El libro con el isxn: " + bookCreateDto.getIsxn() + ", ya existe."), HttpStatus.CONFLICT);
			Book book = modelMapper.map(bookCreateDto, Book.class);
			book.setId(UUID.randomUUID().toString());
			book.setCategory(categoryOptional.get());
			books.add(book);
		}
		bookRepository.saveAll(books);
		return new ResponseEntity<>(new ResponseHttp(201, "Libros creados correctamente."), HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseHttp> findOne(String id) {
		Optional<Book> optionalBook = bookRepository.findById(id);
		if (optionalBook.isPresent()) {
			BookResponseDto bookResponseDto = modelMapper.map(optionalBook.get(), BookResponseDto.class);
			return new ResponseEntity<>(new ResponseHttp(200, bookResponseDto), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseHttp(404,"No existe libro con el id: "+id+"."),HttpStatus.NOT_FOUND);
		}
	}

	public Page<Book> findMany(BookRequestDto request) {
		Sort sort = null;
		if(request.getSort().equals(SortEnum.ASC)) {
			sort = Sort.by(Sort.Order.asc(request.getSortableColumn().toString()));
		}else if(request.getSort().equals(SortEnum.DESC)){
			sort = Sort.by(Sort.Order.desc(request.getSortableColumn().toString()));
		}
		Specification<Book> spec = BookSpecifications.filterBy(request.getIsxn(),request.getTitle(), request.getAuthor(), request.getEditorial(), request.getCategory());
		Pageable pageable= PageRequest.of(request.getOffset(), request.getLimit(),sort);
		return bookRepository.findAll(spec, pageable);
	}

	public ResponseEntity<ResponseHttp> update(String id, BookUpdateDto bookUpdateDto) {
		Optional<Book> optionalBook = bookRepository.findById(id);
		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			BookUpdateDto bookDto = modelMapper.map(book, BookUpdateDto.class);
			if(bookUpdateDto.toString().equals(bookDto.toString())){
				return new ResponseEntity<>(new ResponseHttp(305,"No hay cambios para el libro: "+ bookUpdateDto.getTitle()+"."),HttpStatus.OK);
			}
			if(bookUpdateDto.getIsxn()!=null){
				book.setIsxn(bookUpdateDto.getIsxn());
			}if(bookUpdateDto.getTitle()!=null){
				book.setTitle(bookUpdateDto.getTitle());
			} if(bookUpdateDto.getAuthor()!=null){
				book.setAuthor(bookUpdateDto.getAuthor());
			} if(bookUpdateDto.getEditorial()!=null){
				book.setEditorial(bookUpdateDto.getEditorial());
			} if(bookUpdateDto.getPublicationDate()!=null){
				book.setPublicationDate(bookUpdateDto.getPublicationDate());
			} if(bookUpdateDto.getUnits()!=null){
				book.setUnits(bookUpdateDto.getUnits());
			} if(bookUpdateDto.getCost()!=null){
				book.setCost(bookUpdateDto.getCost());
			} if(bookUpdateDto.getImage()!=null){
				book.setImage(bookUpdateDto.getImage());
			} if(bookUpdateDto.getCategoryId()!=null){
				Optional<Category> categoryOptional = categoryRepository.findById(bookUpdateDto.getCategoryId());
				if (categoryOptional.isEmpty())
					return new ResponseEntity<>(new ResponseHttp(204, "Categoria no encontrada"), HttpStatus.NO_CONTENT);
				book.setCategory(categoryOptional.get());
			}
			book.setAvailability();
			book.modify();
			bookRepository.save(book);
			return new ResponseEntity<>(new ResponseHttp(200,"Libro: "+ bookUpdateDto.getTitle()+", actualizado con exito."), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseHttp(404,"Libro con el id: "+id+", no existe."), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseHttp> delete(String id) {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isPresent()) {
			bookRepository.deleteById(id);
			return new ResponseEntity<>(new ResponseHttp(200,"libro: "+book.get().getTitle()+", eliminado con exito"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseHttp(404,"Libro con el id: "+id+", no existe."), HttpStatus.NOT_FOUND);
		}
	}
}
