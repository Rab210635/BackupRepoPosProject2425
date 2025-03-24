package spengergasse.at.sj2425scherzerrabar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import spengergasse.at.sj2425scherzerrabar.FixturesFactory;
import spengergasse.at.sj2425scherzerrabar.commands.CopyCommand;
import spengergasse.at.sj2425scherzerrabar.domain.*;
import spengergasse.at.sj2425scherzerrabar.dtos.CopyDto;
import spengergasse.at.sj2425scherzerrabar.persistence.BookRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.CopyRepository;
import spengergasse.at.sj2425scherzerrabar.persistence.PublisherRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;


import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.*;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CopyServiceTest {

    private @Mock CopyRepository copyRepository;
    private @Mock BookRepository bookRepository;
    private @Mock PublisherRepository publisherRepository;

    private CopyService copyService;

    @BeforeEach
    void setUp() {
        assumeThat(copyRepository).isNotNull();
        assumeThat(bookRepository).isNotNull();
        assumeThat(publisherRepository).isNotNull();
        copyService = new CopyService(copyRepository, bookRepository, publisherRepository);
    }

    @Test
    void can_create_copy() {
        Publisher publisher = FixturesFactory.publisher(FixturesFactory.address2());
        Book book = FixturesFactory.book(FixturesFactory.author());

        when(publisherRepository.findPublisherByPublisherApiKey(any())).thenReturn(Optional.of(publisher));
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));
        when(copyRepository.save(any(Copy.class))).then(AdditionalAnswers.returnsFirstArg());

        var copy = copyService.createCopy(new CopyCommand(
                "apiKey", "publisherApiKey", BookType.EBOOK,12,"bookApiKey",100f
        ));

        assertThat(copy).isNotNull();
    }

    @Test
    void cant_create_copy_with_missing_publisher() {
        Book book = FixturesFactory.book(FixturesFactory.author());

        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));

        assertThatThrownBy(()-> copyService.createCopy(new CopyCommand("apiKey", "publisherApiKey",
                BookType.EBOOK,12,"bookApiKey",100f)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Publisher not found");
    }

    @Test
    void cant_create_copy_with_missing_book() {
        assertThatThrownBy(()-> copyService.createCopy(new CopyCommand("apiKey", "publisherApiKey",
                BookType.EBOOK,12,"bookApiKey",100f)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    void can_delete_existing_copy() {
        var copy = FixturesFactory.copy();
        when(copyRepository.findCopyByCopyApiKey(any())).thenReturn(Optional.of(copy));

        copyService.deleteCopy(new ApiKey("validApiKey"));

        verify(copyRepository,times(1)).delete(copy);
    }

    @Test
    void cant_delete_not_existing_copy() {
      assertThatThrownBy(()->copyService.deleteCopy(new ApiKey("validApiKey"))).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_update_existing_copy() {
        Publisher publisher = FixturesFactory.publisher(FixturesFactory.address2());
        Book book = FixturesFactory.book(FixturesFactory.author());
        Copy copy = FixturesFactory.copy();

        when(copyRepository.findCopyByCopyApiKey(any())).thenReturn(Optional.of(copy));
        when(publisherRepository.findPublisherByPublisherApiKey(any())).thenReturn(Optional.of(publisher));
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));
        when(copyRepository.save(any(Copy.class))).then(AdditionalAnswers.returnsFirstArg());

        copyService.updateCopy(new CopyCommand(
                "copyApiKey","publisherApiKey",BookType.EBOOK,12,"BookApiKey",100f
        ));

        verify(copyRepository,times(1)).save(any(Copy.class));
        assertThat(copy.getPageCount()).isEqualTo(12);
        assertThat(copy.getBookType()).isEqualTo(BookType.EBOOK);
    }

    @Test
    void cant_update_not_existing_copy() {
        assertThatThrownBy(()-> copyService.updateCopy(new CopyCommand("copyApiKey",
                "publisherApiKey",BookType.EBOOK,12,"BookApiKey",100f)))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void cant_update_existing_copy_with_missing_publisher() {
        Book book = FixturesFactory.book(FixturesFactory.author());
        Copy copy = FixturesFactory.copy();

        when(copyRepository.findCopyByCopyApiKey(any())).thenReturn(Optional.of(copy));
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));

        assertThatThrownBy(()-> copyService.updateCopy(new CopyCommand(
                "copyApiKey","publisherApiKey",BookType.EBOOK,12,"BookApiKey",100f)))
        .isInstanceOf(NoSuchElementException.class)
        .hasMessageContaining("Publisher not found");
    }

    @Test
    void cant_update_existing_copy_with_missing_book() {
        Copy copy = FixturesFactory.copy();

        when(copyRepository.findCopyByCopyApiKey(any())).thenReturn(Optional.of(copy));

        assertThatThrownBy(()-> copyService.updateCopy(new CopyCommand(
                "copyApiKey","publisherApiKey",BookType.EBOOK,12,"BookApiKey",100f)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    void can_get_existing_copy() {
        Copy copy = FixturesFactory.copy();
        when(copyRepository.findCopyByCopyApiKey((any()))).thenReturn(Optional.of(copy));

        var copy1 = copyService.getCopy(copy.getCopyApiKey());
        assertThat(copy1).isEqualTo(CopyDto.copyDtoFromCopy(copy));
        verify(copyRepository,times(1)).findCopyByCopyApiKey(any());
    }

    @Test
    void cant_get_not_existing_copy() {
        assertThatThrownBy(()->copyService.getCopy(new ApiKey("copyApiKey"))).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void can_get_copies() {
        Copy copy = FixturesFactory.copy();
        Copy copy1 = FixturesFactory.copy();
        when(copyRepository.findAll()).thenReturn(List.of(copy,copy1));

        var copies = copyService.getCopies();

        assertThat(copies).hasSize(2);
    }

    @Test
    void can_get_copies_with_book() {
        Copy copy = FixturesFactory.copy();
        Copy copy1 = FixturesFactory.copy();
        Book book = FixturesFactory.book(FixturesFactory.author());
        when(bookRepository.findBookByBookApiKey(any())).thenReturn(Optional.of(book));
        when(copyRepository.getCopiesByBook_BookApiKey(book.getBookApiKey())).thenReturn(List.of(copy,copy1));

        var copies = copyService.getCopiesByBook(book.getBookApiKey());

        assertThat(copies).hasSize(2);
    }

    @Test
    void cant_get_copies_with_missing_book() {
       assertThatThrownBy(()->copyService.getCopiesByBook(new ApiKey("bookApiKey")))
               .isInstanceOf(NoSuchElementException.class)
               .hasMessageContaining("Book not found");
    }

    @Test
    void can_get_copies_with_publisher() {
        Copy copy = FixturesFactory.copy();
        Copy copy1 = FixturesFactory.copy();
        Publisher publisher = FixturesFactory.publisher(FixturesFactory.address2());
        when(publisherRepository.findPublisherByPublisherApiKey(any())).thenReturn(Optional.of(publisher));
        when(copyRepository.getCopiesByPublisher_PublisherApiKey(publisher.getPublisherApiKey())).thenReturn(List.of(copy,copy1));

        var copies = copyService.getCopiesByPublisher(publisher.getPublisherApiKey());

        assertThat(copies).hasSize(2);
    }

    @Test
    void cant_get_copies_with_missing_publisher() {
        assertThatThrownBy(()->copyService.getCopiesByPublisher(new ApiKey("publisherApiKey")))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Publisher not found");
    }

    @Test
    void can_get_copies_with_book_type() {
        Copy copy = FixturesFactory.copy();
        Copy copy1 = FixturesFactory.copy();
        when(copyRepository.getCopiesByBookType(BookType.PAPERBACK)).thenReturn(List.of(copy,copy1));

        var copies = copyService.getCopiesByBookType(BookType.PAPERBACK);

        assertThat(copies).hasSize(2);
    }


}