package game.newAoi.map.orthogonal.model;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.newAoi.map.orthogonal.consant.MapSceneConstant;
import game.newAoi.map.orthogonal.consant.OrthogonalNodeTypeEnum;
import game.newAoi.map.scene.Coordinate;
import game.newAoi.map.scene.MapUnit;

/**
 *
 * 十字链表节点
 *
 * @author : ddv
 * @since : 2020/1/13 10:02 AM
 */
public class OrthogonalNode {

    private static final Logger logger = LoggerFactory.getLogger(OrthogonalNode.class);

    private OrthogonalNodeTypeEnum nodeType;

    private int x;
    private int y;
    /**
     * 节点值
     */
    private MapUnit unit;

    /**
     * x轴节点
     */
    private OrthogonalNode rowPreNode;
    private OrthogonalNode rowNextNode;

    /**
     * y轴节点
     */
    private OrthogonalNode colPreNode;
    private OrthogonalNode colNextNode;

    public OrthogonalNode() {}

    public static OrthogonalNode valueOf(int x, int y, MapUnit unit) {
        OrthogonalNode node = new OrthogonalNode();
        node.x = x;
        node.y = y;
        node.unit = unit;
        return node;
    }

    public static OrthogonalNode valueOf(int x, int y, MapUnit unit, OrthogonalNodeTypeEnum nodeType) {
        OrthogonalNode node = new OrthogonalNode();
        node.x = x;
        node.y = y;
        node.unit = unit;
        node.nodeType = nodeType;
        return node;
    }

    /**
     * 插入横轴
     */
    public void insertRowNode(OrthogonalNode newRowNode) {
        logger.info("插入节点[{} {}]", newRowNode.getX(), newRowNode.getX(), newRowNode.getY());
        findInsertNextRowNode(newRowNode, this, this);
    }

    /**
     * 寻找x轴上插入位置
     *
     * @param newRowNode
     *            待插入节点
     * @param lastInsertNode
     *            上一个非空节点
     * @param insertNode
     *            插入节点 [插入此节点后继]
     *
     */
    private void findInsertNextRowNode(OrthogonalNode newRowNode, OrthogonalNode lastInsertNode,
        OrthogonalNode insertNode) {
        if (nodeType != OrthogonalNodeTypeEnum.X_LIST) {
            throw new IllegalArgumentException("非横轴链表不允许横轴维度操作!");
        }

        // 寻找横轴合适的点 [y命中?无命中]
        while (insertNode != null && newRowNode.y > insertNode.y) {
            lastInsertNode = insertNode;
            insertNode = insertNode.rowNextNode;
        }

        insertNode = insertNode == null || newRowNode.y < insertNode.y ? lastInsertNode : insertNode;
        int currentY = insertNode.y;

        // 寻找竖轴合适的点 [寻找过程中需要保证不能跨过已经确定下来的横轴维度]
        while (insertNode != null && insertNode.y == currentY && newRowNode.x > insertNode.x) {
            lastInsertNode = insertNode;
            insertNode = insertNode.rowNextNode;
        }

        insertNode =
            insertNode == null || newRowNode.x < insertNode.x || insertNode.y != currentY ? lastInsertNode : insertNode;
        insertToNextNode(newRowNode, insertNode);
    }

    /**
     * 将新插节点插入待插节点后面
     *
     * @param newNode
     *            新节点
     * @param insertNode
     *            待插节点
     */
    private void insertToNextNode(OrthogonalNode newNode, OrthogonalNode insertNode) {
        if (nodeType == OrthogonalNodeTypeEnum.X_LIST) {
            // logger.info("将新节点[{} {}] 插入节点[{} {}]后继", newNode.x, newNode.y, insertNode.x, insertNode.y);
            OrthogonalNode originNextNode = insertNode.rowNextNode;
            insertNode.rowNextNode = newNode;
            newNode.rowPreNode = insertNode;
            newNode.rowNextNode = originNextNode;
        }
    }

    /**
     * ============================== get and set ==============================
     */

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public OrthogonalNode getRowNextNode() {
        return rowNextNode;
    }

    public void setRowNextNode(OrthogonalNode rowNextNode) {
        this.rowNextNode = rowNextNode;
    }

    public OrthogonalNode getRowPreNode() {
        return rowPreNode;
    }

    public void setRowPreNode(OrthogonalNode rowPreNode) {
        this.rowPreNode = rowPreNode;
    }

    public OrthogonalNode getColPreNode() {
        return colPreNode;
    }

    public void setColPreNode(OrthogonalNode colPreNode) {
        this.colPreNode = colPreNode;
    }

    public MapUnit getUnit() {
        return unit;
    }

    public void setUnit(MapUnit unit) {
        this.unit = unit;
    }

    public OrthogonalNode getColNextNode() {
        return colNextNode;
    }

    public void setColNextNode(OrthogonalNode colNextNode) {
        this.colNextNode = colNextNode;
    }

    public List<Coordinate> findAllCoordinateList() {
        List<Coordinate> coordinates = new ArrayList<>();
        Set<OrthogonalNode> foundNodes = new HashSet<>();
        findAllCoordinateList(this, coordinates, foundNodes);
        return coordinates;
    }

    private static void findAllCoordinateList(OrthogonalNode currentNode, List<Coordinate> coordinates,
        Set<OrthogonalNode> foundNodes) {
        if (currentNode == null || foundNodes.contains(currentNode)) {
            return;
        }

        foundNodes.add(currentNode);
        coordinates.add(Coordinate.valueOf(currentNode.x, currentNode.y));
        findAllCoordinateList(currentNode.rowPreNode, coordinates, foundNodes);
        findAllCoordinateList(currentNode.rowNextNode, coordinates, foundNodes);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrthogonalNode that = (OrthogonalNode)o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * 查找该节点可视范围内单位
     */
    public List<MapUnit> getVisibleList() {
        List<MapUnit> visibleList = new ArrayList<>();
        Set<OrthogonalNode> foundNodes = new HashSet<>();

        visibleList.add(unit);
        foundNodes.add(this);
        getVisibleList(visibleList, foundNodes);
        return visibleList;
    }

    private void getVisibleList(List<MapUnit> visibleList, Set<OrthogonalNode> foundNodes) {
        recursiveFindVisibleList(this, rowPreNode, visibleList, foundNodes);
        recursiveFindVisibleList(this, rowNextNode, visibleList, foundNodes);
    }

    /**
     * 以center为中心查找周围目标 递归查找
     */
    private static void recursiveFindVisibleList(OrthogonalNode center, OrthogonalNode node, List<MapUnit> visibleList,
        Set<OrthogonalNode> foundNodes) {
        if (node == null || foundNodes.contains(node)) {
            return;
        }

        // x,y轴都超距 无需再处理
        if (Math.abs(center.getX() - node.x) > MapSceneConstant.DISTANCE
            && Math.abs(center.getY() - node.y) > MapSceneConstant.DISTANCE) {
            return;
        }

        visibleList.add(node.getUnit());
        foundNodes.add(node);

        recursiveFindVisibleList(center, node.rowPreNode, visibleList, foundNodes);
        recursiveFindVisibleList(center, node.rowNextNode, visibleList, foundNodes);
    }

    @Override
    public String toString() {
        return "OrthogonalNode{" + "x=" + x + ", y=" + y + ", unit=" + unit + '}';
    }
}
