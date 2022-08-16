package DeliverySystem.fileUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashSet;

public final class FileUtilities {
    private static final Logger logger = Logger.getLogger(FileUtilities.class.getName());

    public static int countUniqueWords(String filename) throws IOException {
        File file = FileUtils.getFile(filename);
        String fileContents = StringUtils.lowerCase(FileUtils.readFileToString(file, Charset.defaultCharset()));
        //logger.info(fileContents);

        fileContents = fileContents.replaceAll("[^a-zA-Z \n 0-9]", "");
        String[] words = fileContents.split("\\s");
        LinkedHashSet<String> uniqueWords = new LinkedHashSet<String>(Arrays.asList(words));
        uniqueWords.remove("");
        //logger.info(uniqueWords);

        FileUtils.writeStringToFile(file, "\n\nCurrent number of unique words in this file: " + uniqueWords.size(), Charset.defaultCharset(), true);
        logger.info("Currently " + uniqueWords.size() + " unique words in file " + file.getName());

        return uniqueWords.size();
    }
}
