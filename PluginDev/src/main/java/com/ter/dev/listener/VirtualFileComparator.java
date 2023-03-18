package com.ter.dev.listener;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.diff.Diff;
import name.fraser.neil.plaintext.diff_match_patch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class VirtualFileComparator {

    private VirtualFile oldFile;
    private VirtualFile newFile;
    private String fileName;

    private static diff_match_patch diffMatchPatch = new diff_match_patch();

    public VirtualFileComparator(VirtualFile oldFile, VirtualFile newFile, String fileName) {
        this.oldFile = oldFile;
        this.newFile = newFile;
        this.fileName = fileName;
        System.out.println("Debug : new comparator");

    }

    public void compareVirtualFiles() {
        System.out.println("Debug : compareVirtualFiles");
        if (oldFile == null || newFile == null) {
            return;
        }
        System.out.println("Debug : compareVirtualFiles : non null files");

        try {
            byte[] oldContent = oldFile.contentsToByteArray();
            byte[] newContent = newFile.contentsToByteArray();

            String oldText = new String(oldContent, StandardCharsets.UTF_8);
            String newText = new String(newContent, StandardCharsets.UTF_8);

            System.out.println("Debug : compareVirtualFiles : old text :\n"+oldText);
            System.out.println("Debug : compareVirtualFiles : new text :\n"+newText);

            //Split text into List of strings
            List<String> oldTextList = Arrays.asList(oldText.split("(\\.|\\n)"));
            List<String> newTextList = Arrays.asList(newText.split("(\\.|\\n)"));

            //If we have different length
            int counter = Math.max(oldTextList.size(), newTextList.size());
            StringBuilder sb = new StringBuilder();


            for(int current = 0; current < counter; current++){
                String oldString = null;
                String newString = null;

                if(oldTextList.size() <= current){
                    oldString = "";
                    newString = newTextList.get(current);
                } else if (newTextList.size() <= current){
                    oldString = oldTextList.get(current);
                    newString = "";
                } else {
                    System.out.println("Debug : compareVirtualFiles : else");

                    if (isLineDifferent(oldTextList.get(current), newTextList.get(current))){
                        System.out.println("Debug : compareVirtualFiles : line is different");

                        oldString = oldTextList.get(current);
                        newString = newTextList.get(current);
                    }
                }

                if(oldString != null && newString != null) {
                    //---- Insert into database here -----
                    sb.append("Changes for Line: " + current + "\n");
                    sb.append("Old: " + oldString + "; New: " + newString +";\n");

                    System.out.println("Debug : compareVirtualFiles : there is some changes");
                }
            }

            System.out.println("Debug : compareVirtualFiles : end compare");
            System.out.println(sb.toString());

        } catch (IOException e) {
            System.out.println("Error reading file contents: " + e.getMessage());
        }
    }

    private static boolean isLineDifferent(String oldString, String newString) {
        LinkedList<diff_match_patch.Diff> deltas = diffMatchPatch.diff_main(oldString,newString);
        System.out.println("Debug : old : "+oldString);
        System.out.println("Debug : new : "+newString);

        for(diff_match_patch.Diff d : deltas){
            System.out.println("Debug : differences : "+d.operation.name());
            if (d.operation == diff_match_patch.Operation.EQUAL) continue;
            return true;
        }
        return false;
    }

}




