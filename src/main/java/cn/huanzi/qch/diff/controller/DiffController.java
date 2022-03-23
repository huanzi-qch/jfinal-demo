package cn.huanzi.qch.diff.controller;

import cn.huanzi.qch.common.model.Result;
import cn.huanzi.qch.diff.service.DiffService;
import cn.huanzi.qch.diff.service.DiffServiceImpl;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.log.Log;

@Path(value = "/diff",viewPath = "/diff")
public class DiffController extends Controller {
    private final Log log = Log.getLog(this.getClass());

    @Inject(DiffServiceImpl.class)
    DiffService diffService;

    public void index() {
        render("diff.html");
    }

    public void diffPrettyHtml() {
        String text1 = get("text1");
        String text2 = get("text2");

        String diffPrettyHtml = diffService.diffPrettyHtml(text1, text2);

        renderJson(Result.of(diffPrettyHtml));
    }
}
