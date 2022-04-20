package com.xzc.jdk.java8;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class List2Tree {

    // 封装集合为树形结构
    private List<Node> buildTree(List<Node> nodes) {
        Map<Integer, List<Node>> children = nodes.stream().filter(node -> node.getPid() != 0)
                .collect(Collectors.groupingBy(node -> node.getPid()));
        nodes.forEach(node -> node.setChildren(children.get(node.getId())));
        return nodes.stream().filter(node -> node.getPid() == 0).collect(Collectors.toList());
    }
}
