package com.sky.library.impl;

import static com.sky.library.impl.Constants.ELLIPSIS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sky.library.Book;
import com.sky.library.BookNotFoundException;
import com.sky.library.BookRepositoryStub;
import com.sky.library.BookService;

/***
 * 
 * @author maxp7
 *
 */
public class BookServiceImplTest {

	
	private BookService bookService = null;
	
	private static final String THE_GRUFFALO_REFERENCE = "BOOK-GRUFF472";
	private static final String WINNIE_THE_POOH_REFERENCE = "BOOK-POOH222";
	private static final String THE_WIND_IN_THE_WILLOWS_REFERENCE = "BOOK-WILL987";
	
	private String summary = "";
	private static String bookReference = "";
	private Book book = null;
	 
	@BeforeEach
	void setUp() throws Exception {
		bookService = new BookServiceImpl(new BookRepositoryStub());
	}
	
	@Test
	public void testRetrieveBook() throws BookNotFoundException {
		bookService = new BookServiceImpl(new BookRepositoryStub());
		 
		String bookReference = "BOOK-WILL987";
		Book book = bookService.retrieveBook(bookReference);
		String expectedReference = "BOOK-WILL987";
		String expectedTitle = "The Wind In The Willows";
		
		Assert.assertNotNull(book);
		Assert.assertTrue(book.getReference().equalsIgnoreCase(expectedReference));
		Assert.assertTrue(book.getTitle().equalsIgnoreCase(expectedTitle));
	}
	
	@Test
	public void testRetrieveBookGRUFF472() throws BookNotFoundException {
		bookService = new BookServiceImpl(new BookRepositoryStub());
		
		String bookReference = "BOOK-GRUFF472";
		Book book = bookService.retrieveBook(bookReference);
		String expectedReference = "BOOK-GRUFF472";
		
		Assert.assertNotNull(book);
		Assert.assertTrue(book.getReference().equalsIgnoreCase(expectedReference));
		
	}
	
	@Test
	@DisplayName("retrieveBook: Invalid Text - Book reference must begin with BOOK-")
	public void testRetrieveBook_Ex() throws BookNotFoundException {
		
		Exception exception = assertThrows(BookNotFoundException.class, () -> {
			 bookReference = "INVALID-TEXT";
			 book = bookService.retrieveBook(bookReference);
		});
		
		Assert.assertNull(book);
		String expectedMessage = Constants.INVALID_TEXT;
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
		
	}

	@DisplayName("retrieveBook: Invalid Text - Book reference must begin with BOOK-")
	@Test
	void testRetrieveBookInvalidTextException_InvalidTest() {
	
		Exception exception = assertThrows(BookNotFoundException.class, () -> {
			bookReference = "abc";
			book = bookService.retrieveBook(bookReference);
		});
		
		Assert.assertNull(book);
		String expectedMessage = Constants.INVALID_TEXT;
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
		
	}

	@DisplayName("retrieveBook: Book not found exception")
    @Test
    public void testRetrieveBookNotFoundException() {
           
            Exception exception = assertThrows(BookNotFoundException.class, () -> {
            	 bookReference = "BOOK-abc";
                 book = bookService.retrieveBook(bookReference);
    		});
            
            Assert.assertNull(book);
            
            String expectedMessage = Constants.BOOK_NOT_FOUND;
    		String actualMessage = exception.getMessage();
    		assertTrue(actualMessage.contains(expectedMessage));
    		
   }
	
	@DisplayName("getBookSummary: GRUFF472 summary")
    @Test
    public void testRetrieveBookGRUF472() {
       
		String summary = null;  
        bookReference = THE_GRUFFALO_REFERENCE;
        
        try {
			book = bookService.retrieveBook(bookReference);
			summary = bookService.getBookSummary(bookReference);
		} catch (BookNotFoundException e) {
			e.printStackTrace();
		}
       
        Assert.assertNotNull(book);
        assertEquals(getGRUFF487BookExpectedSummary(), summary);
    }
	
	@Test
    @DisplayName("getBookSummary: POOH222 summary")
    public void testGetBookSummaryPOOH222() {
       
        try {
            bookReference = WINNIE_THE_POOH_REFERENCE;
            String summary = bookService.getBookSummary(bookReference);
            String expectedSummary = getPOOH222BookExpectedSummary();
            assertEquals(expectedSummary, summary);
            
        } catch (BookNotFoundException e) {
        	e.printStackTrace();
        }
    }
	
	@Test
    @DisplayName("getBookSummary: Exception, informing the client that book reference must begin with BOOK")
    public void testGetBookSummaryInvalidTextException() {
        
		Exception exception = assertThrows(BookNotFoundException.class, () -> {
			bookReference = "abc";
			String summary = bookService.getBookSummary(bookReference);
		});
		
		String expectedMessage = Constants.EXCEPTION_BOOK;
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
		
        
    }
	
	
	@Test
    @DisplayName("getBookSummary: WILL987 summary")
    public void testGetBookSummaryWILL987() {
       
        try {
            bookReference = THE_WIND_IN_THE_WILLOWS_REFERENCE;
            String summary = bookService.getBookSummary(bookReference);
            String expectedSummary = getWILL987BookExpectedSummary();
            assertEquals(expectedSummary, summary);
        } catch (BookNotFoundException e) {
    	   e.printStackTrace();
        }
    }
	 
	@Test
    @DisplayName("getBookSummary: GRUFF472 summary")
    public void testGetBookSummaryGRUFF472() {
        
        try {
            bookReference = THE_GRUFFALO_REFERENCE;
            summary = bookService.getBookSummary(bookReference);
            assertEquals(getGRUFF487BookExpectedSummary(), summary);
        } catch (BookNotFoundException e) {
        	 e.printStackTrace();
        } 
    }
	
	/***
	 * 
	 * @return
	 */
    private String getGRUFF487BookExpectedSummary() {
        String result = "[BOOK-GRUFF472] The Gruffalo - A mouse taking a walk in the woods.";
        return result;
    }

    /***
     * 
     * @return
     */
    private String getPOOH222BookExpectedSummary() {
        String result = "[BOOK-POOH222] Winnie The Pooh - In this first volume, we meet all the friends" +
                ELLIPSIS;
        return result;
    }

    /***
     * 
     * @return
     */
    private String getWILL987BookExpectedSummary() {
        String result = "[BOOK-WILL987] The Wind In The Willows - With the arrival of spring and fine weather outside," +
                ELLIPSIS;
        return result;
    }

}
