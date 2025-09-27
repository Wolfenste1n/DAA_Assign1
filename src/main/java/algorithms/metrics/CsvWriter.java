package algorithms.metrics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;


public class CsvWriter implements AutoCloseable {
    private final BufferedWriter bw;
    private boolean headerWritten = false;

    public CsvWriter(Path path, boolean append) throws IOException {
        Path parent = path.getParent();
        if (parent != null) Files.createDirectories(parent);
        if (append) {
            bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } else {
            bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    public synchronized void writeHeader(String header) throws IOException {
        if (!headerWritten) {
            bw.write(header);
            bw.newLine();
            bw.flush();
            headerWritten = true;
        }
    }

    public synchronized void writeRow(String row) throws IOException {
        bw.write(row);
        bw.newLine();
        bw.flush();
    }

    @Override
    public synchronized void close() throws IOException {
        bw.close();
    }
}