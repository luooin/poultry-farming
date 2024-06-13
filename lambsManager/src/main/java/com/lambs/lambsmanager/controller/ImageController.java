package com.lambs.lambsmanager.controller;

import com.lambs.lambsmanager.config.MyException;
import com.lambs.lambsmanager.util.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/lambs/image")
public class ImageController {

    @Value("${labms.ImgLocation}")
    private String BasePath;

    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upLoadingImg(MultipartFile file) {
        //获取源文件名称
        String originalFilename = file.getOriginalFilename();
        //截取文件后缀名
        String suffix;
        //源文件不为空
        if (originalFilename != null) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            throw new MyException("图片上传失败");
        }
        //使用UUID随机生成文件名，防止文件名称重复造成覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        //创建文件对象,用于存放图片信息
        File files = new File(BasePath);
        //判断源文件是否存在,不存在则创建
        if (!files.exists()) {
            files.mkdirs();
        }
        try {
            //图片移动到指定的位置
            file.transferTo(new File(BasePath + fileName));
        } catch (IOException e) {
            throw new MyException("图片移动失败");
        }
        //文件名称返回
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        //设置图片相应格式
        response.setContentType("image/jpeg");
        //文件输入流
        FileInputStream fileInputStream;
        //文件响应流
        ServletOutputStream outputStream;
        try {
            //采用文件输入流读取文件数据
            fileInputStream = new FileInputStream(new File(BasePath + name));
        } catch (FileNotFoundException e) {
            throw new MyException("找不到指定文件");
        }
        //响应体采用输出流
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            throw new MyException("获取响应流失败");
        }
        try {
            //采用数组写入
            int len = 0;
            //字符数组
            byte[] bytes = new byte[1024];
            //循环写入当读取的长度为-1时,代表完成
            while ((len = (fileInputStream.read(bytes))) != -1) {
                //输出流读取
                outputStream.write(bytes, 0, len);
            }
            //释放输入流、输出流资源
            fileInputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new MyException("图片读写错误");
        }
    }
}
