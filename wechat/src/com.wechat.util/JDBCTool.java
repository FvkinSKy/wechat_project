package com.wechat.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by rui on 2017/5/5.
 * 提供CRUD方法
 */
@Component("jDBCTool")
public class JDBCTool {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询
     *
     * @param sql
     * @return
     */
    public List<Map<String, Object>> findAll(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 单条更新,插入,删除
     *
     * @param sql
     */
    public boolean update(final String sql) {
        try {
            jdbcTemplate.update(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 批量插入,删除
     *
     * @param list
     * @return
     */
    public boolean batchInsOrDel(final List<String> list) {
        try {
            if (list == null) {
                return false;
            }
            String[] sqls = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                sqls[i] = list.get(i);
            }
            jdbcTemplate.batchUpdate(sqls);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
