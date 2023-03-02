package com.example.plugindev;


import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class DocumentListenerTest implements DocumentListener {

    @Override
    public void beforeDocumentChange(@NotNull DocumentEvent event) {
        DocumentListener.super.beforeDocumentChange(event);
    }

    @Override
    public void documentChanged(@NotNull DocumentEvent event) {

        VirtualFile file= FileDocumentManager.getInstance().getFile(event.getDocument());
        assert file != null;
        if(file.getName().equals("AndroidManifest.xml")) {
            String modify = event.getNewFragment().toString();
            System.out.println(modify);
        }
    }
}
