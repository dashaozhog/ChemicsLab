package com.example.chemicslab;

/**
 * Абстракція для відправлення email-повідомлень.
 * Реальна реалізація може використовувати SMTP, SendGrid тощо.
 * У тестах замінюється Mock-об'єктом через Mockito.
 */
public interface EmailSender {

    /**
     * Відправляє email-повідомлення.
     *
     * @param to      адреса отримувача
     * @param subject тема листа
     * @param body    тіло листа
     */
    void send(String to, String subject, String body);
}
