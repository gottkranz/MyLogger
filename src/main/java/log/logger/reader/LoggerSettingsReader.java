package log.logger.reader;

import log.logger.Logger;

public interface LoggerSettingsReader {
    Logger getLogger(String loggerName);
}
