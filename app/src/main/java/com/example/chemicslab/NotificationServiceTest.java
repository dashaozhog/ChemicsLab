package com.example.chemicslab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тести для NotificationService.
 *
 * Техніки, що використовуються:
 *  - @Mock        — автоматичне створення Mock-об'єкта
 *  - @Captor      — ArgumentCaptor для перехоплення аргументів
 *  - verify()     — перевірка факту та кількості викликів
 *  - verify(times(n)) — точна кількість викликів
 *  - assertThrows — перевірка виключень
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    // ───────────────────────────── Моки та каптори ─────────────────────────────

    @Mock
    private EmailSender mockSender;          // Mock замість реального EmailSender

    @Captor
    private ArgumentCaptor<String> toCaptor;      // перехоплює аргумент "to"

    @Captor
    private ArgumentCaptor<String> subjectCaptor; // перехоплює аргумент "subject"

    @Captor
    private ArgumentCaptor<String> bodyCaptor;    // перехоплює аргумент "body"

    private NotificationService service;

    @BeforeEach
    void setUp() {
        // Інжекція Mock через конструктор — ключовий принцип DI для тестабельності
        service = new NotificationService(mockSender);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Тест 1: Happy path — перевірка точних аргументів через verify()
    // ══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("sendWelcome: sender викликано з точними аргументами")
    void sendWelcome_validEmail_callsSenderWithExactArguments() {
        // Arrange
        String email = "a@b.com";
        String name  = "Alice";

        // Act
        service.sendWelcome(email, name);

        // Assert — перевіряємо ТОЧНІ значення аргументів
        verify(mockSender).send(
                "a@b.com",       // to
                "Welcome!",      // subject
                "Hello, Alice!"  // body
        );
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Тест 2: ArgumentCaptor — перехоплення та детальна перевірка аргументів
    // ══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("sendWelcome: captor перехоплює subject='Welcome!' та body містить 'Alice'")
    void sendWelcome_captureArguments_subjectAndBodyAreCorrect() {
        // Arrange
        service.sendWelcome("a@b.com", "Alice");

        // Act — перехоплюємо всі три аргументи методу send()
        verify(mockSender).send(
                toCaptor.capture(),
                subjectCaptor.capture(),
                bodyCaptor.capture()
        );

        // Assert — витягуємо перехоплені значення та перевіряємо
        String capturedTo      = toCaptor.getValue();
        String capturedSubject = subjectCaptor.getValue();
        String capturedBody    = bodyCaptor.getValue();

        assertEquals("a@b.com",    capturedTo,      "Поле 'to' має бути email отримувача");
        assertEquals("Welcome!",   capturedSubject, "Subject має бути 'Welcome!'");
        assertTrue(capturedBody.contains("Alice"),
                "Body має містити ім'я 'Alice', але отримано: " + capturedBody);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Тест 3: verify(times(1)) — рівно один виклик sender.send()
    // ══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("sendWelcome: sender.send() викликається рівно 1 раз")
    void sendWelcome_validEmail_senderCalledExactlyOnce() {
        // Act
        service.sendWelcome("a@b.com", "Alice");

        // Assert — verify з times(1) — явна перевірка кількості викликів
        verify(mockSender, times(1)).send(anyString(), anyString(), anyString());

        // Жодного іншого виклику на mock не було
        verifyNoMoreInteractions(mockSender);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Тест 4: Невалідний email — виключення, sender НЕ викликається
    // ══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("sendWelcome: невалідний email → IllegalArgumentException, sender НЕ викликано")
    void sendWelcome_invalidEmail_throwsExceptionAndSenderNeverCalled() {
        // Arrange
        String invalidEmail = "notemail"; // не містить '@'
        String name         = "Bob";

        // Act + Assert — перевіряємо що виключення справді кидається
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.sendWelcome(invalidEmail, name),
                "Має кинути IllegalArgumentException для невалідного email"
        );

        // Додаткова перевірка повідомлення виключення (опціонально)
        assertTrue(exception.getMessage().contains("Bad email"),
                "Повідомлення виключення має містити 'Bad email'");

        // Ключова перевірка: sender.send() не повинен бути викликаний ЖОДНОГО разу
        verify(mockSender, never()).send(anyString(), anyString(), anyString());
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Бонус-тест: декілька викликів — captor.getAllValues()
    // ══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Бонус: captor.getAllValues() при декількох викликах")
    void sendWelcome_multipleCalls_captorCollectsAllBodies() {
        // Act
        service.sendWelcome("alice@test.com", "Alice");
        service.sendWelcome("bob@test.com",   "Bob");

        // Перехоплюємо body для обох викликів
        verify(mockSender, times(2)).send(anyString(), anyString(), bodyCaptor.capture());

        var allBodies = bodyCaptor.getAllValues();

        assertEquals(2, allBodies.size(), "Має бути 2 перехоплених body");
        assertTrue(allBodies.get(0).contains("Alice"), "Перший body має містити 'Alice'");
        assertTrue(allBodies.get(1).contains("Bob"),   "Другий body має містити 'Bob'");
    }
}
