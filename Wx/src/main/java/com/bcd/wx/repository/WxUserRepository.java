package com.bcd.wx.repository;

import com.bcd.mongodb.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import com.bcd.wx.bean.WxUserBean;

@Repository
public interface WxUserRepository extends BaseRepository<WxUserBean, String> {

}
