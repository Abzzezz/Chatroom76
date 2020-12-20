package net.bplaced.abzzezz.core.util.data;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;

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

    public static void writeToFile(final byte[] bytes, final File des) {
        try (final FileOutputStream fileOutputStream = new FileOutputStream(des)) {
            fileOutputStream.write(bytes);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(final byte[] bytes, final File des, boolean append) {
        try (final FileOutputStream fileOutputStream = new FileOutputStream(des, append)) {
            fileOutputStream.write(bytes);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(final File file) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        final StringBuilder builder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        bufferedReader.close();
        return builder.toString();
    }

    public static void writeListToFile(final File dest, List<String> list) throws IOException {
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dest, true));
        for (String s : list) {
            bufferedWriter.write(s + "\n");
        }
        bufferedWriter.close();
    }

    public static List<String> readListFromFile(final File file) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        final List<String> lines = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines;
    }

    public static List<String> readListFromURL(final URL url) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        final List<String> lines = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines;
    }

    public static String readShader(final URL url) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        final StringBuilder builder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        bufferedReader.close();
        return builder.toString();
    }

    public static void copyFileFromUrl(final File dest, final URL src) throws IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(dest);
        fileOutputStream.getChannel().transferFrom(Channels.newChannel(src.openStream()), 0, Long.MAX_VALUE);
        fileOutputStream.close();
    }

}
