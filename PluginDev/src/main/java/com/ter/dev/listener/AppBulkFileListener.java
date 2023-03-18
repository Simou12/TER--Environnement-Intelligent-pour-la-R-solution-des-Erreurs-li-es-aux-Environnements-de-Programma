package com.ter.dev.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.*;
import name.fraser.neil.plaintext.diff_match_patch;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppBulkFileListener implements BulkFileListener {

    private static final Logger LOGGER = Logger.getLogger(AppBulkFileListener.class.getSimpleName());

    private final Map<String, byte[]> oldContents;

    private static diff_match_patch diffMatchPatch = new diff_match_patch();
    private Project project;
    public AppBulkFileListener() {
        super();
        this.oldContents = new LinkedHashMap<>();
        try {
            FileHandler fileHandler = new FileHandler("myapp.log");
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);
            LOGGER.info("My first log message");
            LOGGER.warning("My warning message");
            LOGGER.severe("My severe message");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating log file", e);
        }
        this.project=null;
    }
    public AppBulkFileListener(Project project) {
        this.oldContents = new LinkedHashMap<>();
        this.project=project;
    }

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
                }else if(event instanceof VFileCreateEvent){
                   LOGGER.info("file created path : "+event.getPath());
                }else if(event instanceof VFileDeleteEvent){
                    LOGGER.info("file suppressed path : "+event.getPath());
                }else if(event instanceof VFileMoveEvent){
                    LOGGER.info("move event detected");
                    VFileMoveEvent moveEvent=(VFileMoveEvent) event;
                    //old path file
                    VirtualFile oldFile=moveEvent.getOldParent();
                    VirtualFile oldFileVersion=oldFile.findChild(moveEvent.getOldPath());
                    String fileName=oldFile.findChild(moveEvent.getOldPath()).getName();
                    //new path file
                    VirtualFile newFileVersion=moveEvent.getNewParent().findChild(moveEvent.getNewPath());
                    if(oldFileVersion !=null && newFileVersion != null){
                        LOGGER.info(
                                "the file "+fileName+" moved from :"+ VfsUtilCore.virtualToIoFile(oldFileVersion).getPath()+
                                        " to "+ VfsUtilCore.virtualToIoFile(newFileVersion).getPath()
                        );
                    }
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
        LinkedList<diff_match_patch.Diff> diff = diffMatchPatch.diff_main(oldContent, newContent);
        diffMatchPatch.diff_cleanupSemantic(diff);

        // Print the results
        int lineNum = 0;

        for (diff_match_patch.Diff d : diff) {
            String[] lines = d.text.split("\n");
            int numLines = lines.length;

            if (d.operation == diff_match_patch.Operation.INSERT) {
                System.out.println("INSERT : line " + (lineNum + 2) + ":");
                for (int i = 0; i < numLines; i++) {
                    System.out.println(lines[i]);
                    //LOGGE
                }
                lineNum += numLines;
            } else if (d.operation == diff_match_patch.Operation.DELETE) {
                System.out.println("DELETE : line " + (lineNum + 2) + ":");
                for (int i = 0; i < numLines; i++) {
                    System.out.println(lines[i]);
                }
                lineNum -= numLines;
            } else {
                lineNum += numLines;
            }
        }
    }

}