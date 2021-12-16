package com.bluedot.electrochemistry.service.search.pages;

import com.bluedot.electrochemistry.dao.BaseMapper;
import com.bluedot.electrochemistry.pojo.domain.User;

import java.util.List;

/**
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public class AccountPage extends AbstractPageSearch<User>{

    @Override
    List<User> getList(BaseMapper mapper, String condition, int pageStart, int pageSize) {
        return mapper.getList(condition,pageStart,pageSize);
    }
}
