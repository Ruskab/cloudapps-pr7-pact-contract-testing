package ikab.dev.javalibros.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import ikab.dev.javalibros.book.Book;
import ikab.dev.javalibros.book.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static ikab.dev.javalibros.rest.BookObjectMother.book;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookRestControllerTest {

    private final static String BOOKS_ENDPOINT = "/api/books/";
    public static final String MOCK_USERNAME = "user";
    public static final String MOCK_PASSWORD = "pass";
    public static final String MOCK_ROLE_USER = "USER";
    public static final String MOCK_ROLE_ADMIN = "ADMIN";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void given_not_authenticated_user_when_list_books_should_return_all_books() throws Exception {
        when(bookRepository.findAll()).thenReturn(List.of(book(1, "book1", "desc1"), book(2, "book2", "desc2")));

        mvc.perform(get(BOOKS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].title", equalTo("book1")))
                .andExpect(jsonPath("$[0].description", equalTo("desc1")))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].title", equalTo("book2")))
                .andExpect(jsonPath("$[1].description", equalTo("desc2")));
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, password = MOCK_PASSWORD, roles = MOCK_ROLE_USER)
    void given_authenticated_user_when_create_book_should_return_success_response() throws Exception {
        var withTitle = "title";
        var withDescription = "description";
        when(bookRepository.save(any(Book.class))).thenReturn(book(1, withTitle, withDescription));

        mvc.perform(post(BOOKS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book(withTitle, withDescription))))

                .andExpect(status().isCreated())
                .andExpect(content().json("""
                                        {"id":1,"title":"%s","description":"%s"}""".formatted(withTitle, withDescription)));
    }

    @Test
    @WithMockUser(username = MOCK_USERNAME, password = MOCK_PASSWORD, roles = MOCK_ROLE_ADMIN)
    void given_authenticated_admin_when_delete_book_should_return_success_response() throws Exception {
        var bookIdToDelete = 1L;
        doNothing().when(bookRepository).deleteById(bookIdToDelete);

        mvc.perform(delete(BOOKS_ENDPOINT + bookIdToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}