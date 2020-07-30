package cn.caishen.worklog.service.system.impl;

import cn.caishen.domain.utils.LbMap;
import cn.caishen.worklog.dao.AreaDao;
import cn.caishen.worklog.dao.CustomerDao;
import cn.caishen.worklog.dao.UserDao;
import cn.caishen.worklog.dao.VersionDao;
import cn.caishen.domain.domain.po.Version;
import cn.caishen.worklog.service.system.SelectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private VersionDao versionDao;

    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo findArea(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = areaDao.findArea();
        return new PageInfo(list);
    }

    @Override
    public PageInfo<LbMap> findCustomer(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = customerDao.findCustomer();
        return new PageInfo(list);
    }

    @Override
    public List<Version> findVersion() {
        QueryWrapper<Version> wrapper = new QueryWrapper<>();
        wrapper.eq("DELETE_FLAG", 0);
        List<Version> list = versionDao.selectList(wrapper);
        return list;
    }

    @Override
    public PageInfo<LbMap> findUser(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = areaDao.findUser();
        return new PageInfo(list);
    }
}
