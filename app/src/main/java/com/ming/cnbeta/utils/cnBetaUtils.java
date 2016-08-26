package com.ming.cnbeta.utils;

import com.ming.cnbeta.db.greendao.CommentItem;

/**
 * Created by ming on 16/2/29.
 */
public class cnBetaUtils {

    public static void copy(CommentItem from,CommentItem to) {
        to.setScore(from.getScore());
        to.setReason(from.getReason());
        to.setIcon(from.getIcon());
        to.setDate(from.getDate());
        to.setName(from.getName());
        to.setComment(from.getComment());
        to.setHost_name(from.getHost_name());
    }

}
