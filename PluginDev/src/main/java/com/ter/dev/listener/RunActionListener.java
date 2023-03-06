package com.ter.dev.listener;

import com.android.tools.idea.uibuilder.handlers.motion.editor.adapters.Annotations.NotNull;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ex.AnActionListener;

public class RunActionListener implements AnActionListener {

    @Override
    public void beforeActionPerformed(@NotNull AnAction action, @NotNull AnActionEvent event) {

        if (action.getClass().getSimpleName().equals("ExecutorAction"))
            System.out.println("Run button is clicked....");


        if (action.getClass().getSimpleName().equals("CompileDirtyAction"))
            System.out.println("Build button is clicked");
    }

}
