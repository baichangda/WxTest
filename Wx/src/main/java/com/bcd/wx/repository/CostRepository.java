package com.bcd.wx.repository;

import com.bcd.mongodb.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import com.bcd.wx.bean.CostBean;

@Repository
public interface CostRepository extends BaseRepository<CostBean, String> {

}
