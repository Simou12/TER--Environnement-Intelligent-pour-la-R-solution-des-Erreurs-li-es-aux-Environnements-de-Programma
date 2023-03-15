package com.ter.dev.add_delete_file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileCreateEvent;
import org.jetbrains.annotations.NotNull;


import java.util.List;

public class AddFileListener implements BulkFileListener {

    private final Project project;

    public AddFileListener(Project project) {
        this.project = project;
    }
    public void after(@NotNull List<? extends VFileEvent> events){
            for (VFileEvent event:events){
                if(event instanceof VFileCreateEvent){
                    System.out.println("Le fichier crée est : "+event.getPath());
                }
            }

    }

}
