package com.yuanbaogo.homevideo.shortvideo.comment.model;


/**
 * @author ganhuanhui
 * 时间：2019/12/12 0012
 * 描述：更多item
 */
public class CommentMoreBean {

    private long totalCount;
    private long position;
    private long positionCount;
    private boolean isOpen;//是否展开

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

//    @Override
//    public int getItemType() {
//        return TYPE_COMMENT_MORE;
//    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getPositionCount() {
        return positionCount;
    }

    public void setPositionCount(long positionCount) {
        this.positionCount = positionCount;
    }
}
