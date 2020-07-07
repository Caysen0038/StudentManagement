package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.StudentCourse;
import com.baokaicong.sm.bean.entity.TeacherCourse;
import com.baokaicong.sm.dao.provider.AuthProvider;
import com.baokaicong.sm.dao.provider.TeacherCourseProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 包凯聪
 * @since 2020-05-11 21:40:12
 */
@Mapper
@Repository
public interface TeacherCourseDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_teacher_course" +
            "        where id = #{id}")
    TeacherCourse queryById(Long id);

    /**
     * 通过sid查询单条数据
     * @param tid
     * @return
     */
    @Select("select *"+
            "        from sm.t_teacher_course" +
            "        where tid = #{tid}" +
            "        limit 0,1")
    TeacherCourse queryByTid(String tid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_teacher_course" +
            "        limit #{offset}, #{limit}")
    List<TeacherCourse> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param teacherCourse 实例对象
     * @return 对象列表
     */
    @SelectProvider(type= TeacherCourseProvider.class,method = "queryAll")
    List<TeacherCourse> queryAll(TeacherCourse teacherCourse);

    /**
     * 新增数据
     *
     * @param teacherCourse 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_teacher_course(tid, coid,cid, term,week,section,status,type)\n" +
            "        values (#{tid}, #{coid},#{cid}, #{term},#{week},#{section},#{status},#{type})")
    int insert(TeacherCourse teacherCourse);

    /**
     * 修改数据
     *
     * @param teacherCourse 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = TeacherCourseProvider.class,method = "update")
    int update(TeacherCourse teacherCourse);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_teacher_course where id = #{id}")
    int deleteById(Long id);

}