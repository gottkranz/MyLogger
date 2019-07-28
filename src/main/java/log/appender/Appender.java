package log.appender;

import log.logger.Level;

public interface Appender {
    void append(String message, Level priorityLevel);
}
