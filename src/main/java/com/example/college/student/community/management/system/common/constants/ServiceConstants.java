package com.example.college.student.community.management.system.common.constants;

public interface ServiceConstants {

    /**
     * 状态: 禁用
     */
    String STATUS_DISABLE = "0";

    /**
     * 状态: 启用
     */
    String STATUS_ENABLE = "1";

    /**
     * 查询: 查询列表的首页页码
     */
    Integer SELECT_LIST_HOME_PAGE_NUM = 1;

    /**
     * 查询: 查询列表的查询所有页码
     */
    Integer SELECT_LIST_ALL_PAGE_SIZE = -1;

    /**
     * 查询: 查询列表的单条数据记录数
     */
    Integer SELECT_LIST_ONE_PAGE_SIZE = 1;

    /**
     * 科室业务: 业务科室
     */
    String DEPT_EXAMINE = "1";

    /**
     * 科室业务: 非业务科室
     */
    String DEPT_NOT_EXAMINE = "0";
}
