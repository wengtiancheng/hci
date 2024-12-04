package com.example.demo.po;

import com.example.demo.vo.UserVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "password")
    private String password;

    //必须注意，在Java中用驼峰，在MySQL字段中用连字符_
    @Basic
    @Column(name = "create_time")
    private Date createTime;

    @ElementCollection
    private List<Integer> mySolutions;

    public UserVO toVO(){
        UserVO userVO=new UserVO();
        userVO.setId(id);
        userVO.setName(name);
        userVO.setPhone(phone);
        userVO.setPassword(password);
        userVO.setCreateTime(createTime);
        userVO.setMySolutions(mySolutions);
        return userVO;
    }
}
