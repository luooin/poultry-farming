package com.lambs.lambsmanager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.HashMap;

/**
 * 配置token拦截器
 */
@Configuration
public class TokenInterceptor implements HandlerInterceptor {

    //导入令牌
    @Resource
    private JwtConfig jwtConfig;

    /**
     * 处理前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求url
        String uri = request.getRequestURI();
        //包含login请求则放行
        if (uri.contains("/login")) {
            return true;
        }
        //尝试从请求头中获取token
        String token = request.getHeader(jwtConfig.getHeader());
        //判断是否获取到token
        if (!StringUtils.hasText(token)) {
            //尝试从请求体中获取token
            token = request.getParameter(jwtConfig.getHeader());
        }
        //再次判断token是否为空
        if (!StringUtils.hasText(token)) {
            //抛出异常
            throwMyException(response, "token为空");
            return false;
        }
        //若token存在
        try {
            jwtConfig.getTokenClaim(token);
        } catch (Exception e) {
            //抛出异常
            throwMyException(response, "token失效");
            return false;
        }
        return true;
    }

    /**
     * 自定义异常
     *
     * @param response
     * @param msg
     */
    public void throwMyException(HttpServletResponse response, String msg) throws IOException {
        //设置相应状态
        response.setStatus(401);
        //设置响应格式
        response.setContentType("application/json;charset=UTF-8");
        //设置编码
        response.setCharacterEncoding("UTF-8");
        //创建ObjectMapper对象,用于数据装换
        ObjectMapper objectMapper = new ObjectMapper();
        //创建hashMap集合存储数据
        HashMap<String, String> errorHashMap = new HashMap<>();
        errorHashMap.put("error", msg);
        //ObjectMapper()对象写入指定的值
        String json = objectMapper.writeValueAsString(errorHashMap);
        //设置返回内容
        response.getWriter().write(json);
    }
}
