package com.kaiwen.base.common.utils;



import java.util.*;

public class TreeUtil {
    
  /*  private final static String ADMINISTRATOR_ROLEGUID = "b35580b19bea4dd8bc62ddb4f18ec41d";
    
    //获取超级管理员的角色id
    public static String getAdministratorRoleguid() {
        return ADMINISTRATOR_ROLEGUID;
    }
    
    *//**
     * 建树的核心思想，处理传过来的list，生成两个结果 一个是result列表，里面存储的是最上层的没有父节点的祖宗节点
     * 还有一个map是一张父节点和子节点的对应表，key为父节点id，value为子节点列表
     *
     *//*
    
    *//**
     * 机构模块信息建树（JSON嵌套）
     *
     * @param list
     * @param <T>
     * @return
     *//*
    public static <T> List<T> buildTree(List<T> list) {
        
        *//* 判断是否为空 *//*
        LinkedList<T> result = new LinkedList<>();
        if (list == null || list.isEmpty()) {
            return result;
        }
        
        // 分类处理机构和模块
        Map<String, List<T>> pcmap = new WeakHashMap<>();
        T obj = list.get(0);
        switch (obj.getClass().toString()) {
            case "class com.southsmart.lbsp.lbsp.entity.TargetGroup":
                dealWithTargetGroupInfo(list, result, pcmap);
                break;
            case "class com.southsmart.lbsp.lbsp.entity.TerminalGroup":
                dealWithTerminalGroupInfo(list, result, pcmap);
                break;
            case "class com.southsmart.lbsp.lbsp.pojo.GroupTreeVo":
                dealWithGroupTreeVo(list, result, pcmap);
                break;
            case "class com.southsmart.lbsp.lbsp.pojo.SingleTreeVo":
                dealWithSingleTreeVo(list, result, pcmap);
                break;
            case "class com.southsmart.lbsp.lbsp.pojo.LayerTreeVo":
                dealWithLayerTreeVo(list, result, pcmap);
                break;
            case "class com.southsmart.lbsp.lbsp.pojo.FencingTreeVo":
                dealWithFencingTreeVo(list, result, pcmap);
                break;
            case "class com.southsmart.lbsp.lbsp.entity.WorkSheetDailyGroup":
                dealWithWorkSheet(list, result, pcmap);
                break;
            case "class com.southsmart.lbsp.lbsp.pojo.WorkSheetTreeVo":
                dealWithWorkSheetVo(list, result, pcmap);
                break;
            default:
                break;
        }
        setTree(result, pcmap);
        return result;
    }

    *//**
     * 工单vo
     *
     * @param inList
     * @param parentList
     * @param outMap
     * @param <T>
     *//*
    public static <T> void dealWithWorkSheetVo(List<T> inList, LinkedList<T> parentList, Map<String, List<T>> outMap) {

        for (T obj : inList) {
            WorkSheetTreeVo groupTreeVo = (WorkSheetTreeVo)obj;
            String parentid = groupTreeVo.getParentid();
            if (parentid == null || parentid.equals("")) {
                //如果是未分组置顶
                if (Constants.UNGROUPED_ID.equals(groupTreeVo.getValue()))
                    parentList.addFirst((T)groupTreeVo);
                else
                    parentList.add((T)groupTreeVo);
            } else {
                List<T> childList = outMap.get(parentid);
                if (childList == null || childList.isEmpty()) {
                    childList = new ArrayList<T>();
                    outMap.put(parentid, childList);
                }
                childList.add((T)groupTreeVo);
            }
        }
    }
    
    *//**
     * @Param
     * @return
     * @Description:工单
     *//*
    public static <T> void dealWithWorkSheet(List<T> inList, LinkedList<T> parentList, Map<String, List<T>> outMap) {
        
        for (T obj : inList) {
            WorkSheetDailyGroup workSheetDailyGroup = (WorkSheetDailyGroup)obj;
            String parentguid = workSheetDailyGroup.getParentId();
            if (parentguid == null || parentguid.equals("")) {
                if (Constants.UNGROUPED_ID.equals(workSheetDailyGroup.getGroupId()))
                    parentList.addFirst((T)workSheetDailyGroup);
                else
                    parentList.add((T)workSheetDailyGroup);
            } else {
                List<T> childList = outMap.get(parentguid);
                if (childList == null || childList.isEmpty()) {
                    childList = new ArrayList<T>();
                    outMap.put(parentguid, childList);
                }
                childList.add((T)workSheetDailyGroup);
            }
        }
    }
    
    *//**
     * @Param
     * @return
     * @Description:围栏分组
     *//*
    public static <T> void dealWithFencingTreeVo(List<T> inList, LinkedList<T> parentList,
        Map<String, List<T>> outMap) {
        
        for (T obj : inList) {
            FencingTreeVo fencingTreeVo = (FencingTreeVo)obj;
            String parentid = fencingTreeVo.getParentid();
            if (parentid == null || parentid.equals("")) {
                //如果是未分组置顶
                if (Constants.UNGROUPED_ID.equals(fencingTreeVo.getValue()))
                    parentList.addFirst((T)fencingTreeVo);
                else
                    parentList.add((T)fencingTreeVo);
            } else {
                List<T> childList = outMap.get(parentid);
                if (childList == null || childList.isEmpty()) {
                    childList = new ArrayList<T>();
                    outMap.put(parentid, childList);
                }
                childList.add((T)fencingTreeVo);
            }
        }
    }
    
    *//**
     * @Param
     * @return
     * @Description:单实例VO处理
     *//*
    public static <T> void dealWithSingleTreeVo(List<T> inList, LinkedList<T> parentList, Map<String, List<T>> outMap) {
        
        for (T obj : inList) {
            SingleTreeVo singleTreeVo = (SingleTreeVo)obj;
            String parentid = singleTreeVo.getParentid();
            if (parentid == null || parentid.equals("")) {
                //如果是未分组置顶
                if (Constants.UNGROUPED_ID.equals(singleTreeVo.getValue()))
                    parentList.addFirst((T)singleTreeVo);
                else
                    parentList.add((T)singleTreeVo);
            } else {
                List<T> childList = outMap.get(parentid);
                if (childList == null || childList.isEmpty()) {
                    childList = new ArrayList<T>();
                    outMap.put(parentid, childList);
                }
                childList.add((T)singleTreeVo);
            }
        }
    }
    
    *//**
     * 图层树处理
     *
     * @param inList
     * @param parentList
     * @param outMap
     * @param <T>
     *//*
    public static <T> void dealWithLayerTreeVo(List<T> inList, LinkedList<T> parentList, Map<String, List<T>> outMap) {
        
        for (T obj : inList) {
            LayerTreeVo layerTreeVo = (LayerTreeVo)obj;
            String parentid = layerTreeVo.getParentid();
            if (parentid == null || parentid.equals("")) {
                //如果是未分组置顶
                if (Constants.UNGROUPED_ID.equals(layerTreeVo.getValue()))
                    parentList.addFirst((T)layerTreeVo);
                else
                    parentList.add((T)layerTreeVo);
            } else {
                List<T> childList = outMap.get(parentid);
                if (childList == null || childList.isEmpty()) {
                    childList = new ArrayList<T>();
                    outMap.put(parentid, childList);
                }
                childList.add((T)layerTreeVo);
            }
        }
    }
    
    *//**
     * 地图服务树的处理
     *
     * @param inList
     * @param parentList
     * @param outMap
     * @param <T>
     *//*
    public static <T> void dealWithGroupTreeVo(List<T> inList, LinkedList<T> parentList, Map<String, List<T>> outMap) {
        
        for (T obj : inList) {
            GroupTreeVo groupTreeVo = (GroupTreeVo)obj;
            String parentid = groupTreeVo.getParentid();
            if (parentid == null || parentid.equals("")) {
                //如果是未分组置顶
                if (Constants.UNGROUPED_ID.equals(groupTreeVo.getValue()))
                    parentList.addFirst((T)groupTreeVo);
                else
                    parentList.add((T)groupTreeVo);
            } else {
                List<T> childList = outMap.get(parentid);
                if (childList == null || childList.isEmpty()) {
                    childList = new ArrayList<T>();
                    outMap.put(parentid, childList);
                }
                childList.add((T)groupTreeVo);
            }
        }
    }
    
    *//**
     * 对象list的处理
     *
     * @param inList
     * @param parentList
     * @param outMap
     * @param <T>
     *//*
    public static <T> void dealWithTargetGroupInfo(List<T> inList, LinkedList<T> parentList,
        Map<String, List<T>> outMap) {
        
        for (T obj : inList) {
            TargetGroup targetGroupInfo = (TargetGroup)obj;
            String parentid = targetGroupInfo.getParentid();
            if (parentid == null || parentid.equals("")) {
                if (Constants.UNGROUPED_ID.equals(targetGroupInfo.getGroupid()))
                    parentList.addFirst((T)targetGroupInfo);
                else
                    parentList.add((T)targetGroupInfo);
            } else {
                List<T> childList = outMap.get(parentid);
                if (childList == null || childList.isEmpty()) {
                    childList = new ArrayList<T>();
                    outMap.put(parentid, childList);
                }
                childList.add((T)targetGroupInfo);
            }
        }
    }
    
    *//**
     * 终端list的处理
     *
     * @param inList
     * @param parentList
     * @param outMap
     * @param <T>
     *//*
    public static <T> void dealWithTerminalGroupInfo(List<T> inList, LinkedList<T> parentList,
        Map<String, List<T>> outMap) {
        
        for (T obj : inList) {
            TerminalGroup terminalGroupInfo = (TerminalGroup)obj;
            String parentguid = terminalGroupInfo.getParentid();
            if (parentguid == null || parentguid.equals("")) {
                if (Constants.UNGROUPED_ID.equals(terminalGroupInfo.getGroupid()))
                    parentList.addFirst((T)terminalGroupInfo);
                else
                    parentList.add((T)terminalGroupInfo);
            } else {
                List<T> childList = outMap.get(parentguid);
                if (childList == null || childList.isEmpty()) {
                    childList = new ArrayList<T>();
                    outMap.put(parentguid, childList);
                }
                childList.add((T)terminalGroupInfo);
            }
        }
    }
    
    *//**
     * 建树
     *
     * @param list
     * @param pcmap
     * @param <T>
     *//*
    public static <T> void setTree(List<T> list, Map<String, List<T>> pcmap) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (list.get(0) instanceof TargetGroup) {
            for (T obj : list) {
                TargetGroup targetGroupInfo = (TargetGroup)obj;
                String parentid = targetGroupInfo.getGroupid();
                List<T> childList = pcmap.get(parentid);
                //如果孩子不是空的话就加进去，建立父子关系
                if (childList != null) {
                    targetGroupInfo.setChildren((List<TargetGroup>)childList);
                    setTree(childList, pcmap);
                }
            }
        } else if (list.get(0) instanceof TerminalGroup) {
            for (T obj : list) {
                TerminalGroup terminalGroupInfo = (TerminalGroup)obj;
                String parentguid = terminalGroupInfo.getGroupid();
                List<T> childList = pcmap.get(parentguid);
                if (childList != null) {
                    terminalGroupInfo.setChildren((List<TerminalGroup>)childList);
                    setTree(childList, pcmap);
                }
            }
        } else if (list.get(0) instanceof GroupTreeVo) {
            for (T obj : list) {
                GroupTreeVo groupTreeVo = (GroupTreeVo)obj;
                String parentid = groupTreeVo.getValue();
                List<T> childList = pcmap.get(parentid);
                if (childList != null) {
                    groupTreeVo.setChildren((List<GroupTreeVo>)childList);
                    setTree(childList, pcmap);
                }
            }
        } else if (list.get(0) instanceof LayerTreeVo) {
            for (T obj : list) {
                LayerTreeVo layerTreeVo = (LayerTreeVo)obj;
                String parentid = layerTreeVo.getValue();
                List<T> childList = pcmap.get(parentid);
                if (childList != null) {
                    layerTreeVo.setChildren((List<LayerTreeVo>)childList);
                    setTree(childList, pcmap);
                }
            }
        } else if (list.get(0) instanceof SingleTreeVo) {
            for (T obj : list) {
                SingleTreeVo singleTreeVo = (SingleTreeVo)obj;
                String parentid = singleTreeVo.getValue();
                List<T> childList = pcmap.get(parentid);
                if (childList != null) {
                    singleTreeVo.setChildren((List<SingleTreeVo>)childList);
                    setTree(childList, pcmap);
                }
            }
        } else if (list.get(0) instanceof FencingTreeVo) {
            for (T obj : list) {
                FencingTreeVo fencingTreeVo = (FencingTreeVo)obj;
                String parentid = fencingTreeVo.getValue();
                List<T> childList = pcmap.get(parentid);
                if (childList != null) {
                    fencingTreeVo.setChildren((List<FencingTreeVo>)childList);
                    setTree(childList, pcmap);
                }
            }
        } else if (list.get(0) instanceof WorkSheetDailyGroup) {
            for (T obj : list) {
                WorkSheetDailyGroup workSheetDailyGroup = (WorkSheetDailyGroup)obj;
                String parentguid = workSheetDailyGroup.getGroupId();
                List<T> childList = pcmap.get(parentguid);
                if (childList != null) {
                    workSheetDailyGroup.setChildren((List<WorkSheetDailyGroup>)childList);
                    setTree(childList, pcmap);
                }
            }
        }else if (list.get(0) instanceof WorkSheetTreeVo) {
            for (T obj : list) {
                WorkSheetTreeVo workSheetDailyGroupVo = (WorkSheetTreeVo)obj;
                String parentguid = workSheetDailyGroupVo.getValue();
                List<T> childList = pcmap.get(parentguid);
                if (childList != null) {
                    workSheetDailyGroupVo.setChildren((List<WorkSheetTreeVo>)childList);
                    setTree(childList, pcmap);
                }
            }
        }
    }
    */
}
