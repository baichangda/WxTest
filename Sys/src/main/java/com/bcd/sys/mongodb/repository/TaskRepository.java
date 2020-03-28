package com.bcd.sys.mongodb.repository;

import com.bcd.mongodb.repository.BaseRepository;
import com.bcd.sys.mongodb.bean.TaskBean;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends BaseRepository<TaskBean, Long> {

}
