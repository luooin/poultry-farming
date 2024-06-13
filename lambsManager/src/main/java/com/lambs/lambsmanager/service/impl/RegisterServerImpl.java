package com.lambs.lambsmanager.service.impl;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.dao.RegisterMapper;
import com.lambs.lambsmanager.pojo.Register;
import com.lambs.lambsmanager.pojo.User;
import com.lambs.lambsmanager.service.LoginServer;
import com.lambs.lambsmanager.service.RegisterServer;
import com.lambs.lambsmanager.service.UserServer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegisterServerImpl implements RegisterServer {
    //导入映射层
    @Resource
    private RegisterMapper registerMapper;
    @Resource
    private LoginServer loginServer;
    @Resource
    private UserServer userServer;
    //导入邮件对象
    @Resource
    private JavaMailSenderImpl mailSender;

    //导入邮件配置
    @Value("${labms.from}")
    private String from;
    @Value("${labms.subject}")
    private String subject;
    @Value("${labms.agree}")
    private String agree;
    @Value("${labms.regect}")
    private String regect;

    /**
     * 单独抽取发送邮件方法
     *
     * @param setTo
     * @param setText
     */
    private void sendEmail(String setTo, String setText) {
        //简单邮件对象
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //发送人
        simpleMailMessage.setFrom(from);
        //收件人
        simpleMailMessage.setTo(setTo);
        //标题
        simpleMailMessage.setSubject(subject);
        //内容
        simpleMailMessage.setText(setText);
        //发送邮件
        mailSender.send(simpleMailMessage);
    }

    /**
     * 传入参数则按照条件查询,没有则查询所有(用于登录界面的注册和系统管理的用户注册)
     *
     * @param register
     * @return
     */
    @Override
    public List<Register> getRegisterList(Register register) {
        //调用查询方法查询注册信息
        List<Register> registerList = registerMapper.getRegisterList(register);
        //如果注册信息未空
        if (registerList.isEmpty()) {
            //未查询到注册信息,则查询用户信息
            User user = new User();
            user.setPhone(register.getPhone());
            //调用方法,传入指定的用户联系方式信息
            List<User> userListByUser = userServer.getUserListByUser(user);
            //判断是否查询到信息
            if (!userListByUser.isEmpty()) {
                throw new MyException("当前用户已存在");
            }
            throw new MyException("暂无信息");
        }
        //查询到注册信息
        if (registerList.size() == 1) {
            //查询到单个用户,获取第一条数据
            Register enroll = registerList.get(0);
            //对数据中的状态进行判断
            //状态为1时待审核
            if (enroll.getStatus() == 1) {
                throw new MyException("当前用户绑定的手机号正在审核...");
                //状态为2时已通过
            } else {
                throw new MyException("当前手机号已注册");
            }
        }

        return registerList;
    }

    /**
     * 传入参数，同意注册
     * 同时修改注册、用户、登录信息
     *
     * @param register
     * @return
     */
    @Transactional
    @Override
    public Integer AgreeRegister(Register register) {
        //同意注册,修改状态
        Integer agreeResult = registerMapper.AlterRegister(register);
        //判断状态是否修改成功
        if (agreeResult != 1) {
            throw new MyException("同意注册失败,请重试!");
        }
        //创建登录信息

        Integer addLoginResult = loginServer.AddLoginUser(register);
        //判断是否修改成功
        if (addLoginResult != 1) {
            throw new MyException("创建登录信息失败,请重试");
        }
        //创建用户信息
        Integer addUserResult = userServer.AddUser(register.getPhone());
        //判断是否修改成功
        if (addUserResult != 1) {
            throw new MyException("创建用户信息失败,请重试");
        }
        //以上执行完成后,发送同意邮件
        try {
            sendEmail(register.getEmail(), agree);
        } catch (Exception e) {
            throw new MyException("发送邮件时出错");
        }
        return addUserResult;
    }

    /**
     * 传入参数,拒绝注册
     *
     * @param register
     * @return
     */
    @Override
    public Integer regectRegister(Register register) {
        Integer result = registerMapper.AlterRegister(register);
        //判断是否成功
        if (result == 0) {
            throw new MyException("拒绝程序出错,请检查");
        }
        //以上执行完成后,发送拒绝邮件
        try {
            sendEmail(register.getEmail(), regect);
        } catch (Exception e) {
            throw new MyException("发送邮件时出错");
        }
        return result;
    }

    /**
     * 传入参数申请注册
     *
     * @param register
     * @return
     */
    @Override
    public Integer ApplyRegister(Register register) {
        Integer integer = registerMapper.ApplyRegister(register);
        //判断注册是否成功
        if (integer == 0) {
            throw new MyException("注册出错");
        }
        return integer;
    }

}
