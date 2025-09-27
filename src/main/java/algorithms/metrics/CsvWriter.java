package algorithms.metrics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    private final File file;
    private final String header;

    public CsvWriter(String path, String header) {
        this.file = new File(path);
        this.header = header;
    }

    public synchronized void writeHeaderIfNeeded() {
        try {
            boolean needHeader = !file.exists() || file.length() == 0;
            file.getParentFile().mkdirs();
            if (needHeader) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                    bw.write(header);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void writeRow(String row) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(row);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
