package com.bluedot.electrochemistry.dao.base;

import com.bluedot.electrochemistry.dao.BaseMapper;
import com.bluedot.framework.simplemybatis.session.SqlSession;
import com.bluedot.framework.simplemybatis.session.SqlSessionFactory;
import com.bluedot.framework.simplespring.core.annotation.Repository;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

/**
 * @author Sens
 * @description
 * @createDate 2021/8/25-14:37
 */
@Repository
public class BaseDao {

    @Autowired
    SqlSessionFactory sqlSessionFactory;


    public BaseMapper createMapper() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.setAutoCommit(true);
        BaseMapper mapper = sqlSession.getMapper(BaseMapper.class);
        return mapper;
    }

    /**
     * 注意：默认的session关闭了自动提交功能
     *
     * @return session
     */
    public SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }

    public SqlSession getAutoCommitSession() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.setAutoCommit(true);
        return sqlSession;
    }

    public <T> int insert(T type) {
        return insert(type,getAutoCommitSession());
    }

    /**
     * 插入记录
     * @param type  泛型 实体对象
     * @param sqlSession 数据库连接
     * @param <T> 泛型方法
     * @return 影响条数
     */
    private <T> int insert(T type, SqlSession sqlSession) {
        return sqlSession.insert(type);
    }

    /**
     * 批量更新 默认开启事务
     * @param ts T[]
     * @return 成功 返回 true,失败返回 false
     */
    public <T> boolean batchInsert(T[] ts) {
        SqlSession session = getSession();
        int res = 0;
        for (T t : ts) {
            insert(t,session);
        }
        boolean success = res == ts.length;
        if (success)
            session.commit();
        if (!success)
            session.rollback();
        return success;
    }


    public <T> int update(T type) {
        SqlSession session = getAutoCommitSession();
        return delete(type,session);
    }

    /**
     * 更新记录
     * @param type  泛型 实体对象
     * @param sqlSession 数据库连接
     * @param <T> 泛型方法
     * @return 影响条数
     */
    private <T> int update(T type, SqlSession sqlSession) {
        return sqlSession.update(type);
    }

    /**
     * 批量更新 默认开启事务
     * @param ts T[]
     * @return 成功 返回 true,失败返回 false
     */
    public <T> boolean batchUpdate(T[] ts) {
        SqlSession session = getSession();
        int res = 0;
        for (T t : ts) {
            update(t,session);
        }
        boolean success = res == ts.length;
        if (success)
            session.commit();
        if (!success)
            session.rollback();
        return success;
    }

    public <T> int delete(T type) {
        return delete(type,getAutoCommitSession());
    }

    /**
     * 删除记录
     * @param type  泛型 实体对象
     * @param sqlSession 数据库连接
     * @param <T> 泛型方法
     * @return 影响条数
     */
    private <T> int delete(T type, SqlSession sqlSession) {
        return sqlSession.delete(type);
    }


    /**
     * 批量删除 默认开启事务
     * @param ts T[]
     * @return 成功 返回 true,失败返回 false
     */
    public <T> boolean batchDelete(T[] ts) {
        SqlSession session = getSession();
        int res = 0;
        for (T t : ts) {
            res += delete(t,session);
        }
        boolean success = res == ts.length;
        if (success)
            session.commit();
        if (!success)
            session.rollback();
        return success;
    }
}
