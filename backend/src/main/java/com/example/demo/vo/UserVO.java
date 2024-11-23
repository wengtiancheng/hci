package com.example.demo.vo;

import com.example.demo.po.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserVO {
    private Integer id; // 用户id
    private String name; // 用户名
    private String phone; // 手机号
    private String password; // 密码
    private Date createTime; // 创建时间
    private List<Integer> mySolutions; // 我的方案 id 列表


    public User toPO(){
        User user=new User();
        user.setId(id);
        user.setName(name);
        user.setPhone(phone);
        user.setPassword(password);
        user.setCreateTime(createTime);
        user.setMySolutions(mySolutions);

        return user;
    }
}
