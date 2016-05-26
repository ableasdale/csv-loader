import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Created by ableasdale on 25/05/2016.
 * <p>
 * Should create a total of 2,109,229 documents
 * <p>
 * 319151 records in entities.csv
 */
public class LoadCSVs {

    private static Logger LOG = LoggerFactory.getLogger("LoadCSVs");


    public static void main(String[] args) {

        LOG.info("Starting Processing");
        LOG.debug("Number of processors: " + Runtime.getRuntime().availableProcessors());

        ExecutorService executor = Executors.newFixedThreadPool(Config.THREADPOOL_SIZE);

        try (Stream<Path> paths = Files.walk(Paths.get(Config.BASE_DATA_DIR)).filter(Files::isRegularFile)) {
            paths.forEach(
                    path -> {
                        if (path.toString().endsWith(".csv"))
                            executor.execute(new CSVFileProcessor(path));
                    }
            );
        } catch (IOException e) {
            LOG.error("File Issue: ", e);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            LOG.error("Caught InterruptedException: ", e);
        }

        LOG.info("Workload completed");
    }
}
