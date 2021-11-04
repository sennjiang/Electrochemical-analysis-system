package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.File;
import com.bluedot.electrochemistry.pojo.domain.Freeze;
import com.bluedot.electrochemistry.pojo.domain.Unfreeze;
import com.bluedot.electrochemistry.pojo.domain.User;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户业务
 *
 * @author KangLongPing
 * @version 1.0
 * @date 2021/9/4 17:07
 **/
@Service
public class UserService extends BaseService {

    @Autowired
    MapperFactory mapperFactory;
    @Autowired
    BaseDao baseDao;

    /**
     * 登录方法
     *
     * @param map User实体类
     */
    private void login(Map map) {

        String username = (String) map.get("username");
        String password = (String) map.get("password");
        BaseMapper mapper = mapperFactory.createMapper();
        User user = mapper.queryUserByUsername(Integer.parseInt(username));
        System.out.println(user);
        if (user != null && password.equals(user.getPassword())) {
            map.put("userInfo", user);
            map.put("code", 200);
            map.put("message", "登录成功");
        } else {
            map.put("code", 500);
            map.put("message", "账号或者密码错误");
        }
    }

    /**
     * 根据用户名查询用户
     *
     * @param map User实体类
     */
    private void queryUser(Map map) {
    }

    /**
     * 修改用户个人信息
     *
     * @param map ResultBean实休类
     */
    private void modifyUser(Map<String, Object> map) {
        doSimpleModifyTemplate(map, new ServiceCallback<User>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                return baseDao.update(parseToUser(map));
            }
        });
    }

    /**
     * 添加用户
     *
     * @param map User实体类
     */
    private void addUSer(Map map) throws ParseException {
        BaseMapper mapper = mapperFactory.createMapper();

        // 1.生成username
        int max = 99999999, min = 10000000;
        long randomNum;
        int username;
        randomNum = System.currentTimeMillis();
        username = (int) (randomNum % (max - min) + min);
        // 保证用户名的唯一性
        while ((mapper.countUserByUsername(username) > 0)) {
            randomNum = System.currentTimeMillis();
            username = (int) (randomNum % (max - min) + min);
        }
        // 2.昵称
        String nickname = (String) map.get("nickname");
        // 3.密码
        String password = (String) map.get("pwd1");
        // 4.性别
        String gender = (String) map.get("gender");
        // 5.生日
        String birth = (String) map.get("birth");
        // 6.邮箱
        String email = (String) map.get("email");
        // 7.注册用户状态默认为1
        int status = 1;
        // 8.头像
        String portrait = "https://typorasss2021.oss-cn-shenzhen.aliyuncs.com/mixue.png";
        // 9.年龄
        int age = 18;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDay = sdf.parse(birth);
            Calendar cal = Calendar.getInstance();
            if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
                throw new IllegalArgumentException(
                        "The birthDay is before Now.It's unbelievable!");
            }
            int yearNow = cal.get(Calendar.YEAR);  //当前年份
            int monthNow = cal.get(Calendar.MONTH);  //当前月份
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
            cal.setTime(birthDay);
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            age = yearNow - yearBirth;   //计算整岁数
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
                } else {
                    age--;//当前月份在生日之前，年龄减一
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 10.记录创建时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String gmtCreated = df.format(new Date());
        ;// new Date()为获取当前系统时间
        Timestamp birthTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(birth).getTime());
        Timestamp gmtCreatedTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(gmtCreated).getTime());
        System.out.println("******************************");
        System.out.println(username + password + nickname + Integer.parseInt(gender) + age + email + birthTime + status + portrait + gmtCreatedTime);
        User newUser = new User(username, password, nickname, Integer.parseInt(gender), age, email, birthTime, status, portrait, gmtCreatedTime);
        int res = baseDao.insert(newUser);
        if (res > 0) {
            map.put("code", 200);
            map.put("message", "注册成功啦");
        } else {
            map.put("code", 500);
            map.put("message", "注册失败");
        }
    }

    /**
     * 用户申请解冻
     *
     * @param map UnfreezeApplication实体类
     */
    private void deleteUSerByUsername(Map map) {
        doSimpleModifyTemplate(map, new ServiceCallback<User>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                return baseDao.delete(parseToUser(map));
            }
        });
    }

    /**
     * 是否存在该用户
     *
     * @param map
     */
    private void existUserByEmail(Map map) {
        String email = (String) map.get("email");
        BaseMapper mapper = mapperFactory.createMapper();
        Long userCount = mapper.countUserByEmail(email);
        if (userCount > 0) {
            map.put("code", 500);
            map.put("message", "用户已存在");
        } else {
            map.put("code", 200);
            map.put("message", "可以注册");
        }
    }

    /**
     * 发送邮件
     * @param map
     * @throws Exception
     */
    private void sendEmail(Map map) throws Exception {
        // 创建Properties 类用于记录邮箱的一些属性
        Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host", "smtp.163.com");
        //端口号，QQ邮箱端口587
        props.put("mail.smtp.port", "25");
        // 此处填写，写信人的账号
        props.put("mail.user", "klpjxau@163.com");
        // 此处填写16位STMP口令
        props.put("mail.password", "NWDATKARUUTXXKJY");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {

            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人的邮箱
        InternetAddress to = new InternetAddress((String) map.get("email"));
        message.setRecipient(Message.RecipientType.TO, to);

        // 设置邮件标题
        message.setSubject("[电化学分析系统] 注册验证码");

        // 要发送的验证码
        String emailCode = UUID.randomUUID().toString().replace("-", "").toLowerCase().substring(0, 5);

        // 设置邮件的内容体
        message.setContent("[电化学分析系统]您的验证码为：" + emailCode, "text/html;charset=UTF-8");

        // 最后当然就是发送邮件啦
        Transport.send(message);

        map.put("code", "200");
        map.put("emailCode", emailCode);
        map.put("message", "邮件已发送");
    }

    /**
     * 根据username获取freeze信息
     * @param map [username]
     */
    private void getFreezeInfo(Map map) {
        String email = (String) map.get("email");
        BaseMapper mapper = mapperFactory.createMapper();
        User user = mapper.queryUserByEmail(email);
        Freeze freeze = mapper.queryFreezeByUsername(user.getUsername());
        // 时间格式化
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        freeze.setFreezeTime(Timestamp.valueOf(sdf.format(freeze.getFreezeTime())));
        map.put("freeze",freeze);
        map.put("nickname",user.getNickname());
    }

    /**
     * 存储解决申请信息
     * @param map
     */
    private void saveUnfreezeInfo(Map map) {
        doSimpleModifyTemplate(map, new ServiceCallback<User>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                return baseDao.insert(parseToUnfreeze(map));
            }
        });
    }

    /**
     * 添加管理员
     *
     * @param map User实体类
     */
    private void addAdmin(Map map) {
    }

    /**
     * 封闭成unfreeze对象
     * @param map
     * @return
     */
    private Unfreeze parseToUnfreeze(Map<String, Object> map) {
        Integer freezeId = Integer.parseInt((String) map.get("freezeId"));
        Long username = Long.parseLong((String) map.get("username"));
        String email = (String) map.get("email");
        Integer handleStatus = Integer.parseInt((String) map.get("handleStatus"));
        String applicationReason = (String) map.get("applicationReason");
        System.out.println("******************");
        System.out.println(applicationReason);
        Unfreeze unfreeze = new Unfreeze();
        unfreeze.setFreezeId(freezeId);
        unfreeze.setUsername(username);
        unfreeze.setEmail(email);
        unfreeze.setHandleStatus(handleStatus);
        unfreeze.setApplicationReason(applicationReason);
        return unfreeze;
    }

    /**
     * 上传头像
     * @param map 头像图片
     */
    private void uploadAvatar(Map<String, Object> map) {
        java.io.File file = (java.io.File) map.get("file");
        String portraitPath = "avatar/" + file.getName();
        Integer username = Integer.parseInt((String) map.get("username"));
        User user = new User();
        user.setUsername(username);
        user.setPortrait(portraitPath);
        doSimpleModifyTemplate(map, new ServiceCallback<User>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                return baseDao.update(user);
            }
        });
    }

    /**
     * 将请求数据中的信息封装成用户对象
     *
     * @param map
     * @return
     */
    private User parseToUser(Map<String, Object> map) {
        Integer username = Integer.parseInt((String) map.get("username"));
        String password = (String) map.get("password");
        String nickname = (String) map.get("nickname");
        Integer gender = (Integer) map.get("gender");
        Integer age = (Integer) map.get("age");
        String email = (String) map.get("email");
        Timestamp birth = (Timestamp) map.get("birth");
        Integer status = (Integer) map.get("status");
        String portrait = (String) map.get("portrait");
        Timestamp gmtCreated = (Timestamp) map.get("gmtCreated");
        return new User(username, password, nickname, gender, age, email, birth, status, portrait, gmtCreated);
    }
}
