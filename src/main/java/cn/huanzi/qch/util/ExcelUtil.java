package cn.huanzi.qch.util;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel工具类
 */
public class ExcelUtil {

    /**
     * 导出
     * 无需依赖POI
     */
    /*
        示例：
        try {
			//列名
			LinkedHashMap<String, String> columns = new LinkedHashMap<>(4);
			columns.put("id","编号");
			columns.put("name","名字");
			columns.put("age","年龄");
			columns.put("time","参加工作时间");

			//数据
			List<Map<String, Object>> datas = new ArrayList<>(3);
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("id","A001");
			hashMap.put("name","张三");
			hashMap.put("age",18);
			hashMap.put("time",new Date());
			datas.add(hashMap);

            //带换行符：&#10;
			HashMap<String, Object> hashMap2 = new HashMap<>();
			hashMap2.put("id","A002");
			hashMap2.put("name","李四&#10;李四1&#10;李四2");
			hashMap2.put("age",20);
			hashMap2.put("time",new Date());
			datas.add(hashMap2);

			HashMap<String, Object> hashMap3 = new HashMap<>();
			hashMap3.put("id","A003");
			hashMap3.put("name","王五");
			hashMap3.put("age",25);
			hashMap3.put("time",new Date());
			datas.add(hashMap3);

			//导出
			ExcelUtil.exportByResponse(this.getResponse(),"Excel导出测试",columns,datas);
			//ExcelUtil.exportByFile(new File("D:\\XFT User\\Downloads\\Excel导出测试.xls"),columns,datas);
        } catch (Exception e) {
            e.printStackTrace();
		}
     */
    public static void exportByResponse(HttpServletResponse response, String fileName, LinkedHashMap<String,String> columns, List<Map<String,Object>> datas) throws Exception {
        response.addHeader("Content-disposition","attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1") + ".xls");
        response.setContentType("application/ms-excel");

        StringBuilder sb = exportOfData(columns, datas);

        OutputStream out = response.getOutputStream();
        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();
        out.close();
    }
    public static void exportByFile(File file, LinkedHashMap<String,String> columns, List<Map<String,Object>> datas) {
        StringBuilder sb = exportOfData(columns, datas);

        try(FileWriter resultFile = new FileWriter(file, false);PrintWriter myFile = new PrintWriter(resultFile);) {
            myFile.println(sb.toString());
        } catch (Exception e) {
            System.err.println("exportByFile()，操作出错...");
            e.printStackTrace();
        }
        System.out.println(file.getName()+"，操作完成！");
    }
    private static StringBuilder exportOfData(LinkedHashMap<String, String> columns, List<Map<String, Object>> datas){
        StringBuilder sb = new StringBuilder("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        sb.append("<table border=\"1\">");

        //列名
        sb.append("<tr style=\"text-align: center;\">");
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            sb.append("<td style=\"background-color:#bad5fd\">" + entry.getValue() + "</td>");
        }
        sb.append("</tr>");

        //数据
        for (Map<String, Object> data : datas) {
            sb.append("<tr style=\"text-align: center;\">");
            for (Map.Entry<String, String> entry : columns.entrySet()) {
                Object dataValue = data.get(entry.getKey());

                //如果是日期类型
                if(dataValue instanceof java.util.Date){
                    dataValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dataValue);
                }
                sb.append("<td>" + dataValue.toString() + "</td>");
            }
            sb.append("</tr>");
        }

        sb.append("</table>");

        return sb;
    }
}
