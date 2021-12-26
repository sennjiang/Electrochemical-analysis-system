package com.bluedot.electrochemistry.service.search.main;

import com.bluedot.electrochemistry.pojo.domain.User;
import com.bluedot.electrochemistry.service.search.SearchDirection;
import com.bluedot.electrochemistry.service.search.condition.AccountCondition;
import com.bluedot.electrochemistry.service.search.condition.Conditional;
import com.bluedot.electrochemistry.service.search.pages.AccountPage;
import com.bluedot.electrochemistry.service.search.pages.PageSearchable;
import com.bluedot.framework.simplespring.util.JsonUtil;

import java.util.List;

/**
 * @author Senn
 * @create 2021/12/26 17:00
 */
public class SearchService implements SearchModularity{

    SearchDirection direction;

    @Override
    public List<?> doService(Conditional condition, PageSearchable<?> pageSearchable) {
        return pageSearchable.search(condition,0,10);
    }

    public static void main(String[] args) {
        SearchService service = new SearchService();
        AccountCondition accountCondition = new AccountCondition();
        AccountPage accountPage = new AccountPage();
        List<?> list = service.doService(accountCondition, accountPage);
        System.out.println(JsonUtil.toJson(list));
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }


}
