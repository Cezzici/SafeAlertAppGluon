package com.safealert;

import java.io.*;
import java.util.UUID;

public class UserIdentifier {

    private static final String FILE_NAME = "user_id.txt";

    public static String getOrCreateUUID() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String uuid = UUID.randomUUID().toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uuid;
    }
}
