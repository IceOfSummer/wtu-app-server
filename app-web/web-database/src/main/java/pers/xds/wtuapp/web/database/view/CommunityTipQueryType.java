package pers.xds.wtuapp.web.database.view;

import pers.xds.wtuapp.web.database.bean.CommunityTip;

/**
 * @author DeSen Xu
 * @date 2022-12-25 20:03
 */
public class CommunityTipQueryType extends CommunityTip {

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
