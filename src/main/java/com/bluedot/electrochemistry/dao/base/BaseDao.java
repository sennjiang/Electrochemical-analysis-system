package com.bluedot.electrochemistry.dao.base;

import com.bluedot.electrochemistry.dao.BaseMapper;
import com.bluedot.framework.simplemybatis.session.SqlSession;
import com.bluedot.framework.simplemybatis.session.SqlSessionFactory;
import com.bluedot.framework.simplespring.core.annotation.Repository;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

/**
 * @author JDsen99
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
        return getAutoCommitSession().insert(type);
    }

    public <T> int update(T type) {
        return getAutoCommitSession().update(type);
    }

    public <T> int delete(T type) {
        return getAutoCommitSession().delete(type);
    }

}
