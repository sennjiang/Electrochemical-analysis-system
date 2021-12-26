package com.bluedot.electrochemistry.service.search.pages;

import com.bluedot.electrochemistry.dao.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.User;
import com.bluedot.electrochemistry.service.search.condition.Conditional;
import com.bluedot.framework.simplespring.core.annotation.Component;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sens
 * @createDate 2021/12/16 19:34
 */
abstract class AbstractPageSearch<E> implements PageSearchable<E>{

    @Autowired
    protected MapperFactory mapperFactory;

    @Override
    public List<E> search(Conditional condition, int pageStart, int pageSize) {
        String sql = condition.decodeCondition();
        BaseMapper mapper = new BaseMapper() {
            @Override
            public List<User> getList(String condition, int pageStart, int pageSize) {
                ArrayList<User> list = new ArrayList<>();
                User user1 = new User();
                User user2 = new User();
                user1.setUsername("1");
                user2.setUsername("2");
                list.add(user1);
                list.add(user2);
                return list;
            }
        };
        return getList(mapper,sql, pageStart, pageSize);
    }

    abstract List<E> getList(BaseMapper mapper, String condition, int pageStart, int pageSize);
}
