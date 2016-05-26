import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by ableasdale on 26/05/2016.
 */
public class SampleThread implements Runnable {

    private Logger LOG = LoggerFactory.getLogger("SampleThread");

    @Override
    public void run() {
        LOG.info("Running");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            LOG.error("InterruptedException Caught: ", e);
        }
        LOG.info("Done");
    }
}
