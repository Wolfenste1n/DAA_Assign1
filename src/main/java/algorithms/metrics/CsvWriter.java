package algorithms.metrics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class CsvWriter implements AutoCloseable {
    private final Path path;
    private final String header;
    private final boolean managedWriter;
    private BufferedWriter bw;

    public CsvWriter(String pathStr, String header) {
        this(Path.of(pathStr), header, false);
    }

    public CsvWriter(Path path, boolean append) throws IOException {
        this(path, null, true);
        Path parent = path.getParent();
        if (parent != null) Files.createDirectories(parent);
        if (append) {
            this.bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } else {
            this.bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    private CsvWriter(Path path, String header, boolean managedWriter) {
        this.path = path;
        this.header = header;
        this.managedWriter = managedWriter;
        this.bw = null;
    }

    public synchronized void writeHeaderIfNeeded() {
        try {
            Path p = path;
            Path parent = p.getParent();
            if (parent != null) Files.createDirectories(parent);
            boolean needHeader = !Files.exists(p) || Files.size(p) == 0;
            if (needHeader && header != null) {
                try (BufferedWriter w = Files.newBufferedWriter(p, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                    w.write(header);
                    w.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void writeRow(String row) {
        try {
            if (managedWriter) {
                bw.write(row);
                bw.newLine();
                bw.flush();
            } else {
                try (BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                    w.write(row);
                    w.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void writeHeader(String headerLine) {
        try {
            Path p = path;
            Path parent = p.getParent();
            if (parent != null) Files.createDirectories(parent);
            try (BufferedWriter w = Files.newBufferedWriter(p, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                w.write(headerLine);
                w.newLine();
            }
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
