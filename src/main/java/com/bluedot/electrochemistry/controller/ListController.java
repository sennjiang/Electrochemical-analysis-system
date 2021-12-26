package com.bluedot.electrochemistry.controller;

import com.bluedot.electrochemistry.service.search.condition.AccountCondition;
import com.bluedot.electrochemistry.service.search.main.SearchService;
import com.bluedot.electrochemistry.service.search.pages.AccountPage;
import com.bluedot.electrochemistry.service.search.pages.PageSearchable;
import com.bluedot.framework.simplespring.core.annotation.Controller;
import com.bluedot.framework.simplespring.core.annotation.RequestMapping;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

/**
 * @author Senn
 * @create 2021/12/26 17:10
 */
@Controller
@RequestMapping("/list")
public class ListController {

    @Autowired
    SearchService searchService;

    @RequestMapping("/users")
    void getAccountList(AccountCondition condition, int pageStart, int pageSize) {
        //     /list/users
        searchService.doService(condition,new AccountPage());
    }
}
