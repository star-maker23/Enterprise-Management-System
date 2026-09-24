package com.ems.admin.config;


import com.ems.admin.EmsAdminApplication;
import com.ems.common.config.EmsConfig;
import com.ems.common.constant.Constants.UploadSubDir;
import java.io.File;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = EmsAdminApplication.class)
@RunWith(SpringRunner.class)
public class EmsConfigTest {

    @Resource
    private EmsConfig config;

    @Test
    public void testConfig() {
        String fileBaseDir = "D:\\ems\\profile";

        Assertions.assertEquals("企业管理系统", config.getName());
        Assertions.assertEquals("1.8.0", config.getVersion());
        Assertions.assertEquals("2022", config.getCopyrightYear());
        Assertions.assertFalse(config.isDemoEnabled());
        Assertions.assertEquals(fileBaseDir, EmsConfig.getFileBaseDir());
        Assertions.assertFalse(EmsConfig.isAddressEnabled());
        Assertions.assertEquals("math", EmsConfig.getCaptchaType());
        Assertions.assertEquals("math", EmsConfig.getCaptchaType());
        Assertions.assertEquals(fileBaseDir + "\\import",
            EmsConfig.getFileBaseDir() + File.separator + UploadSubDir.IMPORT_PATH);
        Assertions.assertEquals(fileBaseDir + "\\avatar",
            EmsConfig.getFileBaseDir() + File.separator + UploadSubDir.AVATAR_PATH);
        Assertions.assertEquals(fileBaseDir + "\\download",
            EmsConfig.getFileBaseDir() + File.separator + UploadSubDir.DOWNLOAD_PATH);
        Assertions.assertEquals(fileBaseDir + "\\upload",
            EmsConfig.getFileBaseDir() + File.separator + UploadSubDir.UPLOAD_PATH);
    }

}
