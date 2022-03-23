package cn.huanzi.qch.diff.service;

import com.jfinal.aop.Inject;
import name.fraser.neil.plaintext.diff_match_patch;

import java.util.LinkedList;

public class DiffServiceImpl implements DiffService{

    @Inject(diff_match_patch.class)
    private diff_match_patch dmp;

    @Override
    public LinkedList<diff_match_patch.Diff> diffMain(String text1, String text2) {
        return dmp.diff_main(text1, text2);
    }

    @Override
    public String diffPrettyHtml(String text1, String text2) {
        return dmp.diff_prettyHtml(diffMain(text1, text2));
    }
}
