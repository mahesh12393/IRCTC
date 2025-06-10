package ticket.booking.utils;

import java.io.*;
import java.nio.file.*;

public class FileResourceUtil {

    public static File getUsersFile() {
        return extractResource("/ticket/booking/localDb/users.json", "users", ".json");
    }

    public static File getTrainsFile() {
        return extractResource("/ticket/booking/localDb/trains.json", "trains", ".json");
    }

    private static File extractResource(String resourcePath, String tempPrefix, String tempSuffix) {
        try {
            InputStream is = FileResourceUtil.class.getResourceAsStream(resourcePath);
            if (is == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }

            File tempFile = File.createTempFile(tempPrefix, tempSuffix);
            tempFile.deleteOnExit();

            Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            is.close();

            return tempFile;
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract resource: " + resourcePath, e);
        }
    }
}