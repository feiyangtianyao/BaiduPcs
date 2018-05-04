package com.cff.ui.file;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.cff.baidupcs.client.service.LsHttpService;
import com.cff.cache.CacheManager;
import com.cff.ui.util.ResourceUtil;

public class PcsFileSystem {
	static Map<String,ImageIcon> fileTypes = new ConcurrentHashMap<String,ImageIcon>();
	static{
		URL dirImage = ResourceUtil.getResource("ui/img/dir.jpg");
		URL fileImage = ResourceUtil.getResource("ui/img/file.jpg");
		fileTypes.put("D", new ImageIcon(dirImage));
		fileTypes.put("F", new ImageIcon(fileImage));
	}
	public static Icon getFileIcon(String fileType) {
		return fileTypes.get(fileType);
	}
	public static PcsFile[] getFiles(PcsFile theFile, boolean showHiden) {
		if(!theFile.isDirectory())return null;
		List<PcsFile> pcsFiles = null;
		if(CacheManager.get(theFile.getPath())!=null){
			pcsFiles = CacheManager.get(theFile.getPath());
		}else{
			LsHttpService lsHttpService = new LsHttpService();
			pcsFiles = lsHttpService.runLsUI(theFile.getPath());
			CacheManager.putPcsFileMap(theFile.getPath(), pcsFiles);
		}
		if(pcsFiles == null)return null;
		PcsFile[] files = new PcsFile[pcsFiles.size()];
		return pcsFiles.toArray(files);
	}
	
}
