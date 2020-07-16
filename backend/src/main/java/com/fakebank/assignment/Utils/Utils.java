package com.fakebank.assignment.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Utils {
    private static final String path = System.getProperty("user.home") + File.separator;

    public Utils() { }

    // This method is needed because I need to convert MultipartFile to File. To do so, I store it temporarily
    public static File convertMultipartFileToFile(MultipartFile file, String name) throws IOException {
        String url = path + name;
        File temporaryFile = new File(url);
        file.transferTo(temporaryFile);
        return temporaryFile;
    }

    public static double roundDouble(double value) {
        return (double) Math.round(value * 100000d) / 100000d;
    }
}
