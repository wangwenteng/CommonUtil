package com.wwt.commonutil.test;


import java.util.List;
import java.util.concurrent.TimeUnit;

public class SyncTask02 implements Runnable{
    private List<Integer> from;
    private List<Integer> to;
    private Integer target;
    public SyncTask02(List<Integer> from, List<Integer> to, Integer target) {
        this.from = from;
        this.to = to;
        this.target = target;
    }
    @Override
    public void run() {
        moveListItem(from,to,target);
    }
    private static void moveListItem(List<Integer> from, List<Integer> to,Integer item){
        log("attempting lock for list",from);
        synchronized (from){
            log("lock acquired for list",from);
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){

            }
            log("attempting lock for list",to);
            synchronized (to){
                log("lock acquired for list",to);
                if(from.remove(item)){
                    to.add(item);
                }
                log("move item to list",to);
            }
        }
    }
    private static void log(String msg,Object target){
        System.out.println(Thread.currentThread().getName()+":"+msg+" "+System.identityHashCode(target));
    }
}
