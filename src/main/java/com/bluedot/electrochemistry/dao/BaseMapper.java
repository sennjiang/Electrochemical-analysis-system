package com.bluedot.electrochemistry.dao;

import com.bluedot.electrochemistry.pojo.domain.User;

import java.util.List;

/**
 * @author Sens
 * @createDate 2021/12/16 19:19
 */
public interface BaseMapper {

    List<User> getList(String condition, int pageStart, int pageSize);
}
