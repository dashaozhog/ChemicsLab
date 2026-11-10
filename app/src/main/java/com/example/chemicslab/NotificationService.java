package com.example.chemicslab;

/**
 * Сервіс сповіщень, який надсилає вітальні листи новим користувачам.
 *
 * <p>Залежність EmailSender ін'єктується через конструктор (Dependency Injection),
 * що робить клас легко тестованим: у тестах передається Mock замість реального sender.</p>
 */
public class NotificationService {

    private final EmailSender sender;

    /**
     * @param sender реалізація для відправлення листів (інжектуєтьсяззовні)
     */
    public NotificationService(EmailSender sender) {
        this.sender = sender;
    }

    /**
     * Надсилає вітальний лист новому користувачу.
     *
     * @param email адреса отримувача — має містити символ '@'
     * @param name  ім'я користувача, яке буде включено в тіло листа
     * @throws IllegalArgumentException якщо email не містить '@'
     */
    public void sendWelcome(String email, String name) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Bad email: " + email);
        }
        sender.send(email, "Welcome!", "Hello, " + name + "!");
    }
}
