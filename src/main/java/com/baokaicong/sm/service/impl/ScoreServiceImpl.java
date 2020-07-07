package com.baokaicong.sm.service.impl;


import com.baokaicong.sm.bean.Order;
import com.baokaicong.sm.bean.Page;
import com.baokaicong.sm.bean.entity.Course;
import com.baokaicong.sm.bean.entity.Score;
import com.baokaicong.sm.dao.ScoreDao;
import com.baokaicong.sm.service.CourseService;
import com.baokaicong.sm.service.ScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreDao scoreDao;

    @Override
    public Score queryById(Long id) {
        return scoreDao.queryById(id);
    }

    @Override
    public List<Score> queryBySid(String sid) {
        return scoreDao.queryAll(new Score().setSid(sid));
    }

    @Override
    public List<Score> queryByTid(String tid) {
        return scoreDao.queryAll(new Score().setTid(tid));
    }

    @Override
    public List<Score> queryByLimit(int offset, int limit) {
        return scoreDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<Score> filter(Score score, Page page, Order order) {
        PageHelper.startPage(page.getCurrent(), page.getPer(),order.orderBy());
        List<Score> list=scoreDao.queryAll(score);
        PageInfo pageInfo = new PageInfo(list);
        page.setTotal(pageInfo.getPages());
        page.setCurrent(pageInfo.getPageNum());
        return list;
    }

    @Override
    public List<Score> filter(Score score) {
        return scoreDao.queryAll(score);
    }

    @Override
    public Score insert(Score score) {
        score.setStatus(0);
        scoreDao.insert(score);
        return score;
    }

    @Override
    public boolean update(Score score) {
        return scoreDao.update(score)==1;
    }

    @Override
    public boolean deleteById(Long id) {
        return scoreDao.deleteById(id)==1;
    }
}
