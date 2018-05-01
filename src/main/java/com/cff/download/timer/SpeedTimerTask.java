package com.cff.download.timer;

import java.util.TimerTask;

import com.cff.baidupcs.util.SystemUtil;
import com.cff.download.SiteFileFetch;

public class SpeedTimerTask extends TimerTask{
	static long lastDown = 0L;
	SiteFileFetch siteFileFetch;
	public SpeedTimerTask(SiteFileFetch siteFileFetch) {
		this.siteFileFetch = siteFileFetch;
	}

	@Override
	public void run() {
		if(siteFileFetch.isOver()){
			try {
				this.cancel();
				System.gc();
			} catch (Throwable e) {
				e.printStackTrace();
				this.cancel();
			}
		}
		
		long downloaded = siteFileFetch.getDownloaded();
		double speed = ((double)(downloaded - lastDown) / 1024) /1000;
		lastDown = downloaded;
		double rate = (double)downloaded / siteFileFetch.getnFileLength() * 100;
		
		String speedDisplay = String.format("%.3f", speed) + "M/s";
		if(speed < 1)speedDisplay = String.format("%.1f", speed * 1000) + "k/s";
		SystemUtil.logClear( "下载线程：" + siteFileFetch.getCurThreadNum() + "/" + siteFileFetch.getTotalThreadNum()
							+ ", 已下载：" + String.format("%.1f", rate) + "%, "
							+ "当前速度：" + speedDisplay,"\r%36s");
	}

	public static void main(String args[]){
		long a = 123445L;
		float b = (float)a/1000;
		System.out.print(b);
	}
}