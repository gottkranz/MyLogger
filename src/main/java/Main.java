import log.logger.Logger;
import log.logger.LoggerFactory;

public class Main {
    public static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.debug("MES");
        logger.fatal("MES");
    }
}
