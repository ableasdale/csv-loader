import com.marklogic.xcc.Session;
import com.marklogic.xcc.exceptions.RequestException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by ableasdale on 25/05/2016.
 *
 * 319151 records in entities.csv
 */
public class ParseCSVs {

    private static Logger LOG = LoggerFactory.getLogger("ParseCSVs");


    public static void main(String[] args) {

        LOG.info("Starting Processing");

        try (Stream<Path> paths = Files.walk(Paths.get(Config.BASE_DATA_DIR)).filter(Files::isRegularFile)) {
            paths.forEach(
                    path -> {
                        if(path.toString().endsWith(".csv"))
                            new Thread(new CSVFileProcessor(path)).start();
                        // LOG.info(path.toString());
                    }
            );
        } catch (IOException e) {
            LOG.error("File Issue: ",e);
        }


        //processMe(cs, in);


        LOG.info("Done");
    }
}
