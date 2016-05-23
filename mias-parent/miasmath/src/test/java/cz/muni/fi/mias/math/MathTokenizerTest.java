package cz.muni.fi.mias.math;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.apache.commons.compress.utils.CharsetNames;
import org.apache.commons.io.IOUtils;
import org.junit.*;

public class MathTokenizerTest {

    private static final String HUGE_MATHML = "mathml/huge-mml.xml";

    @Ignore
    @Test
    public void stressTest() throws Exception {
        Reader reader = readResource(HUGE_MATHML);
        MathTokenizer tokenizer = new MathTokenizer(reader, true, MathTokenizer.MathMLType.BOTH);

        TimeLogger logger = new TimeLogger();

        tokenizer.reset();
        logger.log("tokenizer reset done");

        for (int i = 1; tokenizer.incrementToken(); i++) {
            if (i % 1000 == 0) {
                logger.logWithoutTime(i + " tokens...");
            }
        }
        logger.log("tokenization done");

        tokenizer.end();
        logger.log("tokenizer end done");

        tokenizer.close();
        logger.log("tokenizer close done");
    }

    private Reader readResource(String resource) throws UnsupportedEncodingException, IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(resource);

        Assert.assertNotNull("Resource " + resource + " not found", stream);

        return new InputStreamReader(IOUtils.toBufferedInputStream(stream), CharsetNames.UTF_8);
    }

    private static class TimeLogger {

        private long start;

        TimeLogger() {
            start = System.currentTimeMillis();
        }

        void log(String msg) {
            long now = System.currentTimeMillis();
            System.out.println(msg + " (" + (now - start) + " ms)");
            start = now;
        }

        void logWithoutTime(String msg) {
            System.out.println(msg);
        }

    }

}
