package add_delete_fileListener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileDeleteEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileCreateEvent;
import org.jetbrains.annotations.NotNull;


import java.util.List;

public class AddDeleteFileListener implements BulkFileListener {

    private final Project project;
    public AddDeleteFileListener() {
        this.project = null;
    }

    public AddDeleteFileListener(Project project) {
        this.project = project;
    }
    public void after(@NotNull List<? extends VFileEvent> events){
            for (VFileEvent event:events){
                if(event instanceof VFileCreateEvent){
                    System.out.println("file created path : "+event.getPath());
                }else if(event instanceof VFileDeleteEvent){
                    System.out.println("file suppressed path : "+event.getPath());
                }
            }
    }
    public void before (@NotNull List<? extends VFileEvent> events){

    }
}
