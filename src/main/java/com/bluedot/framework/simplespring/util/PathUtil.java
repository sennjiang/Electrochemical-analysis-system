package com.bluedot.framework.simplespring.util;

/**
 * @author JDsen99
 * @description 获取当前项目的地址
 * @createDate 2021/9/20-11:25
 */
public class PathUtil {

    public static String getAppPath() {
        Class<PathUtil> cls = PathUtil.class;
        ClassLoader loader = cls.getClassLoader();
        //获得类的全名，包括包名
        String clsName = cls.getName() + ".class";
        //获得传入参数所在的包
        Package pack = cls.getPackage();
        StringBuilder path = new StringBuilder();
        //如果不是匿名包，将包名转化为路径
        if (pack != null) {
            String packName = pack.getName();
            //在类的名称中，去掉包名的部分，获得类的文件名
            clsName = clsName.substring(packName.length() + 1);
            //判定包名是否是简单包名，如果是，则直接将包名转换为路径，
            if (!packName.contains(".")) path = new StringBuilder(packName + "/");
            else {//否则按照包名的组成部分，将包名转换为路径
                int start = 0, end = 0;
                end = packName.indexOf(".");
                while (end != -1) {
                    path.append(packName.substring(start, end)).append("/");
                    start = end + 1;
                    end = packName.indexOf(".", start);
                }
                path.append(packName.substring(start)).append("/");
            }
        }
        //调用ClassLoader的getResource方法，传入包含路径信息的类文件名
        java.net.URL url = loader.getResource(path + clsName);
        //从URL对象中获取路径信息
        assert url != null;
        String realPath = url.getPath();
        //去掉路径信息中的协议名"file:"
        int pos = realPath.indexOf("file:");
        if (pos > -1) realPath = realPath.substring(pos + 5);
        //去掉路径信息最后包含类文件信息的部分，得到类所在的路径
        pos = realPath.indexOf(path + clsName);
        realPath = realPath.substring(0, pos - 1);
        //如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
        if (realPath.endsWith("!"))
            realPath = realPath.substring(0, realPath.lastIndexOf("/"));
        if (realPath.startsWith("/"))
            realPath = realPath.substring(1);
        //去除最后的target/classes
        if (realPath.endsWith("/target/classes"))
            realPath = realPath.substring(0, realPath.length() - 15);
   /*------------------------------------------------------------
    ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径
     中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要
     的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的
     中文及空格路径
   -------------------------------------------------------------*/
        try {
            realPath = java.net.URLDecoder.decode(realPath, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return realPath;
    }
}
