package cn.huanzi.qch.util;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
    public static void exportByResponse(HttpServletResponse response, String fileName, LinkedHashMap<String, String> columns, List<Map<String, Object>> datas) throws Exception {
        response.addHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
        response.setContentType("application/ms-excel");

        StringBuilder sb = exportOfData(columns, datas);

        OutputStream out = response.getOutputStream();
        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    public static void exportByFile(File file, LinkedHashMap<String, String> columns, List<Map<String, Object>> datas) {
        StringBuilder sb = exportOfData(columns, datas);

        try (PrintWriter myFile = new PrintWriter(file,"UTF-8")) {
            myFile.println(sb);
        } catch (Exception e) {
            System.err.println("exportByFile()，操作出错...");
            e.printStackTrace();
        }
        System.out.println(file.getName() + "，操作完成！");
    }

    //其他单元格无边框
    private static StringBuilder exportOfData(LinkedHashMap<String, String> columns, List<Map<String, Object>> datas) {
        StringBuilder sb = new StringBuilder("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"" +
                "      xmlns:x=\"urn:schemas-microsoft-com:office:excel\"" +
                "      xmlns=\"http://www.w3.org/TR/REC-html40\">");

        //加这个，其他单元格带边框
        sb.append("<head>" +
                "    <xml>" +
                "        <x:ExcelWorkbook>" +
                "            <x:ExcelWorksheets>" +
                "                <x:ExcelWorksheet>" +
                "                    <x:Name></x:Name>" +
                "                    <x:WorksheetOptions>" +
                "                        <x:DisplayGridlines/>" +
                "                    </x:WorksheetOptions>" +
                "                </x:ExcelWorksheet>" +
                "            </x:ExcelWorksheets>" +
                "        </x:ExcelWorkbook>" +
                "    </xml>" +
                "   <style>td{font-family: \"宋体\";}</style>" +
                "</head>");

        sb.append("<body>");

        sb.append("<table border=\"1\">");

        //列名
        sb.append("<tr style=\"text-align: center;\">");
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            sb.append("<td style=\"background-color:#bad5fd\">").append(entry.getValue()).append("</td>");
        }
        sb.append("</tr>");

        //数据
        for (Map<String, Object> data : datas) {
            sb.append("<tr style=\"text-align: center;\">");
            for (Map.Entry<String, String> entry : columns.entrySet()) {
                Object dataValue = data.get(entry.getKey());

                //如果是日期类型
                if (dataValue instanceof java.util.Date) {
                    dataValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dataValue);
                }
                sb.append("<td>").append(dataValue.toString()).append("</td>");
            }
            sb.append("</tr>");
        }

        sb.append("</table>");

        sb.append("</body>");

        sb.append("</html>");

        return sb;
    }

    //前端导出Excel
    /*
        示例：
         exportExcel("xx业务Excel导出", {"id": "编号", "name": "名字", "age": "年龄", "time": "参加工作时间"}, [{
            "id": "A001",
            "name": "张三",
            "age": "18",
            "time": new Date().toLocaleString()
        },{
            "id": "A002",
            "name": "李四",
            "age": "20",
            "time": new Date().toLocaleString()
        }]);
     */
    /*
        //blob、base64转文件下载，通过A标签模拟点击,设置文件名
        //万能流  application/octet-stream
        //word文件  application/msword
        //excel文件  application/vnd.ms-excel
        //txt文件  text/plain
        //图片文件  image/png、jpeg、gif、bmp
        function downloadByBlob(fileName, text) {
            let a = document.createElement("a");
            a.href = URL.createObjectURL(new Blob([text], {type: "application/octet-stream"}));
            a.download = fileName || 'Blob导出测试.txt';
            a.click();
            a.remove();
            URL.revokeObjectURL(a.href);
        }
        function downloadByBase64(fileName, text) {
            let a = document.createElement('a');
            a.href = 'data:application/octet-stream;base64,' + window.btoa(unescape(encodeURIComponent(text)));
            a.download = fileName || 'Base64导出测试.txt';
            a.click();
            a.remove();
            URL.revokeObjectURL(a.href);
        }

        //踹掉后端，前端导出Excel！
        function exportExcel(fileName,columns,datas){
            //列名
            let columnHtml = "";
            columnHtml += "<tr style=\"text-align: center;\">\n";
            for (let key in columns) {
                columnHtml += "<td style=\"background-color:#bad5fd\">"+columns[key]+"</td>\n";
            }
            columnHtml += "</tr>\n";

            //数据
            let dataHtml = "";
            for (let data of datas) {
                dataHtml += "<tr style=\"text-align: center;\">\n";
                for (let key in columns) {
                    dataHtml += "<td>"+data[key]+"</td>\n";
                }
                dataHtml += "</tr>\n";
            }

            //完整html
            let excelHtml = "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
                    "      xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n" +
                    "      xmlns=\"http://www.w3.org/TR/REC-html40\">\n" +
                    "<head>\n" +
                    "   <!-- 加这个，其他单元格带边框 -->" +
                    "   <xml>\n" +
                    "        <x:ExcelWorkbook>\n" +
                    "            <x:ExcelWorksheets>\n" +
                    "                <x:ExcelWorksheet>\n" +
                    "                    <x:Name></x:Name>\n" +
                    "                    <x:WorksheetOptions>\n" +
                    "                        <x:DisplayGridlines/>\n" +
                    "                    </x:WorksheetOptions>\n" +
                    "                </x:ExcelWorksheet>\n" +
                    "            </x:ExcelWorksheets>\n" +
                    "        </x:ExcelWorkbook>\n" +
                    "   </xml>\n" +
                    "   <style>td{font-family: \"宋体\";}</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<table border=\"1\">\n" +
                    "    <thead>\n" +
                    columnHtml +
                    "    </thead>\n" +
                    "    <tbody>\n" +
                    dataHtml +
                    "    </tbody>\n" +
                    "</table>\n" +
                    "</body>\n" +
                    "</html>";

            //下载
            downloadByBlob((fileName || "导出Excel")+".xls",excelHtml);
        }
     */
}
