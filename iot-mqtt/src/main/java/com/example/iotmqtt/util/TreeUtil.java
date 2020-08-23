package com.example.iotmqtt.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;

public class TreeUtil {

    private TreeUtil(){}
    private static volatile TreeUtil treeUtil;

    public static TreeUtil getInstance(){
        if (treeUtil == null){
            synchronized (TreeUtil.class){
                if (treeUtil == null){
                    treeUtil = new TreeUtil();
                }
            }
        }
        return treeUtil;
    }

    //2万条数据封装耗时15ms
    public Collection<? extends Tree> getCompleteTrees(List<? extends Tree> treeList) {
        //获取数据
//        List<MyTree> treeList = initList();
        //树节点封装
        Multimap<Long, Tree> multimap = treeNodePackageByParentId(treeList);
        //获取根节点
        Collection<Tree> rootNodeList = multimap.get(0L);
        //添加子节点
        addTreeNodeDependency(rootNodeList, multimap);
        return rootNodeList;
    }
    //按parentId聚合
    private Multimap<Long, Tree> treeNodePackageByParentId(List<? extends Tree> treeList) {
        Multimap<Long, Tree> multimap = HashMultimap.create();
        for (Tree treeNode : treeList) {
            multimap.put(treeNode.getParentId(), treeNode);
        }
        return multimap;
    }
    //添加依赖关系
    private void addTreeNodeDependency(Collection<? extends Tree> rootTreeList, Multimap<Long, Tree> multiMap) {
        for (Tree parentTreeNode : rootTreeList) {
            addChildNode(parentTreeNode, multiMap);
        }
    }

    private void addChildNode(Tree parentTreeNode, Multimap<Long, Tree> multiMap) {
        Long id = parentTreeNode.getId();
        if (!multiMap.isEmpty()){
            parentTreeNode.setChild(Lists.newArrayList());
        }
        for (Tree chileTreeNode : multiMap.get(id)) {
            Long childId = chileTreeNode.getId();
            parentTreeNode.addChild(chileTreeNode);
            if (multiMap.containsKey(childId)) {
                addChildNode(chileTreeNode, multiMap);
            }
        }
    }

}
