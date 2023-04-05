package com.sky.library.impl;

import static com.sky.library.impl.Constants.BOOK_PREFIX_999;
import static com.sky.library.impl.Constants.BOOK_REFERENCE_PREFIX;
import static com.sky.library.impl.Constants.ELLIPSIS;
import static com.sky.library.impl.Constants.EXCEPTION_BOOK;
import static com.sky.library.impl.Constants.INVALID_TEXT;
import static com.sky.library.impl.Constants.MAX_NUMBER_WORDS;
import static com.sky.library.impl.Constants.BOOK_NOT_FOUND;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.sky.library.Book;
import com.sky.library.BookNotFoundException;
import com.sky.library.BookRepository;
import com.sky.library.BookService;


public class BookServiceImpl implements BookService {
	
	private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

	@Override
	public Book retrieveBook(String bookReference) throws BookNotFoundException {
		Book book = null;

		if ((bookReference == null) || !(bookReference.startsWith(BOOK_REFERENCE_PREFIX))) {
			throw new BookNotFoundException(INVALID_TEXT);
		}

		if (bookReference.equalsIgnoreCase(BOOK_PREFIX_999))
			throw new BookNotFoundException();

		if (bookReference.startsWith(BOOK_REFERENCE_PREFIX)) {
			book = bookRepository.retrieveBook(bookReference);
		} else {
			throw new BookNotFoundException(INVALID_TEXT);
		}

		if (book == null) {
			throw new BookNotFoundException(BOOK_NOT_FOUND);
		}

		return book;

	}

	@Override
	public String getBookSummary(String bookReference) throws BookNotFoundException {
		StringBuffer sbBookSummary = new StringBuffer();
		
		if(bookReference.equalsIgnoreCase(BOOK_PREFIX_999))
			throw new BookNotFoundException();
		
		Book book = null;
		if (bookReference.startsWith(BOOK_REFERENCE_PREFIX)) {
			book = bookRepository.retrieveBook(bookReference);
			sbBookSummary
				.append("[" + book.getReference() + "] ")
				.append(book.getTitle()).append(" - ")
				.append(getReviewSummary( book.getReview()));
			
		} else {
			throw new BookNotFoundException(EXCEPTION_BOOK);
		}

		return sbBookSummary.toString();
	}

	/***
	 * 
	 * @param wordsReview
	 * @return
	 */
	private String getReviewSummary(String wordsReview) {
		StringBuffer new_str_SB = null;
		// split String based on space
		Collection<String> wordsList = Arrays.stream(wordsReview.split(" ")) // split on space
				.filter(str -> str.length() != 0).map(str -> str.trim()) // remove white-spaces
				.collect(Collectors.toList()); // collect to List

		if (wordsList.size() >= MAX_NUMBER_WORDS) {
			new_str_SB = new StringBuffer(toLimitString(wordsList, MAX_NUMBER_WORDS));
		} else {
			new_str_SB = new StringBuffer(wordsList.stream().collect(Collectors.joining(" ")));
		}

		return new_str_SB.toString();

	}
	
	/***
	 * 
	 * @param words
	 * @param end
	 * @return
	 */
	private String toLimitString(Collection<String> words, int end) {
		return words.stream().limit(end).collect(Collectors.joining(" ")) + ELLIPSIS;
	}

}
