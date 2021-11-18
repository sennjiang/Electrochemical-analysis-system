package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.annotation.Service;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jun
 * @version 1.0
 * @date 2021/9/3 19:29
 */
@Service
public class SystemService extends BaseService {

    private static String hostIP = "rm-bp1v25gip581jpm37oo.mysql.rds.aliyuncs.com";
    private static String userName = "allblue";
    private static String password = "2019Blue";
    //sql文件存储的路径
    private String savePath = "D:/MysqlFile";

    //数据库名
    private static String databaseName = "ea";
    private static final int BUFFER = 8192;

    private String sqlFilePath;

    //系统还原
    private void systemReduction(Map<String,Object> map ){
        try{
            File file = new File("D:/MysqlFile");
            String[] paths = file.list();
            List<com.bluedot.electrochemistry.pojo.domain.File> fileList = new ArrayList<com.bluedot.electrochemistry.pojo.domain.File>();
            int idx = 0;
            for (String path : paths) {
                com.bluedot.electrochemistry.pojo.domain.File file1 = new com.bluedot.electrochemistry.pojo.domain.File(++idx,path,"D:/MysqlFile",(Integer)map.get("username"),"2 KB","1",1,1,(Timestamp)(new Date()),(Timestamp)(new Date()));
                fileList.add(file1);
            }
            map.put("fileList",fileList);
            map.put("length",paths.length);
            map.put("code",200);
            map.put("message","查询成功");
        }catch (Exception e){
            map.put("code",500);
            map.put("message","查询失败");
        }

    }

    //系统备份
    private void systemBackup(Map<String,Object> map ){
        try{

            //sql文件存储名
            String fileName = "badaoBak"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".sql";
            //用户输入的sql文件的存储路径
            savePath = (String) map.get("filePath");


            dataBakExec(fileName);
            System.out.println("备份的sql文件保存路径为:"+this.sqlFilePath);
            map.put("sqlFilePath",this.sqlFilePath);

            map.put("code",200);
            map.put("message","备份成功,路径为："+sqlFilePath);
        }catch (Exception e){
            map.put("code",500);
            map.put("message","备份失败");
        }
    }

    //下载备份文件压缩包
    private File downloadBackupFile(Map map){
        return new File("1","1");
    }

    //删除当前的FTP
    private int deleteFTP(Map map){
        return 1;
    }

    /**
     * 执行数据备份
     * @return
     */
    private void dataBakExec(String fileName)
    {

        File saveFile = new File(savePath);
        // 如果目录不存在
        if (!saveFile.exists()) {
            // 创建文件夹
            saveFile.mkdirs();
        }
        if(!savePath.endsWith(File.separator)){
            savePath = savePath + File.separator;
        }

        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
            this.sqlFilePath= savePath + fileName;
            //导出指定数据库的结构和数据
            Process process = Runtime.getRuntime().exec("E:/software/mysql8-winx64/mysql-8.0.21-winx64/bin/mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName);

            //导出指定数据库指定表的结构和数据
            //Process process = Runtime.getRuntime().exec("E:/software/mysql8-winx64/mysql-8.0.21-winx64/bin/mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName +" book ");
            //导出指定数据库指定表的结构
            //Process process = Runtime.getRuntime().exec("C:/Program Files/MySQL/MySQL Server 5.6/bin/mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName + " book -d");
            //导出指定数据库指定表符合条件的结构和数据
            //Process process = Runtime.getRuntime().exec("C:/Program Files/MySQL/MySQL Server 5.6/bin/mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName +" book "+" --where=\" price> 100" + "\" ");
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine())!= null){
                printWriter.println(line);
            }
            printWriter.flush();
            //0 表示线程正常终止。
            if(process.waitFor() == 0){
                System.out.println("备份数据成功");

            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
