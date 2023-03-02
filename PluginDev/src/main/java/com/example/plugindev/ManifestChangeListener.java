package com.example.plugindev;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.openapi.vfs.newvfs.events.VFilePropertyChangeEvent;
import org.jetbrains.android.dom.AndroidDomElement;
import org.jetbrains.android.dom.manifest.Manifest;
import org.jetbrains.android.util.AndroidUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ManifestChangeListener implements BulkFileListener {

    private static final Logger LOGGER = Logger.getLogger(ManifestChangeListener.class.getSimpleName());

    @Override
    public void after(@NotNull List<? extends VFileEvent> events) {
        for (VFileEvent event : events) {
            if (Objects.requireNonNull(event.getFile()).getName().equals("AndroidManifest.xml")) {
                System.out.println("Android manifest file is being updated");
                LOGGER.log(Level.INFO, "Android manifest file is being updated");
                if (event instanceof VFilePropertyChangeEvent) {
                    System.out.println("VFIle propertyChangeEvent detected");
                    // récupération de la propriété modifiée
                    /*String propertyName = ((VFilePropertyChangeEvent) event).getPropertyName();

                    // récupération de la valeur de la propriété avant la modification
                    Object oldValue = ((VFilePropertyChangeEvent) event).getOldValue();

                    // récupération de la valeur de la propriété après la modification
                    Object newValue = ((VFilePropertyChangeEvent) event).getNewValue();

                    // traitement de la modification
                    System.out.println("La propriété " + propertyName + " a été modifiée. Ancienne valeur : "
                            + oldValue + ". Nouvelle valeur : " + newValue + ".");*/
                } else if (event instanceof VFileContentChangeEvent) {
                    VirtualFile file = event.getFile();
                    Document document = FileDocumentManager.getInstance().getDocument(file);
                    assert document != null;
                    CharSequence before = document.getImmutableCharSequence();
                    System.out.println("New Content : ");
                    System.out.println(before);
                    System.out.println("Old length : " + ((VFileContentChangeEvent) event).getOldLength());
                    System.out.println("New length : " + ((VFileContentChangeEvent) event).getNewLength());
                } else {
                    // traitement de l'ajout ou de la suppression de contenu dans le fichier
                    System.out.println("Le fichier AndroidManifest.xml a été modifié.");
                    String contentBefore = getContentBefore(event);
                    String contentAfter = getContentAfter(event);
                    System.out.println("Contenu avant : " + contentBefore);
                    System.out.println("Contenu après : " + contentAfter);
                }
            }
        }
    }

    private String getContentBefore(VFileEvent event) {
        VirtualFile file = event.getFile();
        assert file != null;
        Document document = FileDocumentManager.getInstance().getDocument(file);
        assert document != null;
        CharSequence before = document.getImmutableCharSequence();
        return before.toString();
    }

    private String getContentAfter(VFileEvent event) {
        VirtualFile file = event.getFile();
        assert file != null;
        Document document = FileDocumentManager.getInstance().getDocument(file);
        CharSequence after = ((VFilePropertyChangeEvent) event).getNewValue().toString();
        return after.toString();
    }
}


    /*@Override
    public void documentChanged(DocumentEvent event) {
        VirtualFile file = event.getDocument();
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
    public void beforeDocumentChange(@NotNull DocumentEvent event) {
        // Do nothing
    }*/
