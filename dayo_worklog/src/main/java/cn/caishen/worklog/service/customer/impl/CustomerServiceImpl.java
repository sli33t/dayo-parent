package cn.caishen.worklog.service.customer.impl;

import cn.caishen.domain.utils.LbMap;
import cn.caishen.worklog.dao.AreaDao;
import cn.caishen.worklog.dao.CustomerDao;
import cn.caishen.domain.domain.po.Area;
import cn.caishen.domain.domain.po.Customer;
import cn.caishen.worklog.service.customer.CustomerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private AreaDao areaDao;

    @Override
    public PageInfo<LbMap> findAll(int page, int limit, LbMap param) {
        PageHelper.startPage(page, limit);
        /*QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("CREATE_TIME");
        wrapper.eq("DELETE_FLAG", 0);

        if (!param.getString("customerName").equals("")){
            wrapper.like("CUSTOMER_NAME", param.getString("customerName"));
        }

        if (!param.getString("telNo").equals("")){
            wrapper.like("TEL_NO", param.getString("telNo"));
        }

        List<Customer> list = customerDao.selectList(wrapper);*/

        List<LbMap> list = customerDao.findAll(param);
        return new PageInfo(list);
    }

    @Override
    public Customer findById(String customerId) {
        Customer customer = customerDao.selectById(customerId);

        Area area = areaDao.selectById(customer.getAreaId());
        if (area!=null){
            customer.setAreaName(area.getAreaName());
        }

        return customer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Customer customer) throws Exception {
        int count = customerDao.insert(customer);
        if (count<=0){
            throw new Exception("客户写入失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Customer customer) throws Exception {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("CUSTOMER_ID", customer.getCustomerId());
        wrapper.eq("ROW_VERSION", customer.getRowVersion());
        customer.setRowVersion(customer.getRowVersion()+1);
        int count = customerDao.update(customer, wrapper);
        if (count<=0){
            throw new Exception("客户更新失败，数据已被他人修改，请重新刷新！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String customerId, String rowVersion) throws Exception {
        Customer customer = new Customer();
        customer.setDeleteFlag(1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        customer.setDeleteDate(dateFormat.parse(dateFormat.format(new Date())));

        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("CUSTOMER_ID", customerId);
        wrapper.eq("ROW_VERSION", rowVersion);
        customer.setRowVersion(Integer.valueOf(rowVersion)+1);

        int count = customerDao.update(customer, wrapper);
        if (count<=0){
            throw new Exception("客户删除失败，请刷新数据！");
        }
    }

    @Override
    public List<Customer> checkCustomerInfo(String customerName) {
        List<Customer> list = customerDao.checkUserInfo(customerName);
        if (list!=null&&list.size()>0){
            return list;
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Customer> findAll() {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.and(Wrapper->Wrapper.eq("DELETE_FLAG", 0).or().isNull("DELETE_FLAG"));
        return customerDao.selectList(wrapper);
    }

    @Override
    public String findIdByName(String customerName) {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("CUSTOMER_NAME", customerName);
        List<Customer> customerList = customerDao.selectList(wrapper);
        if (customerList!=null&&customerList.size()>0){
            return customerList.get(0).getCustomerId();
        }
        return "";
    }
}
