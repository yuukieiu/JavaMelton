package melton.model;

import java.util.LinkedList;

@SuppressWarnings({ "serial", "rawtypes" })
public class TimeLineList extends LinkedList{
	private boolean invisiflug;		// インビジフラグ（こっちが正しい可能性）
	private boolean allwaysnewflug;	// 最新ツイート追尾フラグ（こっちが正し(ry）
	
	// getter
	public boolean getinvisiflug() { return invisiflug; }
	public boolean getallwaysnewflug() { return allwaysnewflug; }
	
	public TimeLineList() {
		invisiflug = false;
		allwaysnewflug = false;
	}
	
	// あとはコントローラーさんがやってくれるだろ（）
}