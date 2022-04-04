package com.shramko;

import org.apache.commons.text.similarity.JaroWinklerDistance;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Boris Shramko
 */

public class Runner {

    private static final String OUTPUT = "src/com/shramko/resources/output.txt";
    private static final String INPUT = "src/com/shramko/resources/demo.txt";

    public static void main(String[] args) throws IOException {

        List<String> datas = getDatasFromFile(INPUT);
        DataConverter.saveResultToFile(DataConverter.getOutputMap(datas), OUTPUT);
    }

    private static List<String> getDatasFromFile(String output) throws IOException {
        Path path = FileSystems.getDefault().getPath(output);
        return Files.readAllLines(path);
    }

}
