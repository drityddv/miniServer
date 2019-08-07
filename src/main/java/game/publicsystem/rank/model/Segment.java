// package game.publicsystem.rank.model;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.ConcurrentSkipListMap;
//
// import game.publicsystem.rank.constant.RankConst;
// import game.publicsystem.rank.model.type.BaseRankInfo;
//
/// **
// * @author : ddv
// * @since : 2019/8/6 10:11 AM
// */
//
// public class Segment<K> {
// // 拷贝占用锁
// private final Object occupy = new Object();
//
// private int capacity = RankConst.MAX_SIZE;
// //
// private Map<K, BaseRankInfo> cache;
//
// private ConcurrentSkipListMap<BaseRankInfo, K> rankMap;
//
// public Segment() {
// this.cache = new ConcurrentHashMap<>();
// this.rankMap = new ConcurrentSkipListMap<>(new DefaultComparator());
// }
//
// public boolean contain(K k) {
// return cache.containsKey(k);
// }
//
// private boolean isOverFull() {
// return rankMap.size() > capacity;
// }
//
// public int getCapacity() {
// return capacity;
// }
//
// public Map<K, BaseRankInfo> getCache() {
// return cache;
// }
//
// public ConcurrentSkipListMap<BaseRankInfo, K> getRankMap() {
// return rankMap;
// }
//
// public void remove(BaseRankInfo<K> rankInfo) {
// cache.remove(rankInfo.getId());
// rankMap.remove(rankInfo);
// }
//
// // 根据boolean 决定是否规避拷贝操作
// public void add(BaseRankInfo<K> rankInfo, boolean needOccupy) {
// if (needOccupy) {
// synchronized (occupy) {
// cache.put(rankInfo.getId(), rankInfo);
// rankMap.put(rankInfo, rankInfo.getId());
// }
// } else {
// cache.put(rankInfo.getId(), rankInfo);
// rankMap.put(rankInfo, rankInfo.getId());
// }
// }
//
// // 添加并且callback一份数据
// public List<BaseRankInfo> addCallback(BaseRankInfo<K> rankInfo) {
// synchronized (occupy) {
// cache.put(rankInfo.getId(), rankInfo);
// rankMap.put(rankInfo, rankInfo.getId());
// List<BaseRankInfo> copy = new ArrayList(rankMap.keySet());
// return copy;
// }
// }
//
// public int getRealSize() {
// return rankMap.size();
// }
// }
