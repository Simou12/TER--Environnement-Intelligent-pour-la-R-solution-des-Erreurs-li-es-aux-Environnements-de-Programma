package com.example.plugindev;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.android.dom.AndroidDomElement;
import org.jetbrains.android.dom.manifest.Manifest;
import org.jetbrains.android.util.AndroidUtils;

import java.util.Arrays;


public class ManifestChangeListener implements DocumentListener {

    private final Project project;

    public ManifestChangeListener(Project project) {
        this.project = project;
    }


    @Override
    public void documentChanged(DocumentEvent event) {
        VirtualFile file = event.getDocument().getVirtualFile();
        if (file == null || !"AndroidManifest.xml".equals(file.getName())) {
            return;
        }

        Manifest manifest = AndroidUtils.loadDomElement(project, file, Manifest.class);
        if (manifest != null) {
            Application.runWhenSmart(project, () -> {
                // Get all the application nodes in the manifest
                AndroidDomElement[] applicationElements = manifest.getApplicationElements();
                if (applicationElements == null || applicationElements.length == 0) {
                    return;
                }

                // Print the attributes of each application node
                Arrays.stream(applicationElements)
                        .flatMap(app -> app.getXmlTag().getAttributes().stream())
                        .forEach(attr -> System.out.println(attr.getName() + " : " + attr.getValue()));
            });
        }
    }

    @Override
    public void beforeDocumentChange(DocumentEvent event) {
        // Do nothing
    }
}
