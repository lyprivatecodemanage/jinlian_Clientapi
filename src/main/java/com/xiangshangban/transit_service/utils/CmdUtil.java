/**
 * Copyright (C), 2015-2018, 上海金念有限公司
 * FileName: CmdUtil
 * Author:   liuguanglong
 * Date:     2018/1/10 13:23
 * Description: 根据设备和后台通讯协议封装的工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.xiangshangban.transit_service.utils;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.transit_service.bean.DoorCmd;
import com.xiangshangban.transit_service.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈根据设备和后台通讯协议封装的工具类〉
 *
 * @author liuguanglong
 * @create 2018/1/10
 * @since 1.0.0
 */

@Component
public class CmdUtil {

    @Value("${command.timeout.seconds}")
    String commandTimeoutSeconds;

    @Value("${serverId}")
    String serverId;


    /**
     * 封装协议数据格式
     * @param doorCmd
     * @param dataName
     * @param dataObject
     * @return
     */
    public static Map<String, Object> messagePackaging(DoorCmd doorCmd, String dataName, Object dataObject, String commandMode){

        //最外层协议格式
        Map protocolMap = new LinkedHashMap();
        //命令格式，协议格式的一部分
        Map commandMap = new LinkedHashMap();
        //携带的业务数据
        Map mapData = new LinkedHashMap();

        protocolMap.put("commandIndex", doorCmd.getCommandIndex());
        protocolMap.put("commandMode", doorCmd.getCommandMode());
        protocolMap.put("commandTotal", doorCmd.getCommandTotal());
        protocolMap.put("commandType", doorCmd.getCommandType());

        //判断是回复还是主动下发，外加data部分空数据
        if (commandMode.equals("C")){
            mapData.put(dataName, dataObject);
            protocolMap.put("data", mapData);
        }else if (commandMode.equals("R")){
            protocolMap.put("result", dataObject);
        }else if (commandMode.equals("NULLDATA")){
            protocolMap.put("data", mapData);
        }

        protocolMap.put("deviceId", doorCmd.getDeviceId());
        protocolMap.put("fileEdition", doorCmd.getFileEdition());
        protocolMap.put("outOfTime", doorCmd.getOutOfTime());
        protocolMap.put("sendTime", doorCmd.getSendTime());
        protocolMap.put("serverId", doorCmd.getServerId());
//        protocolMap.put("status", doorCmd.getStatus());

        commandMap.put("ACTION", doorCmd.getAction());
        commandMap.put("ACTIONCode", doorCmd.getActionCode());
        commandMap.put("subCMDID", doorCmd.getSubCmdId());
        commandMap.put("superCMDID", doorCmd.getSuperCmdId());

        protocolMap.put("command", commandMap);

        String md5check = JSON.toJSONString(protocolMap);
//        System.out.println(md5check);
        String md5 = "";
        try {
            md5 = MD5Util.encryptPassword(md5check, "XC9EO5GKOIVRMBQ2YE8X");
        } catch (Exception e) {
            System.out.println("md5值 = " + md5);
            System.out.println("数据MD5出错:" + e.getMessage());
        }

        protocolMap.put("MD5Check", md5);

        return protocolMap;
    }

}