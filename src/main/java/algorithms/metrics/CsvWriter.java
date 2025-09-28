package algorithms.metrics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class CsvWriter implements AutoCloseable {
    private final Path path;
    private final String header;
    private final boolean append;
    private BufferedWriter bw;

    public CsvWriter(Path path, boolean append) throws IOException {
        this.path = path;
        this.header = null;
        this.append = append;
        Path parent = path.getParent();
        if (parent != null) Files.createDirectories(parent);
        OpenOption[] opts = append
                ? new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND}
                : new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};
        this.bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, opts);
    }

    public CsvWriter(String pathStr, String header) {
        this.path = Paths.get(pathStr);
        this.header = header;
        this.append = true;
        try {
            Path parent = this.path.getParent();
            if (parent != null) Files.createDirectories(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void writeHeaderIfNeeded() {
        try {
            boolean needHeader = !Files.exists(path) || Files.size(path) == 0;
            if (needHeader && header != null) {
                try (BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                    w.write(header);
                    w.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void writeHeader(String header) {
        try (BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            w.write(header);
            w.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void writeRow(String row) {
        try {
            if (bw == null) {
                bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
            bw.write(row);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void close() throws IOException {
        if (bw != null) {
            bw.close();
            bw = null;
        }
    }
}
