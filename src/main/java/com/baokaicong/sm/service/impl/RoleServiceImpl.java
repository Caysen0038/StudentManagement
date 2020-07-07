package com.baokaicong.sm.service.impl;

import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Role;
import com.baokaicong.sm.bean.entity.RoleAuth;
import com.baokaicong.sm.dao.AuthDao;
import com.baokaicong.sm.dao.RoleAuthDao;
import com.baokaicong.sm.dao.RoleDao;
import com.baokaicong.sm.service.RoleService;
import com.baokaicong.sm.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:14
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleAuthDao roleAuthDao;

    @Autowired
    private AuthDao authDao;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Role queryById(Integer id) {
        return this.roleDao.queryById(id);
    }

    @Override
    public Role queryByRid(String rid) {
        return roleDao.queryByRid(rid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Role> queryByLimit(int offset, int limit) {
        return this.roleDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询角色的权限列表
     * @param rid
     * @return
     */
    @Override
    public List<Auth> listAuthByRid(String rid) {
        List<RoleAuth> list=roleAuthDao.queryAll(new RoleAuth().setRid(rid));
        List<Auth> auths=new ArrayList<>();
        for(RoleAuth auth:list){
            if(auth.getAuid().equals("*")){
                return authDao.queryAll(new Auth());
            }else{
                auths.add(authDao.queryByAuid(auth.getAuid()));
            }
        }
        return auths;
    }

    @Override
    public List<Role> filter(Role role, Page page, Order order) {
        PageHelper.startPage(page.getCurrent(), page.getPer(),order.orderBy());
        List<Role> list=roleDao.queryAll(role);
        PageInfo pageInfo = new PageInfo(list);
        page.setTotal(pageInfo.getPages());
        page.setCurrent(pageInfo.getPageNum());
        return list;
    }

    @Override
    public List<Role> filter(Role role) {
        List<Role> list=roleDao.queryAll(role);
        return list;
    }

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    public Role insert(Role role) {
        role.setRid(buildRid());
        this.roleDao.insert(role);
        return role;
    }

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Role role) {
        return this.roleDao.update(role)==1;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.roleDao.deleteById(id) > 0;
    }

    @Override
    public boolean addAuth(String rid, String auid) {
        RoleAuth roleAuth=new RoleAuth().setRid(rid).setAuid(auid);
        List<RoleAuth> list=roleAuthDao.queryAll(roleAuth);
        if(list!=null && list.size()>0){
            return false;
        }
        roleAuth.setTime(System.currentTimeMillis()+"");
        return roleAuthDao.insert(roleAuth)==1;
    }

    @Override
    public boolean dropAuth(String rid, String auid) {
        RoleAuth roleAuth=new RoleAuth().setRid(rid).setAuid(auid);
        List<RoleAuth> list=roleAuthDao.queryAll(roleAuth);
        if(list!=null && list.size()>0){
            for(RoleAuth ra:list){
                roleAuthDao.deleteById(ra.getId());
            }
            return true;
        }
        return false;
    }

    private String buildRid(){
        Role role;
        String rid;
        while(true){
            rid="R"+ StringUtil.getRandomNumber(5);
            role=roleDao.queryByRid(rid);
            if(role==null){
                return rid;
            }
        }
    }
}