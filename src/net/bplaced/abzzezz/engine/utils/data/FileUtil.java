package net.bplaced.abzzezz.engine.utils.data;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    public static void writeStringToFile(final String s, final File dest) throws IOException {
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dest));
        bufferedWriter.write(s);
        bufferedWriter.close();
    }

    public static void appendStringToFile(final String s, final File dest) throws IOException {
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dest, true));
        bufferedWriter.write(s);
        bufferedWriter.close();
    }

    public static String readFromFile(final File file) throws IOException {
        return String.join("\n", getLines(file));
    }

    public static List<String> readListFromFile(final File file) throws IOException {
        return Arrays.asList(getLines(file));
    }

    public static String[] getLines(final File file) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        final StringBuilder builder = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        bufferedReader.close();
        return builder.toString().split("\n");
    }

    public static void copyFileFromUrl(final File dest, final URL src) throws IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(dest);
        fileOutputStream.getChannel().transferFrom(Channels.newChannel(src.openStream()), 0, Long.MAX_VALUE);
        fileOutputStream.close();
    }

}
