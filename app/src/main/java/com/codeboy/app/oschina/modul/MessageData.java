/*
 * Copyright (c) 2014. CodeBoyTeam
 */

package com.codeboy.app.oschina.modul;

import net.oschina.app.bean.PageList;
import net.oschina.app.core.AppContext;


/**
 * 类名 MessageData.java</br>
 * 创建日期 2014年4月25日</br>
 * @author LeonLee (http://my.oschina.net/lendylongli)</br>
 * Email lendylongli@gmail.com</br>
 * 更新时间 2014年4月25日 上午1:04:14</br>
 * 最后更新者 LeonLee</br>
 * 
 * 说明 数据加载结果
 */
public class MessageData<Result extends PageList<?>>{

	//数据状态码
	//错误
	public static final int MESSAGE_STATE_ERROR = -1;
	//空值
	public static final int MESSAGE_STATE_EMPTY = 0;
	//更多数据
	public static final int MESSAGE_STATE_MORE = 1;
	//正在加载
	public static final int MESSAGE_STATE_FULL = 2;
	
	
	public int state;
	public Result result;
	public Exception exception;

	//传入状态码时调用
	public MessageData(int state) {
		this.state = state;
		this.result = null;
		this.exception = null;
	}

	//传入返回时调用
    //Result引用PageList接口，实现每个版块的类的集合
	public MessageData(Result result) {
		if(result != null) {
			//获取某版块的现有的页数中显示的item数量
			int size = result.getPageSize();
			//当该版块现有页数中显示的item数量为0
			if(size == 0) {
				this.state = MESSAGE_STATE_EMPTY;
			}
			//当该版块现有页数中显示的item数量小于总的显示item数量
			else if(size < AppContext.PAGE_SIZE) {
				this.state = MESSAGE_STATE_FULL;
			}
			//当该版块现有页数中显示的item数量等于总的显示item数量
			else {
				this.state = MESSAGE_STATE_MORE;
			}
		} else {
			this.state = MESSAGE_STATE_ERROR;
		}
		this.result = result;
		this.exception = null;
	}

	//当有错误时调用
	public MessageData(Exception exception) {
		this.state = MESSAGE_STATE_ERROR;
		this.result = null;
		this.exception = exception;
	}
}