package net.bplaced.abzzezz.engine.utils.data;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
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
        return getLines(file).collect(Collectors.joining("\n"));
    }

    public static List<String> readListFromFile(final File file) throws IOException {
        return getLines(file).collect(Collectors.toList());
    }

    public static Stream<String> getLines(final File file) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.lines();
    }

    public static void copyFileFromUrl(final File dest, final URL src) throws IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(dest);
        fileOutputStream.getChannel().transferFrom(Channels.newChannel(src.openStream()), 0, Long.MAX_VALUE);
        fileOutputStream.close();
    }

}
