package com.ter.dev.main;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.*;
import com.intellij.util.xml.reflect.AbstractDomChildrenDescription;
import com.intellij.util.xml.reflect.DomGenericInfo;
import com.ter.dev.add_delete_file.AddFileListener;
import org.jetbrains.android.dom.AndroidAttributeValue;
import org.jetbrains.android.dom.manifest.ApplicationComponent;
import org.jetbrains.android.dom.manifest.MetaData;
import org.jetbrains.android.dom.resources.ResourceValue;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.project.Project;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

public class Plugin implements ApplicationComponent {

    public void initComponent() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
       /* for (Project project : projects) {
            // Enregistre le listener pour chaque projet
     
        }*/
    }



    public void disposeComponent() {
        // ...
    }

    public String getComponentName() {
        return "MyApplicationComponent";
    }

    @Override
    public AndroidAttributeValue<ResourceValue> getLabel() {
        return null;
    }

    @Override
    public AndroidAttributeValue<ResourceValue> getIcon() {
        return null;
    }

    @Override
    public List<MetaData> getMetaDatas() {
        return null;
    }

    @Override
    public @Nullable XmlTag getXmlTag() {
        return null;
    }

    @Override
    public @Nullable XmlElement getXmlElement() {
        return null;
    }

    @Override
    public DomElement getParent() {
        return null;
    }

    @Override
    public XmlTag ensureTagExists() {
        return null;
    }

    @Override
    public XmlElement ensureXmlElementExists() {
        return null;
    }

    @Override
    public void undefine() {

    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public @NotNull DomGenericInfo getGenericInfo() {
        return null;
    }

    @Override
    public @NotNull @NlsSafe String getXmlElementName() {
        return null;
    }

    @Override
    public @NotNull @NlsSafe String getXmlElementNamespace() {
        return null;
    }

    @Override
    public @Nullable @NonNls String getXmlElementNamespaceKey() {
        return null;
    }

    @Override
    public void accept(DomElementVisitor visitor) {

    }

    @Override
    public void acceptChildren(DomElementVisitor visitor) {

    }

    @Override
    public @NotNull DomManager getManager() {
        return null;
    }

    @Override
    public @NotNull Type getDomElementType() {
        return null;
    }

    @Override
    public AbstractDomChildrenDescription getChildDescription() {
        return null;
    }

    @Override
    public @NotNull DomNameStrategy getNameStrategy() {
        return null;
    }

    @Override
    public @NotNull ElementPresentation getPresentation() {
        return null;
    }

    @Override
    public GlobalSearchScope getResolveScope() {
        return null;
    }

    @Override
    public <T extends DomElement> @Nullable T getParentOfType(Class<T> requiredClass, boolean strict) {
        return null;
    }

    @Override
    public @Nullable Module getModule() {
        return null;
    }

    @Override
    public void copyFrom(DomElement other) {

    }

    @Override
    public <T extends DomElement> T createMockCopy(boolean physical) {
        return null;
    }

    @Override
    public <T extends DomElement> T createStableCopy() {
        return null;
    }

    @Override
    public <T> @Nullable T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T value) {

    }

    @Override
    public <T extends Annotation> @Nullable T getAnnotation(Class<T> annotationClass) {
        return null;
    }
}
