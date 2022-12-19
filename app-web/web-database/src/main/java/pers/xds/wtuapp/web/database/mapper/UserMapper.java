package pers.xds.wtuapp.web.database.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.lang.Nullable;
import pers.xds.wtuapp.web.database.bean.User;

import java.util.List;

/**
 * @author DeSen Xu
 * @date 2022-08-31 17:10
 */
@Mapper
public interface UserMapper  {

    /**
     * 根据用户名查找用户. 只会获取{@link User#password}, {@link User#role}, {@link User#userId}
     * @param username 用户名
     * @return 指定用户
     */
    @Nullable
    User findByUsername(@Param("username") String username);

    /**
     * 获取用户昵称和email，<b>昵称可能为空</b>
     * @param uid 用户id
     * @return 用户信息
     */
    User selectNameAndEmail(@Param("uid") int uid);

    /**
     * 用户名是否存在
     * @param username 要检查的用户名
     * @return 返回1表示存在，0表示不存在
     */
    @Select("SELECT COUNT(*) FROM user WHERE username = #{u}")
    int isUsernameExist(@Param("u") String username);

    /**
     * 学号是否存在
     * @param wtuId 学号id
     * @return 返回1表示存在，0表示不存在
     */
    @Select("SELECT COUNT(*) FROM user WHERE wtu_id = #{i}")
    int isWtuIdExist(@Param("i") String wtuId);

    /**
     * 获取指定id的用户
     * @param ids 一组id
     * @return 用户信息
     */
    List<User> selectAllByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据id获取用户简单信息
     * @param id id
     * @return 用户简单信息
     */
    User selectByIdForQuery(@Param("id") int id);

    /**
     * 插入一个用户
     * @param user 用户
     * @return 用户id
     */
    int insert(@Param("u") User user);

    /**
     * 获取用户详细信息
     * @param uid 用户id
     * @return 用户详细信息，包括{@link User#email}, {@link User#nickname}, {@link User#wtuId}, {@link User#name}
     * {@link User#className}
     */
    User selectUserDetail(@Param("uid") int uid);
}
