package util;
/**
 * 
 * desc：消息抄送类型的枚举。
 * 若消息抄送类型有改变时，需要修改该枚举类。
 * @author J
 * time:2018年6月111日
 */
public enum MsgCopyType {
	CONVERSATION("会话类型的消息", 1),
	LOGIN("用户登录事件的消息", 2),
	LOGOUT("用户登出事件的消息", 3),
	CHATROOM("聊天室中聊天的消息", 4),
	DATATUNNEL("汇报实时音视频通话时长、白板事件时长的消息", 5),
	DOWNLOAD("汇报音视频/白板文件的大小、下载地址等消息", 6),
	RETRACTED_SINGLE("单聊消息撤回抄送", 7),
	RETRACTED_GROUP("群聊消息撤回抄送", 8),
	CHATROOM_INOUT("汇报主播或管理员进出聊天室事件消息", 9),
	ECP_CALLBACK("汇报专线电话通话结束回调抄送的消息", 10),
	SMS_CALLBACK("汇报短信回执抄送的消息", 11),
	SMS_REPLY("汇报短信上行消息", 12),
	AVROOM_INOUT("汇报用户进出音视频/白板房间的消息", 13),
	CHATROOM_QUEUE_OPERATE("汇报聊天室队列操作的事件消息", 14);

	private String name ;
	private int id ;
	
	private MsgCopyType( String name , int id ){
	    this.name = name ;
	    this.id = id ;
	}
	 
	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public int getId() {
	    return id;
	}
	public void setId(int id) {
	    this.id = id;
	}
	 
	
}
