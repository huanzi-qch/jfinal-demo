package cn.huanzi.qch.diff.service;

import name.fraser.neil.plaintext.diff_match_patch;

import java.util.LinkedList;

public interface DiffService {
    LinkedList<diff_match_patch.Diff> diffMain(String text1, String text2);
    String diffPrettyHtml(String text1, String text2);
}
