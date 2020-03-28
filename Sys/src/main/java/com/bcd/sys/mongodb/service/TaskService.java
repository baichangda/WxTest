package com.bcd.sys.mongodb.service;

import com.bcd.mongodb.service.BaseService;
import com.bcd.sys.mongodb.bean.TaskBean;
import com.bcd.sys.task.Task;
import com.bcd.sys.task.TaskDAO;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 *
 */
@Service
public class TaskService extends BaseService<TaskBean,Long> implements TaskDAO<Long,TaskBean> {
    @Override
    public Serializable doCreate(TaskBean task) {
        return save(task).getId();
    }

    @Override
    public Task doRead(Long id) {
        return findById(id);
    }

    @Override
    public void doUpdate(TaskBean task) {
        save(task);
    }

    @Override
    public void doDelete(TaskBean task) {
        delete(task);
    }
}
