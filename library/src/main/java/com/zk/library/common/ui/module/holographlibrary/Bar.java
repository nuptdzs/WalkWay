/*
 * 	   Created by Daniel Nadeau
 * 	   daniel.nadeau01@gmail.com
 * 	   danielnadeau.blogspot.com
 * 
 * 	   Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.zk.library.common.ui.module.holographlibrary;


import com.zk.library.R;

/**
 * 条目类（如柱状图条目）
 */
public class Bar {

    /**
     * 文字颜色
     */
	private int color;

    /**
     *
     * 渐变起始色
     */
    private int startColor = R.color.color_white_10;

    /**
     * 渐变结束色
     */
    private int endColor = R.color.color_white_80;

    /**
     * 条目drawable
     */
    private int drawable;
    /**
     * 名称
     */
	private String name = "";
    /**
     * 顶部文字
     */
    private String topText = "";
    /**
     * 底部文字
     */
    private String bottomText = "";
    /**
     * 条目文章
     */
    private String itemText = "";
    /**
     * 条目值
     */
	private float value;

    /**
     * 是否显示项目文字
     */
    private boolean isShowBarText = false;

    /**
     * 是否显示顶部文字(默认显示)
     */
    private boolean isShowTopText = true;

    /**
     * 顶部文字是否换行显示
     */
    private boolean isTopTextBreak = false;

    /**
     * 是否显示底部问题
     */
    private boolean isShowBottomText = false;

    /**
     * 用于睡眠
     * 是否是睡眠记录时间段的起始时间
     */
    private boolean isTimeFirst = false;

    /**
     * 用于睡眠
     * 是否是睡眠记录时间段的结束时间
     */
    private boolean isTimeLast = false;

    public boolean isTimeFirst() {
        return isTimeFirst;
    }

    public void setTimeFirst(boolean timeFirst) {
        isTimeFirst = timeFirst;
    }

    public boolean isTimeLast() {
        return isTimeLast;
    }

    public void setTimeLast(boolean timeLast) {
        isTimeLast = timeLast;
    }

    public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}

    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public boolean isShowBarText() {
        return isShowBarText;
    }

    public void setShowBarText(boolean isShowBarText) {
        this.isShowBarText = isShowBarText;
    }

    public boolean isShowTopText() {
        return isShowTopText;
    }

    public void setShowTopText(boolean isShowTopText) {
        this.isShowTopText = isShowTopText;
    }

    public boolean isShowBottomText() {
        return isShowBottomText;
    }

    public void setShowBottomText(boolean isShowBottomText) {
        this.isShowBottomText = isShowBottomText;
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public boolean isTopTextBreak() {
        return isTopTextBreak;
    }

    public void setTopTextBreak(boolean isTopTextBreak) {
        this.isTopTextBreak = isTopTextBreak;
    }
}
