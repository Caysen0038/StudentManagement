package com.baokaicong.sm.dao;

import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.bean.entity.Course;
import com.baokaicong.sm.dao.provider.AuthProvider;
import com.baokaicong.sm.dao.provider.CourseProvider;
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
public interface CourseDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Select("select *"+
            "        from sm.t_course" +
            "        where id = #{id}")
    Course queryById(Long id);

    /**
     * 通过Auid查询单条数据
     * @param coid
     * @return
     */
    @Select("select *"+
            "        from sm.t_course" +
            "        where coid = #{coid}" +
            "        limit 0,1")
    Course queryByCoid(String coid);

//    @Select("select * from sm.t_course " +
//            "where coid in(select coid from t_student_course where sid=#{sid})")
//    List<Course> queryBySid(String sid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Select("select *"+
            "        from sm.t_course" +
            "        limit #{offset}, #{limit}")
    List<Course> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param course 实例对象
     * @return 对象列表
     */
    @SelectProvider(type= CourseProvider.class,method = "queryAll")
    List<Course> queryAll(Course course);

    /**
     * 新增数据
     *
     * @param course 实例对象
     * @return 影响行数
     */
    @Insert("insert into sm.t_course(coid, name,credit,type)" +
            "        values (#{coid}, #{name},#{credit},#{type})")
    int insert(Course course);

    /**
     * 修改数据
     *
     * @param course 实例对象
     * @return 影响行数
     */
    @UpdateProvider(type = CourseProvider.class,method = "update")
    int update(Course course);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from sm.t_course where id = #{id}")
    int deleteById(Long id);

}