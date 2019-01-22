package com.bcd.wx.repository;

import com.bcd.mongodb.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import com.bcd.wx.bean.AccountBean;

@Repository
public interface AccountRepository extends BaseRepository<AccountBean, String> {

}
