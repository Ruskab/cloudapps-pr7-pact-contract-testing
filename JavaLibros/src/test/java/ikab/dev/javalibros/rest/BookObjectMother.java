package ikab.dev.javalibros.rest;


import ikab.dev.javalibros.book.Book;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BookObjectMother {

    public static Book book(String nombre) {
        var book = new Book(nombre, randomString());
        book.setId(randomNumber());
        return book;
    }

    public static Book book(String title, String description) {
        return new Book(title, description);
    }

    public static Book book(int id, String title, String description) {
        var book = new Book(title, description);
        book.setId(id);
        return book;
    }

    private static String randomString() {
        return new Random().ints(97, 122 + 1)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static Integer randomNumber() {
        return ThreadLocalRandom.current().nextInt(0, 99 + 1);
    }
}
