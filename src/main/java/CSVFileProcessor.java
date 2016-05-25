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
import java.nio.file.Path;
import java.util.Map;

/**
 * Created by ableasdale on 25/05/2016.
 */
public class CSVFileProcessor implements Runnable {

    private Logger LOG = LoggerFactory.getLogger("CSVFileProcessor");
    private Reader in = null;
    private String ROOT;

    public CSVFileProcessor(Path filename) {
        // LOG.info("constructor " + filename);

        String theFile = filename.getFileName().toString();
        ROOT = theFile.substring(0, theFile.indexOf('.'));

        try {
            in = new FileReader(filename.toString());
        } catch (FileNotFoundException e) {
            LOG.error("File Issue: ",e);
        }

    };

    public void run() {
        LOG.info("Running: " + in.toString());
        processMe(in, ROOT);
    }

    private String clean(String s) {
        s = s.replace("&", "&amp;");
        s = s.replace("{", "(");
        s = s.replace("}", ")");
        return s;
    };


    private void processMe(Reader reader, String rootElement) {

        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
        } catch (IOException e) {
            LOG.error("File IO Issue: ",e);
        }

        for (CSVRecord record : records) {

            StringBuilder sb = new StringBuilder();
            sb.append("<").append(rootElement).append(">");

            Map<String, String> lhm = record.toMap();
            for (String key : lhm.keySet()) {
                sb.append("<").append(key).append(">").append(clean(lhm.get(key))).append("</").append(key).append(">");
            }
            sb.append("</").append(rootElement).append(">");

            Session s = MarkLogicSessionProvider.getSession();
            try {
                String st = "xdmp:document-insert( concat('/',xdmp:random(),'.xml'),"+sb.toString()+")";
                s.submitRequest(s.newAdhocQuery(st));
            } catch (RequestException e) {
                LOG.error("Failed to load one doc");
                LOG.info(sb.toString());
            }
            s.close();
        }
    };

}
