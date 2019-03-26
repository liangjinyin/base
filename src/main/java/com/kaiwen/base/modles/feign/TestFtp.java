package com.kaiwen.base.modles.feign;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpDirEntry;
import sun.net.ftp.FtpProtocolException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.Iterator;

/**
 * @author: liangjinyin
 * @Date: 2018-12-27
 * @Description:
 */
@Slf4j
public class TestFtp {

    public static FtpClient connectFTP(String url, int port, String username, String password) {
        //创建ftp
        FtpClient ftp = null;
        try {
            //创建地址
            SocketAddress addr = new InetSocketAddress(url, port);
            //连接
            ftp = FtpClient.create();
            ftp.connect(addr);
            //登陆
            ftp.login(username, password.toCharArray());
            System.out.print(username);
            ftp.setBinaryType();
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftp;
    }

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost
     *            FTP主机服务器
     * @param ftpPassword
     *            FTP 登录密码
     * @param ftpUserName
     *            FTP登录用户名
     * @param ftpPort
     *            FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName,
                                         String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            // 连接FTP服务器
            ftpClient.connect(ftpHost, ftpPort);
            // 登陆FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                log.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            log.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    public static void writeFileToFtp(FtpClient ftpClient,String fileName,String content) {
        try{
            // 切换到test目录 ,返回boolean值，如果有该文件夹返回true，否则，返回false
            Iterator<FtpDirEntry> files = ftpClient.listFiles("hy_marathon");
            if (!files.hasNext()) {
                ftpClient.makeDirectory("hy_marathon");
            }
            ftpClient.changeDirectory( "hy_marathon" );
            //向指定文件写入内容，如果没有该文件，则先创建文件再写入。写入的方式是追加。 // 写入的文件名
            InputStream is = new ByteArrayInputStream(content.getBytes());
            ftpClient.appendFile(fileName, is);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        //new TestFtp("132.122.46.203", 10021, "real_project", "hK$y18#fkn");
        FtpClient ftp = TestFtp.connectFTP("192.168.75.128", 21, "liang", "gpdi123!@#");
        String data = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        TestFtp.writeFileToFtp(ftp,String.format("hy_marathon_%s.txt",data ),"asdfhk");
    }
}
