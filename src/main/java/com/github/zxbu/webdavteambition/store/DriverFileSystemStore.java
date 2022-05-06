package com.github.zxbu.webdavteambition.store;

import net.sf.webdav.ITransaction;
import net.sf.webdav.IWebdavStore;
import net.sf.webdav.StoredObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.security.Principal;

public class DriverFileSystemStore implements IWebdavStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverFileSystemStore.class);

    private static DriverClientService driverClientService;

    @Override
    public void destroy() {

    }

    @Override
    public ITransaction begin(Principal principal, HttpServletRequest req, HttpServletResponse resp) {
        return null;
    }

    @Override
    public void checkAuthentication(ITransaction transaction) {

    }

    @Override
    public void commit(ITransaction transaction) {

    }

    @Override
    public void rollback(ITransaction transaction) {

    }

    @Override
    public void createFolder(ITransaction transaction, String folderUri) {

    }

    @Override
    public void createResource(ITransaction transaction, String resourceUri) {

    }

    @Override
    public InputStream getResourceContent(ITransaction transaction, String resourceUri) {
        return null;
    }

    @Override
    public long setResourceContent(ITransaction transaction, String resourceUri, InputStream content, String contentType, String characterEncoding) {
        return 0;
    }

    @Override
    public String[] getChildrenNames(ITransaction transaction, String folderUri) {
        return new String[0];
    }

    @Override
    public long getResourceLength(ITransaction transaction, String path) {
        return 0;
    }

    @Override
    public void removeObject(ITransaction transaction, String uri) {

    }

    @Override
    public boolean moveObject(ITransaction transaction, String destinationPath, String sourcePath) {
        return false;
    }

    @Override
    public StoredObject getStoredObject(ITransaction transaction, String uri) {
        return null;
    }
}
