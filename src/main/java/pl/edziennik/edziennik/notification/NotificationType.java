package pl.edziennik.edziennik.notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    NEW_MARK("newMark"),
    NEW_EXAM("newExam"),
    EDITTED_MARK("edittedMark");

    NotificationType(String messagePart) {
        this.messagePart = messagePart;
    }

    private final String messagePart;
}
