package com.bcd.wx.repository;

import com.bcd.mongodb.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import com.bcd.wx.bean.AccountDetailBean;

@Repository
public interface AccountDetailRepository extends BaseRepository<AccountDetailBean, String> {

}
