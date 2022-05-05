package com.github.zxbu.webdavteambition.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.zxbu.webdavteambition.client.BaiduDriverClient;
import com.github.zxbu.webdavteambition.model.DownloadRequest;
import com.github.zxbu.webdavteambition.model.FileType;
import com.github.zxbu.webdavteambition.model.PathInfo;
import com.github.zxbu.webdavteambition.model.baidu.Dlink;
import com.github.zxbu.webdavteambition.model.result.TFile;
import com.github.zxbu.webdavteambition.util.JsonUtil;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;
import com.github.zxbu.webdavteambition.model.baidu.*;

@Service
public class BaiduDriverClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaiduDriverClientService.class);

    private final BaiduDriverClient client;
    private static String rootPath = "/";
    private TFile rootTFile = null;
    private static int chunkSize = 10485760; // 10MB
    private final static String[] strHex = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static Cache<String, Set<TFile>> tFilesCache = Caffeine.newBuilder()
            .initialCapacity(128)
            .maximumSize(1024)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    @Autowired
    private VirtualTFileService virtualTFileService;

    public BaiduDriverClientService(BaiduDriverClient client) {
        this.client = client;
        BaiduDriverFileSystemStore.setBean(this);
    }

    public String getToken(){
        Map<String, String> params = new HashMap<>();
        params.put("code", "");
        params.put("client_id", "");
        params.put("client_secret", "");
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", "");
        return "";
    }

    public void setToken(String token){
        client.setToken(token);
    }
    //获取网盘容量信息
    public String getQuota (){
        Map<String,String> params = new HashMap<>();
        params.put("access_token","123.66c7f277a73f43be98b1223a97787c4a.YgAKAF3WoK8UyJGM9lPVFNvees7-Y9ACskr8q9D.FX-axw");
        params.put("checkfree","1");
        params.put("checkexpire","1");

        String json = client.get("/api/quota", params);
        System.out.print(json);
        return json;
    }

    // 获取文件列表
    public String getFileList(String path){
        if(!path.startsWith("/"))
        {
            path = "/"+path;
        }
        Map<String,String> params = new HashMap<>();
        params.put("access_token",client.getToken());
        params.put("method","list");
        //params.put("dir", encodeValue(path));
        params.put("dir", (path));
        params.put("order","name");
        //params.put("desc","name");
        params.put("start","0");
        params.put("web","0");
        params.put("limit","100");
        params.put("folder","0");
        params.put("showempty","0");

        String json = client.get("/rest/2.0/xpan/file", params);
        System.out.print(json);
        return json;
    }

    public void createFolder(String path) {

    }

    public Response download(String path, HttpServletRequest request, long size ) {
        TFile file = getTFileByPath(path);
        Map<String,String> params = new HashMap<>();
        params.put("access_token",client.getToken());
        params.put("method","filemetas");
        params.put("fsids","["+file.getId()+"]");
        params.put("dlink","1");

        String json = client.get("/rest/2.0/xpan/multimedia",params);
        MultiMediaResult<Dlink> downList =JsonUtil.readValue(json, new TypeReference<MultiMediaResult<Dlink>>() {});

        String url = downList.getList().get(0).getDlink();
        LOGGER.debug("{} url = {}", path, url);
        return client.download(url.toString(), request, size);
    }

    public TFile getTFileByPath(String path) {
        path = normalizingPath(path);

        return getNodeIdByPath2(path);
    }

    private TFile getNodeIdByPath2(String path) {
        if (!StringUtils.hasLength(path)) {
            path = rootPath;
        }
        if (path.equals(rootPath)) {
            return getRootTFile();
        }
        PathInfo pathInfo = getPathInfo(path);
        TFile tFile = getTFileByPath(pathInfo.getParentPath());
        if (tFile == null ) {
            return null;
        }
        return getNodeIdByParentId(pathInfo.getParentPath(), pathInfo.getName());
    }

    private TFile getNodeIdByParentId(String parentId, String name) {
        Set<TFile> tFiles = getTFiles(parentId);
        for (TFile tFile : tFiles) {
            if (tFile.getName().equals(name)) {
                return tFile;
            }
        }

        return null;
    }

    public PathInfo getPathInfo(String path) {
        path = normalizingPath(path);
        if (path.equals(rootPath)) {
            PathInfo pathInfo = new PathInfo();
            pathInfo.setPath(path);
            pathInfo.setName(path);
            return pathInfo;
        }
        int index = path.lastIndexOf("/");
        String parentPath = path.substring(0, index + 1);
        String name = path.substring(index+1);
        PathInfo pathInfo = new PathInfo();
        pathInfo.setPath(path);
        pathInfo.setParentPath(parentPath);
        pathInfo.setName(name);
        return pathInfo;
    }

    public void remove(String path) {

    }

    public void move(String sourcePath, String targetPath) {
    }

    public void rename(String sourcePath, String newName) {
    }

    public Set<TFile> getTFiles(String nodeId) {
        Set<TFile> tFiles = tFilesCache.get(nodeId, key -> {
            // 获取真实的文件列表
            return getTFiles2(nodeId);
        });
        Set<TFile> all = new LinkedHashSet<>(tFiles);
        // 获取上传中的文件列表
        Collection<TFile> virtualTFiles = virtualTFileService.list(nodeId);
        all.addAll(virtualTFiles);
        return all;
    }

    private Set<TFile> getTFiles2(String nodeId) {
        List<TFile> tFileList = fileListFromApi(nodeId, null, new ArrayList<>());
        tFileList.sort(Comparator.comparing(TFile::getLastOpTime).reversed());
        Set<TFile> tFileSets = new LinkedHashSet<>();
        for (TFile tFile : tFileList) {
            //临时补丁
            if (tFile.getType() == null) {
                if(tFile.getMd5() == null){
                    tFile.setType(FileType.folder.name());
                }else {
                    tFile.setType(FileType.file.name());
                }
            }
            if (!tFileSets.add(tFile)) {
                LOGGER.info("当前目录下{} 存在同名文件：{}，文件大小：{}", nodeId, tFile.getName(), tFile.getSize());
            }
        }
        // 对文件名进行去重，只保留最新的一个
        return tFileSets;
    }

    private List<TFile> fileListFromApi(String nodeId, String marker, List<TFile> all) {
        String json = getFileList(nodeId);
        MultiMediaResult<MultiMedia> multiMediaList = JsonUtil.readValue(json,new TypeReference<MultiMediaResult<MultiMedia>>(){});

        if (multiMediaList.getList() == null) {
            return all;
        }
        for(MultiMedia multiMedia:multiMediaList.getList()){
            TFile tFile = new TFile();
            tFile.setId(multiMedia.getFsId().toString());
            tFile.setName(multiMedia.getServerFilename());
            tFile.setSize(multiMedia.getSize().longValue());
            tFile.setMd5(multiMedia.getMd5());
            tFile.setCreateDate(multiMedia.getLocalCtime());
            tFile.setLastOpTime(multiMedia.getLocalMtime());
            //System.out.println(multiMedia.getLocalCtime().toString());
            if(multiMedia.getIsdir() == 0){
                tFile.setType("file");
            }else {
                tFile.setType("folder");
            }
            tFile.setType(multiMedia.getIsdir() == 0? "file":"folder");
            all.add(tFile);
        }
        return all;
    }


    /**
     * @Description  预上传
     * @param path
     * @param size
     * @param inputStream
     */
    public void uploadPre(String path, long size, InputStream inputStream) {
        path = normalizingPath(path);
        PathInfo pathInfo = getPathInfo(path);
        TFile parent = getTFileByPath(pathInfo.getParentPath());
        if (parent == null) {
            return;
        }
        // 如果已存在，先删除
        TFile tfile = getTFileByPath(path);
        if (tfile != null) {
            if (tfile.getSize() == size) {
                //如果文件大小一样，则不再上传
                return;
            }
            remove(path);
        }

        int chunkCount = (int) Math.ceil(((double) size) / chunkSize); // 进1法
        StringBuffer md5s = new StringBuffer();
        //byte[] buffer = new byte[chunkSize];
        byte[] buffer1 =new byte[chunkSize];
        List<byte[]> bufferList = new ArrayList<byte[]>();
        List<Integer> readList =new ArrayList<Integer>();
        for (int i = 0; i < chunkCount; i++) {
            byte[] buffer = new byte[chunkSize];
            try {
                int read = IOUtils.read(inputStream,buffer,0,buffer.length);
                if (read == -1) {
                    LOGGER.info("文件分片结束。");
                    continue;
                }
                //测试用
                bufferList.add(buffer);
                readList.add(read);
                String md5Str = getMD5(buffer);
                md5s.append(md5Str+"\",\"");
                LOGGER.info("文件分片【{}】的MD5值：{}",i,md5Str);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //md5数组参数
        md5s =new StringBuffer(md5s.substring(0,md5s.length()-3));

        //预上传
        String proJson = preCreate(path,size,0,md5s.toString());
        UploadPreInfo uploadPreInfo = JsonUtil.readValue(proJson, new TypeReference<UploadPreInfo>() {});
        for (Integer j : uploadPreInfo.getBlockList()){
            //分片上传
            upload(path,uploadPreInfo.getUploadid(),j,bufferList.get(j),0,readList.get(j));
        }

        //创建文件
        create(path,uploadPreInfo.getUploadid(),size,0,md5s.toString());

        }

    private String preCreate(String cloudPath, long size, Integer isDir, String blockList){
        Map<String,String> params = new HashMap<>();
        params.put("access_token",client.getToken());
        params.put("method","precreate");
        params.put("path","/apps/网盘测试"+cloudPath);
        UploadPreRequest uploadPreRequest = new UploadPreRequest();
        //由于百度网盘限制 只能上传到 /apps/应用名
        uploadPreRequest.setPath("/apps/网盘测试"+cloudPath);
        uploadPreRequest.setSize(size);
        uploadPreRequest.setIsdir(0);
        uploadPreRequest.setBlock_list("[\"" + blockList + "\"]");

        RequestBody requestBody =  new FormBody.Builder()
                .add("path", "/apps/路由网盘"+cloudPath)
                .add("size",String.valueOf(size))
                .add("isdir","0")
                .add("block_list","[\"" + blockList + "\"]")
                .add("autoinit","1")
                .build();;
        String json = client.post("/rest/2.0/xpan/file?method=precreate&access_token=" + client.getToken(),requestBody);

        return json;
    }

    private void upload(String cloudPath, String uploadid, Integer partseq, byte[] file, final int offset, final int byteCount){
        RequestBody requestBody =  new FormBody.Builder()
                .add("file", file.toString())
                .build();;
        String json = client.upload("https://d.pcs.baidu.com/rest/2.0/pcs/superfile2?method=upload&access_token=" + client.getToken()+"&type=tmpfile&path="+"/apps/路由网盘"+cloudPath+"&uploadid="+uploadid+"&partseq="+partseq, file ,offset,byteCount);

    }

    private void create(String cloudPath,String uploadid, Long size, Integer isDir,String blockList){
        RequestBody requestBody =  new FormBody.Builder()
                .add("path", "/apps/路由网盘"+cloudPath)
                .add("size",String.valueOf(size))
                .add("isdir","0")
                .add("block_list","[\"" + blockList + "\"]")
                .add("uploadid",uploadid)
                .build();;
        String json = client.post("/rest/2.0/xpan/file?method=create&access_token=" + client.getToken(),requestBody);

        //return json;
    }
    private TFile getRootTFile() {
        if (rootTFile == null) {
//            FileGetRequest fileGetRequest = new FileGetRequest();
//            fileGetRequest.setFile_id("root");
//            fileGetRequest.setDrive_id(client.getDriveId());
//            String json = client.post("/file/get", fileGetRequest);
//            rootTFile = JsonUtil.readValue(json, TFile.class);
            rootTFile = new TFile();
            rootTFile.setName("/");
            rootTFile.setId("/");
            rootTFile.setCreateDate(new Date());
            rootTFile.setLastOpTime(new Date());
            rootTFile.setType("folder");
        }
        return rootTFile;
    }

    /**
     * @Description:  获取md5值
     * byte[] bytes 文件分片bytes
     */
    private static String getMD5(byte[] bytes) {
        StringBuilder buffer = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(bytes);
            for (int value : b) {
                int d = value;
                if (d < 0) {
                    d += 256;
                }
                int d1 = d / 16;
                int d2 = d % 16;
                buffer.append(strHex[d1]).append(strHex[d2]);
            }
            return buffer.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private String normalizingPath(String path) {
        path = path.replaceAll("//", "/");
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
}
