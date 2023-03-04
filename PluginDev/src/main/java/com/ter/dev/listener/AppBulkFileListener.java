package com.ter.dev.listener;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class AppBulkFileListener implements BulkFileListener {

    private static final Logger LOGGER = Logger.getLogger(AppBulkFileListener.class.getSimpleName());

    private final List<String> filesToWatch = List.of("AndroidManifest.xml");
    private final Map<String, byte[]> oldContents = new LinkedHashMap<>();

    @Override
    public void after(@NotNull List<? extends @NotNull VFileEvent> events) {
        try {
            for (VFileEvent event : events) {
                String filename = Objects.requireNonNull(event.getFile()).getName();

                if (event instanceof VFileContentChangeEvent) {
                    VirtualFile currentVFile = event.getFile();

                    String before = new String(oldContents.get(filename));
                    String current = new String(currentVFile.contentsToByteArray());

                    logFileDiff(filename, before, current);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void before(@NotNull List<? extends @NotNull VFileEvent> events) {
        for (VFileEvent event : events) {
            try {
                String filename = Objects.requireNonNull(event.getFile()).getName();
                if (event instanceof VFileContentChangeEvent)
                    oldContents.put(filename, event.getFile().contentsToByteArray());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void logFileDiff(String filename, String oldContent, String newContent) {
        String[] oldLines = oldContent.split("\n");
        String[] newLines = newContent.split("\n");

        int i = 0;
        int j = 0;
        while (i < oldLines.length && j < newLines.length) {
            if (!oldLines[i].equals(newLines[j])) {
                if (i < oldLines.length && j < newLines.length) {
                    LOGGER.info(filename + "/Line " + (j + 1) + " added: " + newLines[j]);
                    System.out.println(filename + "/Line " + (j + 1) + " added: " + newLines[j]);
                } else if (i < oldLines.length) {
                    LOGGER.info(filename + "/Line " + (i + 1) + " removed: " + oldLines[i]);
                    System.out.println(filename + "/Line " + (i + 1) + " removed: " + oldLines[i]);
                }
            }
            i++;
            j++;
        }

        // Print any remaining lines (if the file was truncated)
        while (i < oldLines.length) {
            System.out.println(filename + "/Line " + (i + 1) + " removed: " + oldLines[i]);
            i++;
        }
        while (j < newLines.length) {
            System.out.println(filename + "/Line " + (j + 1) + " added: " + newLines[j]);
            j++;
        }
    }
}
